package org.hl7eu.imagingmanifest.imagingmanifest.testData;

import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomIssuerInfo;
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
        setModalities( Set.of("CT","SR") );;
        setPatient( new TestDicomPatient() );

        addSerie( new TestDicomSerie( 1 ) );
        addSerie( new TestDicomSerie( 2) );
    }
}
