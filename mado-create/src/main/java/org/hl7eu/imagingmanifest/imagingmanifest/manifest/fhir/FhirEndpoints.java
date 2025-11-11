package org.hl7eu.imagingmanifest.imagingmanifest.manifest.fhir;

import org.hl7.fhir.r4.model.CodeType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Endpoint;
import org.hl7eu.imagingmanifest.imagingmanifest.model.Configuration;
import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomManifest;

import java.util.List;
import java.util.Optional;

public class FhirEndpoints {
    static List<Endpoint> createEndpoints(DicomManifest manifest) {
        Configuration configuration = manifest.getConfiguration();
        Optional<Object> wadoEndpoint = Optional.ofNullable(configuration.getWadoURL()).map(wado -> new Endpoint()
                .setAddress(wado)
                .setName( "WADO endpoint" )
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
                .setPayloadMimeType(List.of("dicom", "dicom-octet", "dicom-xml", "dicom-json", "image-jpg", "image-gif", "image-jp2", "image-jph", "image-jxl", "video-mpeg", "video-mp4", "video-H265", "text-html", "text-rtf", "application-pdf")
                        .stream().map(CodeType::new).toList()
                )

        );
        Optional<Object> xcWadoEndpoint = Optional.ofNullable(configuration.getXcWadoURL()).map(xcwado -> new Endpoint()
                .setAddress(xcwado)
                .setName( "XC WADO endpoint" )
                .setConnectionType(new Coding()
                        .setSystem("http://hl7.org/fhir/endpoint-connection-type")
                        .setCode("dicom-xc-wado")
                )
                .addPayloadType(new CodeableConcept()
                        .addCoding(new Coding()
                                .setSystem("http://terminology.hl7.org/CodeSystem/endpoint-connection-type")
                                .setCode("dicom-xc-wado")
                        )
                )
                .setPayloadMimeType(List.of("dicom", "dicom-octet", "dicom-xml", "dicom-json", "image-jpg", "image-gif", "image-jp2", "image-jph", "image-jxl", "video-mpeg", "video-mp4", "video-H265", "text-html", "text-rtf", "application-pdf")
                        .stream().map(CodeType::new).toList()
                )

        );
        Optional<Object> imegeViewer = Optional.ofNullable(configuration.getIidURL()).map(iid -> new Endpoint()
                .setAddress(iid)
                .setName( "IHE IID endpoint" )
                .setConnectionType(new Coding()
                        .setSystem("http://hl7.org/fhir/endpoint-connection-type")
                        .setCode("dicom-iid")
                )
                .addPayloadType(new CodeableConcept()
                        .addCoding(new Coding()
                                .setSystem(" http://hl7.eu/fhir/imaging-manifest-r4/CodeSystem/codesystem-endpoint-terminology")
                                .setCode("dicom-image-viewer")
                        )
                )
                .addPayloadMimeType("text-html")
        );
        Optional<Object> webViewer = Optional.ofNullable(configuration.getWebViewerURL()).map(webviewer -> new Endpoint()
                .setAddress(webviewer)
                .setName( "Web viewer" )
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
        );
        return List.of(wadoEndpoint, xcWadoEndpoint, imegeViewer, webViewer).stream().filter(Optional::isPresent).map( Optional::get ).map( obj -> (Endpoint)obj).toList();
    }
}
