package org.hl7eu.imagingmanifest.imagingmanifest.model;

import lombok.Getter;
import lombok.Setter;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;

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
