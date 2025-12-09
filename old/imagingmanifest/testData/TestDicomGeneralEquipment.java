package org.hl7eu.imagingmanifest.old.imagingmanifest.testData;

import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomCodeSequence;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomGeneralEquipment;

public class TestDicomGeneralEquipment extends DicomGeneralEquipment {
    public TestDicomGeneralEquipment(String prefix) {
        setManufacturer( prefix + "-TestManufacturer" );
        setInstitutionName( prefix + "-TestInstitutionName" );

        DicomCodeSequence DicomCodeSequence = new DicomCodeSequence();
        DicomCodeSequence.setCodeValue( "12345" );
        DicomCodeSequence.setCodingSchemeDesignator( "99TEST" );
        DicomCodeSequence.setCodeMeaning( prefix + "-TestInstitutionCodeMeaning" );
        setInstitutionCodeSequence(DicomCodeSequence);
    }
}
