package org.hl7eu.imagingmanifest.imagingmanifest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DicomManifest{
    private Configuration configuration = new Configuration();
    private DicomStudy dicomStudy = new DicomStudy();
}
