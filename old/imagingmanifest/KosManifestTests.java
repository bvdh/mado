package org.hl7eu.imagingmanifest.old.imagingmanifest;

import org.hl7eu.imagingmanifest.old.imagingmanifest.manifest.kos1.KosManifest;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomManifest;
import org.hl7eu.imagingmanifest.old.imagingmanifest.testData.TestDicomManifest;
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
