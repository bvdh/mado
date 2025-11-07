package org.hl7eu.imagingmanifest.imagingmanifest.model;


import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.SpecificCharacterSet;
import org.dcm4che2.data.Tag;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DicomStudies {
    Map<String, DicomStudy> studies = new java.util.HashMap<>();;

    public List<DicomStudy> getStudies() { return studies.values().stream().toList(); }

    public void addInstance(DicomObject dcmObj) {
        SpecificCharacterSet specificCharacterSet = new SpecificCharacterSet("UTF-8");
        String studyInstanceUid = dcmObj.get(Tag.StudyInstanceUID).getString(specificCharacterSet, false);

        DicomStudy study = studies.computeIfAbsent(studyInstanceUid, u -> new DicomStudy(dcmObj));
        study.addInstance(dcmObj);
    }

}
