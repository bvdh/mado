package org.hl7eu.imagingmanifest.imagingmanifest.manifest;

import org.hl7.fhir.r4.model.*;
import org.hl7eu.imagingmanifest.imagingmanifest.model.*;
import org.hl7eu.imagingmanifest.imagingmanifest.model.Configuration;

import java.util.*;

public class FhirManifest extends DicomManifest {

    private final Bundle bundle;
    private ImagingStudy imagingStudy;
    private Patient patient;
    private List<Endpoint> endpoints;
    private Organization institution;
    private Device device;

    public FhirManifest( DicomManifest manifest ){
        endpoints = createEndpoints( manifest );
        patient = createPatient( manifest.getDicomStudy() );
        imagingStudy = createImagingStudy( manifest, patient, endpoints );
        institution = createGeneralEquipementInstitution( manifest.getDicomStudy().getGeneralEquipment() );
        device = createGeneralEquipmentDevice( manifest, manifest.getDicomStudy().getGeneralEquipment(), institution );

        bundle = (Bundle) new Bundle()
                .setType( Bundle.BundleType.COLLECTION )
                .addEntry( createEntryComponent( imagingStudy ) )
                .addEntry( createEntryComponent( patient ) )
                .setId( UUID.randomUUID().toString() );
        endpoints.stream().forEach( (endpoint) -> {bundle.addEntry().setResource(endpoint).setFullUrl("Endpoint/"+endpoint.getId());});
        if ( institution != null ) { bundle.addEntry( createEntryComponent( institution )   );}
        if ( device != null ) { bundle.addEntry( createEntryComponent( device )   );}
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

    private List<Endpoint> createEndpoints(DicomManifest manifest) {
        Configuration configuration = manifest.getConfiguration();
        Optional<Object> wadoEndpoint = Optional.ofNullable(configuration.getWadoURL()).map(wado -> new Endpoint()
                .setAddress(wado)
                .setName( "WADO endpoint" )
                .setConnectionType(new Coding()
                        .setSystem("http://hl7.org/fhir/endpoint-connection-type")
                        .setCode("dicom-wado-rs")
                )
                .addPayloadType(new CodeableConcept()
                        .addCoding(new Coding()
                                .setSystem("http://terminology.hl7.org/CodeSystem/endpoint-connection-type")
                                .setCode("dicom-wado-rs")
                        )
                )
                .setPayloadMimeType(List.of("dicom", "dicom-octet", "dicom-xml", "dicom-json", "image-jpg", "image-gif", "image-jp2", "image-jph", "image-jxl", "video-mpeg", "video-mp4", "video-H265", "text-html", "text-rtf", "application-pdf")
                        .stream().map(CodeType::new).toList()
                )

        );
        Optional<Object> xcWadoEndpoint = Optional.ofNullable(configuration.getXcWadoURL()).map(xcwado -> new Endpoint()
                .setAddress(xcwado)
                .setName( "XC WADO endpoint" )
                .setConnectionType(new Coding()
                        .setSystem("http://hl7.org/fhir/endpoint-connection-type")
                        .setCode("dicom-xc-wado")
                )
                .addPayloadType(new CodeableConcept()
                        .addCoding(new Coding()
                                .setSystem("http://terminology.hl7.org/CodeSystem/endpoint-connection-type")
                                .setCode("dicom-xc-wado")
                        )
                )
                .setPayloadMimeType(List.of("dicom", "dicom-octet", "dicom-xml", "dicom-json", "image-jpg", "image-gif", "image-jp2", "image-jph", "image-jxl", "video-mpeg", "video-mp4", "video-H265", "text-html", "text-rtf", "application-pdf")
                        .stream().map(CodeType::new).toList()
                )

        );
        Optional<Object> imegeViewer = Optional.ofNullable(configuration.getIidURL()).map(iid -> new Endpoint()
                .setAddress(iid)
                .setName( "IHE IID endpoint" )
                .setConnectionType(new Coding()
                        .setSystem("http://hl7.org/fhir/endpoint-connection-type")
                        .setCode("dicom-xc-wado")
                )
                .addPayloadType(new CodeableConcept()
                        .addCoding(new Coding()
                                .setSystem(" http://hl7.eu/fhir/imaging-manifest-r4/CodeSystem/codesystem-endpoint-terminology")
                                .setCode("dicom-image-viewer")
                        )
                )
                .addPayloadMimeType("text-html")
        );
        Optional<Object> webViewer = Optional.ofNullable(configuration.getWebViewerURL()).map(webviewer -> new Endpoint()
                .setAddress(webviewer)
                .setName( "Web viewer" )
                .setConnectionType(new Coding()
                        .setSystem("http://hl7.org/fhir/endpoint-connection-type")
                        .setCode("dicom-xc-wado")
                )
                .addPayloadType(new CodeableConcept()
                        .addCoding(new Coding()
                                .setSystem(" http://hl7.eu/fhir/imaging-manifest-r4/CodeSystem/codesystem-endpoint-terminology")
                                .setCode("web-image-viewer")
                        )
                )
                .addPayloadMimeType("text-html")
        );
        return List.of(xcWadoEndpoint, imegeViewer, webViewer).stream().filter(Optional::isPresent).map( Optional::get ).map( obj -> (Endpoint)obj).toList();
    }

    private Patient createPatient(DicomStudy dicomStudy) {
        Patient patient;
        if ( dicomStudy.getPatient() != null ) {
            DicomPatient dicomPatient = dicomStudy.getPatient();
            patient = new Patient();
            patient.setId( UUID.randomUUID().toString() );

            Optional.ofNullable( dicomPatient.getId() ).ifPresent( value ->{
                Identifier identifier = new Identifier();
                String issuer = "urn:dicom:patientid";
                String issuerType = "TEXT";
                if ( dicomPatient.getIssuer()!=null ) { issuer = dicomPatient.getIdIssuer(); }
                if ( dicomPatient.getIssuer()!=null ) {
                    issuer = dicomPatient.getIssuer().getUniversivalEntityID();
                    issuerType = Optional.ofNullable( dicomPatient.getIssuer().getUniversalEntityIDType() )
                            .orElse( "TEXT" );
                }
                identifier.setValue( value );
                identifier.setType( new CodeableConcept()
                        .addCoding( new Coding()
                                .setSystem( "http://hl7.org/fhir/v2/0203" )
                                .setCode( issuerType )
                        )
                );
                identifier.setSystem( issuer );
                patient.addIdentifier( identifier );
            } );

            for ( DicomName dicomName : dicomPatient.getNames() ) {
                HumanName humanName = patient.addName();
                humanName.setFamily( dicomName.getLastName() );
                humanName.addGiven( dicomName.getFirstName() );
                Optional.ofNullable(dicomName.getPrefix()).ifPresent( n -> Arrays.asList( n.split(" ")).forEach(humanName::addPrefix));
                Optional.ofNullable(dicomName.getMiddleName()).ifPresent( n -> Arrays.asList( n.split(" ")).forEach(humanName::addGiven));
                Optional.ofNullable(dicomName.getSuffix()).ifPresent( n -> Arrays.asList( n.split(" ")).forEach(humanName::addSuffix));
            }
            Optional.ofNullable( dicomPatient.getGender() ).ifPresent( g -> {
                        switch (g) {
                            case "M" -> patient.setGender(Enumerations.AdministrativeGender.MALE);
                            case "F" -> patient.setGender(Enumerations.AdministrativeGender.FEMALE);
                            case "O" -> patient.setGender(Enumerations.AdministrativeGender.OTHER);
                            default -> patient.setGender(Enumerations.AdministrativeGender.UNKNOWN);
                        };
                    }
            );
            Optional.ofNullable( dicomPatient.getBirthDate() ).ifPresent(patient::setBirthDate);

        } else {
            patient = null;
        }
        return patient;
    }

    private static ImagingStudy createImagingStudy(DicomManifest dicomManifest, Patient patient, List<Endpoint> endpoints) {
        DicomStudy dicomStudy = dicomManifest.getDicomStudy();
        ImagingStudy imagingStudy = new ImagingStudy();

        imagingStudy
                .addIdentifier( new Identifier()
                        .setSystem("urn:dicom:uid")
                        .setValue(dicomStudy.getStudyInstanceUID())
                )
                .setStatus( ImagingStudy.ImagingStudyStatus.AVAILABLE )
                .setModality( dicomStudy.getModalities().stream()
                        .map( modalityCode -> getModalityCoding(modalityCode)
                        ).toList()
                )
                .setSubject( new Reference()
                        .setReference( patient!=null ? "Patient/" + patient.getId() : null)
                        .setDisplay( patient!=null && !patient.getName().isEmpty() ?
                                patient.getName().get(0).getNameAsSingleString() : null
                        )
                        .setType( "Patient" )
                )
                .setId( UUID.randomUUID().toString() )
        ;

        Optional.ofNullable( dicomStudy.getStudyDateTime() ).ifPresent( value -> imagingStudy.setStarted( value ));
        Optional.ofNullable( dicomStudy.getAccessionNumber() ).ifPresent( value ->{
                    Identifier identifier = new Identifier().setValue( value );
                    DicomIssuerInfo dicomIssuerInfo = Optional.ofNullable(dicomStudy.getAccessionNumberIssuer() ).orElse( new DicomIssuerInfo());
                    String issuer = Optional.ofNullable( dicomIssuerInfo.getUniversivalEntityID() ).orElse( "urn:dicom:accessionnumber" ); //TODO check for default
                    String issuerType = Optional.ofNullable( dicomIssuerInfo.getUniversalEntityIDType() ).orElse( null );
                    imagingStudy.addBasedOn( new Reference().setIdentifier( new Identifier()
                            .setSystem( issuer )
                                    .setType( new CodeableConcept().addCoding( new Coding()
                                                    .setSystem( "http://hl7.org/fhir/v2/0203" ) //TODO check for default
                                                    .setCode( issuerType )
                                            )
                                    )
                            .setValue( value )));
                }

        ) ;

        //    numberOfSeries	(0020,1206)
        imagingStudy.setNumberOfSeries( dicomStudy.getSeries().size() );

        //    numberOfInstances	(0020,1208)
        imagingStudy.setNumberOfInstances( dicomStudy.getSeries().stream()
                .map( serie -> serie.getInstances().size() )
                .reduce(Integer::sum).orElse( 0 )
        );

        //    procedureReference	(0008,1032)
            //    procedureCode	(0008,1032)
            // TODO https://dicom.innolitics.com/ciods/mr-image/general-study/00081032
//        }


        //    location	(0008,1040) | (0040,0243)
//        if ( dcmObj.contains( Tag.InstitutionalDepartmentName)) {
//            String  institutionalDepartmentName = dcmObj.getString(Tag.InstitutionalDepartmentName);
//            // TODO
//        }

        //    reasonCode	(0040,1002)
//        if ( dcmObj.contains( Tag.ReasonForTheRequestedProcedure)){
//            String  reasonForTheRequestedProcedure = dcmObj.getString(Tag.ReasonForTheRequestedProcedure);
//            // TODO
//        }

        //    reasonReference

        //    note

        //    description	(0008,1030)
        if( dicomStudy.getStudyDescription() != null ) {
            imagingStudy.setDescription(dicomStudy.getStudyDescription());
        }

        for ( DicomSerie dicomSerie : dicomStudy.getSeries() ) {
            ImagingStudy.ImagingStudySeriesComponent series = imagingStudy.addSeries();
            series.setUid( dicomSerie.getSeriesInstanceUID() );
            series.setNumber( dicomSerie.getSeriesNumber() );
            if( dicomSerie.getModality()!=null ) {
                series.setModality(getModalityCoding(dicomSerie.getModality())
                );
            }
            series.setDescription( dicomSerie.getSeriesDescription() );
            series.setNumberOfInstances( dicomSerie.getInstances().size() );
            // endpoint
            series.setBodySite( new Coding()
                    .setCode( dicomSerie.getBodyPartExamined() )
                    .setSystem( "https://dicom.nema.org/medical/dicom/current/output/chtml/part16/sect_CID_4.html" )
            );
            if ( series.getLaterality() != null ) {
                series.setLaterality( new Coding()
                        .setCode( dicomSerie.getLaterality() )
                        .setSystem( "http://dicom.nema.org/medical/dicom/current/output/chtml/part16/sect_CID_244.html" )
                );
            }
            // TODO specimen
            series.setStarted( dicomSerie.getSeriesDateTime() );
            // TODO performer

            for( DicomInstance dicomInstance : dicomSerie.getInstances() ) {
                ImagingStudy.ImagingStudySeriesInstanceComponent instance = series.addInstance();
                instance.setUid( dicomInstance.getSopInstanceUID() );
                if ( dicomInstance.getSopClassUID()!=null ){
                    instance.setSopClass( new Coding()
                            .setCode( dicomInstance.getSopClassUID() )
                            .setSystem( "https://dicom.nema.org/medical/dicom/current/output/chtml/part04/sect_B.5.html#table_B.5-1" )
                    );
                }
                instance.setNumber( dicomInstance.getInstanceNumber() );
                // NOT title );

                imagingStudy.setEndpoint( endpoints.stream().map( ep -> new Reference()
                        .setReference( "Endpoint/" + ep.getId())
                        .setType( "Endpoint" )
                        .setDisplay( ep.getName() )
                )
                .toList() );
            }



        }

        return imagingStudy;
    }

    private static Coding getModalityCoding(String dicomSerie) {
        return new Coding()
                .setCode(dicomSerie)
                .setSystem("https://dicom.nema.org/medical/dicom/current/output/chtml/part16/sect_CID_29.html");
    }

}
