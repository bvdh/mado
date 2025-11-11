package org.hl7eu.imagingmanifest.imagingmanifest.testData;

import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomSerie;

public class TestDicomSerie extends DicomSerie {
    TestDicomSerie( int no ){
        setSeriesInstanceUID( "TestSeriesInstanceUID-"+no );
        setModality( "CT" );
        setSeriesDescription( "TestSeriesDescription" );
        setSeriesNumber( 1 );
        setBodyPartExamined( "HEAD" );
        setLaterality( "B" );

        addDicomInstance( new TestDicomInstance( 1 ) );
        addDicomInstance( new TestDicomInstance( 2 ) );
    }
}
