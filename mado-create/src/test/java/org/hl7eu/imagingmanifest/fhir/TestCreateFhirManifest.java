package org.hl7eu.imagingmanifest.fhir;

import org.hl7eu.imagingmanifest.model.MadoConfiguration;
import org.hl7eu.imagingmanifest.model.MadoSerie;
import org.hl7eu.imagingmanifest.model.MadoStudy;
import org.hl7eu.imagingmanifest.testData.TestMadoManifest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestCreateFhirManifest {
  @Test
  public void testConfiguration() {
    TestMadoManifest manifest = new TestMadoManifest();

    FhirManifest fhirManifestFromManifest = new FhirManifest( manifest );
    FhirManifest fhirManifestFromBundle   = new FhirManifest( fhirManifestFromManifest.getFhirBundle() );

    assertNotNull( fhirManifestFromManifest.getGetConfiguration() );
    assertNotNull( fhirManifestFromBundle.getGetConfiguration() );

    MadoConfiguration testConfiguration             = manifest.getGetConfiguration();
    MadoConfiguration fhirConfigurationFromBundle   = fhirManifestFromBundle.getGetConfiguration();
    MadoConfiguration fhirConfigurationFromManifest = fhirManifestFromManifest.getGetConfiguration();

    assertEquals( testConfiguration.getWadoURL(), fhirConfigurationFromBundle.getWadoURL() );
    assertEquals( testConfiguration.getWadoURL(), fhirConfigurationFromManifest.getWadoURL() );
    assertEquals( testConfiguration.getWebViewerURL(), fhirConfigurationFromManifest.getWebViewerURL() );
    assertEquals( testConfiguration.getWebViewerURL(), fhirConfigurationFromBundle.getWebViewerURL() );
  }

  @Test
  public void testDicomStudy() {
    TestMadoManifest manifest = new TestMadoManifest();

    FhirManifest fhirManifestFromManifest = new FhirManifest( manifest );
    FhirManifest fhirManifestFromBundle   = new FhirManifest( fhirManifestFromManifest.getFhirBundle() );

    assertNotNull( fhirManifestFromManifest.getMadoStudy() );
    assertNotNull( fhirManifestFromBundle.getMadoStudy() );

    MadoStudy testDicomStudy        = manifest.getMadoStudy();
    MadoStudy fhirStudyFromBundle   = fhirManifestFromBundle.getMadoStudy();
    MadoStudy fhirStudyFromManifest = fhirManifestFromManifest.getMadoStudy();

    assertEquals( testDicomStudy.getStudyInstanceUID(), fhirStudyFromBundle.getStudyInstanceUID() );
    assertEquals( testDicomStudy.getStudyInstanceUID(), fhirStudyFromManifest.getStudyInstanceUID() );

    assertEquals( testDicomStudy.getStudyId(), fhirStudyFromManifest.getStudyId() );
    assertEquals( testDicomStudy.getStudyId(), fhirStudyFromBundle.getStudyId() );

    assertEquals( testDicomStudy.getStudyDescription(), fhirStudyFromManifest.getStudyDescription() );
    assertEquals( testDicomStudy.getStudyDescription(), fhirStudyFromBundle.getStudyDescription() );

    assertEquals( testDicomStudy.getSeries().size(), fhirStudyFromManifest.getSeries().size() );
    assertEquals( testDicomStudy.getSeries().size(), fhirStudyFromBundle.getSeries().size() );
  }

  public void testSeries( MadoSerie serie1, MadoSerie serie2 ) {

  }
}
