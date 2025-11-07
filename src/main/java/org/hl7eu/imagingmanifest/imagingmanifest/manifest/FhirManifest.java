package org.hl7eu.imagingmanifest.imagingmanifest.manifest;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.ImagingStudy;
import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomStudy;

import java.util.Date;
import java.util.UUID;

public class FhirManifest {
    private ImagingStudy imagingStudy;

    public FhirManifest( ManifestInterface manifest ){}
    static public FhirManifest create(DicomStudy dicomStudy ){
        FhirManifest fhirManifest =  new FhirManifest();
        fhirManifest.imagingStudy = createImagingStudy( dicomStudy );

        return fhirManifest;
    }

    private static ImagingStudy createImagingStudy(DicomStudy dicomStudy) {
        ImagingStudy imagingStudy = new ImagingStudy();

        imagingStudy
                .addIdentifier( new Identifier()
                        .setSystem("urn:dicom:uid")
                        .setValue(dicomStudy.getStudyInstanceUid())
                )
                .setStatus( ImagingStudy.ImagingStudyStatus.AVAILABLE )
                .setModality( dicomStudy.getModalities() )
                .setId( UUID.randomUUID().toString() )
        ;
        DicomObject dcmObj = dicomStudy.getDicomObj();

        // StudyDate and StudyTime (0008,0020) (0008,0030)
        Date studyDate = dicomStudy.getDicomObj().getDate( Tag.StudyDate );
        Date studyTime = dicomStudy.getDicomObj().getDate( Tag.StudyTime );
        if ( studyDate!=null ){
            Date studyDateTime = (Date) studyDate.clone();
            if (studyTime!=null){
                studyDateTime.setTime( studyTime.getTime() + studyDate.getTime() );
            }
            imagingStudy.setStarted( studyDateTime );
        }

        //    numberOfSeries	(0020,1206)
        if ( dcmObj.contains(Tag.NumberOfStudyRelatedSeries) ){
            imagingStudy.setNumberOfSeries( dcmObj.getInt(Tag.NumberOfStudyRelatedSeries, 0 ));
        }

        //    numberOfInstances	(0020,1208)
        if ( dcmObj.contains(Tag.NumberOfStudyRelatedInstances) ){
            imagingStudy.setNumberOfInstances( dcmObj.getInt(Tag.NumberOfStudyRelatedInstances, 0 ));
        }

        //    procedureReference	(0008,1032)
        if ( dcmObj.contains( Tag.ProcedureCodeSequence )){
//            Attributes  procedureCodeSequence = dcmObj.getNestedDataset(Tag.ProcedureCodeSequence);
            //    procedureCode	(0008,1032)
            // TODO https://dicom.innolitics.com/ciods/mr-image/general-study/00081032
        }


        //    location	(0008,1040) | (0040,0243)
        if ( dcmObj.contains( Tag.InstitutionalDepartmentName)) {
            String  institutionalDepartmentName = dcmObj.getString(Tag.InstitutionalDepartmentName);
            // TODO
        }

        //    reasonCode	(0040,1002)
        if ( dcmObj.contains( Tag.ReasonForTheRequestedProcedure)){
            String  reasonForTheRequestedProcedure = dcmObj.getString(Tag.ReasonForTheRequestedProcedure);
            // TODO
        }

        //    reasonReference

        //    note

        //    description	(0008,1030)
        if ( dcmObj.contains(Tag.StudyDescription )){
            imagingStudy.setDescription( dcmObj.getString(Tag.StudyDescription ));
        }

        return imagingStudy;
    }

    private FhirManifest() {
    }
}
