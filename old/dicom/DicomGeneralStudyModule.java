package org.hl7eu.imagingmanifest.old.dicom;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.util.DateUtils;
import org.hl7eu.imagingmanifest.old.model.GeneralStudyModuleInterface;
import org.hl7eu.imagingmanifest.old.model.HierarchicDesignatorInterface;

import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

public class DicomGeneralStudyModule implements GeneralStudyModuleInterface {
  private final Attributes attributes;

  public DicomGeneralStudyModule(Attributes attributes) {
    this.attributes = attributes;
  }

  @Override
  public Optional<String> getStudyInstanceUID() {
    return Optional.ofNullable( attributes.getString(Tag.StudyInstanceUID) );
  }

  @Override
  public GeneralStudyModuleInterface setStudyInstanceUID(String studyInstanceUID) {
    this.attributes.setString( Tag.StudyInstanceUID,  org.dcm4che3.data.VR.UI, studyInstanceUID );
    return this;
  }

  @Override
  public Optional<Date> getStudyDate() {
    return Optional.ofNullable( attributes.getDate(Tag.StudyDate) );
  }

  @Override
  public GeneralStudyModuleInterface setStudyDate(Date studyDate) {
    attributes.setTimezone(TimeZone.getDefault());
    attributes.setString( Tag.TimezoneOffsetFromUTC, org.dcm4che3.data.VR.SH, TimeZone.getDefault().getDisplayName());
    attributes.setString( Tag.StudyDate, org.dcm4che3.data.VR.DA, DateUtils.formatDA( TimeZone.getDefault(), studyDate ) );
    return this;
  }

  @Override
  public Optional<Date> getStudyTime() {
    return Optional.ofNullable( attributes.getDate(Tag.StudyTime) );
  }

  @Override
  public GeneralStudyModuleInterface setStudyTime(Date studyTime) {
    attributes.setTimezone(TimeZone.getDefault());
    attributes.setString( Tag.TimezoneOffsetFromUTC, org.dcm4che3.data.VR.SH, TimeZone.getDefault().getDisplayName());
    attributes.setString( Tag.StudyDate, org.dcm4che3.data.VR.DA, DateUtils.formatTM( TimeZone.getDefault(), studyTime ) );
    return this;
  }

  @Override
  public Optional<String> getAccessionNumber() {
    return Optional.of(attributes.getString(Tag.AccessionNumber));
  }

  @Override
  public GeneralStudyModuleInterface setAccessionNumber(String accessionNumber) {
    attributes.setString( Tag.AccessionNumber,  VR.SH, accessionNumber );
    return this;
  }

  @Override
  public Optional<HierarchicDesignatorInterface> getIssuerOfAccessionNumber() {
    Sequence seq = attributes.getSequence( Tag.IssuerOfAccessionNumberSequence );
    if ( seq != null && !seq.isEmpty() ) {
      Attributes item = seq.get(0);
      DicomHierarchicDesignator hd = new DicomHierarchicDesignator(item);
      return Optional.of( hd );
    }
    return Optional.empty();
  }

  @Override
  public GeneralStudyModuleInterface setIssuerOfAccessionNumber(HierarchicDesignatorInterface issuerOfAccessionNumber) {
    return null;
  }
}
