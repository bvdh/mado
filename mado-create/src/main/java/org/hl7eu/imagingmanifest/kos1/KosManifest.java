package org.hl7eu.imagingmanifest.kos1;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.UID;
import org.dcm4che3.data.VR;
import org.dcm4che3.util.UIDUtils;
import org.hl7eu.imagingmanifest.dicom.DicomPatientModule;
import org.hl7eu.imagingmanifest.model.ManifestInterface;
import org.hl7eu.imagingmanifest.model.ModelUtil;
import org.hl7eu.imagingmanifest.model.PatientModuleInterface;

import java.util.TimeZone;

public class KosManifest implements ManifestInterface {
  private final Attributes attributes;

  public KosManifest(ManifestInterface testManistModel) {
    this.attributes = new Attributes();
    attributes.setString( Tag.SOPInstanceUID, VR.UI, UIDUtils.createUID() );
    attributes.setString( Tag.SOPClassUID, VR.UI, UID.KeyObjectSelectionDocumentStorage );
    attributes.setTimezone( TimeZone.getDefault() );
    attributes.setString( Tag.Modality, VR.CS, "KO" );
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
}
