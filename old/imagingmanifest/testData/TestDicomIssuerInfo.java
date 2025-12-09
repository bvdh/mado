package org.hl7eu.imagingmanifest.old.imagingmanifest.testData;

import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomIssuerInfo;

public class TestDicomIssuerInfo extends DicomIssuerInfo {
    public TestDicomIssuerInfo(String prefix) {
        setUniversivalEntityID( prefix+"-TestUniversalEntityIDType" );
        setUniversalEntityIDType( "URI" );
    }
}
