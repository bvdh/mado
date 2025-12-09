package org.hl7eu.imagingmanifest.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class MadoPatient {
    private Optional<String> id;
    private DicomIssuerInfo issuer = new DicomIssuerInfo();
    private Optional<String> idIssuer;
    private List<DicomName> names = new ArrayList<>();
    private Optional<String> gender;
    private Optional<Date> birthDate;

    public boolean isEmpty() {
        return id==null && ( names == null || names.isEmpty() );
    }
}
