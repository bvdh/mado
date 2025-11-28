package org.hl7eu.imagingmanifest.dicom;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.hl7eu.imagingmanifest.model.IssuerOfPatientIdInterface;

import java.util.Optional;

public class DicomIssuerOfPatientId implements IssuerOfPatientIdInterface {
  private final Attributes attributes;

  public DicomIssuerOfPatientId(Attributes issuerAttributes) {
    this.attributes = issuerAttributes;
    this.attributes.setString(Tag.TypeOfPatientID,  org.dcm4che3.data.VR.CS, "TEXT");
    this.attributes.setString(Tag.UniversalEntityID,  org.dcm4che3.data.VR.CS, "ISO");
  }

  @Override
  public Optional<String> getUniversalEntityID() {
    return Optional.ofNullable( attributes.getString(org.dcm4che3.data.Tag.UniversalEntityID) );
  }

  @Override
  public IssuerOfPatientIdInterface setUniversalEntityID(String universalEntityID) {
    attributes.setString( org.dcm4che3.data.Tag.UniversalEntityID,  org.dcm4che3.data.VR.UT, universalEntityID );
    return this;
  }

  @Override
  public Optional<String> getUniversalEntityIDType() {
    return Optional.ofNullable( attributes.getString(org.dcm4che3.data.Tag.UniversalEntityIDType) );
  }

  @Override
  public IssuerOfPatientIdInterface setUniversalEntityIDType(String universalEntityIDType) {
    attributes.setString( org.dcm4che3.data.Tag.UniversalEntityIDType,  org.dcm4che3.data.VR.CS, universalEntityIDType );
    return this;
  }

//  @Override
//  public Optional<String> getIdentifierTypeCode() {
//    return Optional.ofNullable( attributes.getString(org.dcm4che3.data.Tag.IdentifierTypeCode) );
//  }
//
//  @Override
//  public IssuerOfPatientIdInterface setIdentifierTypeCode(String identifierTypeCode) {
//    attributes.setString( org.dcm4che3.data.Tag.IdentifierTypeCode,  org.dcm4che3.data.VR.CS, identifierTypeCode );
//    return this;
//  }
}
