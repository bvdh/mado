package org.hl7eu.imagingmanifest.old.imagingmanifest.testData;

import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomName;
import org.hl7eu.imagingmanifest.old.imagingmanifest.model.DicomPatient;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class TestDicomPatient extends DicomPatient {
    public TestDicomPatient(){
        LocalDate localDate = LocalDate.parse("2018-05-05");
        setBirthDate(Date.from(LocalDate.parse("2018-05-05").atStartOfDay(ZoneId.systemDefault()).toInstant()) );
        setGender("F");
        setId("TestPatientId");
        setIdIssuer( "PatientIdIssuer" );
        DicomName name1 = new DicomName();
            name1.setLastName("TestFamilyName");
            name1.setFirstName("TestGivenName");
            name1.setMiddleName("TestMiddleName");
            name1.setPrefix("TestPrefix");
            name1.setSuffix("TestSuffix");
        DicomName name2 = new DicomName();
            name2.setLastName("TestFamilyName");
            name2.setFirstName("TestGivenName");
            name2.setMiddleName("TestMiddleName");
            name2.setPrefix("TestPrefix");
            name2.setSuffix("TestSuffix");
        setNames( List.of(  name1, name2 ) );
//
//        setPatientId( "TestPatientId" );
//        setPatientName( "TestPatientName" );
//        setPatientBirthDate( "19700101" );
//        setPatientSex( "M" );
    }
}
