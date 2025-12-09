package org.hl7eu.imagingmanifest.fhir;

import org.hl7.fhir.r4.model.*;
import org.hl7eu.imagingmanifest.model.MadoConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MadoConfigurationFromFhir implements MadoConfiguration {

  private List<Endpoint> endpoints = List.of();
  private Optional<String> webViewerUrl = Optional.empty();
  private Optional<String> wadoUrl = Optional.empty();

  public MadoConfigurationFromFhir(FhirManifest manifest, ImagingStudy imagingStudy ) {

    HashMap<String, Endpoint> endpointMap = new HashMap<>();
    if (imagingStudy != null) {
      if (imagingStudy.hasEndpoint()) {
        for (Reference endpointRef : imagingStudy.getEndpoint()) {
          Resource resource = manifest.getResourceFromBundle(endpointRef.getReference());
          if (resource instanceof Endpoint endpoint) {
            endpointMap.put(endpoint.getId(), endpoint);
          }
        }
      }
      if (imagingStudy.hasSeries()) {
        for (ImagingStudy.ImagingStudySeriesComponent series : imagingStudy.getSeries()) {
          if (series.hasEndpoint()) {
            for (Reference endpointRef : series.getEndpoint()) {
              Resource resource = manifest.getResourceFromBundle(endpointRef.getReference());
              if (resource instanceof Endpoint endpoint) {
                endpointMap.put(endpoint.getId(), endpoint);
              }
            }
          }
        }
      }

      this.endpoints = new ArrayList<>(endpointMap.values());

      endpointMap.values().forEach(endpoint -> {
        String connectionTypeCode = endpoint.getConnectionType().getCode();
        switch (connectionTypeCode) {
          case "dicom-wado-rs" -> this.wadoUrl = Optional.ofNullable(endpoint.getAddress());
//                case "dicom-xc-wado" -> configuration.setXcWadoURL( endpoint.getAddress() );
//                case "dicom-iid" -> configuration.setIidURL( endpoint.getAddress() );
          case "web-viewer" -> this.webViewerUrl = Optional.ofNullable(endpoint.getAddress());
        }
      });

    }
  }

  @Override
  public Optional<String> getWadoURL() {
    return wadoUrl;
  }

  @Override
  public Optional<String> getWebViewerURL() {
    return webViewerUrl;
  }
}
