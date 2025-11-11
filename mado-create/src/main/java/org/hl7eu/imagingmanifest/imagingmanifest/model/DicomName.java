package org.hl7eu.imagingmanifest.imagingmanifest.model;

import lombok.Getter;
import lombok.Setter;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.PersonName;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;

import java.util.Optional;

@Getter
@Setter
public class DicomName {
    String lastName;
    String firstName;
    String middleName;
    String prefix;
    String suffix;

    public DicomName() {}
    public DicomName(PersonName personName) {
        setLastName( personName.get( PersonName.Component.FamilyName) );
        setFirstName( personName.get( PersonName.Component.GivenName) );
        setMiddleName( personName.get( PersonName.Component.MiddleName) );
        setPrefix( personName.get(PersonName.Component.NamePrefix ));
        setSuffix( personName.get( PersonName.Component.NameSuffix) );
    }

    public PersonName getPersonName() {
        PersonName personName = new PersonName();
        Optional.ofNullable( getPrefix() ).ifPresent(value -> personName.set( PersonName.Component.NamePrefix, value ));
        Optional.ofNullable( getMiddleName() ).ifPresent( value -> personName.set( PersonName.Component.MiddleName, value ));
        Optional.ofNullable( getLastName() ).ifPresent( value -> personName.set( PersonName.Component.FamilyName, value ));
        Optional.ofNullable( getSuffix() ).ifPresent( value -> personName.set( PersonName.Component.NameSuffix, value ));
        return  personName;
    }
}
