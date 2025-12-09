//package org.hl7eu.imagingmanifest.fhir;
//
//import org.hl7.fhir.r4.model.CodeableConcept;
//import org.hl7.fhir.r4.model.Coding;
//import org.hl7.fhir.r4.model.Organization;
//import org.hl7eu.imagingmanifest.model.*;
//
//import java.util.UUID;
//
//public class FhirInstitution {
//    static Organization createGeneralEquipementInstitution(DicomGeneralEquipment generalEquipment) {
//        if (generalEquipment == null || (generalEquipment.getInstitutionName() == null && generalEquipment.getInstitutionCodeSequence() == null)) {
//            return null;
//        }
//        Organization organization = (Organization) new Organization().setId( UUID.randomUUID().toString() );
//        organization.setName(generalEquipment.getInstitutionName());
//        if (generalEquipment.getInstitutionCodeSequence() != null && generalEquipment.getInstitutionCodeSequence().getCodeValue() != null) {
//            DicomCodeSequence codeSequence = generalEquipment.getInstitutionCodeSequence();
//            CodeableConcept codeableConcept = new CodeableConcept();
//            Coding coding = new Coding()
//                    .setSystem(codeSequence.getCodingSchemeDesignator())
//                    .setCode(codeSequence.getCodeValue())
//                    .setDisplay(codeSequence.getCodeMeaning());
//            codeableConcept.addCoding(coding);
//            organization.addType(codeableConcept);
//
//        }
//        return organization;
//    }
//}
