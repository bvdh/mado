package org.hl7eu.imagingmanifest.imagingmanifest.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class DicomStudy {

    private Map<String, DicomSerie> series = new HashMap<>();
    private String studyInstanceUID;
    private String studyId;
    private Date   studyDateTime;
    private String studyDescription;
    private String accessionNumber;
    private DicomIssuerInfo accessionNumberIssuer;
    private DicomIssuerInfo issuerInfo;
    private Set<String> modalities = new HashSet<>();
    private DicomPatient patient;
    private DicomGeneralEquipment generalEquipment;
    public DicomStudy() {
    }

    public DicomStudy(DicomStudy source) {
        series = source.series;;
        studyInstanceUID = source.studyInstanceUID;
        studyId = source.studyId;
        studyDateTime = source.studyDateTime;
        studyDescription = source.studyDescription;
        accessionNumber = source.accessionNumber;
        modalities = source.modalities;
    }

    public void addSeries(DicomSerie dcmSerie)
    {
        series.put( dcmSerie.getSeriesInstanceUID(), dcmSerie );
        String modality = dcmSerie.getModality();
        if ( modality != null && ! modality.isEmpty() ) {
            modalities.add( modality );
        }
    }
    public DicomSerie getSerie( String seriesInstanceUID ) {
        return series.get( seriesInstanceUID )  ;
    }
    public void addSerie(DicomSerie dcmSerie) {
        series.put( dcmSerie.getSeriesInstanceUID(), dcmSerie );
    }

    public List<DicomSerie> getSeries() {
        return series.values().stream().toList();
    }

}
