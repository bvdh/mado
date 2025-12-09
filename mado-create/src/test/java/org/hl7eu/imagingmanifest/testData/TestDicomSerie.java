package org.hl7eu.imagingmanifest.testData;

import lombok.Setter;
import org.hl7eu.imagingmanifest.model.*;

import java.util.Date;
import java.util.Optional;


public class TestDicomSerie implements MadoSerie {
  @Setter private String modality;
  @Setter private String seed;
  @Setter private String seriesInstanceUID;
  @Setter private String description;
  @Setter private String seriesNumber;
  @Setter private Date seriesDateTime;

  @Override
  public Optional<String> getModality() {
    return Optional.ofNullable( modality );
  }

  @Override
  public Optional<String> getSeriesInstanceUID() {
    return Optional.ofNullable( seriesInstanceUID );
  }

  @Override
  public Optional<String> getSeriesDescription() {
    return Optional.ofNullable( description );
  }

  @Override
  public Optional<String> getBodyPartExamined() {
    return Optional.empty();
  }

  @Override
  public Optional<String> getLaterality() {
    return Optional.empty();
  }

  @Override
  public Optional<String> getSeriesNumber() {
    return Optional.ofNullable( seriesNumber );
  }

  @Override
  public Optional<Date> getSeriesDateTime() {
    return Optional.ofNullable( seriesDateTime );
  }
}
