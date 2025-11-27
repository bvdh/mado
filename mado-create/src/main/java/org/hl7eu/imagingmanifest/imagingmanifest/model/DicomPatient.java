package org.hl7eu.imagingmanifest.imagingmanifest.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class DicomPatient {
    String id;
    DicomIssuerInfo issuer = new DicomIssuerInfo();
    String idIssuer;
    List<DicomName> names = new ArrayList<>();
    String gender;
    Date birthDate;

    public boolean isEmpty() {
        return id==null && ( names == null || names.isEmpty() );
    }
}
