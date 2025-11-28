package org.hl7eu.imagingmanifest.testdata;

import org.checkerframework.checker.units.qual.A;
import org.dcm4che3.data.Attributes;
import org.hl7eu.imagingmanifest.dicom.DicomCodeSequence;
import org.hl7eu.imagingmanifest.dicom.DicomGeneralEquipmentModule;

public class TestKosManufacturer extends DicomGeneralEquipmentModule {
    public TestKosManufacturer() {
      super( new Attributes());
      setManufacturer( "KOS manufacturer" );
      setInstitutionName( "KOS institution" );
      setInstitutionCodeSequence(  new DicomCodeSequence( new Attributes())
          .setCodeMeaning("KOSInstitutionCodeMeaning" )
          .setCodeValue("KOSInstitutionCodeValue")
          .setCodingSchemeDesignator("KOSCodingSchemeDesignator")
      );
    }
}
