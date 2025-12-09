package org.hl7eu.imagingmanifest.fhir;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.ImagingStudy;
import org.hl7.fhir.r4.model.Resource;
import org.hl7eu.imagingmanifest.model.MadoStudy;
import org.hl7eu.imagingmanifest.model.MadoConfiguration;
import org.hl7eu.imagingmanifest.model.MadoManifest;

import java.util.ArrayList;
import java.util.List;

public class FhirManifest implements MadoManifest {
  private Bundle bundle = new Bundle();
  private FhirManifestImagingStudy imagingStudy;

  public FhirManifest(MadoManifest manifest) {
    this.imagingStudy = new FhirManifestImagingStudy( this, manifest );
    List<Bundle.BundleEntryComponent> existingEntries = this.bundle.getEntry();
    this.bundle.setEntry( new ArrayList<>() );
    this.addResource( this.imagingStudy );
    this.bundle.getEntry().addAll( existingEntries );
  }

  public FhirManifest(Bundle fhirBundle) {
    this.bundle = fhirBundle!=null ? fhirBundle : new Bundle();

    // imaging study is first imaging study in the bundle
    this.imagingStudy = bundle.hasEntry()? bundle.getEntry().stream()
        .filter( entry -> entry.hasResource() && entry.getResource() instanceof ImagingStudy )
        .map( entry -> (ImagingStudy) entry.getResource() )
        .map(FhirManifestImagingStudy::new)
        .findFirst()
        .orElse( new FhirManifestImagingStudy() )
        : new FhirManifestImagingStudy();
  }

  public Bundle getFhirBundle() {
    return this.bundle.copy();
  }

  // MADOManifest methods
  @Override
  public MadoConfiguration getGetConfiguration() {
    return new MadoConfigurationFromFhir( this, this.imagingStudy );
  }

  @Override
  public MadoStudy getMadoStudy() {
    return new MadoStudyFromFhir( this );
  }

  ///Utol
  Resource getResourceFromBundle( String reference ) {
    if ( this.bundle != null && this.bundle.hasEntry() ) {
      for ( Bundle.BundleEntryComponent entry : bundle.getEntry() ) {
        if ( entry.hasFullUrl() && entry.getFullUrl().equals(reference) ) {
          return (Resource) entry.getResource();
        }
      }
    }
    return null;
  }
  void addResource( Resource resource ){
    this.bundle.addEntry( new Bundle.BundleEntryComponent()
        .setResource( resource )
        .setFullUrl( FhirUtil.getReference( resource ).getReference() )
    );
  }

  FhirManifestImagingStudy getImagingStudy(){
    return imagingStudy;
  }

}


