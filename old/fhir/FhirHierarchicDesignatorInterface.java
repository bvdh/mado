package org.hl7eu.imagingmanifest.old.fhir;

import org.hl7.fhir.r4.model.Identifier;
import org.hl7eu.imagingmanifest.old.model.HierarchicDesignatorInterface;

import java.util.Optional;
import java.util.UUID;

public class FhirHierarchicDesignatorInterface implements HierarchicDesignatorInterface {
  private final Identifier identifier;
  private String universalEntityIDType;
  private String universalEntityID;

  public FhirHierarchicDesignatorInterface(Identifier identifier) {
    this.identifier = identifier;
    this.detectType();
  }

  @Override
  public Optional<String> getLocalNamespaceEntityID() {
    return Optional.empty();
  }

  @Override
  public HierarchicDesignatorInterface setLocalNamespaceEntityID(String localNamespaceEntityID) {
    return this;
  }

  @Override
  public Optional<String> getUniversalEntityID() {
    return Optional.ofNullable(universalEntityIDType).map( str -> str.replace("urn:oid:", ""));
  }

  @Override
  public HierarchicDesignatorInterface setUniversalEntityID(String universalEntityID) {
    this.universalEntityID = universalEntityID;
    detectType();
    switch ( universalEntityIDType ) {
      case "ISO" -> identifier.setSystem( "urn:oid:" + universalEntityID );
      case "UUID" -> identifier.setSystem( universalEntityID );
      case "URI" -> identifier.setSystem( universalEntityID );
      default -> identifier.setSystem( universalEntityID );
    }
    updateIdeintifierSystem();
    return this;
  }

  private void detectType() {
    if ( this.universalEntityIDType == null ) {
      String system = identifier.getSystem();
      if ( system.startsWith("urn:oid:") ){
        universalEntityIDType = "ISO";
      } else if (UUID.fromString(system).toString().equals(system) ) {
        universalEntityIDType =  "UUID";
      } else {
        universalEntityIDType = "URI"; ;
      }
    }
  }

  @Override
  public Optional<String> getUniversalEntityIDType() {
    return Optional.ofNullable(universalEntityIDType);
  }

  @Override
  public HierarchicDesignatorInterface setUniversalEntityIDType(String universalEntityIDType) {
    this.universalEntityIDType = universalEntityIDType;
    updateIdeintifierSystem();
    return this;
  }

  private void updateIdeintifierSystem() {
    switch ( universalEntityIDType ) {
      case "ISO" -> identifier.setSystem( "urn:oid:" + universalEntityID );
      case "UUID" -> identifier.setSystem( universalEntityID );
      case "URI" -> identifier.setSystem( universalEntityID );
      default -> identifier.setSystem( universalEntityID );
    }
  }
}
