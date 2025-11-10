package org.hl7eu.imagingmanifest.imagingmanifest;

import org.dcm4che3.data.Tag;

import java.util.Date;

public class DicomUtil {
     static public Date getDateFromDicomDateAndTime(Date dicomDate, Date dicomTime) {
        if ( dicomDate!=null ){
            Date studyDateTime = (Date) dicomDate.clone();
            if (dicomTime!=null){
                studyDateTime.setTime( dicomTime.getTime() + dicomDate.getTime() );
            }
            return studyDateTime;
        }
        return null;
    }
}
