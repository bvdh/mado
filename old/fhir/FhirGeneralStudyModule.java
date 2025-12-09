package org.hl7eu.imagingmanifest.old.fhir;

import org.hl7.fhir.r4.model.*;
import org.hl7eu.imagingmanifest.old.imagingmanifest.DicomUtil;
import org.hl7eu.imagingmanifest.old.model.GeneralStudyModuleInterface;
import org.hl7eu.imagingmanifest.old.model.HierarchicDesignatorInterface;

import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

public class FhirGeneralStudyModule implements GeneralStudyModuleInterface {
  private final FhirManifest manifest;

  FhirGeneralStudyModule(FhirManifest manifest ) {
    this.manifest = manifest;
  }

  @Override
  public Optional<String> getStudyInstanceUID() {
    ImagingStudy imagingStudy = manifest.ensureImagingStudy();
    if (imagingStudy.hasIdentifier() ) {
      for ( var identifier : imagingStudy.getIdentifier() ) {
        if ( identifier.hasSystem() && identifier.getSystem().equals("urn:dicom:uid") &&
            identifier.hasType() && identifier.getType().hasCoding( "http://hl7.eu/fhir/imaging-r4/CodeSystem/codesystem-missing-dicom-terminology", "0020000D")
        ) {
          return Optional.ofNullable( identifier.getValue() );
        }
      }
    }
    return Optional.empty();
  }

  @Override
  public GeneralStudyModuleInterface setStudyInstanceUID(String studyInstanceUID) {
    ImagingStudy imagingStudy = manifest.ensureImagingStudy();
    imagingStudy.addIdentifier()
        .setSystem("urn:dicom:uid")
        .setType( new CodeableConcept( new Coding().setSystem( "http://hl7.eu/fhir/imaging-r4/CodeSystem/codesystem-missing-dicom-terminology").setCode( "0020000D" ) ))
        .setValue(studyInstanceUID);
    return this;
  }

  @Override
  public Optional<Date> getStudyDate() {
    ImagingStudy imagingStudy = manifest.ensureImagingStudy();
    if ( imagingStudy.hasStartedElement() ) {
      return Optional.of( DicomUtil.toDate( imagingStudy.getStarted() ) );
    }
    return Optional.empty();
  }

  @Override
  public GeneralStudyModuleInterface setStudyDate(Date studyDate) {
    ImagingStudy imagingStudy = manifest.ensureImagingStudy();
    Date started = imagingStudy.getStarted();
    imagingStudy.setStarted( DicomUtil.getDateFromDicomDateAndTime( studyDate, started, TimeZone.getDefault()));
    return this;
  }

  @Override
  public Optional<Date> getStudyTime() {
    ImagingStudy imagingStudy = manifest.ensureImagingStudy();
    if ( imagingStudy.hasStartedElement() ) {
      return Optional.of( DicomUtil.toTime( imagingStudy.getStarted() ) );
    }
    return Optional.empty();
  }

  @Override
  public GeneralStudyModuleInterface setStudyTime(Date studyTime) {
    ImagingStudy imagingStudy = manifest.ensureImagingStudy();
    Date started = imagingStudy.getStarted();
    imagingStudy.setStarted( DicomUtil.getDateFromDicomDateAndTime( started, studyTime, TimeZone.getDefault()));
    return this;
  }

  @Override
  public Optional<String> getAccessionNumber() {
    return getAccessionNumberIdentifier().map( Identifier::getValue );
  }

  @Override
  public GeneralStudyModuleInterface setAccessionNumber(String accessionNumber) {
    Identifier identifier = ensureAccessionNumberIdentifier();
    identifier.setValue( accessionNumber );
    ensureAccessionNumberIdentifier();
    return this;
  }

  @Override
  public Optional<HierarchicDesignatorInterface> getIssuerOfAccessionNumber() {
    return getAccessionNumberIdentifier().map( FhirHierarchicDesignatorInterface::new );
  }

  @Override
  public GeneralStudyModuleInterface setIssuerOfAccessionNumber(HierarchicDesignatorInterface issuerOfAccessionNumber) {
    HierarchicDesignatorInterface hd = new FhirHierarchicDesignatorInterface( ensureAccessionNumberIdentifier() );
    return this;
  }

  private Optional<Identifier> getAccessionNumberIdentifier() {
    Optional<ServiceRequest> requestedProcedure = manifest.getRequestedProcedure();
    if ( requestedProcedure.isPresent() && requestedProcedure.get().hasIdentifier() ) {
      for ( Identifier identifier : requestedProcedure.get().getIdentifier() ) {
        if ( identifier.hasType() && identifier.getType().hasCoding( "http://terminology.hl7.org/CodeSystem/v2-0203", "ACSN") ) {
          return Optional.of( identifier );
        }
      }
    }
    return Optional.empty();
  }

  private Identifier ensureAccessionNumberIdentifier() {
    Optional<Identifier> optIdentifier = getAccessionNumberIdentifier();

    if ( optIdentifier.isPresent() ) { return optIdentifier.get(); }

    Identifier identifier = new Identifier()
        .setType( new CodeableConcept( new Coding().setCode("ACSN").setSystem("http://terminology.hl7.org/CodeSystem/v2-0203")  ));

    manifest.ensureRequestedProcedure().addIdentifier( identifier );

    updateAccessionNumberIdentifier();

    return identifier;
  }

  private void updateAccessionNumberIdentifier() {
    Identifier identifier = ensureAccessionNumberIdentifier();
    Optional<Reference> optRreference = manifest.ensureImagingStudy().getBasedOn().stream()
        .filter(Reference::hasReference)
        .filter( ref -> ref.getReference().equals( "ServiceRequest/"+manifest.ensureRequestedProcedure().getId() ) ).findFirst();

    Reference reference;
    if ( optRreference.isEmpty() ) {
      manifest.ensureImagingStudy().addBasedOn( new Reference( "ServiceRequest/"+manifest.ensureRequestedProcedure().getId()));
      reference = new Reference( "ServiceRequest/"+manifest.ensureRequestedProcedure().getId());
    } else {
      reference = optRreference.get();
    }
    reference.setIdentifier( identifier );
  }
}
