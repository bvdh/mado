package org.hl7eu.imagingmanifest.fhir;

import org.hl7.fhir.r4.model.*;
import org.hl7eu.imagingmanifest.model.MadoStudy;
import org.hl7eu.imagingmanifest.model.MadoConfiguration;
import org.hl7eu.imagingmanifest.model.MadoManifest;

import java.util.Optional;
import java.util.UUID;

public class FhirManifestImagingStudy extends ImagingStudy {
  public FhirManifestImagingStudy(ImagingStudy imagingStudy ) {
    imagingStudy.copyValues( this );
  }

  public FhirManifestImagingStudy( FhirManifest fhirManifest, MadoManifest manifest) {
    MadoStudy dicomStudy = manifest.getMadoStudy();
    MadoConfiguration madoConfiguration = manifest.getGetConfiguration();

    this.setId( UUID.randomUUID().toString() );
    this.setStatus( ImagingStudy.ImagingStudyStatus.AVAILABLE );

    dicomStudy.getStudyInstanceUID().ifPresent(idValue -> {
      this.addIdentifier( new FhirStudyInstanceIdIdentifier( idValue ));
    });
    dicomStudy.getStudyId().ifPresent(idValue -> {
      this.addIdentifier( new FhirStudyIdIdentifier( idValue ));
    });

    dicomStudy.getStudyDateTime().ifPresent( this::setStarted);

    dicomStudy.getStudyDescription().ifPresent( this::setDescription );

    // endpoints
    if ( !this.hasEndpoint() ) {
      this.setEndpoint( new java.util.ArrayList<>() );
    }
    manifest.getGetConfiguration().getWadoURL().ifPresent( url -> {
      Endpoint wadoEndpoint = new FhirWadoEndpoint( url );
      this.endpoint.add( FhirUtil.getReference( wadoEndpoint ) );
      fhirManifest.addResource( wadoEndpoint );
    });
    manifest.getGetConfiguration().getWebViewerURL().ifPresent( url -> {
      Endpoint webViewerEndpoint = new FhirWebUrlEndpoint( url );
      this.endpoint.add( FhirUtil.getReference( webViewerEndpoint ) );
      fhirManifest.addResource( webViewerEndpoint );
    });

    // series
    manifest.getMadoStudy().getSeries().forEach(series -> {
      ImagingStudy.ImagingStudySeriesComponent fhirSeries = addSeries();
      series.getSeriesInstanceUID().ifPresent(fhirSeries::setId );
      series.getSeriesDescription().ifPresent(fhirSeries::setDescription);


//      series.getSeriesDescription().ifPresent( numberStr -> if (  .fhirSeries::setNumber );
    });
  }

  public FhirManifestImagingStudy() {
  }
}
