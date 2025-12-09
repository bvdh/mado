package org.hl7eu.imagingmanifest.old.fhir;

import lombok.Getter;
import org.hl7.fhir.r4.model.*;
import org.hl7eu.imagingmanifest.old.model.*;

import java.util.Optional;

public class FhirManifest implements ManifestInterface {

  @Getter
  private Bundle bundle;
  @Getter
  private Patient patient;
  @Getter
  private ImagingStudy imagingStudy;
  @Getter
  private ServiceRequest requestedProcedure;

  private FhirManifestAuthor manifestAuthor;

  public FhirManifest( Bundle bundle ) {
    this.bundle = bundle;
    if ( bundle != null && bundle.hasEntry() ) {
      bundle.getEntry().forEach( entry -> {
        if ( entry.hasResource() && entry.getResource() instanceof ImagingStudy) {
          this.imagingStudy = (ImagingStudy) entry.getResource();
          this.patient = imagingStudy.hasSubject() && imagingStudy.getSubject().hasReference()
            ? (Patient)getResourceFromBundle( bundle, imagingStudy.getSubject().getReference() )
            : new Patient();
        }
        if ( entry.hasResource() && entry.getResource() instanceof Provenance) {
          this.manifestAuthor = (Provenance) entry.getResource();
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
  public ManifestInterface setManifestAuthor(FhirManifestAuthor manifestAuthor) {
    ModelUtil.copyManifestAuthor( manifestAuthor, getManifestAuthor() );
    return this;
  }

  @Override
  public FhirManifestAuthor getManifestAuthor() {
    return new FhirManifestAuthor( this );
  }





  public ImagingStudy ensureImagingStudy() {
    if ( this.imagingStudy == null ) {
      this.imagingStudy = new ImagingStudy();
    }
    return this.imagingStudy;
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


  @Override
  public ManifestInterface setManifestAuthor(GeneralEquipmentModuleInterface generalEquipmentModule) {
    return null;
  }


}
