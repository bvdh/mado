package org.hl7eu.imagingmanifest.fhir;

import org.hl7.fhir.r4.model.Coding;
import org.hl7eu.imagingmanifest.model.CodeSequenceInterface;

import java.util.Optional;

public class FhirCodeSequence implements CodeSequenceInterface {
  private final Coding coding;

  FhirCodeSequence( Coding coding ) {
    this.coding = coding;
  }

  @Override
  public Optional<String> getCodeValue() {
    return Optional.ofNullable( this.coding ).map( Coding::getCode );
  }

  @Override
  public CodeSequenceInterface setCodeValue(String codeValue) {
    this.coding.setCode( codeValue );
    return this;
  }

  @Override
  public Optional<String> getCodingSchemeDesignator() {
    return Optional.ofNullable( this.coding ).map( Coding::getSystem );
  }

  @Override
  public CodeSequenceInterface setCodingSchemeDesignator(String codingSchemeDesignator) {
    this.coding.setSystem( codingSchemeDesignator );
    return this;
  }

  @Override
  public Optional<String> getCodeMeaning() {
    return Optional.ofNullable( this.coding ).map( Coding::getDisplay );
  }

  @Override
  public CodeSequenceInterface setCodeMeaning(String codeMeaning) {
    this.coding.setDisplay( codeMeaning );
    return this;
  }

  @Override
  public Optional<String> getCodingSchemeVersion() {
    return Optional.empty();
  }

  @Override
  public CodeSequenceInterface setCodingSchemeVersion(String codingSchemeVersion) {
    return null;
  }
}
