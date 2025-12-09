package org.hl7eu.imagingmanifest.old.testdata;

import org.hl7eu.imagingmanifest.old.model.ManifestInterface;
import org.hl7eu.imagingmanifest.old.model.PatientModuleInterface;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManifestTestUtil {
  public static void testWetherManifestsAreTheSame(ManifestInterface manifest1, ManifestInterface manifest2) {
    testWhetherPatientModulesAreTheSame( manifest1.getPatientModule(), manifest2.getPatientModule() );
  }

  private static void testWhetherPatientModulesAreTheSame(PatientModuleInterface patientModule1, PatientModuleInterface patientModule2) {
    assertEquals( patientModule1.getPatientName(), patientModule2.getPatientName() );
    assertEquals( patientModule1.getPatientID(), patientModule2.getPatientID() );
    assertEquals( patientModule1.getIssuerOfPatientID(), patientModule2.getIssuerOfPatientID() );
    assertEquals( patientModule1.getPatientBirthDate(), patientModule2.getPatientBirthDate() );
    assertEquals( patientModule1.getPatientSex(), patientModule2.getPatientSex() );
    assertEquals( patientModule1.getOtherPatientNames(), patientModule2.getOtherPatientNames() );
    assertEquals( patientModule1.getOtherPatientIDsSequence().size(), patientModule2.getOtherPatientIDsSequence().size() );
    patientModule1.getOtherPatientIDsSequence().forEach( (otherPatientID1) -> {
      boolean found = false;
      for ( var otherPatientID2 : patientModule2.getOtherPatientIDsSequence() ) {
        if ( otherPatientID1.getPatientID().equals( otherPatientID2.getPatientID() ) &&
             otherPatientID1.getIssuerOfPatientID().equals( otherPatientID2.getIssuerOfPatientID() )
        ) {
          found = true;
          break;
        }
      }
      assertEquals( true, found, "OtherPatientIDsSequence item not found in both manifests: " + otherPatientID1.getPatientID().orElse( "<empty>" ) );
    } );
  }
}
