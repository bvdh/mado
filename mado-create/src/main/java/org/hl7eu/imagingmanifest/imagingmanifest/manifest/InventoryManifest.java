package org.hl7eu.imagingmanifest.imagingmanifest.manifest;

import org.dcm4che3.data.*;
import org.dcm4che3.util.UIDUtils;
import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomManifest;
import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomStudy;

import java.util.Date;

public class InventoryManifest extends DicomManifest {
    Attributes attributes = new Attributes();
    public InventoryManifest( DicomManifest dicomManifest ) {
        DicomStudy dicomStudy = dicomManifest.getDicomStudy();
        // SOP Class
        attributes.setString( Tag.SOPInstanceUID, VR.UI, UIDUtils.createUID() );
        attributes.setString( Tag.SOPClassUID, VR.UI, UID.InventoryStorage ); // TODO check

        // Inventory
        attributes.setDate(Tag.ContentDate, new Date());
        attributes.setDate(Tag.ContentTime, new Date());
        attributes.setString(Tag.InventoryPurpose, VR.LT, "Manifest" ); // not required
        attributes.setString(Tag.InventoryInstanceDescription, VR.LT, "Manifest with URL's and key images" ); // not required

        attributes.setString(Tag.InventoryLevel, VR.CS, "INSTANCE" ); // STUDY | SERIES | INSTANCE
        attributes.setString(Tag.InventoryCompletionStatus, VR.CS, "COMPLETED" );
        attributes.setInt( Tag.NumberOfStudyRecordsInInstance, VR.UL, 1 ); // fixed to 1
        attributes.setInt( Tag.TotalNumberOfStudyRecords, VR.UL, 1 ); // fixed to 1

        Sequence storedEndpointSeq = attributes.newSequence( Tag.StudyAccessEndPointsSequence  , 1 );
        Attributes storedEndpoint = new Attributes();
        storedEndpointSeq.add( storedEndpoint );
        storedEndpoint.setString( Tag.StoredInstanceBaseURI, VR.UR, dicomManifest.getConfiguration().getWebViewerURL() ); // TODO
        storedEndpoint.setString( Tag.RetrieveURL, VR.UR, dicomManifest.getConfiguration().getWadoURL() ); // TODO

        // add study info
        Sequence incorporatedInventoryInstanceSequence = attributes.newSequence( Tag.IncorporatedInventoryInstanceSequence  , 1 );
        Attributes studyInfo = new Attributes();
        studyInfo.setString(Tag.StudyInstanceUID, VR.UI, dicomStudy.getStudyInstanceUID() );
        studyInfo.setString(Tag.StudyID, VR.UI, dicomStudy.getStudyId() );
        studyInfo.setString(Tag.StudyDescription, VR.LO, dicomStudy.getStudyDescription() );
        if ( dicomStudy.getStudyDateTime() != null ) {
            studyInfo.setDate(Tag.StudyDate, VR.DA, dicomStudy.getStudyDateTime());
            studyInfo.setDate(Tag.StudyTime, VR.TM, dicomStudy.getStudyDateTime());
        }

        // Modalities in Study
        if ( dicomStudy.getModalities().isEmpty()) {}
        else if( dicomStudy.getModalities().size() == 1 ) {
            studyInfo.setString(Tag.ModalitiesInStudy, VR.CS, dicomStudy.getModalities().iterator().next() );
        } else {
            studyInfo.setString(
                            Tag.ModalitiesInStudy,
                            VR.CS,
                            "["+String.join(",", dicomStudy.getModalities())+"]"
                        );
        }
        attributes.setDate( Tag.ItemInventoryDateTime, VR.DT, new Date() );
        attributes.setInt( Tag.NumberOfStudyRelatedSeries, VR.IS, dicomStudy.getSeries().size() );
        attributes.setInt( Tag.NumberOfStudyRelatedInstances, VR.IS,
                dicomStudy.getSeries().stream().mapToInt( serie -> serie.getInstances().size() ).sum()
        );

        incorporatedInventoryInstanceSequence.add( studyInfo );

        dicomStudy.getSeries().stream().forEach(series -> {
            Attributes seriesInfo = new Attributes();
            seriesInfo.setString(Tag.SeriesInstanceUID, VR.UI, series.getSeriesInstanceUID() );
            seriesInfo.setString(Tag.Modality, VR.CS, series.getModality() );
            seriesInfo.setInt(Tag.NumberOfSeriesRelatedInstances, VR.IS, series.getInstances().size() );
            seriesInfo.setString(Tag.SeriesDescription, VR.LO, series.getSeriesDescription() );
            seriesInfo.setString( Tag.BodyPartExamined, VR.CS, series.getBodyPartExamined() );

            Sequence incorporatedSeriesInstanceSequence = seriesInfo.newSequence( Tag.IncorporatedInventoryInstanceSequence  , series.getInstances().size() );
            series.getInstances().forEach( instance -> {
                Attributes instanceInfo = new Attributes();
                instanceInfo.setString(Tag.SOPInstanceUID, VR.UI, instance.getSopInstanceUID());
                instanceInfo.setString(Tag.SOPClassUID, VR.UI, instance.getSopClassUID());
                instanceInfo.setInt(Tag.InstanceNumber, VR.IS, instance.getInstanceNumber());
                // TODO key images
                incorporatedSeriesInstanceSequence.add( instanceInfo );
            });

            incorporatedInventoryInstanceSequence.add( seriesInfo );
        });

        // other inventory data
        attributes.setString( Tag.InventoryCompletionStatus, VR.CS, "COMPLETE" );
        attributes.setInt( Tag.TotalNumberOfStudyRecords, VR.UV, 1 );


    }

    Attributes getInvetoryManifest() {
        return attributes;
    }
}
