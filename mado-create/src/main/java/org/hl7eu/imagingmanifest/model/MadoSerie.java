package org.hl7eu.imagingmanifest.model;

import java.util.*;

public interface MadoSerie {
  Optional<String> getModality();
  Optional<String> getSeriesInstanceUID();
  Optional<String> getSeriesDescription();
  Optional<String> getBodyPartExamined();
  Optional<String> getLaterality();
  Optional<String> getSeriesNumber();
  Optional<Date> getSeriesDateTime();
}
