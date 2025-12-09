package org.hl7eu.imagingmanifest.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public interface MadoConfiguration {
  public Optional<String> getWadoURL();
//    String xcWadoURL;
//    String iidURL;
  public Optional<String> getWebViewerURL();

}
