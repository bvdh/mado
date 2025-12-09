package org.hl7eu.imagingmanifest.old.model;

//import org.hl7eu.imagingmanifest.old.fhir.FhirManifestAuthor;

public interface ManifestInterface {

  public PatientModuleInterface getPatientModule();
  public ManifestInterface setPatientModule( PatientModuleInterface patientModule );


  public GeneralStudyModuleInterface getGeneralStudyModule();

  ManifestInterface setGeneralStudyModule(GeneralStudyModuleInterface generalStudyModule);

  public ManifestInterface setManifestAuthor(GeneralStudyModuleInterface generalStudyModule );

//  ManifestInterface setManifestAuthor(FhirManifestAuthor mafiestAuthor);

//  public FhirManifestAuthor getManifestAuthor();
  public ManifestInterface setManifestAuthor(GeneralEquipmentModuleInterface generalEquipmentModule);

}
