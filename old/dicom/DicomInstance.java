package org.hl7eu.imagingmanifest.old.dicom;

import org.dcm4che3.data.Attributes;

public class DicomInstance {
  private final Attributes attributes;

  public DicomInstance(Attributes attributes) {
    this.attributes = attributes;
  }

  public String getSopInstanceUID() {
    return null;
  }
}
