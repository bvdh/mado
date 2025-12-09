package org.hl7eu.imagingmanifest.old.imagingmanifest.testData;

import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomManifest;

public class TestDicomManifest extends DicomManifest
{
    public TestDicomManifest(){
        setConfiguration( new TestConifiguration() );
        setDicomStudy( new TestDicomStudy() );
    }
}

