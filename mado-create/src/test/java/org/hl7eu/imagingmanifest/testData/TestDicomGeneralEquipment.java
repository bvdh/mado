//package org.hl7eu.imagingmanifest.testData;
//
//import org.hl7eu.imagingmanifest.model.*;
//
//public class TestDicomGeneralEquipment extends DicomGeneralEquipment {
//    public TestDicomGeneralEquipment(String prefix) {
//        setManufacturer( prefix + "-TestManufacturer" );
//        setInstitutionName( prefix + "-TestInstitutionName" );
//
//        DicomCodeSequence DicomCodeSequence = new DicomCodeSequence();
//        DicomCodeSequence.setCodeValue( "12345" );
//        DicomCodeSequence.setCodingSchemeDesignator( "99TEST" );
//        DicomCodeSequence.setCodeMeaning( prefix + "-TestInstitutionCodeMeaning" );
//        setInstitutionCodeSequence(DicomCodeSequence);
//    }
//}
