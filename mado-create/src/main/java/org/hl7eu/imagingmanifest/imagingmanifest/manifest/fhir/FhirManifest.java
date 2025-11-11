package org.hl7eu.imagingmanifest.imagingmanifest.manifest.fhir;

import org.hl7.fhir.r4.model.*;
import org.hl7eu.imagingmanifest.imagingmanifest.model.*;
import org.hl7eu.imagingmanifest.imagingmanifest.model.Configuration;

import java.util.*;

public class FhirManifest extends DicomManifest {

    private final Bundle bundle;
    private final List<ServiceRequest> serviceRequests = new ArrayList<>();
    private Procedure procedure;
    private ImagingStudy imagingStudy;
    private Patient patient;
    private List<Endpoint> endpoints;
    private Organization institution;
    private Device device;

    public FhirManifest( Bundle bundle ){
        this.bundle = bundle;
        populate();
    }

    private void populate() {
        setConfiguration( populateConfiguration() );
        setDicomStudy( FhirImagingStudy.populateDicomStudy( imagingStudy, patient) );
        getDicomStudy().setGeneralEquipment( FhirGeneralEquipment.populateGeneralEquipment( device, institution  ) );
    }



    private Configuration populateConfiguration(){
        Configuration configuration = new Configuration();
        endpoints.forEach(endpoint ->{
            String connectionTypeCode = endpoint.getConnectionType().getCode();
            switch (connectionTypeCode) {
                case "dicom-wado-rs" -> configuration.setWadoURL( endpoint.getAddress() );
                case "dicom-xc-wado" -> configuration.setXcWadoURL( endpoint.getAddress() );
                case "dicom-iid" -> configuration.setIidURL( endpoint.getAddress() );
                case "web-viewer" -> configuration.setWebViewerURL( endpoint.getAddress() );
            }
        });
        return configuration;
    }

    public FhirManifest( DicomManifest manifest ){
        endpoints = FhirEndpoints.createEndpoints( manifest );
        patient = FhirPatient.createPatient( manifest.getDicomStudy() );

        Optional.ofNullable( manifest.getDicomStudy().getAccessionNumber() ).ifPresent(accessionNumber -> serviceRequests.add( FhirServiceRequest.createAccessionServiceRequest( accessionNumber, manifest.getDicomStudy().getAccessionNumberIssuer(), patient ) ));
        device = FhirGeneralEquipment.createGeneralEquipmentDevice( manifest, manifest.getDicomStudy().getGeneralEquipment(), institution );
        procedure = FhirProcedure.createProcedure( manifest.getDicomStudy(), patient, device, serviceRequests );
        imagingStudy = FhirImagingStudy.createImagingStudy( manifest, patient, endpoints, serviceRequests );
        institution = FhirInstitution.createGeneralEquipementInstitution( manifest.getDicomStudy().getGeneralEquipment() );

        bundle = (Bundle) new Bundle()
                .setType( Bundle.BundleType.COLLECTION )
                .setId( UUID.randomUUID().toString() );

        Optional.ofNullable( imagingStudy ).ifPresent( value -> bundle.addEntry( createEntryComponent(value)));
        Optional.ofNullable( patient ).ifPresent( value -> bundle.addEntry( createEntryComponent(value)));
        Optional.ofNullable( institution ).ifPresent( value -> bundle.addEntry( createEntryComponent(value)));
        Optional.ofNullable( device ).ifPresent( value -> bundle.addEntry( createEntryComponent(value)));
        Optional.ofNullable( procedure ).ifPresent( value -> bundle.addEntry( createEntryComponent(value)));

        serviceRequests.forEach((serviceRequest) -> bundle.addEntry().setResource(serviceRequest).setFullUrl("Endpoint/"+serviceRequest.getId()));
        endpoints.forEach( (endpoint) -> {bundle.addEntry().setResource(endpoint).setFullUrl("Endpoint/"+endpoint.getId());});

        populate();
    }



    private Bundle.BundleEntryComponent createEntryComponent( Resource resource  ) {
        return new Bundle.BundleEntryComponent()
                .setResource( resource )
                .setFullUrl(resource.fhirType()+ "/" + resource.getId() );

    }






}
