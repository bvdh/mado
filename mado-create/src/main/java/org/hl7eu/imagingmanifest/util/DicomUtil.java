package org.hl7eu.imagingmanifest.util;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.util.DateUtils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DicomUtil {

  static public String toDateString( Date date, TimeZone tz ) {
    return DateUtils.formatDA( tz, date );
  }

  static public String toTimeString(Date date, TimeZone tz) {
    return DateUtils.formatTM( tz, date );
  }

   static public Date getDateFromDicomDateAndTime(Date dicomDate, Date dicomTime, TimeZone timeZone ) {
      if ( dicomDate!=null ){
        String dateStr = toDateString( dicomDate, timeZone );
        String timeStr = toTimeString( dicomTime, timeZone );

        String dateTimeStr = dateStr + "@" + timeStr + " " + timeZone.getID();
        DateFormat df = new SimpleDateFormat("yyyyMMdd@HHmmss.SSSS Z");
        df.setTimeZone( timeZone );
        Date studyDateTime = null;
        try {
          studyDateTime = df.parse( dateTimeStr );
        } catch (ParseException e) {
          return null;
        }
        return studyDateTime;
      }
      return null;
  }


}
