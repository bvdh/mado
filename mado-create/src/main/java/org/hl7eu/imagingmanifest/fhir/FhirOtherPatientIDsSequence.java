package org.hl7eu.imagingmanifest.fhir;

import org.hl7.fhir.r4.model.Identifier;
import org.hl7eu.imagingmanifest.model.IssuerOfPatientIdInterface;
import org.hl7eu.imagingmanifest.model.OtherPatientIDsSequenceInterface;

import java.util.Optional;

public class FhirOtherPatientIDsSequence implements OtherPatientIDsSequenceInterface {
  private final Identifier identifier;

  FhirOtherPatientIDsSequence (Identifier identifier ){
    this.identifier = identifier;
  }
  @Override
  public Optional<String> getPatientID() {
    return Optional.ofNullable( identifier.getValue() );
  }

  @Override
  public OtherPatientIDsSequenceInterface setPatientID(String patientID) {
    identifier.setValue( patientID );
    return this;
  }

  @Override
  public Optional<String> getIssuerOfPatientID() {
    if ( identifier.getSystem() == null ) {
      return Optional.empty();
    }
    return Optional.of( identifier.getSystem().replace("urn:oid:", "") );
  }

  @Override
  public OtherPatientIDsSequenceInterface setIssuerOfPatientID(String issuerOfPatientId) {
    identifier.setSystem( issuerOfPatientId );
    return this;
  }

  @Override
  public Optional<String> getTypeOfPatientID() {
    return Optional.empty();
  }

  @Override
  public OtherPatientIDsSequenceInterface setTypeOfPatientID(String typeOfPatientID) {
    return this;
  }

  @Override
  public Optional<IssuerOfPatientIdInterface> getIssuerOfPatientIDQualifier() {
    return Optional.empty();
  }

  @Override
  public OtherPatientIDsSequenceInterface setIssuerOfPatientIDQualifier(IssuerOfPatientIdInterface issuerOfPatientIdQualifier) {
    return this;
  }
}
