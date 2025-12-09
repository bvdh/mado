package org.hl7eu.imagingmanifest.fhir;

import org.hl7.fhir.r4.model.*;
import org.hl7eu.imagingmanifest.model.DicomIssuerInfo;

public class FhirUtil {
    static Coding getModalityCoding(String dicomSerie) {
        return new Coding()
                .setCode(dicomSerie)
                .setSystem("https://dicom.nema.org/medical/dicom/current/output/chtml/part16/sect_CID_29.html");
    }

    public static Reference getReference(Resource resource) {
        if ( resource==null ){ return null; }

        Reference reference = new Reference()
                .setReference( resource.fhirType() + "/" + resource.getId() )
                .setType( resource.fhirType() )
                .setDisplay( resource instanceof Patient patient && !patient.getName().isEmpty() ?
                        patient.getName().get(0).getNameAsSingleString() : null
                );
        if ( resource instanceof ServiceRequest serviceRequest ) {
            if ( serviceRequest.getIdentifierFirstRep() != null ) {
                reference.setIdentifier( serviceRequest.getIdentifierFirstRep() );
            }
        }
        return reference;
    }

    public static String getIssuerSystem(DicomIssuerInfo issuer) {
        if ( issuer==null ) {
            return null;
        }
        switch ( issuer.getUniversalEntityIDType() ){
            case "URI" -> {
                return "urn:uri:"+issuer.getUniversivalEntityID();
            }
            case "ISO" -> {
                return "urn:oid:"+issuer.getUniversivalEntityID();
            }
            case "UUID" -> {
                return issuer.getUniversivalEntityID();
            }
        }
        return null;
    }

    public static String getIssuerValue(String accessionNumber, DicomIssuerInfo issuer) {
        if ( issuer==null ) {
            return accessionNumber;
        }
        switch ( issuer.getUniversalEntityIDType() ){
            case "URI" -> {
                return "urn:uri:"+accessionNumber;
            }
            case "ISO" -> {
                return "urn:oid:"+accessionNumber;
            }
            case "UUID" -> {
                return accessionNumber;
            }
        }
        return null;
    }



}
