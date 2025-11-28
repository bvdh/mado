package org.hl7eu.imagingmanifest.model;

import org.hl7eu.imagingmanifest.imagingmanifest.manifest.kos1.GeneralEquipmentModule;

import java.util.Optional;

public interface ManifestInterface {

  public PatientModuleInterface getPatientModule();
  public ManifestInterface setPatientModule( PatientModuleInterface patientModule );


  public GeneralStudyModuleInterface getGeneralStudyModule();
  public ManifestInterface setGeneralStudyModule(GeneralStudyModuleInterface generalStudyModule );

  public GeneralEquipmentModuleInterface getGeneralEquipmentModule();
  public ManifestInterface setGeneralEquipmentModule(GeneralEquipmentModuleInterface generalEquipmentModule);

}
