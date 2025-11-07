package org.hl7eu.imagingmanifest.imagingmanifest.model;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.SpecificCharacterSet;
import org.dcm4che2.data.Tag;

import java.util.HashMap;
import java.util.Map;

public class DicomInstance {
    private final DicomObject dcmObj;
    SpecificCharacterSet specificCharacterSet = new SpecificCharacterSet("UTF-8");

    public DicomInstance( DicomObject dcmObj ) {
        this.dcmObj = dcmObj;
    }
}