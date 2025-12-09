package org.hl7eu.imagingmanifest.old.dicom;

import org.hl7eu.imagingmanifest.model.EmptyPatientModule;
import org.hl7eu.imagingmanifest.old.model.ModelUtil;
import org.junit.jupiter.api.Test;

public class DicomPatientModuleTest {
  @Test
  public void TestWithEmptyTestData() {
    EmptyPatientModule emptyPatientModule = new EmptyPatientModule();
    ModelUtil.copyPatientModuleData( emptyPatientModule, emptyPatientModule );

    assertTrue( emptyPatientModule.getPatientName().isEmpty() );
    assertTrue( emptyPatientModule.getPatientID().isEmpty() );
    assertTrue( emptyPatientModule.getIssuerOfPatientID().isEmpty() );
    assertTrue( emptyPatientModule.getPatientBirthDate().isEmpty() );
    assertTrue( emptyPatientModule.getPatientSex().isEmpty() );
    assertTrue( emptyPatientModule.getOtherPatientNames().isEmpty() );
  }
}