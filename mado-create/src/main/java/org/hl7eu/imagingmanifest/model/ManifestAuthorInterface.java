package org.hl7eu.imagingmanifest.model;

import java.util.Optional;

public interface ManifestAuthorInterface {
  public Optional<String> getManufacturer();
  public ManifestAuthorInterface setManufacturer(String manufacturer );

  public Optional<String> getInstitutionName();
  public ManifestAuthorInterface setInstitutionName(String institutionName );

  public Optional<CodeSequenceInterface> getInstitutionCodeSequence();
  public ManifestAuthorInterface setInstitutionCodeSequence(CodeSequenceInterface institutionCodeSequence );

}
