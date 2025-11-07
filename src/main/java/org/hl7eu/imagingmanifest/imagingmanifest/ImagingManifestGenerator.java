package org.hl7eu.imagingmanifest.imagingmanifest;

import org.apache.commons.cli.*;
import org.hl7eu.imagingmanifest.imagingmanifest.manifest.DicomManifestSource;
import org.hl7eu.imagingmanifest.imagingmanifest.manifest.FhirManifest;
import org.hl7eu.imagingmanifest.imagingmanifest.loader.DicomFileLoader;
import org.hl7eu.imagingmanifest.imagingmanifest.manifest.InventoryManifest;
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

            List<DicomManifestSource> baseManifest = loader.getStudies().stream()
                    .map(DicomManifestSource::new)
                    .toList()
                    ;
            List<InventoryManifest> inventoryManifests = baseManifest.stream()
                    .map(InventoryManifest::new)
                    .toList()
                    ;


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
