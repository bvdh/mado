package org.hl7eu.imagingmanifest.imagingmanifest.model;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.SpecificCharacterSet;
import org.dcm4che2.data.Tag;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;

import java.util.*;

public class DicomStudy {
    private final DicomObject dcmObj;
    Map<String, DicomSerie> series = new HashMap<>();
    SpecificCharacterSet specificCharacterSet = new SpecificCharacterSet("UTF-8");
    public DicomStudy(DicomObject dcmObj) {
        this.dcmObj = dcmObj;
    }

    public void addInstance(DicomObject dcmObj) {
        DicomElement seriesElement = dcmObj.get(Tag.SeriesInstanceUID);
        if (seriesElement != null) {
            String seriesInstanceUid = seriesElement.getString(specificCharacterSet, false);
            DicomSerie serie = series.computeIfAbsent(seriesInstanceUid, u -> new DicomSerie(dcmObj));
            serie.addInstance( dcmObj );
        }


    }

    public String getStudyInstanceUid() {
        return dcmObj.get(Tag.StudyInstanceUID).getString(specificCharacterSet, false);
    }

    public List<Coding> getModalities() {
        Set<String> modalities = new HashSet<>();
        this.series.values().forEach( serie -> {
            modalities.add( serie.getModality() );
        });
        return modalities.stream().map( mod -> {
            Coding coding = new Coding();
            coding.setSystem("http://dicom.nema.org/resources/ontology/DCM");
            coding.setCode(mod);
            return coding;
        }).toList();
    }

    public DicomObject getDicomObj() {
        return this.dcmObj;
    }
}
