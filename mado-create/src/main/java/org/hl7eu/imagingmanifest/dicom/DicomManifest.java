package org.hl7eu.imagingmanifest.dicom;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.hl7eu.imagingmanifest.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DicomManifest implements ManifestInterface {
  private Attributes attributes;
  private final Map<String, DicomSerie> series = new HashMap<>();

  public DicomManifest(Attributes attributes ) {
    this.attributes = attributes;
  }

  public DicomManifest( ManifestInterface testManistModel) {
    this.attributes = new Attributes();
    setPatientModule( testManistModel.getPatientModule() );
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

  /*********************************************************
   * Add Instance to the DicomStudy
   * @param attributes
   */
  public void addInstance(Attributes attributes) {
    String seriesUid = attributes.getString(Tag.SeriesInstanceUID);
    DicomSerie serie = series.get(seriesUid);
    if ( serie == null ) {
      serie = new DicomSerie( attributes );
    }
    DicomInstance instance = new DicomInstance( attributes );

    serie.addDicomInstance( instance );
  }

  /// ////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public GeneralStudyModuleInterface getGeneralStudyModule() {
    return new DicomGeneralStudyModule( attributes );
  }

  @Override
  public ManifestInterface setGeneralStudyModule(GeneralStudyModuleInterface generalStudyModule) {
    DicomGeneralStudyModule dicomGeneralStudyModule = new DicomGeneralStudyModule( attributes );
    return this;
  }

  @Override
  public GeneralEquipmentModuleInterface getGeneralEquipmentModule() {
    return new DicomGeneralEquipmentModule( attributes );
  }

  @Override
  public ManifestInterface setGeneralEquipmentModule(GeneralEquipmentModuleInterface generalEquipmentModule) {
    DicomGeneralEquipmentModule dicomGeneralEquipmentModule = new DicomGeneralEquipmentModule( attributes );
    ModelUtil.copyGeneralEquipmentModule( generalEquipmentModule, dicomGeneralEquipmentModule );
    return this;
  }
}
