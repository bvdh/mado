package org.hl7eu.imagingmanifest.model;

import org.dcm4che3.data.Attributes;

import java.util.Optional;

public interface OtherPatientIDsSequenceInterface {
  public Optional<String> getPatientID();
  public OtherPatientIDsSequenceInterface setPatientID( String patientID );

  public Optional<String> getIssuerOfPatientID();
  public OtherPatientIDsSequenceInterface setIssuerOfPatientID( String issuerOfPatientId );


  public Optional<String> getTypeOfPatientID();
  public OtherPatientIDsSequenceInterface setTypeOfPatientID( String typeOfPatientID );

  public Optional<IssuerOfPatientIdInterface> getIssuerOfPatientIDQualifier();
  public OtherPatientIDsSequenceInterface setIssuerOfPatientIDQualifier( IssuerOfPatientIdInterface issuerOfPatientIdQualifier );

}
