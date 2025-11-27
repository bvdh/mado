package org.hl7eu.imagingmanifest.imagingmanifest;

import org.hl7eu.imagingmanifest.imagingmanifest.manifest.fhir.FhirManifest;
import org.hl7eu.imagingmanifest.imagingmanifest.manifest.kos1.KosManifest;
import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomManifest;
import org.hl7eu.imagingmanifest.imagingmanifest.testData.TestDicomManifest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class KosManifestTests {
    @Test
    public void testEmptyManifest() {
      DicomManifest dicomManifest = new DicomManifest();
      KosManifest kosManifest = new KosManifest( dicomManifest );
      assertFalse( false );
    }

    @Test
    public void allFields(){
      DicomManifest dicomManifest = new TestDicomManifest();
      KosManifest kosManifest = new KosManifest( dicomManifest );

      DicomManifestTestUtil.testManifest( dicomManifest, kosManifest );

    }
}
