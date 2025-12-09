package org.hl7eu.imagingmanifest.old.model;

import java.util.Optional;

public interface CodeSequenceInterface {

  public Optional<String> getCodeValue();
  public CodeSequenceInterface setCodeValue( String codeValue );

  public Optional<String> getCodingSchemeDesignator();
  public CodeSequenceInterface setCodingSchemeDesignator( String codingSchemeDesignator );

  public Optional<String> getCodeMeaning();
  public CodeSequenceInterface setCodeMeaning( String codeMeaning );

  public Optional<String> getCodingSchemeVersion();
  public CodeSequenceInterface setCodingSchemeVersion(String  codingSchemeVersion );


}
