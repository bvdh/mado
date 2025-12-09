package org.hl7eu.imagingmanifest.old.imagingmanifest.manifest.kos1;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.hl7eu.imagingmanifest.old.imagingmanifest.DicomUtil;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomIssuerInfo;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomManifest;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomStudy;

import java.util.Optional;
import java.util.TimeZone;

public class KosGeneralStudyModule {
  static void populateManifest(Attributes kos, KosManifest kosManifest) {
    DicomStudy dicomStudy = kosManifest.getDicomStudy();
    dicomStudy.setStudyInstanceUID( kos.contains(Tag.StudyInstanceUID) ? kos.getString(Tag.StudyInstanceUID): null );
    dicomStudy.setStudyInstanceUID( kos.contains(Tag.StudyID) ? kos.getString(Tag.StudyID): null );
    dicomStudy.setStudyDescription( kos.contains(Tag.StudyDescription) ? kos.getString(Tag.StudyDescription): null );
    dicomStudy.setStudyDateTime(kos.contains(Tag.StudyDate)
        ? DicomUtil.getDateFromDicomDateAndTime( kos.getDate(Tag.StudyDate), kos.getDate( Tag.StudyTime ), TimeZone.getTimeZone( dicomStudy.getTimeZone()))
        : null
    );
    dicomStudy.setAccessionNumber( kos.contains(Tag.AccessionNumber) ? kos.getString(Tag.AccessionNumber): null );

    DicomIssuerInfo accessionNumberIssuer = dicomStudy.getAccessionNumberIssuer();
    accessionNumberIssuer.setUniversivalEntityID( kos.contains(Tag.UniversalEntityID) ? kos.getString(Tag.UniversalEntityID): null );
    accessionNumberIssuer.setUniversalEntityIDType( kos.contains(Tag.UniversalEntityIDType) ? kos.getString(Tag.UniversalEntityIDType): null );
  }

  static void addGeneralStudyModule(Attributes kos, DicomManifest dicomManifest) {
    DicomStudy dicomStudy = dicomManifest.getDicomStudy();
    Optional.ofNullable(dicomStudy.getStudyInstanceUID()).ifPresent(value -> kos.setString( Tag.StudyInstanceUID, VR.UI, value) );
    Optional.ofNullable(dicomStudy.getStudyId()).ifPresent(value -> kos.setString( Tag.StudyID, VR.SH, value) );
    Optional.ofNullable(dicomStudy.getStudyDescription()).ifPresent(value -> kos.setString( Tag.StudyDescription, VR.LO, value) );
    Optional.ofNullable(dicomStudy.getStudyDateTime()).ifPresent(value -> {
      kos.setString( Tag.TimezoneOffsetFromUTC, VR.SH, dicomStudy.getTimeZone() );
      kos.setString( Tag.StudyDate, VR.DA, DicomUtil.toDateString(value));
      kos.setString( Tag.StudyTime, VR.TM, DicomUtil.toTimeString(value));
    } );
    Optional.ofNullable(dicomStudy.getAccessionNumber()).ifPresent(value -> kos.setString( Tag.AccessionNumber, VR.SH, value) );
    Optional.ofNullable(dicomStudy.getAccessionNumberIssuer()).ifPresent(value -> {
      kos.setString( Tag.UniversalEntityID, VR.UT, value.getUniversivalEntityID() );
      kos.setString( Tag.UniversalEntityIDType, VR.CS, value.getUniversalEntityIDType() );
    } );
    Optional.ofNullable( dicomStudy.getStudyDescription()).ifPresent(value -> kos.setString( Tag.StudyDescription, VR.LO, value) );
  }
}
