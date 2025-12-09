package org.hl7eu.imagingmanifest.model;

import java.util.*;

public interface MadoStudy {

  public Optional<String> getStudyInstanceUID();
  public Optional<String> getStudyId();
  public Optional<Date> getStudyDateTime();
//  private String timeZone = "CET";
  public Optional<String>  getStudyDescription();

//  public Optional<String> getAccessionNumber();
//  private DicomIssuerInfo accessionNumberIssuer = new DicomIssuerInfo();
//  private DicomIssuerInfo issuerInfo = new DicomIssuerInfo();
//
//  private Set<String> modalities = new HashSet<>();
//  private MadoPatient patient = new MadoPatient();
//
//  private DicomGeneralEquipment generalEquipment = new DicomGeneralEquipment();
//  private DicomCodeSequence anatomicalRegion = new DicomCodeSequence();
//
//  private String placerOrderNumber;
//  private DicomIssuerInfo placerOrderNumberIssuer = new DicomIssuerInfo();
//
//  public DicomStudy() {
//  }
//
//  public DicomStudy(DicomStudy source) {
//      series = source.series;;
//      studyInstanceUID = source.studyInstanceUID;
//      studyId = source.studyId;
//      studyDateTime = source.studyDateTime;
//      studyDescription = source.studyDescription;
//      accessionNumber = source.accessionNumber;
//      modalities = source.modalities;
//      placerOrderNumber = source.placerOrderNumber;
//      // TODO specimen
//  }
//
//  public void update(){
//    Arrays.stream(this.getClass().getMethods())
//        .filter( method -> method.getName().startsWith("get") )
//        .forEach( method -> {
//          try {
//            method.invoke( this );
//          } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//          } catch (InvocationTargetException e) {
//            throw new RuntimeException(e);
//          }
//        });
//  }
//
//  public void addSeries(DicomSerie dcmSerie)
//  {
//      series.put( dcmSerie.getSeriesInstanceUID(), dcmSerie );
//      String modality = dcmSerie.getModality();
//      if ( modality != null && ! modality.isEmpty() ) {
//          modalities.add( modality );
//      }
//  }
//  public DicomSerie getSerie(String seriesInstanceUID ) {
//      return series.get( seriesInstanceUID )  ;
//  }
//  public void addSerie(DicomSerie dcmSerie) {
//      series.put( dcmSerie.getSeriesInstanceUID(), dcmSerie );
//  }
//
  public List<MadoSerie> getSeries();
}
