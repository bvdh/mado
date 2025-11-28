package org.hl7eu.imagingmanifest.testdata;

import org.hl7eu.imagingmanifest.model.*;

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

  @Override
  public GeneralStudyModuleInterface getGeneralStudyModule() {
    return null;
  }

  @Override
  public ManifestInterface setGeneralStudyModule(GeneralStudyModuleInterface generalStudyModule) {
    return this;
  }

  @Override
  public GeneralEquipmentModuleInterface getGeneralEquipmentModule() {
    return null;
  }

  @Override
  public ManifestInterface setGeneralEquipmentModule(GeneralEquipmentModuleInterface generalEquipmentModule) {
    return null;
  }
}
