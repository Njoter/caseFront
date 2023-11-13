package no.njoter.casefront.util;

import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import no.njoter.casefront.Case;

public class CasePrinter {

    public static void printCase(Case newCase, Label label) {

        String text = "";
        StringBuilder sb = new StringBuilder(text);

        String beskrivelse = "Beskrivelse:";
        String navn = "Navn:";
        String tlf = "Tlf:";
        String varenr = "Varenummer:";
        String løsning = "Løsningsforslag:";
        String ansattNavn = "Ansattes navn:";
        sb.append(beskrivelse).append("\n").append(newCase.getBeskrivelse()).append("\n");

        String s = newCase.getNavn();
        if (!s.isEmpty()) {
            sb.append(navn).append("\n").append(s).append("\n");
        }
        if (!s.isEmpty()) {
            sb.append(tlf).append("\n").append(s).append("\n");
        }
        if (!s.isEmpty()) {
            sb.append(varenr).append("\n").append(s).append("\n");
        }

        text += "Beskrivelse:";
        text += (newCase.getBeskrivelse()) + "\n";
        text += ("-----------------------------------------------------------") + "\n";
        text += (newCase.getNavn()) + "\n";
        text += ("-----------------------------------------------------------") + "\n";
        text += (newCase.getTlf()) + "\n";
        text += ("-----------------------------------------------------------") + "\n";
        text += (newCase.getVarenr()) + "\n";
        text += ("-----------------------------------------------------------") + "\n";
        text += (newCase.getLøsning()) + "\n";
        text += ("-----------------------------------------------------------") + "\n";
        text += (newCase.getAnsattNavn()) + "\n";
        text += ("-----------------------------------------------------------") + "\n";
        text += (newCase.getTidspunkt().toString());

        label.setText(text);
    }

    public static TextFlow printTest(Case newCase) {
        Text text1 = new Text("Beskrivelse:");
        Text text2 = new Text(newCase.getBeskrivelse());
        Text text3 = new Text("Navn:");
        Text text4 = new Text(newCase.getNavn());
        Text text5 = new Text("Tlf:");
        Text text6 = new Text(newCase.getTlf());
        Text text7 = new Text("Varenr:");
        Text text8 = new Text(newCase.getVarenr());
        Text text9 = new Text("Løsningsforslag:");
        Text text10 = new Text(newCase.getLøsning());
        Text text11 = new Text("Ansattes navn:");
        Text text12 = new Text(newCase.getAnsattNavn());

        TextFlow textFlow = new TextFlow();
        textFlow.getChildren().addAll(text1, text2);
        if (!text4.getText().isEmpty()) {
            textFlow.getChildren().addAll(text3, text4);
        }
        if (!text6.getText().isEmpty()) {
            textFlow.getChildren().addAll(text5, text6);
        }
        if (!text8.getText().isEmpty()) {
            textFlow.getChildren().addAll(text7, text8);
        }
        if (text10.getText().isEmpty()) {
            textFlow.getChildren().addAll(text9, text10);
        }
        textFlow.getChildren().addAll(text11, text12);
        return textFlow;
    }
}
