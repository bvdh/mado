package org.hl7eu.imagingmanifest.fhir;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.ImagingStudy;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Resource;
import org.hl7eu.imagingmanifest.model.ManifestInterface;
import org.hl7eu.imagingmanifest.model.ModelUtil;
import org.hl7eu.imagingmanifest.model.PatientModuleInterface;

public class FhirManifest implements ManifestInterface {

  private Bundle bundle;
  private Patient patient;
  private ImagingStudy imagingStudy;

  public FhirManifest(Bundle bundle ) {
    this.bundle = bundle;
    if ( bundle != null && bundle.hasEntry() ) {
      bundle.getEntry().forEach( entry -> {
        if ( entry.hasResource() && entry.getResource() instanceof ImagingStudy) {
          this.imagingStudy = (ImagingStudy) entry.getResource();
          this.patient = imagingStudy.hasSubject() && imagingStudy.getSubject().hasReference()
            ? (Patient)getResourceFromBundle( bundle, imagingStudy.getSubject().getReference() )
            : new Patient();
        }
      });
    }
  }

  public FhirManifest( ManifestInterface testManistModel) {
    setPatientModule( testManistModel.getPatientModule() );
  }

  private Resource getResourceFromBundle( Bundle bundle, String reference ) {
    if ( bundle != null && bundle.hasEntry() ) {
      for ( Bundle.BundleEntryComponent entry : bundle.getEntry() ) {
        if ( entry.hasFullUrl() && entry.getFullUrl().equals(reference) ) {
          return (Resource) entry.getResource();
        }
      }
    }
    return null;
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
}
