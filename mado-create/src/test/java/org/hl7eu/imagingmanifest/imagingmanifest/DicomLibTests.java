package org.hl7eu.imagingmanifest.imagingmanifest;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DicomLibTests {
  @Test
  public void testDicomLibDate() {
    Date d1 = new Date();
    Attributes attributes = new Attributes();
    attributes.setTimezone(TimeZone.getTimeZone("GMT"));

    String time = DicomUtil.toTimeString(d1);
    String date = DicomUtil.toDateString(d1);

    attributes.setString( Tag.StudyDate, VR.DT, date );
    attributes.setString( Tag.StudyTime, VR.TM, time );

    Date dateDate = attributes.getDate( Tag.StudyDate );
    Date timeDate = attributes.getDate( Tag.StudyTime );
    Date d2 = DicomUtil.getDateFromDicomDateAndTime( dateDate, timeDate, TimeZone.getTimeZone("GMT") );

    assertEquals( d1, d2, "Dates should be equal" );
  }
}
