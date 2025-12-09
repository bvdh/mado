package org.hl7eu.imagingmanifest.old.imagingmanifest.manifest.kos1;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.hl7eu.imagingmanifest.old.imagingmanifest.DicomUtil;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.Configuration;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomManifest;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomSerie;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomStudy;

import java.util.Optional;
import java.util.TimeZone;

public class KeyObjectDocumentModule {
  static void addKeyObjectDocumentSeries(Attributes kos, DicomManifest dicomManifest) {
    DicomStudy dicomStudy = dicomManifest.getDicomStudy();
    addReferencedRequestSequence(kos, dicomManifest);

    // current requested procedure evidence sequence
    Sequence currentRequestedProcedureEvidenceSequence = kos.newSequence(Tag.CurrentRequestedProcedureEvidenceSequence, 0);
    Attributes currentRequestedProcedureEvidenceSequenceAttributes = new Attributes();
    currentRequestedProcedureEvidenceSequenceAttributes.setString(Tag.StudyInstanceUID, VR.UI, dicomStudy.getStudyInstanceUID());
    currentRequestedProcedureEvidenceSequenceAttributes.setString(Tag.StudyID, VR.UI, dicomStudy.getStudyId());
    currentRequestedProcedureEvidenceSequenceAttributes.setString(Tag.RetrieveURI, VR.UR, dicomManifest.getConfiguration().getIidURL());

    Sequence referencedSeriesSequence = currentRequestedProcedureEvidenceSequenceAttributes.newSequence(Tag.ReferencedSeriesSequence, 0);
    dicomStudy.getSeries().forEach(series -> {
      addReferencedSeriesSequence( referencedSeriesSequence, series, dicomManifest.getConfiguration() );
    });

    currentRequestedProcedureEvidenceSequence.add(currentRequestedProcedureEvidenceSequenceAttributes );
  }

  public static void populateManifest(Attributes kos, DicomManifest dicomManifest ) {
    DicomStudy dicomStudy = dicomManifest.getDicomStudy();
    if ( kos.contains( Tag.CurrentRequestedProcedureEvidenceSequence ) && !kos.getSequence(Tag.CurrentRequestedProcedureEvidenceSequence).isEmpty() ) {
      Attributes currentRequestedProcedureEvidenceSequenceAttributes = kos.getSequence(Tag.CurrentRequestedProcedureEvidenceSequence).getFirst();
      dicomStudy.setStudyInstanceUID( currentRequestedProcedureEvidenceSequenceAttributes.getString( Tag.StudyInstanceUID ) );
      dicomStudy.setStudyId( currentRequestedProcedureEvidenceSequenceAttributes.getString( Tag.StudyID ) );
      dicomManifest.getConfiguration().setIidURL( currentRequestedProcedureEvidenceSequenceAttributes.getString(Tag.RetrieveURI ) );

      Sequence referencedSeriesSequence = currentRequestedProcedureEvidenceSequenceAttributes.getSequence(Tag.ReferencedSeriesSequence);
      if ( referencedSeriesSequence != null && !referencedSeriesSequence.isEmpty()) {
        for ( Attributes referencedSeriesSequenceAttributes : referencedSeriesSequence) {
          populateManifestFromReferencedSeriesSequence( referencedSeriesSequenceAttributes, dicomStudy, dicomManifest.getConfiguration() );
        }
      }
    }
  }


  private static void addReferencedRequestSequence(Attributes kos, DicomManifest dicomManifest) {
    // referenced request sequence
    Sequence referencedRequestSequence = kos.newSequence(Tag.ReferencedSeriesSequence, 0);
    Attributes referencedRequestSequenceAttributes = new Attributes();
    DicomStudy dicomStudy = dicomManifest.getDicomStudy();

    referencedRequestSequenceAttributes.setString(Tag.StudyInstanceUID, VR.UI, dicomStudy.getStudyInstanceUID());
    referencedRequestSequenceAttributes.setString(Tag.StudyID, VR.SH, dicomStudy.getStudyId());
    referencedRequestSequenceAttributes.setString(Tag.StudyDescription, VR.LO, dicomStudy.getStudyDescription());
    if ( dicomStudy.getStudyDateTime() != null ) {
      referencedRequestSequenceAttributes.setDate(Tag.StudyDate, VR.DA, dicomStudy.getStudyDateTime());
      referencedRequestSequenceAttributes.setDate(Tag.StudyTime, VR.TM, dicomStudy.getStudyDateTime());
    }
    referencedRequestSequenceAttributes.setString(Tag.AccessionNumber, VR.UI, dicomStudy.getAccessionNumber());
    {
      Sequence issuerOfAccessionNumberSequence = referencedRequestSequenceAttributes.newSequence(Tag.IssuerOfAccessionNumberSequence, 1);
      Attributes issuerOfAccessionNumberAttributes = new Attributes();
      issuerOfAccessionNumberAttributes.setString(Tag.UniversalEntityID, VR.UT, dicomStudy.getAccessionNumberIssuer().getUniversivalEntityID());
      issuerOfAccessionNumberAttributes.setString(Tag.UniversalEntityIDType, VR.CS, dicomStudy.getAccessionNumberIssuer().getUniversalEntityIDType());
      issuerOfAccessionNumberSequence.add(issuerOfAccessionNumberAttributes);
    }
    {
      referencedRequestSequenceAttributes.setString( Tag.PlacerOrderNumberImagingServiceRequest, VR.UT, dicomStudy.getPlacerOrderNumber() );
      Sequence orderPlacerIdentifierSequence = referencedRequestSequenceAttributes.newSequence(Tag.OrderPlacerIdentifierSequence, 1);
      Attributes orderPlacerIdentifierSequenceAttributes = new Attributes();
      orderPlacerIdentifierSequenceAttributes.setString( Tag.UniversalEntityID, VR.UT, dicomStudy.getPlacerOrderNumberIssuer().getUniversivalEntityID() );
      orderPlacerIdentifierSequenceAttributes.setString( Tag.UniversalEntityIDType, VR.CS, dicomStudy.getPlacerOrderNumberIssuer().getUniversalEntityIDType() );
      orderPlacerIdentifierSequence.add(orderPlacerIdentifierSequenceAttributes);
    }
    referencedRequestSequence.add(referencedRequestSequenceAttributes);
  }

  private static void addReferencedSeriesSequence(Sequence currentRequestedProcedureEvidenceSequence, DicomSerie dicomSerie, Configuration configuration) {
    Attributes seriesAttributes = new Attributes();

    seriesAttributes.setString(Tag.SeriesInstanceUID, VR.UI, dicomSerie.getSeriesInstanceUID() );
    Optional.ofNullable(dicomSerie.getSeriesDateTime()).ifPresent(value -> {
      seriesAttributes.setDate(Tag.SeriesDate, VR.TM, value);
      seriesAttributes.setDate(Tag.SeriesTime, VR.TM, value);
    });
    seriesAttributes.setString(Tag.Modality, VR.CS, dicomSerie.getModality());
    seriesAttributes.setString(Tag.SeriesDescription, VR.LO, dicomSerie.getSeriesDescription());
    seriesAttributes.setString( Tag.SeriesNumber, VR.IS, Integer.toString( dicomSerie.getSeriesNumber() ) );
    seriesAttributes.setString( Tag.BodyPartExamined, VR.CS, dicomSerie.getBodyPartExamined() );
    seriesAttributes.setString( Tag.Laterality, VR.CS, dicomSerie.getLaterality() );

    // TODO Retrieve AE Title
    // TODO Retrieve Location UID
    seriesAttributes.setString( Tag.RetrieveURI, VR.UR, configuration.getWadoURL() );

//    Sequence referencedSopSequence = seriesAttributes.newSequence(Tag.ReferencedSOPSequence, dicomSerie.getInstances().size());
    currentRequestedProcedureEvidenceSequence.add(seriesAttributes);
  }

  static void populateManifestFromReferencedSeriesSequence(Attributes sequenceAttributes, DicomStudy dicomStudy, Configuration configuration) {
    DicomSerie dicomSerie = new DicomSerie();
    dicomSerie.setSeriesInstanceUID( sequenceAttributes.getString( Tag.SeriesInstanceUID ) );
    dicomSerie.setSeriesDescription( sequenceAttributes.getString( Tag.SeriesDescription ) );
    dicomSerie.setModality( sequenceAttributes.getString( Tag.Modality ) );
    dicomSerie.setSeriesNumber( sequenceAttributes.getInt( Tag.SeriesNumber, 0 ) );
    dicomStudy.getModalities().add(dicomSerie.getModality());
    dicomSerie.setBodyPartExamined( sequenceAttributes.getString( Tag.BodyPartExamined ) );
    dicomSerie.setLaterality( sequenceAttributes.getString( Tag.Laterality ) );
    if ( sequenceAttributes.contains(Tag.SeriesDate)){
      dicomSerie.setSeriesDateTime(DicomUtil.getDateFromDicomDateAndTime(sequenceAttributes.getDate( Tag.SeriesDate ), sequenceAttributes.getDate(Tag.SeriesTime), TimeZone.getTimeZone( dicomStudy.getTimeZone()) ) );
    }
    dicomStudy.addSeries( dicomSerie );
    configuration.setWadoURL( sequenceAttributes.getString( Tag.RetrieveURI ) );
  }
}
