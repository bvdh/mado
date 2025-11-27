package org.hl7eu.imagingmanifest.dicom;

import org.dcm4che3.data.Attributes;
import org.hl7eu.imagingmanifest.model.ManifestInterface;
import org.hl7eu.imagingmanifest.model.ModelUtil;
import org.hl7eu.imagingmanifest.model.PatientModuleInterface;

public class DicomManifest implements ManifestInterface {
  private Attributes attributes;

  public DicomManifest(Attributes attributes ) {
    this.attributes = attributes;
  }

  public DicomManifest( ManifestInterface testManistModel) {
    this.attributes = new Attributes();
    setPatientModule( testManistModel.getPatientModule() );
  }

  @Override
  public PatientModuleInterface getPatientModule() {
    return new DicomPatientModule(attributes);
  }

  @Override
  public ManifestInterface setPatientModule(PatientModuleInterface patientModule) {
    ModelUtil.copyPatientModuleData( patientModule, getPatientModule() );
    return this;
  }
}
