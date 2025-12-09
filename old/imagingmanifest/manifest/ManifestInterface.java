package org.hl7eu.imagingmanifest.old.imagingmanifest.manifest;

public interface ManifestInterface {
    String getStudyInstanceUID();

    String geViewerUrl();

    String getWadoUrl();

    boolean getStudyDate();

    boolean getStudyTime();
}
