package org.hl7eu.imagingmanifest.dicom;

import org.checkerframework.checker.units.qual.A;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.data.Tag;
import org.hl7eu.imagingmanifest.model.IssuerOfPatientIdInterface;
import org.hl7eu.imagingmanifest.model.ModelUtil;
import org.hl7eu.imagingmanifest.model.OtherPatientIDsSequenceInterface;

import java.util.Optional;

public class DicomOtherPatientIDsSequence implements OtherPatientIDsSequenceInterface {
  private final Attributes attributes;

  public DicomOtherPatientIDsSequence(Attributes otherPatientIDsSequenceAttributes) {
    this.attributes = otherPatientIDsSequenceAttributes;
  }

  public Attributes getAttributes(){
    return this.attributes;
  }
  @Override
  public Optional<String> getPatientID() {
    return Optional.ofNullable( attributes.getString(Tag.PatientID) ); // Tag.PatientID
  }
  @Override
  public OtherPatientIDsSequenceInterface setPatientID(String patientID) {
    attributes.setString(Tag.PatientID,  org.dcm4che3.data.VR.LO, patientID );
    return this;
  }

  @Override
  public Optional<String> getIssuerOfPatientID() {
    return Optional.ofNullable( attributes.getString(Tag.IssuerOfPatientID) );
  }
  @Override
  public OtherPatientIDsSequenceInterface setIssuerOfPatientID(String issuerOfPatientId) {
    attributes.setString(Tag.IssuerOfPatientID,  org.dcm4che3.data.VR.LO, issuerOfPatientId );
    return this;
  }

  @Override
  public Optional<String> getTypeOfPatientID() {
    return Optional.ofNullable( attributes.getString(Tag.TypeOfPatientID) );
  }

  @Override
  public OtherPatientIDsSequenceInterface setTypeOfPatientID(String typeOfPatientID) {
    attributes.setString( Tag.TypeOfPatientID,  org.dcm4che3.data.VR.CS, typeOfPatientID );
    return this;
  }

  @Override
  public Optional<IssuerOfPatientIdInterface> getIssuerOfPatientIDQualifier() {
    Sequence sequence = attributes.getSequence(Tag.IssuerOfPatientID);
    if ( sequence == null ) {
      return Optional.empty();
    }
    Attributes issuerAttributes = sequence.getFirst();
    return Optional.of( new DicomIssuerOfPatientId(issuerAttributes) );
  }

  @Override
  public OtherPatientIDsSequenceInterface setIssuerOfPatientIDQualifier(IssuerOfPatientIdInterface issuerOfPatientIdQualifier) {
    Sequence sequence = attributes.getSequence(Tag.IssuerOfPatientID);
    if ( sequence == null ) {
      sequence = attributes.newSequence( Tag.IssuerOfPatientID, 1 );
    }
    if( sequence.isEmpty() ) {
      sequence.add( new Attributes() );
    }
    Attributes issuerAttributes = sequence.getFirst();
    DicomIssuerOfPatientId thisIssuer = new DicomIssuerOfPatientId( issuerAttributes );
    ModelUtil.copyIssuerOfPatientIDData( issuerOfPatientIdQualifier, thisIssuer );
    return this;
  }

}
