package org.hl7eu.imagingmanifest.old.kos1;

import org.dcm4che3.data.*;
import org.dcm4che3.util.UIDUtils;
import org.hl7eu.imagingmanifest.old.dicom.DicomGeneralEquipmentModule;
import org.hl7eu.imagingmanifest.old.dicom.DicomGeneralStudyModule;
import org.hl7eu.imagingmanifest.old.dicom.DicomPatientModule;
import org.hl7eu.imagingmanifest.old.model.*;

import java.util.TimeZone;

public class KosManifest implements ManifestInterface {
  private final Attributes attributes;

  public KosManifest( ManifestInterface testManistModel, GeneralEquipmentModuleInterface kosManufacturer ) {
    this.attributes = new Attributes();
    attributes.setString( Tag.SOPInstanceUID, VR.UI, UIDUtils.createUID() );
    attributes.setString( Tag.SOPClassUID, VR.UI, UID.KeyObjectSelectionDocumentStorage );
    attributes.setTimezone( TimeZone.getDefault() );
    attributes.setString( Tag.Modality, VR.CS, "KO" );
    this.setGeneralEquipmentModule( kosManufacturer );
    this.setPatientModule( testManistModel.getPatientModule() );
  }

  @Override
  public PatientModuleInterface getPatientModule() {
    return new DicomPatientModule(attributes);
  }

  @Override
  public ManifestInterface setPatientModule(PatientModuleInterface patientModule) {
    ModelUtil.copyPatientModuleData( patientModule, getPatientModule() );
    return this;
  }

  @Override
  public GeneralStudyModuleInterface getGeneralStudyModule() {
    return new DicomGeneralStudyModule(this.attributes);
  }

  @Override
  public ManifestInterface setGeneralStudyModule(GeneralStudyModuleInterface generalStudyModule) {
    ModelUtil.copyGeneralStudyModuleData( generalStudyModule, getGeneralStudyModule() );
    return this;
  }

  @Override
  public GeneralEquipmentModuleInterface getGeneralEquipmentModule() {
    return(  new DicomGeneralEquipmentModule( this.attributes) );
  }

  @Override
  public ManifestInterface setGeneralEquipmentModule(GeneralEquipmentModuleInterface generalEquipmentModule) {
    ModelUtil.copyGeneralEquipmentModule( generalEquipmentModule, getGeneralEquipmentModule() );
    return this;
  }
}
