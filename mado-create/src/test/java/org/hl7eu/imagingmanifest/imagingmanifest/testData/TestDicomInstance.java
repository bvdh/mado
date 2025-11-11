package org.hl7eu.imagingmanifest.imagingmanifest.testData;

import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomInstance;

import java.util.Date;

public class TestDicomInstance extends DicomInstance {
    public TestDicomInstance(int i) {
        setInstanceNumber( i );
        setSopClassUID( "1.2.840.10008.5." );
        setSopInstanceUID( "TestSopInstanceUID-"+i );
        setContentDateTime( new Date() );
        setNumberOfFrames( i+100);
    }
}
