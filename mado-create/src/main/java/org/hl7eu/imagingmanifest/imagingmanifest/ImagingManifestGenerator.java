package org.hl7eu.imagingmanifest.imagingmanifest;

import org.apache.commons.cli.*;
import org.hl7eu.imagingmanifest.imagingmanifest.loader.DicomFileLoader;
import org.hl7eu.imagingmanifest.imagingmanifest.manifest.fhir.FhirManifest;
import org.hl7eu.imagingmanifest.imagingmanifest.manifest.InventoryManifest;
import org.hl7eu.imagingmanifest.imagingmanifest.manifest.kos1.KosManifest;
import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomManifest;
import org.hl7eu.imagingmanifest.imagingmanifest.model.DicomStudy;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImagingManifestGenerator {

    public void main(String[] args){
        Options options = new Options();
        options.addOption("h","help",false,"help");
        options.addOption("f","file",true,"DICOM file");
        options.addOption("d","dir",true,"Directory containing DICOM files.");
        CommandLineParser parser = new DefaultParser();

        List<String> dicomFiles = new ArrayList<>();

        try {
            CommandLine commandLine = parser.parse( options, args );
            if ( commandLine.hasOption("h") || commandLine.getArgList().isEmpty()){
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("help", options);
            }
            String[] files = commandLine.getOptionValues("f");
            String[] dirs  = commandLine.getOptionValues("d");


            if (dirs != null){
                for ( String dir: dirs){
                  Path dirPath = Paths.get(dir);
                    List<Path> allFiles = new ArrayList<>();
                    try {
                        listAllFiles(dirPath, allFiles);
                    } catch (IOException e) {
                        System.err.println("Error listing files in directory " + dir);
                    }

                    for ( Path path: allFiles ){
                        String filenameString = path.toString();
                        if ( filenameString.endsWith(".dcm") ){
                            dicomFiles.add(filenameString);
                        } else {
                            System.out.println("Not a DCM file: " + path);
                        }
                    }
                    System.out.println("Found DICOM files:");
                    allFiles.forEach(System.out::println);
                }
            }
            DicomFileLoader loader = new DicomFileLoader();
            for ( String filePath: dicomFiles ){
                loader.parseFile( filePath );
            }

            List<DicomStudy> dicomStudyList = loader.getStudies();;

            List<FhirManifest> fhirManifests = new ArrayList<>();
            List<KosManifest> kosManifests = new ArrayList<>();
            List<InventoryManifest> inventoryManifests = new ArrayList<>();

//            int i = dicomStudyList.size();
            for( DicomStudy dicomStudy: dicomStudyList ){
                System.out.print('.');
                DicomManifest dicomManifest = new DicomManifest();
                dicomManifest.setDicomStudy(dicomStudy);
                inventoryManifests.add( new InventoryManifest( dicomManifest ) );
                kosManifests.add( new KosManifest( dicomManifest ));
                fhirManifests.add( new FhirManifest( dicomManifest ) );
            }
            int i=0;
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }

    }
    private static void listAllFiles(Path currentPath, List<Path> allFiles)
            throws IOException
    {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentPath))
        {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    listAllFiles(entry, allFiles);
                } else {
                    allFiles.add(entry);
                }
            }
        }
    }
}
