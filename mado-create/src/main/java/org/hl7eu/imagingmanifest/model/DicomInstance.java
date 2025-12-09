package org.hl7eu.imagingmanifest.model;

import lombok.Getter;
import lombok.Setter;
import org.dcm4che3.data.Attributes;

import java.util.Date;

@Getter
@Setter
public class DicomInstance {
    private String sopClassUID;
    private String sopInstanceUID;
    private Date contentDateTime;
    private int instanceNumber;
    private int numberOfFrames;

}