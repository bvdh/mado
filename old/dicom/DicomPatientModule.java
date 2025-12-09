package org.hl7eu.imagingmanifest.old.dicom;

import org.dcm4che3.data.*;
import org.dcm4che3.util.DateUtils;
import org.hl7eu.imagingmanifest.old.model.IssuerOfPatientIdInterface;
import org.hl7eu.imagingmanifest.old.model.ModelUtil;
import org.hl7eu.imagingmanifest.old.model.OtherPatientIDsSequenceInterface;
import org.hl7eu.imagingmanifest.old.model.PatientModuleInterface;

import java.util.*;

public class DicomPatientModule extends Attributes implements PatientModuleInterface {

  private Attributes attributes = new Attributes();

  public DicomPatientModule(Attributes attributes){
    this.attributes = attributes;
  }

  // PatientName
  @Override
  public Optional<PersonName> getPatientName(){
    if ( !attributes.contains( Tag.PatientName ) ) {
      return Optional.empty();
    }
    return Optional.of( new PersonName( attributes.getString(Tag.PatientName) ) );
  }

  @Override
  public PatientModuleInterface setPatientName(PersonName name){
    attributes.setString(Tag.PatientName,  VR.PN, name.toString() );
    return this;
  }

  @Override
  public List<PersonName> getOtherPatientNames() {
    return attributes.contains(Tag.OtherPatientNames)
        ? Arrays.stream(attributes.getStrings(Tag.OtherPatientNames)).map( PersonName::new ).toList()
        : List.of()
        ;
  }

  @Override
  public PatientModuleInterface addOtherPatientName(PersonName name) {
    List<PersonName> otherPatientNames = new ArrayList<>(getOtherPatientNames());
    if ( name!=null ) {
      otherPatientNames.add(name);
    }
    String[] namesArray = otherPatientNames.stream().map(PersonName::toString).toArray(String[]::new);
    attributes.setString(Tag.OtherPatientNames, VR.PN, namesArray);
    return this;
  }

  // PatientID
  @Override
  public Optional<String> getPatientID(){
    return Optional.ofNullable(attributes.getString(Tag.PatientID));
  }

  @Override
  public DicomPatientModule setPatientID(String patientID){
    attributes.setString(Tag.PatientID, VR.LO, patientID );
    return this;
  }

//  @Override
//  public Optional<String> getTypeOfPatientID(){
//    return Optional.ofNullable(attributes.getString(Tag.TypeOfPatientID));
//  }
//
//  @Override
//  public PatientModule setTypeOfPatientID(String typeOfPatientID){
//    attributes.setString(Tag.TypeOfPatientID, VR.CS, typeOfPatientID );
//    return this;
//  }

  @Override
  public Optional<String> getIssuerOfPatientID() {
    return Optional.ofNullable( attributes.getString(Tag.IssuerOfPatientID) );
  }

  @Override
  public PatientModuleInterface setIssuerOfPatientID(String issuerOfPatientID) {
    attributes.setString(Tag.IssuerOfPatientID, VR.LO, issuerOfPatientID);
    return this;
  }

  @Override
  public Optional<IssuerOfPatientIdInterface> getIssuerOfPatientIDQualifiers() {
    if ( !attributes.contains( Tag.IssuerOfPatientIDQualifiersSequence ) ) {
      return Optional.empty();
    }
    Sequence seq = attributes.getSequence( Tag.IssuerOfPatientIDQualifiersSequence );
    if ( seq.isEmpty() ) { return Optional.empty(); }
    DicomIssuerOfPatientId dicomIssuer = new DicomIssuerOfPatientId( seq.get(0) );
    return Optional.of( dicomIssuer );
  }

  @Override
  public PatientModuleInterface setIssuerOfPatientID(IssuerOfPatientIdInterface issuerOfPatientID) {
    Sequence seq = attributes.getSequence( Tag.IssuerOfPatientIDQualifiersSequence );
    if ( seq == null ) {
      seq = attributes.newSequence( Tag.IssuerOfPatientIDQualifiersSequence, 0 );
      seq.add( new Attributes() );
    }
    DicomIssuerOfPatientId dicomIssuer = new DicomIssuerOfPatientId( seq.getFirst() );
    ModelUtil.copyIssuerOfPatientIDData( issuerOfPatientID, dicomIssuer );
    return this;
  }


  @Override
  public PatientModuleInterface setOtherPatientIDsSequence(List<? extends OtherPatientIDsSequenceInterface> otherPatientIDsSequences) {
    Sequence otherPatientIDsSeq = attributes.getSequence(Tag.OtherPatientIDsSequence);
    if ( otherPatientIDsSeq == null ) {
      otherPatientIDsSeq = attributes.newSequence( Tag.OtherPatientIDsSequence, 0 );
    }
    otherPatientIDsSeq.clear();
    for( OtherPatientIDsSequenceInterface  otherPatientIDsSequence : otherPatientIDsSequences ){
      Attributes seqAttrs = new Attributes();
      DicomOtherPatientIDsSequence dicomOtherPatientIDsSequence = new DicomOtherPatientIDsSequence( seqAttrs );
      ModelUtil.copyOtherPatientIDsSequence( otherPatientIDsSequence, dicomOtherPatientIDsSequence );
      otherPatientIDsSeq.add( seqAttrs );
    }
    return this;
  }
  @Override
  public List<? extends OtherPatientIDsSequenceInterface> getOtherPatientIDsSequence() {
    Sequence sequence = attributes.getSequence(Tag.OtherPatientIDsSequence);
    if ( sequence==null || sequence.isEmpty() ) {
      return List.of();
    }
    return sequence.stream()
        .map(DicomOtherPatientIDsSequence::new).toList();
  }

  @Override
  public Optional<Date> getPatientBirthDate() {
    return Optional.ofNullable(attributes.getDate(Tag.PatientBirthDate));
  }

  @Override
  public PatientModuleInterface setPatientBirthDate(Date birthDate) {
    attributes.setString(Tag.PatientBirthDate, VR.DA, DateUtils.formatDA(TimeZone.getDefault(), birthDate ) );
    return this;
  }

  @Override
  public Optional<String> getPatientSex() {
    return Optional.ofNullable( attributes.getString(Tag.PatientSex) );
  }

  @Override
  public PatientModuleInterface setPatientSex(String gender) {
    attributes.setString(Tag.PatientSex, VR.CS, gender );
    return this;
  }


  public Attributes getAttributes() {
    return this.attributes;
  }
}
