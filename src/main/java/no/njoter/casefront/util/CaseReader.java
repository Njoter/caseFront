package no.njoter.casefront.util;

import no.njoter.casefront.Case;
import no.njoter.casefront.gui.MainWindow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CaseReader {

    public static void readFromFile(ArrayList<Case> caseArray, String folderPath) {
        File folder = new File(folderPath);
        String[] files = null;
        if (folder.exists()) {
            files = folder.list();
            if (files != null) {
                for (String file : files) {
                    Case newCase = createCase(file);
                    if (newCase != null) {
                        System.out.println("Case was successfully made.");
                        caseArray.add(newCase);
                    } else {
                        System.out.println("Case was null.");
                        return;
                    }
                }
            }
        } else {
            System.out.println("Folder does not exist.");
        }
    }

    private static Case createCase(String filename) {
        String[] fields = null;
        BufferedReader reader = null;
        Case newCase = null;
        try {
            reader = new BufferedReader(new FileReader(MainWindow.FOLDER_PATH + filename));
            fields = readLines(reader);
            if (fields != null) {
                newCase = setCaseFields(fields);
            }
            return newCase;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    private static String[] readLines(BufferedReader reader) {
        String[] fields = null;
        StringBuilder text = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    text.append("\n");
                } else {
                    text.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fields = text.toString().split(";");
        return fields;
    }

    private static Case setCaseFields(String[] fields) {
        LocalDateTime tidspunkt = LocalDateTime.parse(fields[6]);
        Case newCase = new Case();
        newCase.setBeskrivelse(fields[0]);
        newCase.setNavn(fields[1]);
        newCase.setTlf(fields[2]);
        newCase.setVarenr(fields[3]);
        newCase.setLøsning(fields[4]);
        newCase.setAnsattNavn(fields[5]);
        newCase.setTidspunkt(tidspunkt);
        newCase.setFullText();
        return newCase;
    }
}
