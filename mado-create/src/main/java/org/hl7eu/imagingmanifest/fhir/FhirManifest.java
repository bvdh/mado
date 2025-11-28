package org.hl7eu.imagingmanifest.fhir;

import org.hl7.fhir.r4.model.*;
import org.hl7eu.imagingmanifest.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.jena.vocabulary.SchemaDO.device;

public class FhirManifest implements ManifestInterface {

  private Bundle bundle;
  private Patient patient;
  private ImagingStudy imagingStudy;
  private ServiceRequest requestedProcedure;
  private Device mainGeneralEquipmentDevice; // the common one to be used in KOS, a study may have multiple devices
  private List<Device> generalEquipmentDevices = new ArrayList<>();
  private Provenance manifestAuthor;

  public FhirManifest( Bundle bundle ) {
    this.bundle = bundle;
    if ( bundle != null && bundle.hasEntry() ) {
      bundle.getEntry().forEach( entry -> {
        if ( entry.hasResource() && entry.getResource() instanceof ImagingStudy) {
          this.imagingStudy = (ImagingStudy) entry.getResource();
          this.patient = imagingStudy.hasSubject() && imagingStudy.getSubject().hasReference()
            ? (Patient)getResourceFromBundle( bundle, imagingStudy.getSubject().getReference() )
            : new Patient();

          for ( ImagingStudy.ImagingStudySeriesComponent series: imagingStudy.getSeries() ) {
            if ( series.hasPerformer() ) {
              for ( var performer : series.getPerformer() ) {
                if ( performer.hasActor() && performer.getActor().hasReference() ) {
                  Resource actor = getResourceFromBundle( bundle, performer.getActor().getReference() );
                  if ( performer.hasFunction() ){
                    if ( performer.getFunction().hasCoding(  "http://terminology.hl7.org/CodeSystem/v3-ParticipationType", "DEV" ) &&
                        actor instanceof Device
                    ) {
                      generalEquipmentDevices.add((Device) actor);
                      if (mainGeneralEquipmentDevice == null) {
                        mainGeneralEquipmentDevice = (Device) actor;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      });
    }
  }

  public FhirManifest( ManifestInterface source ) {
    setPatientModule( source.getPatientModule() );
    setGeneralStudyModule( source.getGeneralStudyModule() );

  }

  Resource getResourceFromBundle( Bundle bundle, String reference ) {
    if ( bundle != null && bundle.hasEntry() ) {
      for ( Bundle.BundleEntryComponent entry : bundle.getEntry() ) {
        if ( entry.hasFullUrl() && entry.getFullUrl().equals(reference) ) {
          return (Resource) entry.getResource();
        }
      }
    }
    return null;
  }
  public Optional<Resource> getResourceFromBundle(Reference who) {
    if ( who == null || !who.hasReference() ) {
      return Optional.empty();
    }
    return Optional.ofNullable( getResourceFromBundle( bundle, who.getReference() ) );
  }
  @Override
  public PatientModuleInterface getPatientModule() {
    return new FhirPatientModule( patient );
  }

  @Override
  public ManifestInterface setPatientModule(PatientModuleInterface otherPatientModule) {
    if ( this.patient == null ) {
      this.patient = new Patient();
    }
    FhirPatientModule myPatienModule = new FhirPatientModule( patient );
    ModelUtil.copyPatientModuleData( otherPatientModule, myPatienModule );
    return this;
  }

  @Override
  public GeneralStudyModuleInterface getGeneralStudyModule() {
    return new FhirGeneralStudyModule(this);
  }

  @Override
  public ManifestInterface setGeneralStudyModule(GeneralStudyModuleInterface generalStudyModule) {
    FhirGeneralStudyModule fhirGeneralStudyModule = new FhirGeneralStudyModule( this );
    ModelUtil.copyGeneralStudyModuleData( generalStudyModule, fhirGeneralStudyModule );
    return this;
  }

  @Override
  public GeneralEquipmentModuleInterface getGeneralEquipmentModule() {
    return new FhirGeneralEquipmentModule( this );
  }
  @Override
  public ManifestInterface setGeneralEquipmentModule(GeneralEquipmentModuleInterface generalEquipmentModule) {
    ModelUtil.copyGeneralEquipmentModule( generalEquipmentModule, getGeneralEquipmentModule() );
    return this;
  }

  public Optional<ImagingStudy> getImagingStudy() {
    return  Optional.ofNullable( imagingStudy );
  }

  public ImagingStudy ensureImagingStudy() {
    if ( this.imagingStudy == null ) {
      this.imagingStudy = new ImagingStudy();
    }
    return this.imagingStudy;
  }

  public Optional<ServiceRequest> getRequestedProcedure() {
    return Optional.ofNullable(this.requestedProcedure);
  }

  public ServiceRequest ensureRequestedProcedure() {
    if ( this.requestedProcedure == null ) {
      this.requestedProcedure = (ServiceRequest) new ServiceRequest()
          .setId("requested-procedure")
      ;
      imagingStudy.addBasedOn( new Reference().setReference("ServiceRequest/" + this.requestedProcedure.getId()) );
    }
    return this.requestedProcedure;
  }

  public Optional<Device> getMainGeneralEquipmentDevice() {
    return Optional.ofNullable( this.mainGeneralEquipmentDevice);
  }
  Device ensureGeneralEquipmentDevice() {
    if ( this.mainGeneralEquipmentDevice == null ) {
      this.mainGeneralEquipmentDevice = (Device) new Device()
          .setId("general-equipment-device")
      ;
      this.generalEquipmentDevices.add( this.mainGeneralEquipmentDevice );
      for( ImagingStudy.ImagingStudySeriesComponent series: imagingStudy.getSeries() ) {
        if ( !getGeneralEquipmentDevice( series ).isPresent() ) {
          series.addPerformer( new ImagingStudy.ImagingStudySeriesPerformerComponent()
              .setFunction( new CodeableConcept().addCoding( new Coding()
                  .setSystem( "http://terminology.hl7.org/CodeSystem/v3-ParticipationType" )
                  .setCode( "DEV" )
              ) )
              .setActor( new Reference().setReference( "Device/" + this.mainGeneralEquipmentDevice.getId() ) )
          );
        }
      }

    }
    return this.mainGeneralEquipmentDevice;
  }

  Optional<Device> getGeneralEquipmentDevice( ImagingStudy.ImagingStudySeriesComponent series ) {
    if ( series.hasPerformer() ) {
      for ( var performer : series.getPerformer() ) {
        if ( performer.hasFunction() &&
            performer.getFunction().hasCoding(  "http://terminology.hl7.org/CodeSystem/v3-ParticipationType", "DEV" )
        ) {
          return Optional.ofNullable(
              (Device) getResourceFromBundle( bundle, performer.getActor().getReference() )
          );
        }
      }
    }
    return Optional.empty();
  }

  public Optional<Provenance> getManifestAuthor() {
    return Optional.ofNullable(this.manifestAuthor);
  }


}
