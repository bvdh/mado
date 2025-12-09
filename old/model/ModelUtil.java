package org.hl7eu.imagingmanifest.old.model;


public class ModelUtil {
  public static void copyPatientModuleData(PatientModuleInterface source, PatientModuleInterface target) {
    source.getPatientName().ifPresent( target::setPatientName );
    source.getPatientID().ifPresent( target::setPatientID );
    source.getIssuerOfPatientID().ifPresent( target::setIssuerOfPatientID );
    source.getPatientBirthDate().ifPresent( target::setPatientBirthDate );
    source.getPatientSex().ifPresent( target::setPatientSex );
    source.getOtherPatientNames().forEach( target::addOtherPatientName );
    source.getIssuerOfPatientID().ifPresent( target::setIssuerOfPatientID );
    target.setOtherPatientIDsSequence( source.getOtherPatientIDsSequence() );
  }

  public static void copyIssuerOfPatientIDData(IssuerOfPatientIdInterface source, IssuerOfPatientIdInterface target) {
//    source.getIdentifierTypeCode().ifPresent(target::setIdentifierTypeCode);
    source.getUniversalEntityID().ifPresent(target::setUniversalEntityID);
    source.getUniversalEntityIDType().ifPresent(target::setUniversalEntityIDType);
  }

  public static void copyOtherPatientIDsSequence(OtherPatientIDsSequenceInterface source, OtherPatientIDsSequenceInterface target) {
    source.getPatientID().ifPresent( target::setPatientID );
    source.getIssuerOfPatientID().ifPresent( target::setIssuerOfPatientID );
    source.getTypeOfPatientID().ifPresent( target::setTypeOfPatientID );
    source.getIssuerOfPatientIDQualifier().ifPresent( target::setIssuerOfPatientIDQualifier );
  }

  public static void copyGeneralStudyModuleData(GeneralStudyModuleInterface source, GeneralStudyModuleInterface target) {
    source.getStudyInstanceUID().ifPresent(target::setStudyInstanceUID);
    source.getAccessionNumber().ifPresent(target::setAccessionNumber);
    source.getStudyDate().ifPresent(target::setStudyDate);
    source.getStudyTime().ifPresent(target::setStudyTime);
    source.getIssuerOfAccessionNumber().ifPresent(target::setIssuerOfAccessionNumber);
  }

  public static void copyGeneralEquipmentModule(GeneralEquipmentModuleInterface source, GeneralEquipmentModuleInterface target ){
    source.getManufacturer().ifPresent(target::setManufacturer);
    source.getInstitutionName().ifPresent(target::setInstitutionName);
    source.getInstitutionCodeSequence().ifPresent(target::setInstitutionCodeSequence);
  }

  public static void copyCodeSequence(CodeSequenceInterface source, CodeSequenceInterface target) {
    source.getCodeValue().ifPresent(target::setCodeValue);
    source.getCodingSchemeDesignator().ifPresent(target::setCodingSchemeDesignator);
    source.getCodingSchemeVersion().ifPresent(target::setCodingSchemeVersion);
    source.getCodeMeaning().ifPresent(target::setCodeMeaning);
  }

  public static void copyManifestAuthor(FhirManifestAuthor source, FhirManifestAuthor target) {
    source.getInstitutionCodeSequence().ifPresent(target::setInstitutionCodeSequence);
    source.getInstitutionName().ifPresent(target::setInstitutionName);
    source.getManufacturer().ifPresent(target::setManufacturer);
  }
}
