package org.hl7eu.imagingmanifest.old.imagingmanifest.loader;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.io.DicomInputStream;
import org.hl7eu.imagingmanifest.old.imagingmanifest.manifest.DicomStudyFromDicom;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomStudy;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicomFileLoader {
    Map<String, DicomStudyFromDicom> dicomStudyFromDicomMap = new HashMap<>();

    public void parseFile(String filePath){
        Attributes dcmObj;
        DicomInputStream din = null;
        try {
            din = new DicomInputStream(new File(filePath));
            dcmObj = din.readDataset();
            String studyInstanceUid = dcmObj.getString(org.dcm4che3.data.Tag.StudyInstanceUID);
            DicomStudyFromDicom dicomStudyFromDicom = dicomStudyFromDicomMap.get(studyInstanceUid);
            if ( dicomStudyFromDicom == null ) {
                dicomStudyFromDicom = new DicomStudyFromDicom( dcmObj );
                dicomStudyFromDicomMap.put( studyInstanceUid, dicomStudyFromDicom );
            }
            dicomStudyFromDicom.addInstance(dcmObj);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        finally {
            try {
                din.close();
            }
            catch (IOException ignore) {
            }
        }
    }

    public List<DicomStudy> getStudies() {
        return this.dicomStudyFromDicomMap.values().stream().map( dicomStudyFromDicom -> dicomStudyFromDicom.getDicomStudy() ).toList();
    }
}
