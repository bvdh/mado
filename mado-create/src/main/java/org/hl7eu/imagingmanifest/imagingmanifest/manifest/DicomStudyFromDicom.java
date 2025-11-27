package org.hl7eu.imagingmanifest.imagingmanifest.manifest;

import lombok.Getter;
import org.dcm4che3.data.*;
import org.hl7eu.imagingmanifest.imagingmanifest.DicomUtil;
import org.hl7eu.imagingmanifest.imagingmanifest.model.*;

import java.util.Date;
import java.util.TimeZone;

public class DicomStudyFromDicom {
    DicomStudy dicomStudy = new DicomStudy();
    public DicomStudyFromDicom(Attributes dcmObj){
        dicomStudy.setStudyInstanceUID( dcmObj.getString(Tag.StudyInstanceUID) );
        dicomStudy.setStudyId( dcmObj.getString( Tag.StudyID ) );
        dicomStudy.setStudyDescription( dcmObj.getString(Tag.StudyDescription) );
        dicomStudy.setAccessionNumber( dcmObj.getString(Tag.AccessionNumber) );
        if ( dcmObj.contains( Tag.IssuerOfAccessionNumberSequence ) ){
            DicomIssuerInfo dicomIssuerInfo =  new DicomIssuerInfo();
            dicomIssuerInfo.setUniversivalEntityID( dcmObj.getString( Tag.UniversalEntityID ) );
            dicomIssuerInfo.setUniversalEntityIDType( dcmObj.getString( Tag.UniversalEntityIDType ) );
            dicomStudy.setAccessionNumberIssuer( dicomIssuerInfo );
        }
//        dicomStudy.setAccessionNumber( dcmObj.getString(Tag.IssuerOfAccessionNumberSequence) );

        //    started	(0008,0020)+(0008,0030)
        Date studyDate = dcmObj.getDate( Tag.StudyDate );
        Date studyTime = dcmObj.getDate( Tag.StudyTime );
        dicomStudy.setStudyDateTime( DicomUtil.getDateFromDicomDateAndTime(studyDate, studyTime, TimeZone.getTimeZone( dicomStudy.getTimeZone())) );
        dicomStudy.setPatient( createPatient( dcmObj ) );
        dicomStudy.setGeneralEquipment( createGeneralEquipment( dcmObj ) );

//        if ( dcmObj.contains( Tag.AnatomicRegionSequence ) ){
//            DicomCodeSequence anatomicRegion = new DicomCodeSequence();
//            Sequence anatomicRegionSeq = dcmObj.getSequence( Tag.AnatomicRegionSequence );
//            Attributes attributes = anatomicRegionSeq.getFirst();
//            anatomicRegion.setCodeValue( attributes.getString( Tag.CodeValue ) );
//            anatomicRegion.setCodeMeaning( attributes.getString( Tag.CodeMeaning ) );
//            anatomicRegion.setCodingSchemeDesignator( attributes.getString( Tag.CodingSchemeDesignator ) );
//            dicomStudy.setAnatomicalRegion( anatomicRegion );
//        }

        addInstance( dcmObj );
    }

    private DicomGeneralEquipment createGeneralEquipment(Attributes dcmObj) {
        DicomGeneralEquipment generalEquipment = new DicomGeneralEquipment();
        generalEquipment.setManufacturer( dcmObj.getString( Tag.Manufacturer ) );
        generalEquipment.setInstitutionName( dcmObj.getString( Tag.InstitutionName ) );
        if ( dcmObj.contains( Tag.InstitutionCodeSequence ) ){
            Sequence institutionCodeSeq = dcmObj.getSequence( Tag.InstitutionCodeSequence );
            DicomCodeSequence dicomCodeSequence = new DicomCodeSequence();
            if ( !institutionCodeSeq.isEmpty() ){
                Attributes attributes = institutionCodeSeq.getFirst();
                dicomCodeSequence.setCodeValue( attributes.getString( Tag.CodeValue ) );
                dicomCodeSequence.setCodeMeaning( attributes.getString( Tag.CodeMeaning ) );
                dicomCodeSequence.setCodingSchemeDesignator( attributes.getString( Tag.CodingSchemeDesignator ) );
            }
            generalEquipment.setInstitutionCodeSequence( dicomCodeSequence );
        }
        return generalEquipment;
    }

    private DicomPatient createPatient(Attributes dcmObj) {
        DicomPatient dicomPatient = new DicomPatient();
        dicomPatient.setId( dcmObj.getString( Tag.PatientID ) );
        dicomPatient.setIdIssuer( dcmObj.getString( Tag.IssuerOfPatientID ) );
        if( dcmObj.contains( Tag.IssuerOfPatientIDQualifiersSequence  ) ){
            DicomIssuerInfo issuer = new DicomIssuerInfo();
            issuer.setUniversalEntityIDType( dcmObj.getString( Tag.IssuerOfPatientIDQualifiersSequence ) );
            issuer.setUniversivalEntityID( dcmObj.getString( Tag.IssuerOfPatientID ) );
            dicomPatient.setIssuer( issuer );
        }
        if ( dcmObj.contains( Tag.PatientName ) ){
            PersonName personName = new PersonName( dcmObj.getString( Tag.PatientName ) );
            DicomName patientName = new DicomName( personName );
            dicomPatient.getNames().add( patientName );
        }
        if ( dcmObj.contains( Tag.OtherPatientNames ) ){
            String[] pns = dcmObj.getStrings(Tag.OtherPatientNames);
            for ( String pn : pns ){
                PersonName personName = new PersonName( pn );
                DicomName otherPatientName = new DicomName( personName );
                dicomPatient.getNames().add( otherPatientName );
            }
        }
        if ( dcmObj.contains( Tag.PatientBirthDate ) ){
            dicomPatient.setBirthDate( DicomUtil.getDateFromDicomDateAndTime( dcmObj.getDate( Tag.PatientBirthDate ), dcmObj.getDate( Tag.PatientBirthTime ), TimeZone.getTimeZone( dicomStudy.getTimeZone()) ) );
        }


        dicomPatient.setGender( dcmObj.getString(Tag.PatientSex) );

        return dicomPatient.isEmpty() ? null : dicomPatient;
    }

    public void addInstance(Attributes dcmObj) {
        String seriesUid = dcmObj.getString(Tag.SeriesInstanceUID);
        DicomSerie serie = dicomStudy.getSerie(seriesUid);
        if ( serie == null ) {
             serie = new DicomSerie();

            serie.setSeriesInstanceUID( seriesUid );
            serie.setModality( dcmObj.getString(Tag.Modality) );
            if ( serie.getModality()!=null ){
                dicomStudy.getModalities().add( serie.getModality() );
            }
            serie.setSeriesDescription( dcmObj.getString(Tag.SeriesDescription) );
            serie.setSeriesNumber( dcmObj.getInt(Tag.SeriesNumber, 0) );
            serie.setLaterality( dcmObj.getString(Tag.Laterality) );
            serie.setBodyPartExamined( dcmObj.getString(Tag.BodyPartExamined) );
            serie.setSeriesDateTime( DicomUtil.getDateFromDicomDateAndTime(
                    dcmObj.getDate( Tag.SeriesDate ),
                    dcmObj.getDate( Tag.SeriesTime ),
                    TimeZone.getTimeZone( dicomStudy.getTimeZone())
            ) );
            serie.setBodyPartExamined( dcmObj.getString(Tag.BodyPartExamined) );
            dicomStudy.addSerie( serie );
        }
        DicomInstance instance = new DicomInstance();
        instance.setSopInstanceUID( dcmObj.getString(Tag.SOPInstanceUID) );
        instance.setInstanceNumber( dcmObj.getInt(Tag.InstanceNumber, 0) );
        instance.setSopClassUID( dcmObj.getString(Tag.SOPClassUID) );

        serie.addDicomInstance( instance );
    }

    @Getter
    String viewerUrl = "undefined";
    @Getter
    String wadoUrl = "undefined";

    public DicomStudy getDicomStudy() {
        return this.dicomStudy;
    }
}
