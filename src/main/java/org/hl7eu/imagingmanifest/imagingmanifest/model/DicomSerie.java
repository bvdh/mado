package org.hl7eu.imagingmanifest.imagingmanifest.model;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.SpecificCharacterSet;
import org.dcm4che2.data.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicomSerie {
    private final DicomObject dcmObj;
    Map<String,DicomInstance> instances = new HashMap<>();
    SpecificCharacterSet specificCharacterSet = new SpecificCharacterSet("UTF-8");
    public DicomSerie(DicomObject dcmObj) {
        this.dcmObj = dcmObj;
    }

    public void addInstance(DicomObject dcmObj) {
        String sopInstanceUid = dcmObj.get(Tag.SOPInstanceUID).getString(specificCharacterSet, false);
        DicomInstance instance = instances.computeIfAbsent(sopInstanceUid, u -> new DicomInstance(dcmObj));
    }

    public String getModality() {
        return dcmObj.get(Tag.Modality).getString(specificCharacterSet, false);
    }
}
