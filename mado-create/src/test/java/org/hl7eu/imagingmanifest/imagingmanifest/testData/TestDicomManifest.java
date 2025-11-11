package org.hl7eu.imagingmanifest.imagingmanifest.testData;

import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomManifest;

public class TestDicomManifest extends DicomManifest
{
    public TestDicomManifest(){
        setConfiguration( new TestConifiguration() );
        setDicomStudy( new TestDicomStudy() );
    }
}

