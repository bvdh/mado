package org.hl7eu.imagingmanifest.model;

import java.util.Optional;

public interface GeneralEquipmentModuleInterface {
  public Optional<String> getManufacturer();
  public GeneralEquipmentModuleInterface setManufacturer( String manufacturer );

  public Optional<String> getInstitutionName();
  public GeneralEquipmentModuleInterface setInstitutionName( String institutionName );

  public Optional<CodeSequenceInterface> getInstitutionCodeSequence();
  public GeneralEquipmentModuleInterface setInstitutionCodeSequence( CodeSequenceInterface institutionCodeSequence );

}
