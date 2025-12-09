package org.hl7eu.imagingmanifest.old.testdata;

import org.dcm4che3.data.PersonName;
import org.hl7eu.imagingmanifest.old.model.IssuerOfPatientIdInterface;
import org.hl7eu.imagingmanifest.old.model.OtherPatientIDsSequenceInterface;
import org.hl7eu.imagingmanifest.old.model.PatientModuleInterface;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TestPatientModule implements PatientModuleInterface {

  PersonName personName = new PersonName( "Doe^John" );
  private String patientId = "patientId";
  private String issuerOfPatientId = "TestIssuer";
  private String patientSex = "M";
  private Date patientBirthDate = new Date( 1980, 1,1 );
  private List<PersonName> otherPatientNames = List.of(new PersonName("Pietje^Puk"));
  private List<? extends OtherPatientIDsSequenceInterface> otherPatientIDsSequence = List.of( new TestOtherPatientIDsSequence() );
  @Override
  public Optional<PersonName> getPatientName() {
    return Optional.of(personName);
  }

  @Override
  public PatientModuleInterface setPatientName(PersonName name) {
    this.personName = name;
    return this;
  }

  @Override
  public Optional<String> getPatientID() {
    return Optional.of( patientId );
  }

  @Override
  public PatientModuleInterface setPatientID(String patientID) {
    this.patientId = patientID;
    return this;
  }

  @Override
  public Optional<String> getIssuerOfPatientID() {
    return Optional.of( issuerOfPatientId );
  }

  @Override
  public PatientModuleInterface setIssuerOfPatientID(String issuerOfPatientID) {
    setIssuerOfPatientID( issuerOfPatientID );
    return this;
  }

  @Override
  public Optional<IssuerOfPatientIdInterface> getIssuerOfPatientIDQualifiers() {
    return Optional.of( new TestIssuerOfPatientId() );
  }

  @Override
  public PatientModuleInterface setIssuerOfPatientID(IssuerOfPatientIdInterface issuerOfPatientID) {
    return this;
  }

  @Override
  public List<PersonName> getOtherPatientNames() {
    return otherPatientNames;
  }

  @Override
  public PatientModuleInterface addOtherPatientName(PersonName name) {
    this.otherPatientNames.add( name );
    return this;
  }

  @Override
  public Optional<Date> getPatientBirthDate() {
    return Optional.of( patientBirthDate );
  }

  @Override
  public PatientModuleInterface setPatientBirthDate(Date birthDate) {
    this.patientBirthDate = birthDate;
    return this;
  }

  @Override
  public Optional<String> getPatientSex() {
    return Optional.of(patientSex);
  }

  @Override
  public PatientModuleInterface setPatientSex(String gender) {
    this.patientSex = gender;
    return this;
  }

  @Override
  public List<? extends OtherPatientIDsSequenceInterface> getOtherPatientIDsSequence() {
    return otherPatientIDsSequence;
  }

  @Override
  public PatientModuleInterface setOtherPatientIDsSequence(List<? extends OtherPatientIDsSequenceInterface> otherPatientIDsSequence) {
    this.otherPatientIDsSequence = otherPatientIDsSequence;
    return this;
  }

}
