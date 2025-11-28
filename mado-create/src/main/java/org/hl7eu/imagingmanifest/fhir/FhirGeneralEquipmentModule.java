package org.hl7eu.imagingmanifest.fhir;

import org.hl7.fhir.r4.model.Device;
import org.hl7eu.imagingmanifest.model.CodeSequenceInterface;
import org.hl7eu.imagingmanifest.model.GeneralEquipmentModuleInterface;

import java.util.Optional;

/*******************************
 * The institution and manufacturer of the manifest.
 */
public class FhirGeneralEquipmentModule implements GeneralEquipmentModuleInterface {
  private final FhirManifest manifest;

  public FhirGeneralEquipmentModule(FhirManifest manifest) {
    this.manifest = manifest;
  }

  /**************************************************
   * Manugfacturer maps onto Device.
   * @return
   */
  @Override
  public Optional<String> getManufacturer() {
    return manifest.getMainGeneralEquipmentDevice().map(Device::getManufacturer);
  }

  @Override
  public GeneralEquipmentModuleInterface setManufacturer(String manufacturer) {
    manifest.ensureGeneralEquipmentDevice().setManufacturer( manufacturer );
    return this;
  }

  @Override
  public Optional<String> getInstitutionName() {
    Optional<Device> generalEquipmentDevice = manifest.getMainGeneralEquipmentDevice();
    if ( generalEquipmentDevice.isPresent() ) {
      Device device = generalEquipmentDevice.get();
      if ( device.hasLocation() ) {
        return Optional.of( device.getOwner().getDisplay() );
      }
    }
    return Optional.empty();
  }

  @Override
  public GeneralEquipmentModuleInterface setInstitutionName(String institutionName) {
    return null;
  }

  @Override
  public Optional<CodeSequenceInterface> getInstitutionCodeSequence() {
    return Optional.empty();
  }

  @Override
  public GeneralEquipmentModuleInterface setInstitutionCodeSequence(CodeSequenceInterface institutionCodeSequence) {
    return null;
  }
}
