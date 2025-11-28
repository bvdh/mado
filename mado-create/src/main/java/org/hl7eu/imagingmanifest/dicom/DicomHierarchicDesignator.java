package org.hl7eu.imagingmanifest.dicom;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.hl7eu.imagingmanifest.model.HierarchicDesignatorInterface;

import java.util.Optional;

public class DicomHierarchicDesignator implements HierarchicDesignatorInterface {
  private final Attributes attributes;

  DicomHierarchicDesignator(Attributes attributes) {
    this.attributes = attributes;
  }
  @Override
  public Optional<String> getLocalNamespaceEntityID() {
    return Optional.ofNullable( attributes.getString(Tag.LocalNamespaceEntityID) );
  }

  @Override
  public HierarchicDesignatorInterface setLocalNamespaceEntityID(String localNamespaceEntityID) {
    attributes.setString( Tag.LocalNamespaceEntityID,  VR.CS, localNamespaceEntityID );
    return this;
  }

  @Override
  public Optional<String> getUniversalEntityID() {
    return Optional.ofNullable( attributes.getString(Tag.UniversalEntityID) );
  }

  @Override
  public HierarchicDesignatorInterface setUniversalEntityID(String universalEntityID) {
    attributes.setString( Tag.UniversalEntityID,  VR.CS, universalEntityID );
    return this;
  }

  @Override
  public Optional<String> getUniversalEntityIDType() {
    return Optional.ofNullable( attributes.getString(Tag.UniversalEntityIDType) );
  }

  @Override
  public HierarchicDesignatorInterface setUniversalEntityIDType(String universalEntityIDType) {
    attributes.setString( Tag.UniversalEntityIDType,  VR.CS, universalEntityIDType );
    return this;
  }
}
