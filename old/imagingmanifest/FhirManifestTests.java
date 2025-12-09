package org.hl7eu.imagingmanifest.old.imagingmanifest;

import org.hl7eu.imagingmanifest.old.imagingmanifest.manifest.fhir.FhirManifest;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomManifest;
import org.hl7eu.imagingmanifest.old.imagingmanifest.testData.TestDicomManifest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FhirManifestTests {
    @Test
    public void testEmptyManifest() {
        DicomManifest dicomManifest = new DicomManifest();
        FhirManifest fhirManifest = new FhirManifest( dicomManifest );
        assertFalse( false );
    }

    @Test
    public void allFields(){
        DicomManifest dicomManifest = new TestDicomManifest();
        FhirManifest fhirManifest = new FhirManifest( dicomManifest );

        DicomManifestTestUtil.testManifest( dicomManifest, fhirManifest );

    }
}
