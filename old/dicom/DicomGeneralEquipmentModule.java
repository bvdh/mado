package org.hl7eu.imagingmanifest.old.dicom;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.hl7eu.imagingmanifest.model.CodeSequenceInterface;
import org.hl7eu.imagingmanifest.old.model.GeneralEquipmentModuleInterface;
import org.hl7eu.imagingmanifest.old.model.ModelUtil;

import java.util.Optional;

public class DicomGeneralEquipmentModule implements GeneralEquipmentModuleInterface {
  private final Attributes attributes;

  public DicomGeneralEquipmentModule(Attributes attributes) {
    this.attributes = attributes;
  }

  @Override
  public Optional<String> getManufacturer() {
    return Optional.ofNullable( attributes.getString(Tag.Manufacturer) );
  }

  @Override
  public GeneralEquipmentModuleInterface setManufacturer(String manufacturer) {
    attributes.setString( Tag.Manufacturer,  VR.LO, manufacturer );
    return this;
  }

  @Override
  public Optional<String> getInstitutionName() {
    return Optional.ofNullable( attributes.getString(Tag.InstitutionName) );
  }

  @Override
  public GeneralEquipmentModuleInterface setInstitutionName(String institutionName) {
    attributes.setString( Tag.InstitutionName,  VR.LO, institutionName );
    return this;
  }

  @Override
  public Optional<CodeSequenceInterface> getInstitutionCodeSequence() {
    if (attributes.contains( Tag.InstitutionCodeSequence ))
      return Optional.of(new DicomCodeSequence(attributes.getNestedDataset(Tag.InstitutionCodeSequence)));
    return Optional.empty();
  }

  @Override
  public GeneralEquipmentModuleInterface setInstitutionCodeSequence(CodeSequenceInterface institutionCodeSequence) {
    Sequence seq = attributes.getSequence(Tag.InstitutionCodeSequence);
    if ( seq ==null ){
      seq = attributes.newSequence( Tag.InstitutionCodeSequence, 1 );
      seq.add( new Attributes() );
    }
    ModelUtil.copyCodeSequence( institutionCodeSequence, new DicomCodeSequence( seq.getFirst() ));
    return this;
  }
}
