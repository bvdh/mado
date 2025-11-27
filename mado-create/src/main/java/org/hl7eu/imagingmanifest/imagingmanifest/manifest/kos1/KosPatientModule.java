package org.hl7eu.imagingmanifest.imagingmanifest.manifest.kos1;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.PersonName;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.hl7eu.imagingmanifest.imagingmanifest.model.*;

import java.util.Iterator;
import java.util.Optional;

class KosPatientModule {

  static void populateManifest( Attributes kos, DicomManifest manifest ) {

    DicomPatient dicomPatient = new DicomPatient();
    dicomPatient.setId( kos.getString( Tag.PatientID ) );
    dicomPatient.setGender( kos.getString( Tag.PatientSex ) );
    dicomPatient.setBirthDate( kos.getDate( Tag.PatientBirthDate ) );
    if ( kos.contains( Tag.PatientName ) ){
      PersonName personName = new PersonName( kos.getString( Tag.PatientName ) );
      DicomName patientName = new DicomName( personName );
      dicomPatient.getNames().add( patientName );
    }
    if ( kos.contains( Tag.OtherPatientNames ) ){
      String[] pns = kos.getStrings(Tag.OtherPatientNames);
      if ( pns != null ) {
        for ( String pn : pns ){
          PersonName personName = new PersonName( pn );
          DicomName otherPatientName = new DicomName( personName );
          dicomPatient.getNames().add( otherPatientName );
        }
      }
    }

    String universalEntityID = kos.getString( Tag.UniversalEntityID );
    String universalEntityIDType = kos.getString( Tag.UniversalEntityIDType );
    if ( universalEntityID != null || universalEntityIDType != null ) {
      DicomIssuerInfo issuerInfo = new DicomIssuerInfo();
      issuerInfo.setUniversivalEntityID( universalEntityID );
      issuerInfo.setUniversalEntityIDType( universalEntityIDType );
      dicomPatient.setIssuer( issuerInfo );
    }

    manifest.getDicomStudy().setPatient( dicomPatient );
  }
  static void addPatientModule(Attributes kos, DicomManifest dicomManifest) {
    DicomPatient dicomPatient = dicomManifest.getDicomStudy().getPatient();
    if (dicomPatient ==null ) {
      return;
    }
    Optional.ofNullable(dicomPatient.getGender()).ifPresent(value -> kos.setString( Tag.PatientSex, VR.CS, value) );
    Optional.ofNullable(dicomPatient.getId()).ifPresent(value -> {
      kos.setString( Tag.PatientID, VR.CS, value);
      kos.setString( Tag.TypeOfPatientID, VR.LO, "TEXT" );
    } );
    Optional.ofNullable(dicomPatient.getIdIssuer()).ifPresent(value -> kos.setString( Tag.IssuerOfPatientID, VR.CS, value) );
    Optional.ofNullable(dicomPatient.getBirthDate()).ifPresent(value -> kos.setDate( Tag.PatientBirthDate, VR.DA, value) );
    kos.addAll( addPatientNames( kos, dicomPatient ) );
  }

  private static Attributes addPatientNames(Attributes kos, DicomPatient dicomPatient) {
    Attributes names = new Attributes();
    Iterator<DicomName> it = dicomPatient.getNames().iterator();
    if ( it.hasNext() ) {
      DicomName dicomName = it.next();
      kos.setValue( Tag.PatientName, VR.PN, dicomName.getPersonName() );
    }
    while( it.hasNext() ) {
      PersonName otherPersonName = it.next().getPersonName();
      kos.setValue( Tag.OtherPatientNames, VR.PN, otherPersonName );
      kos.setValue( Tag.OtherPatientNames, VR.PN, otherPersonName );
    }
    if ( dicomPatient.getIssuer()!=null){
      DicomIssuerInfo issuer = dicomPatient.getIssuer();
      Optional.ofNullable( issuer.getUniversivalEntityID() ).ifPresent( value -> kos.setString( Tag.UniversalEntityID, VR.UT, value ) );
      Optional.ofNullable( issuer.getUniversalEntityIDType() ).ifPresent( value -> kos.setString( Tag.UniversalEntityIDType, VR.CS, value ) );
    }
    return names;
  }
}
