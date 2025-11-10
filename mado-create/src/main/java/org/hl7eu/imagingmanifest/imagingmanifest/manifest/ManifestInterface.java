package org.hl7eu.imagingmanifest.imagingmanifest.manifest;

import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomStudy;

public interface ManifestInterface {
    String getStudyInstanceUID();

    String geViewerUrl();

    String getWadoUrl();

    boolean getStudyDate();

    boolean getStudyTime();
}
