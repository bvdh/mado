//package org.hl7eu.imagingmanifest.fhir;
//
//import lombok.Getter;
//import org.hl7.fhir.r4.model.*;
//import org.hl7eu.imagingmanifest.model.MadoManifest;
//
//import java.util.*;
//
//public class FhirManifestBundle {
//
//  @Getter private final Bundle bundle;
//  @Getter private Patient patient;
//  @Getter private FhirManifestImagingStudy imagingStudy;
//  @Getter private List<Endpoint> endpoints = new ArrayList<>();
////    private Procedure procedure;
////    private Patient patient;
////    private Organization institution;
////    private Device device;
////    private final List<ServiceRequest> serviceRequests = new ArrayList<>();
//
//  public FhirManifestBundle(Bundle bundle ){
//    this.bundle = bundle;
//
//    // imaging study is first imaging study in the bundle
//    this.imagingStudy = bundle.hasEntry()? bundle.getEntry().stream()
//            .filter( entry -> entry.hasResource() && entry.getResource() instanceof ImagingStudy )
//            .map( entry -> (ImagingStudy) entry.getResource() )
//            .map( imagingStudy -> new FhirManifestImagingStudy( imagingStudy, bundle ) )
//            .findFirst()
//            .orElse( new FhirManifestImagingStudy() )
//        : new FhirManifestImagingStudy();
//
//    // endpoints in imaging study
//    endpoints = setEndpoints( imagingStudy, bundle );
//
//    // patient
//    this.patient = this.imagingStudy.hasSubject()
//        ? (Patient) getResourceFromBundle( bundle, this.imagingStudy.getSubject().getReference() )
//        : new Patient();
//  }
//  /// ////////////////////////////////////////////
//
//  private List<Endpoint> setEndpoints(ImagingStudy imagingStudy, Bundle bundle) {
//    HashMap<String, Endpoint> endpointMap = new HashMap<>();
//    if ( imagingStudy!=null ){
//      if ( imagingStudy.hasEndpoint() ){
//        for ( Reference endpointRef : imagingStudy.getEndpoint() ) {
//          Resource resource = getResourceFromBundle( bundle, endpointRef.getReference() );
//          if (resource instanceof Endpoint endpoint) {
//            endpointMap.put( endpoint.getId(), endpoint );
//          }
//        }
//      }
//      if ( imagingStudy.hasSeries() ){
//        for ( ImagingStudy.ImagingStudySeriesComponent series : imagingStudy.getSeries() ) {
//          if ( series.hasEndpoint() ) {
//            for ( Reference endpointRef : series.getEndpoint() ) {
//              Resource resource = getResourceFromBundle( bundle, endpointRef.getReference() );
//              if (resource instanceof Endpoint endpoint) {
//                endpointMap.put( endpoint.getId(), endpoint );
//              }
//            }
//          }
//        }
//      }
//      return new ArrayList<>( endpointMap.values() );
//    }
//    return new ArrayList<>();
//  }
//
//  private Resource getResourceFromBundle( Bundle bundle, String reference ) {
//    if ( bundle != null && bundle.hasEntry() ) {
//      for ( Bundle.BundleEntryComponent entry : bundle.getEntry() ) {
//        if ( entry.hasFullUrl() && entry.getFullUrl().equals(reference) ) {
//          return (Resource) entry.getResource();
//        }
//      }
//    }
//    return null;
//  }
//
//  public FhirManifestBundle(MadoManifest manifest ){
//    this.bundle = (Bundle) new Bundle()
//        .setType( Bundle.BundleType.COLLECTION )
//        .setId( UUID.randomUUID().toString() )
//    ;
//
////    this.imagingStudy = new FhirManifestImagingStudy( manifest );
//  }
//
//  //    private void populate() {
////        setConfiguration( populateConfiguration() );
////        setDicomStudy( FhirImagingStudy.populateDicomStudy( imagingStudy, patient) );
////        getDicomStudy().setGeneralEquipment( FhirGeneralEquipment.populateGeneralEquipment( device, institution  ) );
////    }
////
////
////
////    private Configuration populateConfiguration(){
////        Configuration configuration = new Configuration();
////        endpoints.forEach(endpoint ->{
////            String connectionTypeCode = endpoint.getConnectionType().getCode();
////            switch (connectionTypeCode) {
////                case "dicom-wado-rs" -> configuration.setWadoURL( endpoint.getAddress() );
//////                case "dicom-xc-wado" -> configuration.setXcWadoURL( endpoint.getAddress() );
//////                case "dicom-iid" -> configuration.setIidURL( endpoint.getAddress() );
////                case "web-viewer" -> configuration.setWebViewerURL( endpoint.getAddress() );
////            }
////        });
////        return configuration;
////    }
////
////    public FhirManifest( DicomManifest manifest ){
////        endpoints = FhirEndpoints.createEndpoints( manifest );
////        patient = FhirPatient.createPatient( manifest.getDicomStudy() );
////
////        Optional.ofNullable( manifest.getDicomStudy().getAccessionNumber() ).ifPresent(accessionNumber -> serviceRequests.add( FhirServiceRequest.createAccessionServiceRequest( accessionNumber, manifest.getDicomStudy().getAccessionNumberIssuer(), patient ) ));
////        device = FhirGeneralEquipment.createGeneralEquipmentDevice( manifest, manifest.getDicomStudy().getGeneralEquipment(), institution );
////        procedure = FhirProcedure.createProcedure( manifest.getDicomStudy(), patient, device, serviceRequests );
////        imagingStudy = FhirImagingStudy.createImagingStudy( manifest, patient, endpoints, serviceRequests );
////        institution = FhirInstitution.createGeneralEquipementInstitution( manifest.getDicomStudy().getGeneralEquipment() );
////
////        bundle = (Bundle) new Bundle()
////                .setType( Bundle.BundleType.COLLECTION )
////                .setId( UUID.randomUUID().toString() );
////
////        Optional.ofNullable( imagingStudy ).ifPresent( value -> bundle.addEntry( createEntryComponent(value)));
////        Optional.ofNullable( patient ).ifPresent( value -> bundle.addEntry( createEntryComponent(value)));
////        Optional.ofNullable( institution ).ifPresent( value -> bundle.addEntry( createEntryComponent(value)));
////        Optional.ofNullable( device ).ifPresent( value -> bundle.addEntry( createEntryComponent(value)));
////        Optional.ofNullable( procedure ).ifPresent( value -> bundle.addEntry( createEntryComponent(value)));
////
////        serviceRequests.forEach((serviceRequest) -> bundle.addEntry().setResource(serviceRequest).setFullUrl("Endpoint/"+serviceRequest.getId()));
////        endpoints.forEach( (endpoint) -> {bundle.addEntry().setResource(endpoint).setFullUrl("Endpoint/"+endpoint.getId());});
////
////        populate();
////    }
////
////
////
////    private Bundle.BundleEntryComponent createEntryComponent( Resource resource  ) {
////        return new Bundle.BundleEntryComponent()
////                .setResource( resource )
////                .setFullUrl(resource.fhirType()+ "/" + resource.getId() );
////
////    }
////
////
//
//
//
//
//}
