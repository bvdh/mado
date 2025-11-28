package org.hl7eu.imagingmanifest.fhir;

import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Device;
import org.hl7eu.imagingmanifest.model.CodeSequenceInterface;
import org.hl7eu.imagingmanifest.model.ManifestAuthorInterface;

import java.util.Optional;

public class FhirManifestAuthor implements ManifestAuthorInterface {
  private final FhirManifest manifest;
  private final Coding deviceAutorCode = new Coding("http://terminology.hl7.org/CodeSystem/provenance-participant-type", "assembler ", null);
  private final Coding institutionCode = new Coding("http://terminology.hl7.org/CodeSystem/provenance-participant-type", "author ", null);

  FhirManifestAuthor(FhirManifest manifest ) {
    this.manifest = manifest;
  }
  @Override
  public Optional<String> getManufacturer() {
    manifest.getManifestAuthor().ifPresent( author -> {
      if ( author.hasAgent() ){
        for( var agent : author.getAgent() ) {
          if ( agent.hasType() && agent.getType().hasCoding("http://terminology.hl7.org/CodeSystem/provenance-participant-type", "author ")) {
            return manifest.getResourceFromBundle( agent.getWho() ).map( resource -> {);
              if ( resource instanceof Device) {
                return ((Device)resource).getManufacturer();
              }
              return Optional.empty();
            } );
          }
        }
      }
    } );
    return Optional.empty();
  }

  @Override
  public ManifestAuthorInterface setManufacturer(String manufacturer) {
    manifest.ensureManifestAuthor().ifPresent( author -> {
      if ( author.hasAgent() ){
        for( var agent : author.getAgent() ) {
          if ( agent.hasType() && agent.getType().hasCoding("http://terminology.hl7.org/CodeSystem/provenance-participant-type", "author ")) {
            manifest.getResourceFromBundle( agent.getWho() ).ifPresent( resource -> {
              if ( resource instanceof Device) {
                ((Device)resource).setManufacturer( manufacturer );
              }
            } );
          }
        }
      }
    } );
    return this;
  }

  @Override
  public Optional<String> getInstitutionName() {
    return Optional.empty();
  }

  @Override
  public ManifestAuthorInterface setInstitutionName(String institutionName) {
    return null;
  }

  @Override
  public Optional<CodeSequenceInterface> getInstitutionCodeSequence() {
    return Optional.empty();
  }

  @Override
  public ManifestAuthorInterface setInstitutionCodeSequence(CodeSequenceInterface institutionCodeSequence) {
    return null;
  }
}
