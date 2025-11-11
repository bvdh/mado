package org.hl7eu.imagingmanifest.imagingmanifest.manifest.fhir;

import org.apache.jena.sparql.algebra.Op;
import org.hl7.fhir.r4.model.*;
import org.hl7eu.imagingmanifest.imagingmanifest.model.*;
import org.hl7eu.imagingmanifest.imagingmanifest.model.Configuration;

import java.util.*;

public class FhirManifest extends DicomManifest {

    private final Bundle bundle;
    private final List<ServiceRequest> serviceRequests = new ArrayList<ServiceRequest>();
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
    }



    private Configuration populateConfiguration(){
        Configuration configuration = new Configuration();
        endpoints.stream().forEach( endpoint ->{
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

        Optional.ofNullable( manifest.getDicomStudy().getAccessionNumber() ).ifPresent(accessionNumber -> {
            serviceRequests.add( FhirServiceRequest.createAccessionServiceRequest( accessionNumber, manifest.getDicomStudy().getAccessionNumberIssuer(), patient ) );
        });
        imagingStudy = FhirImagingStudy.createImagingStudy( manifest, patient, endpoints, serviceRequests );
        institution = createGeneralEquipementInstitution( manifest.getDicomStudy().getGeneralEquipment() );
        device = createGeneralEquipmentDevice( manifest, manifest.getDicomStudy().getGeneralEquipment(), institution );

        bundle = (Bundle) new Bundle()
                .setType( Bundle.BundleType.COLLECTION )
                .setId( UUID.randomUUID().toString() );

        Optional.ofNullable( imagingStudy ).ifPresent( value -> bundle.addEntry( createEntryComponent(value)));
        Optional.ofNullable( patient ).ifPresent( value -> bundle.addEntry( createEntryComponent(value)));
        Optional.ofNullable( institution ).ifPresent( value -> bundle.addEntry( createEntryComponent(value)));
        Optional.ofNullable( device ).ifPresent( value -> bundle.addEntry( createEntryComponent(value)));

        serviceRequests.forEach((serviceRequest) -> {bundle.addEntry().setResource(serviceRequest).setFullUrl("Endpoint/"+serviceRequest.getId());});
        endpoints.forEach( (endpoint) -> {bundle.addEntry().setResource(endpoint).setFullUrl("Endpoint/"+endpoint.getId());});

        populate();
    }



    private Bundle.BundleEntryComponent createEntryComponent( Resource resource  ) {
        return new Bundle.BundleEntryComponent()
                .setResource( resource )
                .setFullUrl(resource.fhirType()+ "/" + resource.getId() );

    }

    private Organization createGeneralEquipementInstitution( DicomGeneralEquipment generalEquipment) {
        if (generalEquipment == null || (generalEquipment.getInstitutionName() == null && generalEquipment.getInstitutionCodeSequence() == null)) {
            return null;
        }
        Organization organization = (Organization) new Organization().setId( UUID.randomUUID().toString() );
        organization.setName(generalEquipment.getInstitutionName());
        if (generalEquipment.getInstitutionCodeSequence() != null && generalEquipment.getInstitutionCodeSequence().getCodeValue() != null) {
            DicomCodeSequence codeSequence = generalEquipment.getInstitutionCodeSequence();
            CodeableConcept codeableConcept = new CodeableConcept();
            Coding coding = new Coding()
                    .setSystem(codeSequence.getCodingSchemeDesignator())
                    .setCode(codeSequence.getCodeValue())
                    .setDisplay(codeSequence.getCodeMeaning());
            codeableConcept.addCoding(coding);
            organization.addType(codeableConcept);

        }
        return organization;
    }

    private Device createGeneralEquipmentDevice(DicomManifest manifest, DicomGeneralEquipment generalEquipment, Organization institution) {
        if ( generalEquipment == null ) { return null; }
        Device device = (Device) new Device()
                .setManufacturer( generalEquipment.getManufacturer() )
                .setId( UUID.randomUUID().toString() )
                ;
        if ( institution != null ) {
            device.setOwner( new Reference()
                    .setReference( "Organization/" + institution.getId() )
                    .setType( "Organization" )
                    .setDisplay( institution.getName() )
            );
        }
        return null;
    }


}
