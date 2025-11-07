package org.hl7eu.imagingmanifest.imagingmanifest.manifest;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.TransferSyntax;
import org.dcm4che2.io.DicomOutputStream;
import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomStudy;

public class KosManifest {

    public static KosManifest create(DicomStudy dicomStudy) {
        DicomOutputStream dos = new DicomOutputStream(System.out);

        writePatientModule(dos, dicomStudy);

        return new KosManifest();
    }

    private static void writePatientModule(DicomOutputStream dos, DicomStudy dicomStudy) {
        addWhenPresent( dos, dicomStudy.getDicomObj().get(Tag.PatientName));
        addWhenPresent( dos, dicomStudy.getDicomObj().get(Tag.PatientID));
        addWhenPresent( dos, dicomStudy.getDicomObj().get(Tag.IssuerOfPatientID));
        addIssuerOfPatientIDQualifiersSequence( dos, dicomStudy.getDicomObj() );

    }

    private static void addIssuerOfPatientIDQualifiersSequence(DicomOutputStream dos, DicomObject dicomObj) {
        DicomElement seq = dicomObj.get(Tag.IssuerOfPatientIDQualifiersSequence);
        if ( seq!=null  ){
            seq.hasItems();
            try {
                dos.writeDataset(seq.getDicomObject(), TransferSyntax.NoPixelData );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void addWhenPresent(DicomOutputStream dos, DicomElement dicomElement) {
        if ( dicomElement != null && dicomElement.hasItems() ) {
            try {
                dos.writeDataset(dicomElement.getDicomObject(), TransferSyntax.NoPixelData );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
