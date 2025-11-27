package org.hl7eu.imagingmanifest.testdata;

import org.hl7eu.imagingmanifest.model.ManifestInterface;
import org.hl7eu.imagingmanifest.model.ModelUtil;
import org.hl7eu.imagingmanifest.model.PatientModuleInterface;

public class TestManifest implements ManifestInterface {
  private PatientModuleInterface patientModule = new TestPatientModule();

  @Override
  public PatientModuleInterface getPatientModule() {
    return patientModule;
  }

  @Override
  public ManifestInterface setPatientModule(PatientModuleInterface otherPatientModule) {
    ModelUtil.copyPatientModuleData( otherPatientModule, this.patientModule );
    return this;
  }
}
