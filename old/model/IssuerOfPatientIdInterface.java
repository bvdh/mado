package org.hl7eu.imagingmanifest.old.model;

import java.util.Optional;

public interface IssuerOfPatientIdInterface {
  public Optional<String> getUniversalEntityID();
  public IssuerOfPatientIdInterface setUniversalEntityID( String universalEntityID );

  public Optional<String> getUniversalEntityIDType();
  public IssuerOfPatientIdInterface setUniversalEntityIDType( String universalEntityIDType );

//  public Optional<String> getIdentifierTypeCode();
//  public IssuerOfPatientIdInterface setIdentifierTypeCode( String identifierTypeCode );

}
