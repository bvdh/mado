package org.hl7eu.imagingmanifest.fhir;

import org.hl7.fhir.r4.model.ImagingStudy;
import org.hl7eu.imagingmanifest.model.MadoSerie;
import org.hl7eu.imagingmanifest.model.MadoStudy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MadoStudyFromFhir implements MadoStudy {

  private final FhirManifestImagingStudy imagingStudy;

  MadoStudyFromFhir(FhirManifest fhirManifest ){
    this.imagingStudy = fhirManifest.getImagingStudy();
  }

  @Override
  public Optional<String> getStudyInstanceUID() {
    return imagingStudy.getIdentifier().stream()
        .map(FhirStudyInstanceIdIdentifier::getStudyInstanceUid)
        .filter(Optional::isPresent)
        .map( Optional::get )
        .findFirst();
  }

  @Override
  public Optional<String> getStudyId() {
    return imagingStudy.getIdentifier().stream()
        .map(FhirStudyIdIdentifier::getStudyId)
        .filter(Optional::isPresent)
        .map( Optional::get )
        .findFirst();
  }

  @Override
  public Optional<Date> getStudyDateTime() {
    return Optional.ofNullable( imagingStudy.getStarted() );
  }

  @Override
  public Optional<String> getStudyDescription() {
    return Optional.ofNullable( imagingStudy.getDescription() );
  }

  @Override
  public List<MadoSerie> getSeries() {
    if ( this.imagingStudy.hasSeries() ){
      List<MadoSerie> list = new ArrayList<>();
      for (ImagingStudy.ImagingStudySeriesComponent imagingStudySeriesComponent : this.imagingStudy.getSeries()) {
        MadoSerieFromFhir madoSerieFromFhir = new MadoSerieFromFhir(imagingStudySeriesComponent);
        list.add(madoSerieFromFhir);
      }
      return list;
    }
    return List.of();
  }


}
