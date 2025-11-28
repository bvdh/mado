package org.hl7eu.imagingmanifest.imagingmanifest;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DicomUtil {

  static public String toDateString( Date date ) {
    DateFormat df;

    df = new SimpleDateFormat("yyyyMMdd");
    String str = df.format(date); // Converting date in "dd/MM/yyyy" format
    return str;
  }

  static public String toTimeString( Date date ) {
    DateFormat df;

    df = new SimpleDateFormat("HHmmss.SSS");
    String str = df.format(date); // Converting date in "dd/MM/yyyy" format
    return str;
  }
   static public Date getDateFromDicomDateAndTime(Date dicomDate, Date dicomTime, TimeZone timeZone ) {
      if ( dicomDate!=null ){
        Date date = toDate(dicomDate, timeZone);
        Date time = toTime(dicomTime, timeZone);

          String dateStr = toDateString( dicomDate );
          String timeStr = ( dicomTime!=null ) ? toTimeString( dicomTime ) : "000000.000";
          String dateTimeStr = dateStr + "@" + timeStr + " " + timeZone.getID();
          DateFormat df = new SimpleDateFormat("yyyyMMdd@HHmmss.SSSS Z");
          df.setTimeZone( timeZone );
          Date studyDateTime = null;
          try {
            studyDateTime = df.parse( dateTimeStr );
          } catch (ParseException e) {
            throw new RuntimeException(e);
          }
//        Date studyDateTime = (Date) dicomDate.clone();
//          if (dicomTime!=null){
//              studyDateTime.setTime( dicomTime.getTime() + dicomDate.getTime() );
//          }
          return studyDateTime;
      }
      return null;
  }


  public static Date toDate(Date time) {
    return toDate(time, TimeZone.getDefault());
  }
  private static Date toDate(Date date, TimeZone timeZone) {
    Calendar cal = Calendar.getInstance( timeZone );
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  public static Date toTime(Date date) {
    return toTime(date, TimeZone.getDefault());
  }
  private static Date toTime(Date time, TimeZone timeZone) {
    Calendar cal = Calendar.getInstance( timeZone );
    cal.setTime(time);
    cal.set(Calendar.DAY_OF_MONTH, 0);
    cal.set(Calendar.MONTH, 0);
    cal.set(Calendar.YEAR, 0);
    return cal.getTime();
  }
}
