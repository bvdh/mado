package org.hl7eu.imagingmanifest.imagingmanifest.testData;

import org.hl7eu.imagingmanifest.imagingmanifest.model.Configuration;

public class TestConifiguration extends Configuration
{
    TestConifiguration(){
        setIidURL("http://example.com/iid");
        setWadoURL("http://example.com/wado");
        setWebViewerURL("http://example.com/webviewer");
        setXcWadoURL("http://example.com/xcwado");
    }
}
