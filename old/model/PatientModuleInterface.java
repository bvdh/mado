package org.hl7eu.imagingmanifest.old.model;

import org.dcm4che3.data.PersonName;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PatientModuleInterface {
  // PatientName
  Optional<PersonName> getPatientName();

  PatientModuleInterface setPatientName(PersonName name);

  Optional<String> getPatientID();
  PatientModuleInterface setPatientID( String patientID );

  // Not possible in FHIR
//  Optional<String> getTypeOfPatientID();
//  PatientModuleInterface setTypeOfPatientID( String typeOfPatientID );

  Optional<String> getIssuerOfPatientID();
  PatientModuleInterface setIssuerOfPatientID( String issuerOfPatientID );

  Optional<IssuerOfPatientIdInterface> getIssuerOfPatientIDQualifiers();
  PatientModuleInterface setIssuerOfPatientID( IssuerOfPatientIdInterface issuerOfPatientID );

  List<PersonName> getOtherPatientNames();
  PatientModuleInterface addOtherPatientName( PersonName name );

  Optional<Date> getPatientBirthDate();
  PatientModuleInterface setPatientBirthDate( Date birthDate );

  Optional<String> getPatientSex();
  PatientModuleInterface setPatientSex( String gender );

  List<? extends OtherPatientIDsSequenceInterface> getOtherPatientIDsSequence();
  PatientModuleInterface setOtherPatientIDsSequence( List<? extends OtherPatientIDsSequenceInterface> otherPatientIDsSequence );



}
