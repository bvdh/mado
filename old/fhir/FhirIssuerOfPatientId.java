package org.hl7eu.imagingmanifest.old.fhir;

import org.hl7.fhir.r4.model.Identifier;
import org.hl7eu.imagingmanifest.old.model.IssuerOfPatientIdInterface;

import java.util.Optional;

public class FhirIssuerOfPatientId implements IssuerOfPatientIdInterface {
  private String universalEntityId;
  private String universalEntityIDType = "ISO";

  public FhirIssuerOfPatientId(Identifier identifier) {
    if ( identifier.hasSystem() ) {
      this.universalEntityId = "urn:oid:" + identifier.getSystem();
    }
  }

  @Override
  public Optional<String> getUniversalEntityID() {
    if ( universalEntityId != null && universalEntityId.startsWith("urn:oid:") ) {
      return Optional.of( universalEntityId.substring(8) );
    }
    return Optional.ofNullable( universalEntityId );
  }

  @Override
  public IssuerOfPatientIdInterface setUniversalEntityID(String universalEntityID) {
    this.universalEntityId = "urn:oid"+universalEntityID;
    return this;
  }

  @Override
  public Optional<String> getUniversalEntityIDType() {
    return Optional.ofNullable( universalEntityIDType );
  }

  @Override
  public IssuerOfPatientIdInterface setUniversalEntityIDType(String universalEntityIDType) {
    this.universalEntityIDType = universalEntityIDType;
    return this;
  }
}
