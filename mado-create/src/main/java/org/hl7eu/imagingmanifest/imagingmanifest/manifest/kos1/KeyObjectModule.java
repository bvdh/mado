package org.hl7eu.imagingmanifest.imagingmanifest.manifest.kos1;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.util.UIDUtils;
import org.hl7eu.imagingmanifest.imagingmanifest.DicomUtil;
import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomInstance;
import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomSerie;
import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomStudy;

import java.util.*;

public class KeyObjectModule {
  static void addKeyObjectDocument(Attributes kos, DicomStudy dicomStudy ) {
    Set<Integer> usedInstanceNumber = new HashSet<>();
    for (DicomSerie series : dicomStudy.getSeries()) {
      for ( DicomInstance instance : series.getInstances() ) {
        usedInstanceNumber.add(instance.getInstanceNumber());
      }
    }
    int instanceNumber = 684;
    while ( usedInstanceNumber.contains( instanceNumber ) ) {
      instanceNumber++;
    }
    kos.setInt( Tag.InstanceNumber, VR.IS, instanceNumber );
    kos.setDate( Tag.ContentDate, VR.DA, new Date() );
    kos.setDate( Tag.ContentTime, VR.TM, new Date() );

    createReferencedRequestSequence( kos, dicomStudy );
    createCurrentRequestedProcedureEvidenceSequence( kos, dicomStudy );
  }

  private static void createCurrentRequestedProcedureEvidenceSequence(Attributes kos, DicomStudy dicomStudy) {
    Sequence referencedRequestSequence = kos.newSequence( Tag.CurrentRequestedProcedureEvidenceSequence, 1 );
    {
      Attributes attributes = new Attributes();
      referencedRequestSequence.add(attributes);
      Optional.ofNullable(dicomStudy.getStudyInstanceUID()).ifPresent(value -> attributes.setString(Tag.StudyInstanceUID, VR.UI, value));
      Sequence referencedSeriesSequence = attributes.newSequence(Tag.ReferencedSeriesSequence, dicomStudy.getSeries().size());
      {
        Iterator<DicomSerie> dicomSerieIt = dicomStudy.getSeries().iterator();
        while ( dicomSerieIt.hasNext()) {
          Attributes seriesAttributes = new Attributes();
          referencedSeriesSequence.add(seriesAttributes);

          DicomSerie dicomSerie = dicomSerieIt.next();
          Optional.ofNullable(dicomSerie.getSeriesInstanceUID()).ifPresent(value -> seriesAttributes.setString(Tag.SeriesInstanceUID, VR.UI, value));
          Optional.ofNullable(dicomSerie.getSeriesDateTime()).ifPresent(value -> {
            seriesAttributes.setTimezone( TimeZone.getTimeZone(dicomStudy.getTimeZone()) );
            seriesAttributes.setString( Tag.TimezoneOffsetFromUTC, VR.SH, dicomStudy.getTimeZone() );
            seriesAttributes.setString(Tag.SeriesDate, VR.TM, DicomUtil.toDateString(value) );
            seriesAttributes.setString(Tag.SeriesTime, VR.TM, DicomUtil.toTimeString(value) );
          });
          Optional.ofNullable(dicomSerie.getModality()).ifPresent(value -> seriesAttributes.setString(Tag.Modality, VR.CS, value));
          Optional.ofNullable(dicomSerie.getSeriesDescription()).ifPresent(value -> seriesAttributes.setString(Tag.SeriesDescription, VR.LO, value));

          Sequence referencedSopSequence = attributes.newSequence(Tag.ReferencedSOPSequence, dicomSerie.getInstances().size());
          Iterator<Attributes> referencedSopSequenceIt = referencedSeriesSequence.iterator();
          Iterator<DicomInstance> dicomInstanceIt = dicomSerie.getInstances().iterator();
          while (referencedSopSequenceIt.hasNext() && dicomInstanceIt.hasNext()) {
            Attributes instanceAttributes = referencedSopSequenceIt.next();
            DicomInstance dicomInstance = dicomInstanceIt.next();
            Optional.ofNullable(dicomInstance.getSopClassUID()).ifPresent(value -> instanceAttributes.setString(Tag.ReferencedSOPClassUID, VR.UI, value));
            Optional.ofNullable(dicomInstance.getSopInstanceUID()).ifPresent(value -> instanceAttributes.setString(Tag.ReferencedSOPInstanceUID, VR.UI, value));
            instanceAttributes.setInt(Tag.InstanceNumber, VR.IS, dicomInstance.getInstanceNumber());
            instanceAttributes.setInt(Tag.NumberOfFrames, VR.IS, dicomInstance.getNumberOfFrames());
          }

        }
      }
    }
  }

  private static void createReferencedRequestSequence(Attributes kos, DicomStudy dicomStudy) {
    Sequence referencedRequestSequence = kos.newSequence( Tag.ReferencedRequestSequence, 1 );
    Attributes attributes = new Attributes();

    Optional.ofNullable(dicomStudy.getStudyInstanceUID()).ifPresent(value -> attributes.setString( Tag.StudyInstanceUID, VR.UI, value) );
    Optional.ofNullable(dicomStudy.getStudyId()).ifPresent(value -> attributes.setString( Tag.StudyID, VR.SH, value) );
    Optional.ofNullable(dicomStudy.getStudyDescription()).ifPresent(value -> attributes.setString( Tag.StudyDescription, VR.LO, value) );
    Optional.ofNullable(dicomStudy.getStudyDateTime()).ifPresent(value -> {
      attributes.setTimezone( TimeZone.getTimeZone(dicomStudy.getTimeZone()) );
      attributes.setString( Tag.StudyDate, VR.DA, DicomUtil.toDateString(dicomStudy.getStudyDateTime()));
      attributes.setString( Tag.StudyTime, VR.TM, DicomUtil.toTimeString(dicomStudy.getStudyDateTime()));
    } );
    // TODO again?
    Optional.ofNullable(dicomStudy.getAccessionNumber()).ifPresent(value -> attributes.setString( Tag.AccessionNumber, VR.SH, value) );
    Optional.ofNullable(dicomStudy.getAccessionNumberIssuer()).ifPresent(value -> {
      kos.setString( Tag.UniversalEntityID, VR.UT, value.getUniversivalEntityID() );
      kos.setString( Tag.UniversalEntityIDType, VR.CS, value.getUniversalEntityIDType() );
    } );
    referencedRequestSequence.add(attributes);
  }



  private void addKeyObjectDocumentSeries(Attributes kos ) {
    kos.setString(Tag.Modality, VR.CS, "KO" );
    kos.setString(Tag.SeriesInstanceUID, VR.UI, UIDUtils.createUID() );
    kos.setString( Tag.SeriesNumber, VR.IS, UIDUtils.createUID() );
    kos.setDate( Tag.SeriesDate, VR.DA, new Date() );
    kos.setDate( Tag.SeriesTime, VR.TM, new Date() );
  }

}
