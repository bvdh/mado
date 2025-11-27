package org.hl7eu.imagingmanifest.model;

import org.dcm4che3.data.Sequence;
import org.hl7eu.imagingmanifest.dicom.DicomOtherPatientIDsSequence;

import java.util.Optional;

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
    target.getIssuerOfPatientIDQualifier().ifPresent( source::setIssuerOfPatientIDQualifier );
  }
}
