package no.njoter.casefront.util;

import no.njoter.casefront.Case;
import no.njoter.casefront.gui.MainWindow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CaseWriter {

    public static void writeToFile(Case newCase, String folderPath) {
        File file = null;
        try {
            String fileName = newCase.getTidspunkt().toString();
            file = new File(folderPath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, false));
            writer.write(newCase.getBeskrivelse() + ";");
            writer.write(newCase.getNavn() + ";");
            writer.write(newCase.getTlf() + ";");
            writer.write(newCase.getVarenr() + ";");
            writer.write(newCase.getLøsning() + ";");
            writer.write(newCase.getAnsattNavn() + ";");
            writer.write(newCase.getTidspunkt().toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void deleteFile(Case newCase, String folderPath) {
        String fileName = newCase.getTidspunkt().toString();
        File file = new File(folderPath + fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
