package org.hl7eu.imagingmanifest.fhir;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.PersonName;
import org.hl7.fhir.r4.model.*;
import org.hl7eu.imagingmanifest.dicom.DicomOtherPatientIDsSequence;
import org.hl7eu.imagingmanifest.model.ModelUtil;
import org.hl7eu.imagingmanifest.model.OtherPatientIDsSequenceInterface;
import org.hl7eu.imagingmanifest.model.PatientModuleInterface;

import java.util.*;

public class FhirPatientModule implements PatientModuleInterface {
  private Patient patient;
  FhirPatientModule( Patient patient ) { this.patient = patient !=null ? patient : new Patient(); }

  @Override
  public Optional<PersonName> getPatientName() {
    if ( !patient.hasName() && patient.getName().isEmpty() ) {
      return Optional.empty();
    }
    HumanName name = patient.getNameFirstRep();
    PersonName personName = new PersonName();
    populatePersonName(name, personName);
    return Optional.of(personName);
  }

  private static void populatePersonName( HumanName name, PersonName personName) {
    if (!name.getFamily().isEmpty()) {
      personName.set( PersonName.Component.FamilyName, name.getFamily() );
    }
    if (!name.getPrefix().isEmpty()) {
      personName.set( PersonName.Component.NamePrefix, String.join(" ", name.getPrefix().stream().map(PrimitiveType::getValue).toList() ) );
    }
    if (!name.getGiven().isEmpty()) {
      personName.set( PersonName.Component.GivenName, name.getGiven().getFirst().getValue() );
    }
    if ( name.getGiven().size() > 1 ) {
      personName.set( PersonName.Component.MiddleName, String.join(" ", name.getGiven().subList(1, name.getGiven().size()).stream().map(PrimitiveType::getValue).toList() ) );
    }
    if (!name.getSuffix().isEmpty()) {
      personName.set( PersonName.Component.NameSuffix, String.join(" ", name.getSuffix().stream().map(PrimitiveType::getValue).toList() ) );
    }
  }

  @Override
  public PatientModuleInterface setPatientName(PersonName name) {
    HumanName humanName;
    if ( !patient.hasName() || patient.getName().isEmpty() ){
      humanName = new HumanName();
      patient.addName( humanName );
    } else {
      humanName = patient.getNameFirstRep();
    }
    populateHumanName(name, humanName);
    return this;
  }

  private static void populateHumanName(PersonName name, HumanName humanName) {
    String prefix = name.get( PersonName.Component.NamePrefix );
    humanName.setPrefix( prefix!=null ? Arrays.stream(prefix.split(" ")).map(StringType::new).toList() : List.of() );
    humanName.setFamily( name.get( PersonName.Component.FamilyName ) );
    List<String> givenNames = new java.util.ArrayList<>();
    if ( name.get( PersonName.Component.GivenName ) != null ) {
      givenNames.add( name.get( PersonName.Component.GivenName ) );
    }
    String middleName = name.get( PersonName.Component.MiddleName );
    if ( middleName != null ) {
      givenNames.addAll(Arrays.asList(middleName.split(" ")));
    }
    humanName.setGiven( givenNames.stream().map(StringType::new).toList() );

    String suffix = name.get( PersonName.Component.NameSuffix );
    humanName.setSuffix( suffix!=null ? Arrays.stream(suffix.split(" ")).map(StringType::new).toList() : List.of() );
  }

  @Override
  public Optional<String> getPatientID() {
    if ( patient.hasIdentifier() && !patient.getIdentifier().isEmpty() ) {
      return Optional.ofNullable( patient.getIdentifierFirstRep().getValue() );
    }
    return Optional.empty();
  }

  @Override
  public PatientModuleInterface setPatientID(String patientID) {
    if ( patient.hasIdentifier() && !patient.getIdentifier().isEmpty() ) {
      patient.getIdentifierFirstRep().setValue( patientID );
    } else {
      Identifier identifier = new Identifier();
      identifier.setValue( patientID );
      identifier.setUse( Identifier.IdentifierUse.USUAL ); // identifiese first
      patient.addIdentifier( identifier );
    }
    return this;
  }
  @Override
  public Optional<String> getIssuerOfPatientID() {
    if ( patient.hasIdentifier() && !patient.getIdentifier().isEmpty() ) {
      return Optional.ofNullable( patient.getIdentifierFirstRep().getSystem() );
    }
    return Optional.empty();
  }

  @Override
  public PatientModuleInterface setIssuerOfPatientID(String issuerOfPatientID) {
    if ( patient.hasIdentifier() && !patient.getIdentifier().isEmpty() ) {
      patient.getIdentifierFirstRep().setSystem( issuerOfPatientID );
    } else {
      Identifier identifier = new Identifier();
      identifier.setSystem( issuerOfPatientID );
      patient.addIdentifier( identifier );
    }
    return this;
  }

  @Override
  public List<PersonName> getOtherPatientNames() {
    if ( patient.hasName() && patient.getName().size()>1 ) {
      List<PersonName> otherNames = new java.util.ArrayList<>();
      for ( int i =1; i<patient.getName().size(); i++ ) {
        HumanName name = patient.getName().get(i);
        PersonName personName = new PersonName();
        populatePersonName(name, personName);
        otherNames.add( personName );
      }
      return otherNames;
    }
    return List.of();
  }

  @Override
  public PatientModuleInterface addOtherPatientName(PersonName name) {
    HumanName humanName = new HumanName();
    populateHumanName(name, humanName);
    patient.addName( humanName );
    return this;
  }

  @Override
  public Optional<Date> getPatientBirthDate() {
    return Optional.ofNullable( patient.getBirthDate() );
  }

  @Override
  public PatientModuleInterface setPatientBirthDate(Date birthDate) {
    patient.setBirthDate( birthDate );
    return this;
  }

  @Override
  public Optional<String> getPatientSex() {
    String genderCode = patient.getGender()!=null ? patient.getGender().toCode(): null;
    if ( genderCode == null ) { return Optional.empty(); }

    String newCode =
        genderCode.equals("female") ? "F" :
        genderCode.equals("male")   ? "M" :
        "O";
    return Optional.of( newCode );
  }

  @Override
  public PatientModuleInterface setPatientSex(String gender) {
    Optional.ofNullable( gender ).ifPresent( g -> {
          switch (g) {
            case "M" -> patient.setGender(Enumerations.AdministrativeGender.MALE);
            case "F" -> patient.setGender(Enumerations.AdministrativeGender.FEMALE);
            case "O" -> patient.setGender(Enumerations.AdministrativeGender.OTHER);
            default -> patient.setGender(Enumerations.AdministrativeGender.UNKNOWN);
          };
        }
    );
    return this;
  }

  @Override
  public List<? extends OtherPatientIDsSequenceInterface> getOtherPatientIDsSequence() {
    return patient.getIdentifier().stream()
        .filter(Identifier::hasUse)
        .filter( identifier -> !identifier.getUse().equals( Identifier.IdentifierUse.USUAL ) )
        .map(FhirOtherPatientIDsSequence::new).toList();
  }

  @Override
  public PatientModuleInterface setOtherPatientIDsSequence( List<? extends OtherPatientIDsSequenceInterface> otherPatientIDsSequence) {
    Optional<Identifier> mainIdentifier = patient.getIdentifier().stream().filter(identifier -> identifier.getUse() == Identifier.IdentifierUse.USUAL ).findFirst();

    List<Identifier> identifiers = new ArrayList<Identifier>();
    mainIdentifier.ifPresent(identifiers::add);

    otherPatientIDsSequence.forEach(otherIdentifier -> {
      Identifier identifier = new Identifier();
      identifier.setUse( Identifier.IdentifierUse.SECONDARY ); // identifiese other
      ModelUtil.copyOtherPatientIDsSequence( otherIdentifier, new FhirOtherPatientIDsSequence(identifier) );
      identifiers.add( identifier );
    });
    patient.setIdentifier( identifiers );
    return this;
  }
}
