package org.hl7eu.imagingmanifest.imagingmanifest.loader;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.SpecificCharacterSet;
import org.dcm4che2.data.Tag;
import org.dcm4che2.io.DicomInputStream;
import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomStudies;
import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomStudy;

import java.io.File;
import java.io.IOException;
import java.lang.classfile.Attribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DicomFileLoader {
    DicomStudies dicomStudies = new DicomStudies();

    public void parseFile(String filePath){
        DicomObject dcmObj;
        DicomInputStream din = null;
        try {
            din = new DicomInputStream(new File(filePath));
            dcmObj = din.readDicomObject();

            dicomStudies.addInstance(dcmObj);
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
        return this.dicomStudies.getStudies();
    }
}
