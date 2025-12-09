package org.hl7eu.imagingmanifest.old.imagingmanifest.manifest.kos1;

import org.dcm4che3.data.*;
import org.dcm4che3.util.UIDUtils;
import org.hl7eu.imagingmanifest.old.imagingmanifest.DicomUtil;
import org.hl7eu.imagingmanifest.imagingmanifest.model.*;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomInstance;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomManifest;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomSerie;

import java.util.*;

public class KosManifest extends DicomManifest {

    private Attributes kos = new Attributes();
    public KosManifest(DicomManifest dicomManifest) {
        // SOP Class
        kos.setString( Tag.SOPInstanceUID, VR.UI, UIDUtils.createUID() );
        kos.setString( Tag.SOPClassUID, VR.UI, UID.KeyObjectSelectionDocumentStorage );
        kos.setTimezone( TimeZone.getDefault() );
        kos.setString( Tag.InstanceCreationDate, VR.DA, DicomUtil.toDateString( new Date() ));
        kos.setString( Tag.InstanceCreationTime, VR.TM, DicomUtil.toTimeString( new Date() ) );

        KosPatientModule.addPatientModule( kos, dicomManifest );
        KosGeneralStudyModule.addGeneralStudyModule( kos, dicomManifest );
        KeyObjectDocumentModule.addKeyObjectDocumentSeries( kos, dicomManifest );
        GeneralEquipmentModule.addGeneralEquipmentModule( kos, dicomManifest );
//        KeyObjectModule.addKeyObjectDocument( kos, dicomManifest.getDicomStudy() );

        addSrContentModule(kos, dicomManifest);

        populateDicomManifestAttributes( kos );
    }

  private void populateDicomManifestAttributes(Attributes kos) {
    KosPatientModule.populateManifest( kos, this );
    KosGeneralStudyModule.populateManifest( kos, this );
    GeneralEquipmentModule.populateManifest( kos, this );
    KeyObjectDocumentModule.populateManifest( kos, this );

  }

  private void addSrContentModule(Attributes kos, DicomManifest dicomManifest) {
        {
            Sequence conceptNameCodeSequence = kos.newSequence(Tag.ConceptNameCodeSequence, 1);
            Attributes conceptNameCodeSequenceAttributes = new Attributes();
            conceptNameCodeSequence.add(conceptNameCodeSequenceAttributes);

            conceptNameCodeSequenceAttributes.setString(Tag.CodeValue, VR.SH, "113030");
            conceptNameCodeSequenceAttributes.setString(Tag.CodingSchemeDesignator, VR.SH, "DCM");
            conceptNameCodeSequenceAttributes.setString(Tag.CodeMeaning, VR.LO, "Manifest");
        }
        kos.setString( Tag.ContinuityOfContent, VR.CS, "SEPARATE" );
        {
            Sequence contentTemplateSequence = kos.newSequence(Tag.ContentTemplateSequence, 1);
            Attributes contentTemplateSequenceAttributes = new Attributes();
            contentTemplateSequence.add(contentTemplateSequenceAttributes);

            contentTemplateSequenceAttributes.setString(Tag.MappingResource, VR.CS, "DCMR");
            contentTemplateSequenceAttributes.setString(Tag.TemplateIdentifier, VR.CS, "2010");
        }
        {
            int instanceCount = dicomManifest.getDicomStudy().getSeries().stream()
                    .mapToInt( serie -> serie.getInstances().size() )
                    .sum();

            Sequence contentSequence = kos.newSequence(Tag.ContentSequence, instanceCount );

            for ( DicomSerie dicomSerie: dicomManifest.getDicomStudy().getSeries()){
                for ( DicomInstance dicomInstance: dicomSerie.getInstances()) {
                    Attributes contentSequenceAttributes = new Attributes();
                    contentSequence.add(contentSequenceAttributes);

                    contentSequenceAttributes.setString(Tag.RelationshipType, VR.CS, "CONTAINS");
                    String dicomInstanceUID = dicomInstance.getSopInstanceUID();
                    String modality = dicomSerie.getModality();
                    if ( modality != null ) {
                        Set<String> compositeModalities = Set.of(
                                "ASMT", "AU", "Audio", "CTPROTOCOL", "DOC", "FID", "HC", "IOL", "KO", "M3D", "OT", "PLAN", "PR",
                                "REG", "RTDOSE", "RTPLAN", "RTRECORD", "RTSTRUCT", "RWV", "SEG", "SMR", "SR", "STAIN", "TEXTUREMAP"
                        ); // https://dicom.nema.org/medical/dicom/current/output/chtml/part16/sect_CID_32.html
                        if (compositeModalities.contains(modality)) {
                            contentSequenceAttributes.setString(Tag.ValueType, VR.SH, "IMAGE");
                        } else {
                            switch (dicomInstance.getSopClassUID()) {
                                case "1.2.840.10008.5.1.4.1.1.9.1.1":
                                case "1.2.840.10008.5.1.4.1.1.9.1.2":
                                case "1.2.840.10008.5.1.4.1.1.9.1.3":
                                    contentSequenceAttributes.setString(Tag.ValueType, VR.SH, "WAVEFORM");
                                    break;
                                default:
                                    contentSequenceAttributes.setString(Tag.CodeMeaning, VR.LO, "IMAGE");
                            }
                        }

                    }
                    {
                        Sequence referencedSOPSequence = contentSequenceAttributes.newSequence(Tag.ReferencedSOPSequence, 1);
                        Attributes referencedSOPSequenceAttributes = new Attributes();
                        referencedSOPSequence.add(referencedSOPSequenceAttributes);
                        referencedSOPSequenceAttributes.setString(Tag.ReferencedSOPClassUID, VR.UI, dicomInstance.getSopClassUID());
                        referencedSOPSequenceAttributes.setString(Tag.ReferencedSOPInstanceUID, VR.UI, dicomInstance.getSopInstanceUID());
                    }
                }
            }

        }
    }




}
