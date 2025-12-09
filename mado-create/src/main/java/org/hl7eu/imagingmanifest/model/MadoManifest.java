package org.hl7eu.imagingmanifest.model;

import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Endpoint;

public interface MadoManifest {
  MadoConfiguration getGetConfiguration();
  MadoStudy getMadoStudy();
}
