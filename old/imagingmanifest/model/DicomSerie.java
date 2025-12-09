package org.hl7eu.imagingmanifest.old.imagingmanifest.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Getter
@Setter
public class DicomSerie {
    Map<String,DicomInstance> instances = new HashMap<>();
    String modality;
    String seriesInstanceUID;
    String seriesDescription;
    String bodyPartExamined;
    String laterality;
    Integer seriesNumber;
    Date seriesDateTime;

    public void addDicomInstance( DicomInstance dicomInstance )
    {
        instances.put( dicomInstance.getSopInstanceUID(), dicomInstance );
    }
    public List<DicomInstance> getInstances() {
        return instances.values().stream().toList();
    }
}
