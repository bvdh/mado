package org.hl7eu.imagingmanifest.testData;

import org.hl7eu.imagingmanifest.model.*;

public class TestMadoManifest implements MadoManifest
{

  @Override
  public MadoConfiguration getGetConfiguration() {
    return new TestConfiguration();
  }

  @Override
  public MadoStudy getMadoStudy() {
    return new TestDicomStudy();
  }

}

