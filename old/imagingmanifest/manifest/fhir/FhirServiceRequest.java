package org.hl7eu.imagingmanifest.old.imagingmanifest.manifest.fhir;

import org.hl7.fhir.r4.model.*;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomIssuerInfo;

import java.util.UUID;

public class FhirServiceRequest {
    static ServiceRequest createAccessionServiceRequest(String accessionNumber, DicomIssuerInfo issuer, Patient patient) {
        ServiceRequest serviceRequest = (ServiceRequest) new ServiceRequest()
                .setStatus( ServiceRequest.ServiceRequestStatus.COMPLETED )
                .setIntent( ServiceRequest.ServiceRequestIntent.ORDER )
                .setSubject( FhirUtil.getReference( patient ) )
                .addIdentifier( new Identifier()
                        .setValue(FhirUtil.getIssuerValue( accessionNumber, issuer ))
                        .setSystem( FhirUtil.getIssuerSystem( issuer ) )
                        .setType( new CodeableConcept()
                                .addCoding( new Coding()
                                        .setCode( accessionNumber)
                                        .setSystem("http://:")
                                )
                        ).setType( new CodeableConcept()
                                .addCoding( new Coding()
                                        .setSystem("http://terminology.hl7.org/CodeSystem/v2-0203")
                                        .setCode("ACSN")
                                )
                        )

                )
                .setId( UUID.randomUUID().toString() );
        ;
        return serviceRequest;
    }

}
