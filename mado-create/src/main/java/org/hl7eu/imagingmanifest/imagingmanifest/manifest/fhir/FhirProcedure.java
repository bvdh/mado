package org.hl7eu.imagingmanifest.imagingmanifest.manifest.fhir;

import org.hl7.fhir.r4.model.Device;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Procedure;
import org.hl7.fhir.r4.model.ServiceRequest;
import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomStudy;

import java.util.List;
import java.util.UUID;

public class FhirProcedure {
    static Procedure createProcedure(DicomStudy dicomStudy, Patient patient, Device device, List<ServiceRequest> serviceRequests) {
        Procedure procedure = (Procedure) new Procedure()
                .setSubject( FhirUtil.getReference( patient ) )
                .addUsedReference( FhirUtil.getReference( device ) )
                .setId( UUID.randomUUID().toString() );

        serviceRequests.forEach(serviceRequest -> procedure.addBasedOn( FhirUtil.getReference( serviceRequest ) ));
        return procedure;


    }
}
