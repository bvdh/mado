package org.hl7eu.imagingmanifest.imagingmanifest;

import org.hl7eu.imagingmanifest.imagingmanifest.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DicomManifestTestUtil {
    static void testManifest(DicomManifest source, DicomManifest target)  {
        testConfiguration( source.getConfiguration(), target.getConfiguration() );
        testDicomStudy( source.getDicomStudy(), target.getDicomStudy() );
    }

    private static void testDicomStudy(DicomStudy source, DicomStudy target) {
        if (source==null || target==null) {
            assertEquals( source, target );
        };
        assertEquals( source.getStudyInstanceUID(), target.getStudyInstanceUID() );
        assertEquals( source.getStudyId(), target.getStudyId() );
        assertEquals( source.getAccessionNumber(), target.getAccessionNumber() );
        testDicomIssuerInfo( source.getAccessionNumberIssuer(), target.getAccessionNumberIssuer() );
        assertEquals( source.getStudyDateTime(), target.getStudyDateTime() );
        assertEquals( source.getStudyDescription(), target.getStudyDescription() );
        assertEquals( source.getModalities(), target.getModalities() );
        testPatient( source.getPatient(), target.getPatient() );
        testGeneralEquipment( source.getGeneralEquipment(), target.getGeneralEquipment() );
        testSeriesList( source.getSeries(), target.getSeries() );
    }

    private static void testGeneralEquipment(DicomGeneralEquipment source, DicomGeneralEquipment target) {
        if (source==null || target==null  ) { assertEquals( source, target );  };
        assertEquals( source.getManufacturer(), target.getManufacturer() );
        assertEquals( source.getInstitutionName(), target.getInstitutionName() );
        testCodeSequence( source.getInstitutionCodeSequence(), target.getInstitutionCodeSequence() );
    }

    private static void testCodeSequence(DicomCodeSequence source, DicomCodeSequence target) {
        if (source==null || target==null  ) { assertEquals( source, target );  };
        assertEquals( source.getCodeValue(), target.getCodeValue() );
        assertEquals( source.getCodingSchemeDesignator(), target.getCodingSchemeDesignator() );
        assertEquals( source.getCodeMeaning(), target.getCodeMeaning() );
    }

    private static void testSeriesList(List<DicomSerie> source, List<DicomSerie> target ){
        assertEquals( source.size(), target.size() );
        for (int i = 0; i < source.size(); i++) {
            DicomSerie sourceSerie = source.get(i);
            DicomSerie targetSerie = target.get(i);

            assertEquals( sourceSerie.getSeriesInstanceUID(), targetSerie.getSeriesInstanceUID() );
            assertEquals( sourceSerie.getModality(), targetSerie.getModality() );
            assertEquals( sourceSerie.getSeriesDescription(), targetSerie.getSeriesDescription() );
            assertEquals( sourceSerie.getBodyPartExamined(), targetSerie.getBodyPartExamined() );
            assertEquals( sourceSerie.getLaterality(), targetSerie.getLaterality() );
            assertEquals( sourceSerie.getSeriesNumber(), targetSerie.getSeriesNumber() );

            List<DicomInstance> sourceInstances = sourceSerie.getInstances();
            List<DicomInstance> targetInstances = targetSerie.getInstances();

            assertEquals( sourceInstances.size(), targetInstances.size() );
            for (int j = 0; j < sourceInstances.size(); j++) {
                DicomInstance sourceInstance = sourceInstances.get(j);
                DicomInstance targetInstance = targetInstances.get(j);
                assertEquals( sourceInstance.getSopInstanceUID(), targetInstance.getSopInstanceUID() );
                assertEquals( sourceInstance.getInstanceNumber(), targetInstance.getInstanceNumber() );
            }
        }
    }

    private static void testPatient(DicomPatient source, DicomPatient target ) {
        if (source==null || target==null  ) { assertEquals( source, target );  }
        assertEquals( source.getId(), target.getId() );
        assertEquals( source.getIssuer(), target.getIssuer() );
//        testDicomIssuerInfo( source.getIssuerInfo(), target.getIssuerInfo() );
        testDicomNames( source.getNames(), target.getNames() );
        assertEquals( source.getBirthDate(), target.getBirthDate() );
        assertEquals( source.getGender(), target.getGender() );
    }

    private static void testDicomNames(List<DicomName> names, List<DicomName> names1) {
        assertEquals( names.size(), names1.size() );
        for (int i = 0; i < names.size(); i++) {
            DicomName source = names.get(i);
            DicomName target = names1.get(i);
            assertEquals( source.getLastName(), target.getLastName() );
            assertEquals( source.getMiddleName(), target.getMiddleName() );
            assertEquals( source.getPrefix(), target.getPrefix() );
            assertEquals( source.getSuffix(), target.getSuffix() );
        }
    }

    private static void testDicomIssuerInfo(DicomIssuerInfo source, DicomIssuerInfo target) {
        assertTrue( source!=null || target!=null || (source == null && target == null) );
        assertEquals( source.getUniversalEntityIDType(), target.getUniversalEntityIDType() );
        assertEquals( source.getUniversivalEntityID(), target.getUniversivalEntityID() );
    }

    static void testConfiguration( Configuration source, Configuration target) {
        if (source==null || target==null) {
            assertEquals( source, target );
        };
        assertEquals( source.getWadoURL(),      target.getWadoURL() );
        assertEquals( source.getXcWadoURL(),    target.getXcWadoURL() );
        assertEquals( source.getIidURL(),       target.getIidURL() );
        assertEquals( source.getWebViewerURL(), target.getWebViewerURL() );
    }
}
