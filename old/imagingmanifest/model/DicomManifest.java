package org.hl7eu.imagingmanifest.old.imagingmanifest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DicomManifest{
    private Configuration configuration = new Configuration();
    private DicomStudy dicomStudy = new DicomStudy();
}
