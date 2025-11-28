package org.hl7eu.imagingmanifest.model;

import org.hl7eu.imagingmanifest.fhir.FhirManifestAuthor;

public interface ManifestInterface {

  public PatientModuleInterface getPatientModule();
  public ManifestInterface setPatientModule( PatientModuleInterface patientModule );


  public GeneralStudyModuleInterface getGeneralStudyModule();

  ManifestInterface setGeneralStudyModule(GeneralStudyModuleInterface generalStudyModule);

  public ManifestInterface setManifestAuthor(GeneralStudyModuleInterface generalStudyModule );

  ManifestInterface setManifestAuthor(FhirManifestAuthor mafiestAuthor);

  public FhirManifestAuthor getManifestAuthor();
  public ManifestInterface setManifestAuthor(GeneralEquipmentModuleInterface generalEquipmentModule);

}
