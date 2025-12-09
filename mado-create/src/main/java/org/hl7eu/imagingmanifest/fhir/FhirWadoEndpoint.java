package org.hl7eu.imagingmanifest.fhir;

import org.hl7.fhir.r4.model.CodeType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Endpoint;

import java.util.List;
import java.util.stream.Stream;

public class FhirWadoEndpoint extends Endpoint {
  public FhirWadoEndpoint(String wadoURL) {
    this.setAddress( wadoURL)
        .setName( "WADO endpoint" )
        .setStatus( EndpointStatus.ACTIVE)
        .setConnectionType(new Coding()
                .setSystem("http://hl7.org/fhir/endpoint-connection-type")
                .setCode("dicom-wado-rs")
        )
        .addPayloadType(new CodeableConcept()
                .addCoding(new Coding()
                        .setSystem("http://terminology.hl7.org/CodeSystem/endpoint-connection-type")
                        .setCode("dicom-wado-rs")
                )
        )
        .setPayloadMimeType(
            Stream.of("dicom", "dicom-octet", "dicom-xml", "dicom-json", "image-jpg", "image-gif", "image-jp2", "image-jph", "image-jxl", "video-mpeg", "video-mp4", "video-H265", "text-html", "text-rtf", "application-pdf").map(CodeType::new).toList()
        )
        .setId("wado-url-endpoint-"+System.nanoTime());
  }
}
