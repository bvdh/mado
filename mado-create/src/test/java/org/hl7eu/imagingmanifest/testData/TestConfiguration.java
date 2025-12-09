package org.hl7eu.imagingmanifest.testData;

import org.hl7eu.imagingmanifest.model.*;

import java.util.Optional;

public class TestConfiguration implements MadoConfiguration
{
    private String wadoUrl = "http://example.com/wado";
    private String webViewerUrl = "http://example.com/webviewer";

  @Override
  public Optional<String> getWadoURL() {
    return Optional.of(wadoUrl);
  }

  @Override
  public Optional<String> getWebViewerURL() {
    return Optional.of(webViewerUrl);
  }
}
