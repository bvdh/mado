package org.hl7eu.imagingmanifest.imagingmanifest.testData;

import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomIssuerInfo;

public class TestDicomIssuerInfo extends DicomIssuerInfo {
    public TestDicomIssuerInfo(String prefix) {
        setUniversivalEntityID( prefix+"-TestUniversalEntityIDType" );
        setUniversalEntityIDType( "URI" );
    }
}
