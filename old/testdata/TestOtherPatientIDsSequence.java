package org.hl7eu.imagingmanifest.old.testdata;

import org.hl7eu.imagingmanifest.old.model.IssuerOfPatientIdInterface;
import org.hl7eu.imagingmanifest.old.model.OtherPatientIDsSequenceInterface;

import java.util.Optional;

public class TestOtherPatientIDsSequence implements OtherPatientIDsSequenceInterface {
  private String patientId = "otherPatientId";
  private String issuerOfPatientID = "OtherTestIssuer";
  private String typeOfPatientId = "TestTypeOfPatientID";
  private IssuerOfPatientIdInterface issuerOfPatientIDQualifier;

  @Override
  public Optional<String> getPatientID() {
    return Optional.ofNullable(patientId);
  }

  @Override
  public OtherPatientIDsSequenceInterface setPatientID(String patientID) {
    this.patientId = patientID;
    return this;
  }

  @Override
  public Optional<String> getIssuerOfPatientID() {
    return Optional.ofNullable(issuerOfPatientID);
  }

  @Override
  public OtherPatientIDsSequenceInterface setIssuerOfPatientID(String issuerOfPatientId) {
    issuerOfPatientID = issuerOfPatientId;
    return this;
  }

  @Override
  public Optional<String> getTypeOfPatientID() {
    return Optional.ofNullable( typeOfPatientId );
  }

  @Override
  public OtherPatientIDsSequenceInterface setTypeOfPatientID(String typeOfPatientID) {
    this.typeOfPatientId = typeOfPatientID;
    return this;
  }

  @Override
  public Optional<IssuerOfPatientIdInterface> getIssuerOfPatientIDQualifier() {
    return Optional.ofNullable( issuerOfPatientIDQualifier );
  }

  @Override
  public OtherPatientIDsSequenceInterface setIssuerOfPatientIDQualifier(IssuerOfPatientIdInterface issuerOfPatientIdQualifier) {
    this.issuerOfPatientIDQualifier = issuerOfPatientIdQualifier;
    return this;
  }
}
