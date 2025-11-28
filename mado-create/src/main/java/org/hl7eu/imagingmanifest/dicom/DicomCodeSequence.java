package org.hl7eu.imagingmanifest.dicom;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.hl7eu.imagingmanifest.model.CodeSequenceInterface;

import java.util.Optional;

public class DicomCodeSequence implements CodeSequenceInterface {
  private final Attributes attributes;

  public DicomCodeSequence(Attributes attributes) { this.attributes = attributes; }
  @Override
  public Optional<String> getCodeValue() {
    return Optional.ofNullable( attributes.getString( Tag.CodeValue ) );
  }

  @Override
  public CodeSequenceInterface setCodeValue(String codeValue) {
    attributes.setString( Tag.CodeValue,  VR.SH, codeValue );
    return this;
  }

  @Override
  public Optional<String> getCodingSchemeDesignator() {
    return Optional.ofNullable( attributes.getString( Tag.CodingSchemeDesignator ) );
  }

  @Override
  public CodeSequenceInterface setCodingSchemeDesignator(String codingSchemeDesignator) {
    attributes.setString( Tag.CodingSchemeDesignator,  VR.SH, codingSchemeDesignator );
    return this;
  }

  @Override
  public Optional<String> getCodeMeaning() {
    return Optional.ofNullable( attributes.getString( Tag.CodeMeaning ) );
  }

  @Override
  public CodeSequenceInterface setCodeMeaning(String codeMeaning) {
    attributes.setString( Tag.CodeMeaning,  VR.LO, codeMeaning );
    return this;
  }

  @Override
  public Optional<String> getCodingSchemeVersion() {
    return Optional.ofNullable( attributes.getString( Tag.CodingSchemeVersion ) );
  }

  @Override
  public CodeSequenceInterface setCodingSchemeVersion(String codingSchemeVersion) {
    attributes.setString( Tag.CodingSchemeVersion,  VR.SH, codingSchemeVersion );
    return this;
  }
}
