package org.hl7eu.imagingmanifest.testData;

import org.hl7eu.imagingmanifest.model.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class TestDicomStudy implements MadoStudy {
      @Override
  public Optional<String> getStudyInstanceUID() {
    return Optional.of("study-instance-uid");
  }

  @Override
  public Optional<String> getStudyId() {
    return Optional.of("study-id");
  }

  @Override
  public Optional<Date> getStudyDateTime() {
    return Optional.of( new Date(System.currentTimeMillis()- 4L *31*24*60*60*1000));
  }

  @Override
  public Optional<String> getStudyDescription() {
    return Optional.of("study-description");
  }

  @Override
  public List<MadoSerie> getSeries() {
    TestDicomSerie serie1 = new TestDicomSerie();
    serie1.setSeriesNumber( "1" );
    serie1.setModality("MR");
    serie1.setDescription("serie1-description");
    serie1.setSeriesDateTime(new Date( System.currentTimeMillis()-20*24*60*60*1000));

    TestDicomSerie serie2 = new TestDicomSerie();
    serie2.setSeriesNumber( "2" );
    serie2.setModality("US");
    serie2.setDescription("serie2-description");
    serie2.setSeriesDateTime(new Date( System.currentTimeMillis()-19*24*60*60*1000));

    return List.of( serie1, serie2 );
  }

//  @Override
  public Optional<String> getAccessionNumber() {
    return Optional.of( "accession-number" );
  }
}
