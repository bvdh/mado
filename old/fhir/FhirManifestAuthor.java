//package org.hl7eu.imagingmanifest.old.fhir;
//
//import org.hl7.fhir.r4.model.*;
//import org.hl7eu.imagingmanifest.model.DicomCodeSequence;
//import org.hl7eu.imagingmanifest.old.model.ManifestAuthorInterface;
//
//import java.util.Optional;
//
//public class FhirManifestAuthor implements ManifestAuthorInterface {
//  private final FhirManifest manifest;
//  private final Coding deviceAutorCode = new Coding("http://terminology.hl7.org/CodeSystem/provenance-participant-type", "assembler ", null);
//  private final Coding institutionCode = new Coding("http://terminology.hl7.org/CodeSystem/provenance-participant-type", "author ", null);
//
//  FhirManifestAuthor( FhirManifest manifest ) {
//    this.manifest = manifest;
//  }
//
//  @Override
//  public Optional<String> getManufacturer() {
////    return Optional.ofNullable(manifest.a.map( Device::getManufacturer );
//  }
//
//  @Override
//  public ManifestAuthorInterface setManufacturer(String manufacturer) {
////    if ( manifestAuthorDevice == null ) {
////      this.manifestAuthorDevice = (Device) new Device().setId("ManifestAuthor");
////    }
////    this.manifestAuthorDevice.setManufacturer(manufacturer);
//    return this;
//  }
//
//  @Override
//  public Optional<String> getInstitutionName() {
////    return Optional.ofNullable( this.manifestAuthorOrganization ).map( Organization::getName );
//  }
//
//  @Override
//  public ManifestAuthorInterface setInstitutionName(String institutionName) {
//    if ( manifestAuthorOrganization == null ) {
//      this.manifestAuthorOrganization = (Organization) new Organization().setId("ManifestAuthorInstitution");
//    }
//    manifestAuthorOrganization.setName( institutionName );
//    return this;
//  }
//
//  @Override
//  public Optional<CodeSequenceInterface> getInstitutionCodeSequence() {
//    return Optional.ofNullable( this.manifestAuthorOrganization )
//        .map(Organization::getType)
//        .filter( types -> !types.isEmpty() )
//        .map( types -> types.get(0).getCodingFirstRep() )
//        .map(FhirCodeSequence::new);
//  }
//
//
//  @Override
//  public ManifestAuthorInterface setInstitutionCodeSequence(CodeSequenceInterface institutionCodeSequence) {
//    return null;
//  }
//}
