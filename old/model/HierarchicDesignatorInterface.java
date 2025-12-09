package org.hl7eu.imagingmanifest.old.model;

import java.util.Optional;

public interface HierarchicDesignatorInterface {
  public Optional<String> getLocalNamespaceEntityID();
  public HierarchicDesignatorInterface setLocalNamespaceEntityID(String localNamespaceEntityID);

  public Optional<String> getUniversalEntityID();
  public HierarchicDesignatorInterface setUniversalEntityID(String universalEntityID);

  public Optional<String> getUniversalEntityIDType();
  public HierarchicDesignatorInterface setUniversalEntityIDType(String universalEntityIDType);
}
