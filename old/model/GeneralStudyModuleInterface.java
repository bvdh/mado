package org.hl7eu.imagingmanifest.old.model;

import java.util.Date;
import java.util.Optional;

public interface GeneralStudyModuleInterface {
  public Optional<String> getStudyInstanceUID();
  public GeneralStudyModuleInterface setStudyInstanceUID( String studyInstanceUID );

  public Optional<Date> getStudyDate();
  public GeneralStudyModuleInterface setStudyDate( Date studyDate );

  public Optional<Date> getStudyTime();
  public GeneralStudyModuleInterface setStudyTime( Date studyTime );

  public Optional<String> getAccessionNumber();
  public GeneralStudyModuleInterface setAccessionNumber( String accessionNumber );

  public Optional<HierarchicDesignatorInterface> getIssuerOfAccessionNumber();
  public GeneralStudyModuleInterface setIssuerOfAccessionNumber( HierarchicDesignatorInterface issuerOfAccessionNumber );
}
