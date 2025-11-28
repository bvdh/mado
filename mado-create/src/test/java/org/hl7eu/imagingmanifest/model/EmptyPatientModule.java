package org.hl7eu.imagingmanifest.model;

import org.dcm4che3.data.PersonName;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class EmptyPatientModule implements PatientModuleInterface{
  @Override
  public Optional<PersonName> getPatientName() {
    return Optional.empty();
  }

  @Override
  public PatientModuleInterface setPatientName(PersonName name) {
    return null;
  }

  @Override
  public Optional<String> getPatientID() {
    return Optional.empty();
  }

  @Override
  public PatientModuleInterface setPatientID(String patientID) {
    return null;
  }

  @Override
  public Optional<String> getIssuerOfPatientID() {
    return Optional.empty();
  }

  @Override
  public PatientModuleInterface setIssuerOfPatientID(String issuerOfPatientID) {
    return this;
  }

  @Override
  public Optional<IssuerOfPatientIdInterface> getIssuerOfPatientIDQualifiers() {
    return Optional.empty();
  }

  @Override
  public PatientModuleInterface setIssuerOfPatientID(IssuerOfPatientIdInterface issuerOfPatientID) {
    return null;
  }

  @Override
  public List<PersonName> getOtherPatientNames() {
    return List.of();
  }

  @Override
  public PatientModuleInterface addOtherPatientName(PersonName name) {
    return null;
  }

  @Override
  public Optional<Date> getPatientBirthDate() {
    return Optional.empty();
  }

  @Override
  public PatientModuleInterface setPatientBirthDate(Date birthDate) {
    return null;
  }

  @Override
  public Optional<String> getPatientSex() {
    return Optional.empty();
  }

  @Override
  public PatientModuleInterface setPatientSex(String gender) {
    return null;
  }

  @Override
  public List<? extends OtherPatientIDsSequenceInterface> getOtherPatientIDsSequence() {
    return List.of();
  }

  @Override
  public PatientModuleInterface setOtherPatientIDsSequence(List<? extends OtherPatientIDsSequenceInterface> otherPatientIDsSequence) {
    return null;
  }
}
