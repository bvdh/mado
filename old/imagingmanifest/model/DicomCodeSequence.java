package org.hl7eu.imagingmanifest.old.imagingmanifest.model;

import lombok.Getter;
import lombok.Setter;
import org.dcm4che3.data.Attributes;

@Getter
@Setter
public class DicomCodeSequence {
    String codeValue;
    String codingSchemeDesignator;
    String codeMeaning;

}
