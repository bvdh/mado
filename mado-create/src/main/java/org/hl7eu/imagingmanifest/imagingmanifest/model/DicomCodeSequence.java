package org.hl7eu.imagingmanifest.imagingmanifest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DicomCodeSequence {
    String codeValue;
    String codingSchemeDesignator;
    String codeMeaning;
}
