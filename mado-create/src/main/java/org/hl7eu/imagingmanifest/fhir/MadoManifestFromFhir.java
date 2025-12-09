//package org.hl7eu.imagingmanifest.fhir;
//
//import org.hl7eu.imagingmanifest.model.DicomStudy;
//import org.hl7eu.imagingmanifest.model.MadoConfiguration;
//import org.hl7eu.imagingmanifest.model.MadoManifest;
//
//public class MadoManifestFromFhir implements MadoManifest {
//  private final FhirManifest fhirManifest;
//
//  public MadoManifestFromFhir(FhirManifest fhirManifestBundle ) {
//    this.fhirManifest = fhirManifestBundle;
//  }
//
//  @Override
//  public MadoConfiguration getGetConfiguration() {
//    return null;
//  }
//
//  @Override
//  public DicomStudy getDicomStudy() {
//    return new MadoDicomStudyFromFhir( fhirManifestBundle );
//  }
//}
