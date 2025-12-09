//package org.hl7eu.imagingmanifest.fhir;
//
//import org.hl7.fhir.r4.model.Coding;
//import org.hl7.fhir.r4.model.Device;
//import org.hl7.fhir.r4.model.Organization;
//import org.hl7.fhir.r4.model.Reference;
//import org.hl7eu.imagingmanifest.model.*;
//
//import java.util.UUID;
//
//public class FhirGeneralEquipment {
//    static Device createGeneralEquipmentDevice(MadoManifest manifest, DicomGeneralEquipment generalEquipment, Organization institution) {
//        if ( generalEquipment == null ) { return null; }
//        Device device = (Device) new Device()
//                .setManufacturer( generalEquipment.getManufacturer() )
//                .setId( UUID.randomUUID().toString() )
//                ;
//        if ( institution != null ) {
//            device.setOwner( new Reference()
//                    .setReference( "Organization/" + institution.getId() )
//                    .setType( "Organization" )
//                    .setDisplay( institution.getName() )
//            );
//        }
//        return device;
//    }
//
//    public static DicomGeneralEquipment populateGeneralEquipment(Device device, Organization institution) {
//        if ( device == null ) { return null; }
//        DicomGeneralEquipment generalEquipment = new DicomGeneralEquipment();
//        generalEquipment.setManufacturer( device.getManufacturer() );
//        if ( institution != null ) {
//            generalEquipment.setInstitutionName( institution.getName() );
//            if ( institution.getType() != null ) {
//                DicomCodeSequence institutionCode = new DicomCodeSequence();
//                if ( !institution.getType().isEmpty() && !institution.getType().getFirst().isEmpty() ) {
//                    Coding coding = institution.getType().getFirst().getCoding().getFirst();
//                    institutionCode.setCodeMeaning( coding.getDisplay() );
//                    institutionCode.setCodeValue( coding.getCode() );
//                    institutionCode.setCodingSchemeDesignator( coding.getSystem() );
//                }
//                generalEquipment.setInstitutionCodeSequence( institutionCode );
//            }
//
//        }
//        return generalEquipment;
//    }
//}
