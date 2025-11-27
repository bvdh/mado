package org.hl7eu.imagingmanifest.imagingmanifest;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
}
