package org.hl7eu.imagingmanifest.fhir;

import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Identifier;

import java.util.Optional;

public class FhirStudyInstanceIdIdentifier extends Identifier {
  FhirStudyInstanceIdIdentifier( String studyInstanceUid ){
      setSystem("urn:dicom:uid");
      setType( new CodeableConcept()
            .addCoding( new Coding()
                .setCode( "0020000D")
                .setSystem("http://hl7.eu/fhir/imaging-manifest-r5/CodeSystem/codesystem-missing-dicom-terminology")
            )
        );
      setValue(studyInstanceUid);
  }

  static Optional<String> getStudyInstanceUid( Identifier identifier ){
    if (  identifier !=null
       && identifier.getSystem().equals("urn:dicom:uid")
       && identifier.hasType()
       && identifier.getType().hasCoding( "http://hl7.eu/fhir/imaging-manifest-r5/CodeSystem/codesystem-missing-dicom-terminology", "0020000D" )
    ){
     return Optional.ofNullable(identifier.getValue());
    }
    return Optional.empty();
  }

}
