package org.hl7eu.imagingmanifest.old.dicom;

import lombok.Getter;
import lombok.Setter;
import org.dcm4che3.data.Attributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DicomSerie {
  private final Attributes attributes;
  Map<String, DicomInstance> instances = new HashMap<>();

  public DicomSerie(Attributes attributes) {
    this.attributes = attributes;
  }

  public void addDicomInstance( DicomInstance dicomInstance )
  {
    String instanceUID = dicomInstance.getSopInstanceUID();
    instances.put( dicomInstance.getSopInstanceUID(), dicomInstance );
  }
  public List<DicomInstance> getInstances() {
    return instances.values().stream().toList();
  }
}
