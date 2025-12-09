package org.hl7eu.imagingmanifest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DicomGeneralEquipment {
    // add optional usefull fields?
    String manufacturer;
    String institutionName;
    DicomCodeSequence institutionCodeSequence = new DicomCodeSequence();
}
