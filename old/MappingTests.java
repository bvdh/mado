package org.hl7eu.imagingmanifest.old;

import org.dcm4che3.data.Attributes;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7eu.imagingmanifest.old.fhir.FhirManifest;
import org.hl7eu.imagingmanifest.old.dicom.DicomManifest;
import org.hl7eu.imagingmanifest.old.kos1.KosManifest;
import org.hl7eu.imagingmanifest.old.testdata.ManifestTestUtil;
import org.hl7eu.imagingmanifest.old.testdata.TestKosManufacturer;
import org.hl7eu.imagingmanifest.old.testdata.TestManifest;
import org.junit.jupiter.api.Test;

public class MappingTests {
  @Test
  public void testEmpty(){
//    TestManistModel testManistModel = new TestManistModel();
    FhirManifest fhirManifest = new FhirManifest( new Bundle() );
    DicomManifest dicomManifest = new DicomManifest( new Attributes() );
    KosManifest kosManifest = new KosManifest( fhirManifest, new TestKosManufacturer() );

    ManifestTestUtil.testWetherManifestsAreTheSame( dicomManifest, fhirManifest );
    ManifestTestUtil.testWetherManifestsAreTheSame( dicomManifest, kosManifest );
    ManifestTestUtil.testWetherManifestsAreTheSame( kosManifest, fhirManifest );
    ManifestTestUtil.testWetherManifestsAreTheSame( kosManifest, dicomManifest );
    ManifestTestUtil.testWetherManifestsAreTheSame( fhirManifest, dicomManifest );
    ManifestTestUtil.testWetherManifestsAreTheSame( fhirManifest, kosManifest );
  }

  @Test
  public void testWithTestData(){
    TestManifest testManistModel = new TestManifest();
    FhirManifest fhirManifest = new FhirManifest( testManistModel );
    DicomManifest dicomManifest = new DicomManifest( testManistModel );
    KosManifest kosManifest = new KosManifest( testManistModel, new TestKosManufacturer() );

    ManifestTestUtil.testWetherManifestsAreTheSame( dicomManifest, fhirManifest );
    ManifestTestUtil.testWetherManifestsAreTheSame( dicomManifest, kosManifest );
    ManifestTestUtil.testWetherManifestsAreTheSame( kosManifest, fhirManifest );
    ManifestTestUtil.testWetherManifestsAreTheSame( kosManifest, dicomManifest );
    ManifestTestUtil.testWetherManifestsAreTheSame( fhirManifest, dicomManifest );
    ManifestTestUtil.testWetherManifestsAreTheSame( fhirManifest, kosManifest );
  }
}
