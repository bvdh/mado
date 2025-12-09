//package org.hl7eu.imagingmanifest.fhir;
//
//import org.hl7.fhir.r4.model.*;
//import org.hl7eu.imagingmanifest.model.*;
//
//import java.util.Arrays;
//import java.util.Optional;
//import java.util.UUID;
//
//public class FhirPatient {
//    static Patient createPatient(DicomStudy dicomStudy) {
//        Patient patient;
//        if ( dicomStudy.getPatient() != null ) {
//            MadoPatient madoPatient = dicomStudy.getPatient();
//            patient = new Patient();
//            patient.setId( UUID.randomUUID().toString() );
//
//            Optional.ofNullable( madoPatient.getId() ).ifPresent(value ->{
//                Identifier identifier = new Identifier();
//                String issuer = "urn:dicom:patientid";
//                String issuerType = "TEXT";
//                if ( madoPatient.getIssuer()!=null ) { issuer = madoPatient.getIdIssuer(); }
//                if ( madoPatient.getIssuer()!=null ) {
//                    issuer = madoPatient.getIssuer().getUniversivalEntityID();
//                    issuerType = Optional.ofNullable( madoPatient.getIssuer().getUniversalEntityIDType() )
//                            .orElse( "TEXT" );
//                }
//                identifier.setValue( value );
//                identifier.setType( new CodeableConcept()
//                        .addCoding( new Coding()
//                                .setSystem( "http://hl7.org/fhir/v2/0203" )
//                                .setCode( issuerType )
//                        )
//                );
//                identifier.setSystem( issuer );
//                patient.addIdentifier( identifier );
//            } );
//
//            for ( DicomName dicomName : madoPatient.getNames() ) {
//                HumanName humanName = patient.addName();
//                humanName.setFamily( dicomName.getLastName() );
//                humanName.addGiven( dicomName.getFirstName() );
//                Optional.ofNullable(dicomName.getPrefix()).ifPresent( n -> Arrays.asList( n.split(" ")).forEach(humanName::addPrefix));
//                Optional.ofNullable(dicomName.getMiddleName()).ifPresent( n -> Arrays.asList( n.split(" ")).forEach(humanName::addGiven));
//                Optional.ofNullable(dicomName.getSuffix()).ifPresent( n -> Arrays.asList( n.split(" ")).forEach(humanName::addSuffix));
//            }
//            Optional.ofNullable( madoPatient.getGender() ).ifPresent(g -> {
//                        switch (g) {
//                            case "M" -> patient.setGender(Enumerations.AdministrativeGender.MALE);
//                            case "F" -> patient.setGender(Enumerations.AdministrativeGender.FEMALE);
//                            case "O" -> patient.setGender(Enumerations.AdministrativeGender.OTHER);
//                            default -> patient.setGender(Enumerations.AdministrativeGender.UNKNOWN);
//                        };
//                    }
//            );
//            Optional.ofNullable( madoPatient.getBirthDate() ).ifPresent(patient::setBirthDate);
//
//        } else {
//            patient = null;
//        }
//        return patient;
//    }
//
//    static MadoPatient populatePatient(Patient patient) {
//        if ( patient==null ) {
//            return null;
//        }
//
//        MadoPatient madoPatient = new MadoPatient();
//
//        madoPatient.setId( patient.getIdentifierFirstRep()!=null ? patient.getIdentifierFirstRep().getValue() : null );
//        String genderCode = patient.getGender()!=null ? patient.getGender().toCode(): null;
//        String newCode = genderCode == null ? null :
//                genderCode.equals("female") ? "F" :
//                        genderCode.equals("male")   ? "M" :
//                                genderCode.equals("other")  ? "O" : null;
//        madoPatient.setGender(newCode);
//        madoPatient.setBirthDate( patient.getBirthDate() );
//        patient.getName().stream().forEach(name -> {
//            DicomName dicomName = new DicomName();
//            dicomName.setLastName( name.getFamily() );
//            if (!name.getGiven().isEmpty()) {
//                dicomName.setFirstName( name.getGiven().get(0).getValue() );
//            }
//            if (!name.getPrefix().isEmpty()) {
//                dicomName.setPrefix( String.join(" ", name.getPrefix().stream().map( p -> p.getValue() ).toList() ) );
//            }
//            if ( name.getGiven().size() > 1 ) {
//                dicomName.setMiddleName( String.join(" ", name.getGiven().subList(1, name.getGiven().size()).stream().map( p -> p.getValue() ).toList() ) );
//            }
//            if (!name.getSuffix().isEmpty()) {
//                dicomName.setSuffix( String.join(" ", name.getSuffix().stream().map( p -> p.getValue() ).toList() ) );
//            }
//            madoPatient.getNames().add( dicomName );
//        });
//
//        return madoPatient;
//    }
//}
