//package org.hl7eu.imagingmanifest.fhir;
//
//import org.hl7.fhir.r4.model.Patient;
//import org.hl7eu.imagingmanifest.model.MadoPatient;
//
//import java.util.Optional;
//
//public class MadoPatientFromFhir extends MadoPatient {
//  private final Patient patient;
//
//  public MadoPatientFromFhir(FhirManifestBundle fhirManifestBundle ) {
//    this.patient = fhirManifestBundle.getPatient();
//  }
//
//  @Override
//  public Optional<String> getGender(){
//    String genderCode = patient.getGender()!=null ? patient.getGender().toCode(): null;
//    if ( genderCode == null ) { return Optional.empty(); }
//
//    String newCode =
//        genderCode.equals("female") ? "F" :
//        genderCode.equals("male")   ? "M" :
//        "O";
//
//    return Optional.of(newCode);
//  }
//}
