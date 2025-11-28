package org.hl7eu.imagingmanifest.testdata;

import org.hl7eu.imagingmanifest.model.IssuerOfPatientIdInterface;

import java.util.Optional;

class TestIssuerOfPatientId implements IssuerOfPatientIdInterface {

  private String universalEntityID = "TestUniversalEntityID";
  private String universalEntityIDType = "ISO";
  private String identifierTypeCode = "TEXT";

  @Override
  public Optional<String> getUniversalEntityID() {
    return Optional.of(universalEntityID);
  }

  @Override
  public IssuerOfPatientIdInterface setUniversalEntityID(String universalEntityID) {
    this.universalEntityID = universalEntityID;
    return this;
  }

  @Override
  public Optional<String> getUniversalEntityIDType() {
    return Optional.of(universalEntityIDType);
  }

  @Override
  public IssuerOfPatientIdInterface setUniversalEntityIDType(String universalEntityIDType) {
    this.universalEntityIDType = universalEntityIDType;
    return this;
  }

//  @Override
//  public Optional<String> getIdentifierTypeCode() {
//    return Optional.of(identifierTypeCode);
//  }
//
//  @Override
//  public IssuerOfPatientIdInterface setIdentifierTypeCode(String identifierTypeCode) {
//    this.identifierTypeCode = identifierTypeCode;
//    return this;
//  }
}
