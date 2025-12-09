package org.hl7eu.imagingmanifest.old.imagingmanifest.manifest.kos1;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomCodeSequence;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomGeneralEquipment;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomManifest;

public class GeneralEquipmentModule {

  static void addGeneralEquipmentModule(Attributes kos, DicomManifest dicomManifest ) {
    DicomGeneralEquipment dicomGeneralEquipment = dicomManifest.getDicomStudy().getGeneralEquipment();
    if ( dicomGeneralEquipment==null ) {
      return;
    }
    kos.setString( Tag.Manufacturer, VR.LO, dicomGeneralEquipment.getManufacturer() );
    kos.setString( Tag.InstitutionName, VR.LO, dicomGeneralEquipment.getInstitutionName() );
    DicomCodeSequence dicomCodeSequence = dicomGeneralEquipment.getInstitutionCodeSequence();
    Sequence institutionCodeSeq = kos.newSequence( Tag.InstitutionCodeSequence, 1 );
    Attributes attributes = new Attributes();
    attributes.setString( Tag.CodeValue, VR.LO, dicomCodeSequence.getCodeValue() );
    attributes.setString( Tag.CodeMeaning, VR.LO, dicomCodeSequence.getCodeMeaning() );
    attributes.setString( Tag.CodingSchemeDesignator, VR.LO, dicomCodeSequence.getCodingSchemeDesignator() );
    institutionCodeSeq.add(attributes);
  }

  public static void populateManifest(Attributes kos, KosManifest kosManifest) {
    DicomGeneralEquipment generalEquipment = kosManifest.getDicomStudy().getGeneralEquipment();
    generalEquipment.setManufacturer( kos.contains(Tag.Manufacturer) ? kos.getString(Tag.Manufacturer): null );
    generalEquipment.setInstitutionName( kos.contains(Tag.InstitutionName) ? kos.getString(Tag.InstitutionName): null );
    if ( kos.contains( Tag.InstitutionCodeSequence ) ) {
      Sequence institutionCodeSeq = kos.getSequence(Tag.InstitutionCodeSequence);
      if (!institutionCodeSeq.isEmpty()) {
        Attributes attributes = institutionCodeSeq.get(0);
        DicomCodeSequence dicomCodeSequence = new DicomCodeSequence();
        dicomCodeSequence.setCodeValue(attributes.contains(Tag.CodeValue) ? attributes.getString(Tag.CodeValue) : null);
        dicomCodeSequence.setCodeMeaning(attributes.contains(Tag.CodeMeaning) ? attributes.getString(Tag.CodeMeaning) : null);
        dicomCodeSequence.setCodingSchemeDesignator(attributes.contains(Tag.CodingSchemeDesignator) ? attributes.getString(Tag.CodingSchemeDesignator) : null);
        generalEquipment.setInstitutionCodeSequence(dicomCodeSequence);
      }
    }
  }
}
