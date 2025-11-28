| Manifest                         | KOS                                               | FHIR                                                    |
|----------------------------------|---------------------------------------------------|---------------------------------------------------------|
| PatientModule                    | PatientModule                                     | ImagingStudy.subject                                    |
| .patientID                       | .patientID                                        | .identifier.value                                       |
| .patientName                     | .patientName                                      | .name[0]                                                |
| .issuerOfPatientID               | .issuerOfPatientID                                | .identifier.assigner.display                            |
| .otherPatientNames               | .otherPatientNames                                | .name[1..*]                                             |
| .patientBirthDate                | .patientBirthDate                                 | .birthDate                                              |
| .patientSex                      | .patientSex                                       | .gender                                                 |
|                                  |                                                   |                                                         |
| GeneralStudy                     |                                                   | ImagingStudy                                            |
| .studyInstanceUID                |                                                   | .identifier                                             |
| .studyDate                       |                                                   | .started                                                |
|                                  |                                                   |                                                         |
| ManifestAuthor                   | GeneralEquipmentModule                            | Provenance                                              |
| .manufacturer                    | .manufacturer                                     | .agent.where(type.code='assembler').who->Device.name    |
| .institutionName                 | .institutionName                                  | .agent.where(type.code='author').who->Organization.name |
| .institutionCodeSeq              | .institutionCodeSeq                               | .agent.where(type.code='author').who->Organization.type |
|                                  |                                                   |                                                         |
| ClinicalOrders                   | KeyObjectDocumentModule.ReferencedRequestSequence | ImagingStudy.basedOn->ServiceRequest                    |
| .accessionNumber                 | .studyInstanceUID                                 | .basedOn.requestedProcedure.study.reference             |
| .issuerOfAccessionNumberSequence |                                                   |
 |  