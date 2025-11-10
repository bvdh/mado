package org.hl7eu.imagingmanifest.imagingmanifest.manifest;

import org.apache.jena.sparql.algebra.Op;
import org.checkerframework.checker.units.qual.A;
import org.dcm4che3.data.*;
import org.dcm4che3.util.UIDUtils;
import org.hl7eu.imagingmanifest.imagingmanifest.model.*;

import java.awt.image.DirectColorModel;
import java.util.*;

public class KosManifest {
    private Attributes kos = new Attributes();
    public KosManifest(DicomManifest dicomManifest) {
        // SOP Class
        kos.setString( Tag.SOPInstanceUID, VR.UI, UIDUtils.createUID() );
        kos.setString( Tag.SOPClassUID, VR.UI, UID.KeyObjectSelectionDocumentStorage );
        kos.setDate(   Tag.InstanceCreationDate, VR.DA, new Date() );
        kos.setDate(   Tag.InstanceCreationTime, VR.TM, new Date() );


        addPatientModule(kos, dicomManifest.getDicomStudy());
        addGeneralStudyModule(kos, dicomManifest.getDicomStudy());
        addKeyObjectDocumentSeries(kos);
        addGeneralEquipmentModule(kos, dicomManifest.getDicomStudy().getGeneralEquipment() );
        addKeyObjectDocument(kos, dicomManifest.getDicomStudy() );

        addSrContentModule(kos, dicomManifest);
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

    private void addKeyObjectDocument(Attributes kos, DicomStudy dicomStudy ) {
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

    private void createCurrentRequestedProcedureEvidenceSequence(Attributes kos, DicomStudy dicomStudy) {
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
                        seriesAttributes.setDate(Tag.SeriesDate, VR.TM, value);
                        seriesAttributes.setDate(Tag.SeriesTime, VR.TM, value);
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

    private void createReferencedRequestSequence(Attributes kos, DicomStudy dicomStudy) {
        Sequence referencedRequestSequence = kos.newSequence( Tag.ReferencedRequestSequence, 1 );
        Attributes attributes = new Attributes();

        Optional.ofNullable(dicomStudy.getStudyInstanceUID()).ifPresent(value -> attributes.setString( Tag.StudyInstanceUID, VR.UI, value) );
        Optional.ofNullable(dicomStudy.getStudyId()).ifPresent(value -> attributes.setString( Tag.StudyID, VR.SH, value) );
        Optional.ofNullable(dicomStudy.getStudyDescription()).ifPresent(value -> attributes.setString( Tag.StudyDescription, VR.LO, value) );
        Optional.ofNullable(dicomStudy.getStudyDateTime()).ifPresent(value -> {
            attributes.setDate( Tag.StudyDate, VR.DA, value);
            attributes.setDate( Tag.StudyTime, VR.TM, value);
        } );
        // TODO again?
        Optional.ofNullable(dicomStudy.getAccessionNumber()).ifPresent(value -> attributes.setString( Tag.AccessionNumber, VR.SH, value) );
        Optional.ofNullable(dicomStudy.getAccessionNumberIssuer()).ifPresent(value -> {
            kos.setString( Tag.UniversalEntityID, VR.UT, value.getUniversivalEntityID() );
            kos.setString( Tag.UniversalEntityIDType, VR.CS, value.getUniversalEntityIDType() );
        } );
        referencedRequestSequence.add(attributes);
    }

    private void addGeneralEquipmentModule(Attributes kos, DicomGeneralEquipment dicomGeneralEquipment ) {
        kos.setString( Tag.Manufacturer, VR.LO, dicomGeneralEquipment.getManufacturer() );
        kos.setString( Tag.InstitutionName, VR.LO, dicomGeneralEquipment.getInstitutionName() );
        DicomCodeSequence dicomCodeSequence = dicomGeneralEquipment.getInstitutionCodeSequence();
        Sequence institutionCodeSeq = kos.newSequence( Tag.InstitutionCodeSequence, 1 );
        Attributes attributes = new Attributes();
        attributes.setString( Tag.CodeValue, VR.LO, dicomCodeSequence.getCodeValue() );
        attributes.setString( Tag.CodeMeaning, VR.LO, dicomCodeSequence.getCodeMeaning() );
        attributes.setString( Tag.CodingSchemeDesignator, VR.LO, dicomCodeSequence.getCodingSchemeDesignator() );
        institutionCodeSeq.add(attributes);
    }

    private void addKeyObjectDocumentSeries(Attributes kos ) {
        kos.setString(Tag.Modality, VR.CS, "KO" );
        kos.setString(Tag.SeriesInstanceUID, VR.UI, UIDUtils.createUID() );
        kos.setString( Tag.SeriesNumber, VR.IS, UIDUtils.createUID() );
        kos.setDate( Tag.SeriesDate, VR.DA, new Date() );
        kos.setDate( Tag.SeriesTime, VR.TM, new Date() );
    }


    private void addPatientModule(Attributes kos, DicomStudy dicomStudy) {
        DicomPatient dicomPatient = dicomStudy.getPatient();
        Optional.ofNullable(dicomPatient.getGender()).ifPresent(value -> kos.setString( Tag.PatientSex, VR.CS, value) );
        Optional.ofNullable(dicomPatient.getId()).ifPresent(value -> {
            kos.setString( Tag.PatientID, VR.CS, value);
            kos.setString( Tag.TypeOfPatientID, VR.LO, "TEXT" );
        } );
        Optional.ofNullable(dicomPatient.getIdIssuer()).ifPresent(value -> kos.setString( Tag.IssuerOfPatientID, VR.CS, value) );
        Optional.ofNullable(dicomPatient.getBirthDate()).ifPresent(value -> kos.setDate( Tag.PatientBirthDate, VR.DA, value) );
        kos.addAll( getPatientNames( dicomPatient ) );
    }

    private Attributes getPatientNames(DicomPatient dicomPatient) {
        Attributes names = new Attributes();
        Iterator<DicomName> it = dicomPatient.getNames().iterator();
        if ( it.hasNext() ) {
            DicomName dicomName = it.next();
            kos.setValue( Tag.PatientName, VR.PN, dicomName.getPersonName() );
        }
        while( it.hasNext() ) {
            PersonName otherPersonName = it.next().getPersonName();
            kos.setValue( Tag.OtherPatientNames, VR.PN, otherPersonName );
            kos.setValue( Tag.OtherPatientNames, VR.PN, otherPersonName );
        }
        if ( dicomPatient.getIssuer()!=null){
            DicomIssuerInfo issuer = dicomPatient.getIssuer();
            Optional.ofNullable( issuer.getUniversivalEntityID() ).ifPresent( value -> kos.setString( Tag.UniversalEntityID, VR.UT, value ) );
            Optional.ofNullable( issuer.getUniversalEntityIDType() ).ifPresent( value -> kos.setString( Tag.UniversalEntityIDType, VR.CS, value ) );
        }
        return names;
    }

    private void addGeneralStudyModule(Attributes kos, DicomStudy dicomStudy) {
        Optional.ofNullable(dicomStudy.getStudyInstanceUID()).ifPresent(value -> kos.setString( Tag.StudyInstanceUID, VR.UI, value) );
        Optional.ofNullable(dicomStudy.getStudyId()).ifPresent(value -> kos.setString( Tag.StudyID, VR.SH, value) );
        Optional.ofNullable(dicomStudy.getStudyDescription()).ifPresent(value -> kos.setString( Tag.StudyDescription, VR.LO, value) );
        Optional.ofNullable(dicomStudy.getStudyDateTime()).ifPresent(value -> {
            kos.setDate( Tag.StudyDate, VR.DA, value);
            kos.setDate( Tag.StudyTime, VR.TM, value);
        } );
        Optional.ofNullable(dicomStudy.getAccessionNumber()).ifPresent(value -> kos.setString( Tag.AccessionNumber, VR.SH, value) );
        Optional.ofNullable(dicomStudy.getAccessionNumberIssuer()).ifPresent(value -> {
            kos.setString( Tag.UniversalEntityID, VR.UT, value.getUniversivalEntityID() );
            kos.setString( Tag.UniversalEntityIDType, VR.CS, value.getUniversalEntityIDType() );
        } );
        Optional.ofNullable( dicomStudy.getStudyDescription()).ifPresent(value -> kos.setString( Tag.StudyDescription, VR.LO, value) );
    }

}
