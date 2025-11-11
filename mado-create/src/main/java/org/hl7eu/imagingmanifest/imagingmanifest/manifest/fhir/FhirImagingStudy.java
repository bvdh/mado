package org.hl7eu.imagingmanifest.imagingmanifest.manifest.fhir;

import org.hl7.fhir.r4.model.*;
import org.hl7eu.imagingmanifest.imagingmanifest.model.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FhirImagingStudy {
    static ImagingStudy createImagingStudy(DicomManifest dicomManifest, Patient patient, List<Endpoint> endpoints, List<ServiceRequest> serviceRequests) {
        DicomStudy dicomStudy = dicomManifest.getDicomStudy();
        ImagingStudy imagingStudy = new ImagingStudy();

        imagingStudy
                .setStatus( ImagingStudy.ImagingStudyStatus.AVAILABLE )
                .setModality( dicomStudy.getModalities().stream()
                        .map(FhirUtil::getModalityCoding
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

        Optional.ofNullable( dicomStudy.getStudyInstanceUID() ).ifPresent(value ->
                imagingStudy
                        .addIdentifier( new Identifier()
                                .setSystem("urn:dicom:uid")
                                .setType( new CodeableConcept()
                                        .addCoding( new Coding()
                                                .setCode( "0020000D")
                                                .setSystem("http://hl7.eu/fhir/imaging-manifest-r5/CodeSystem/codesystem-missing-dicom-terminology")
                                        )
                                )
                                .setValue(value)
                        )

        );
        Optional.ofNullable( dicomStudy.getStudyId() ).ifPresent( value ->
                imagingStudy
                        .addIdentifier( new Identifier()
                                .setSystem("urn:dicom:id")
                                .setType( new CodeableConcept()
                                        .addCoding( new Coding()
                                                .setCode( "00200010")
                                                .setSystem("http://hl7.eu/fhir/imaging-manifest-r5/CodeSystem/codesystem-missing-dicom-terminology")
                                        )
                                )
                                .setValue(value)
                        )

        );

        Optional.ofNullable( dicomStudy.getStudyDateTime() ).ifPresent( value -> imagingStudy.setStarted( value ));
        serviceRequests.stream().forEach( serviceRequest -> {
            imagingStudy.addBasedOn( FhirUtil.getReference( serviceRequest ) );
        });

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
                series.setModality(FhirUtil.getModalityCoding(dicomSerie.getModality())
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

    static DicomStudy populateDicomStudy( ImagingStudy imagingStudy, Patient patient ) {
        DicomStudy dicomStudy = new DicomStudy();
        dicomStudy.setPatient( FhirPatient.populatePatient( patient ) );

        imagingStudy.getIdentifier().forEach(identifier -> {
            if ( identifier.getType()!=null && identifier.getType().getCodingFirstRep()!=null ) {
                String code = identifier.getType().getCodingFirstRep().getCode();
                switch (code) {
                    case "0020000D" -> dicomStudy.setStudyInstanceUID(identifier.getValue());
                    case "00200010" -> dicomStudy.setStudyId(identifier.getValue());
                }
            }
        });
        Optional.ofNullable( imagingStudy.getBasedOn() ).get().stream().forEach( basedOn -> {
            if ( basedOn.getIdentifier()!=null ) {
                Identifier identifier = basedOn.getIdentifier();
                Optional.ofNullable(identifier.getType()).ifPresent(type -> {
                    if ( type.hasCoding( "http://terminology.hl7.org/CodeSystem/v2-0203", "ACSN" ) ){
                        DicomIssuerInfo dicomIssuerInfo = new DicomIssuerInfo();
                        String issuer = identifier.getSystem();
                        if ( issuer!=null ){
                            if ( issuer.startsWith("urn:uri:") ) {
                                dicomIssuerInfo.setUniversalEntityIDType( "URI" );
                                dicomIssuerInfo.setUniversivalEntityID( issuer.substring(8) );
                                dicomStudy.setAccessionNumber( identifier.getValue().substring(8) );
                            } else if ( issuer.startsWith("urn:oid:") ) {
                                dicomIssuerInfo.setUniversalEntityIDType( "ISO" );
                                dicomIssuerInfo.setUniversivalEntityID( issuer.substring(8) );
                                dicomStudy.setAccessionNumber( identifier.getValue().substring(8) );
                            } else {
                                dicomIssuerInfo.setUniversalEntityIDType( "UUID" );
                                dicomIssuerInfo.setUniversivalEntityID( issuer );
                                dicomStudy.setAccessionNumber( identifier.getValue() );
                            }
                        }
                        dicomStudy.setAccessionNumberIssuer( dicomIssuerInfo );

                    }
                });
            }
        });
        dicomStudy.setStudyDescription( imagingStudy.getDescription() );
        dicomStudy.setStudyDateTime( imagingStudy.getStarted() );
        dicomStudy.setModalities( imagingStudy.getModality().stream().map( Coding::getCode)
                .collect( java.util.stream.Collectors.toSet() )
        );

        imagingStudy.getSeries().stream()
                .map(FhirImagingStudy::populateDicomSerie)
                .forEach(dicomStudy::addSerie);

        return dicomStudy;
    }

    static DicomSerie populateDicomSerie(ImagingStudy.ImagingStudySeriesComponent series) {
        DicomSerie dicomSerie = new DicomSerie();
        dicomSerie.setSeriesInstanceUID( series.getUid() );
        dicomSerie.setModality( series.getModality().getCode() );
        dicomSerie.setSeriesNumber( series.getNumber() );
        dicomSerie.setSeriesDescription( series.getDescription() );

        series.getInstance().stream()
                .map( instance -> populateDicomInstance( instance ) )
                .forEach(dicomSerie::addDicomInstance);

        return dicomSerie;
    }

    static DicomInstance populateDicomInstance(ImagingStudy.ImagingStudySeriesInstanceComponent instance) {
        DicomInstance dicomInstance = new DicomInstance();
        dicomInstance.setSopInstanceUID( instance.getUid() );
        dicomInstance.setSopClassUID( instance.getSopClass().getCode() );
        dicomInstance.setInstanceNumber( instance.getNumber() );
        return dicomInstance;
    }

}
