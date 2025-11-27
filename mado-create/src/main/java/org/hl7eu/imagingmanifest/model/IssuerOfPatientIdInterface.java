package org.hl7eu.imagingmanifest.model;

import org.dcm4che3.data.Attributes;

import java.util.Optional;

public interface IssuerOfPatientIdInterface {
  public Optional<String> getUniversalEntityID();
  public IssuerOfPatientIdInterface setUniversalEntityID( String universalEntityID );

  public Optional<String> getUniversalEntityIDType();
  public IssuerOfPatientIdInterface setUniversalEntityIDType( String universalEntityIDType );

//  public Optional<String> getIdentifierTypeCode();
//  public IssuerOfPatientIdInterface setIdentifierTypeCode( String identifierTypeCode );

}
