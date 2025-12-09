package org.hl7eu.imagingmanifest.fhir;

import org.hl7.fhir.r4.model.CodeType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Endpoint;

import java.util.stream.Stream;

public class FhirWebUrlEndpoint extends Endpoint {
  public FhirWebUrlEndpoint(String webViewerUrl ) {
    this.setAddress( webViewerUrl)
        .setName( "Web viewer" )
        .setStatus( EndpointStatus.ACTIVE)
        .setConnectionType(new Coding()
                .setSystem("http://hl7.org/fhir/endpoint-connection-type")
                .setCode("web-viewer")
        )
        .addPayloadType(new CodeableConcept()
                .addCoding(new Coding()
                        .setSystem(" http://hl7.eu/fhir/imaging-manifest-r4/CodeSystem/codesystem-endpoint-terminology")
                        .setCode("web-image-viewer")
                )
        )
        .addPayloadMimeType("text-html")
        .setId("web-viewer-endpoint-"+System.nanoTime());
  }
}
