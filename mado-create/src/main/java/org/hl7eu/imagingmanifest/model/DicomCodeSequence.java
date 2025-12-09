package org.hl7eu.imagingmanifest.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter( AccessLevel.PUBLIC )
@Setter( AccessLevel.PROTECTED )
public class DicomCodeSequence {
    String codeValue;
    String codingSchemeDesignator;
    String codeMeaning;
}
