package org.hl7eu.imagingmanifest.imagingmanifest.testData;

import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomCodeSequence;
import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomStudy;

import java.util.Date;
import java.util.Set;

public class TestDicomStudy extends DicomStudy {
    TestDicomStudy(){
        setStudyId( "TestStudyId" );
        setStudyInstanceUID( "TestStudyInstanceUID" );
        setAccessionNumber( "TestAccessionNumber" );
        setStudyDescription( "TestStudyDescription" );
        setAccessionNumber(  "TestAccessionNumber" );
        setAccessionNumberIssuer( new TestDicomIssuerInfo("AccessionNumber") );
        setStudyDateTime( new Date() );

//        DicomCodeSequence anatomicalRegion = new DicomCodeSequence();
//        anatomicalRegion.setCodeValue( "T-D0050" );
//        anatomicalRegion.setCodeMeaning( "Head and Neck" );
//        anatomicalRegion.setCodingSchemeDesignator( "SCT" );
//        setAnatomicalRegion( anatomicalRegion );

        setGeneralEquipment( new TestDicomGeneralEquipment( "GE") );
        setModalities( Set.of("CT","SR") );
        setPatient( new TestDicomPatient() );

        addSerie( new TestDicomSerie( 1 ) );
        addSerie( new TestDicomSerie( 2) );

    }
}
