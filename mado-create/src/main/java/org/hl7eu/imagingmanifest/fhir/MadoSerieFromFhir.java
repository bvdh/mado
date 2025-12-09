package org.hl7eu.imagingmanifest.fhir;

import org.hl7.fhir.r4.model.ImagingStudy;
import org.hl7eu.imagingmanifest.model.MadoSerie;

import java.util.Date;
import java.util.Optional;

public class MadoSerieFromFhir implements MadoSerie {
  private final ImagingStudy.ImagingStudySeriesComponent serie;

  public MadoSerieFromFhir(ImagingStudy.ImagingStudySeriesComponent serie) {
    this.serie = serie;
  }

  @Override
  public Optional<String> getModality() {
    return Optional.ofNullable( serie.hasModality()?serie.getModality().getCode() : null );
  }

  @Override
  public Optional<String> getSeriesInstanceUID() {
    return Optional.ofNullable( serie.getUid() );
  }

  @Override
  public Optional<String> getSeriesDescription() {
    return Optional.ofNullable( serie.getDescription() );
  }

  @Override
  public Optional<String> getBodyPartExamined() {
    return Optional.empty();
  }

  @Override
  public Optional<String> getLaterality() {
    return Optional.ofNullable( serie.hasLaterality()?serie.getLaterality().getCode() : null );
  }

  @Override
  public Optional<String> getSeriesNumber() {
    return Optional.ofNullable( serie.hasNumber() ? serie.getNumber()+"" : null );
  }

  @Override
  public Optional<Date> getSeriesDateTime() {
    return Optional.ofNullable(serie.getStarted());
  }
}
