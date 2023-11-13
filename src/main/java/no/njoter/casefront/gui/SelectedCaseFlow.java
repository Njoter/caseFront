package no.njoter.casefront.gui;

import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import no.njoter.casefront.Case;

public class SelectedCaseFlow extends TextFlow {

    private final Text beskrivelseHeader = new Text("Beskrivelse:");
    private final Text navnHeader = new Text("Navn:");
    private final Text tlfHeader = new Text("Tlf:");
    private final Text varenrHeader = new Text("Varenummer:");
    private final Text løsningHeader = new Text("Løsningsforslag:");
    private final Text ansattNavnHeader = new Text("Ansattes navn:");

    private Text beskrivelse = new Text();
    private Text navn = new Text();
    private Text tlf = new Text();
    private Text varenr = new Text();
    private Text løsning = new Text();
    private Text ansattNavn = new Text();

    public SelectedCaseFlow() {
        this.setPadding(new Insets(20));
        beskrivelseHeader.setStyle("-fx-font-weight: bold;");
        navnHeader.setStyle("-fx-font-weight: bold;");
        tlfHeader.setStyle("-fx-font-weight: bold;");
        varenrHeader.setStyle("-fx-font-weight: bold;");
        løsningHeader.setStyle("-fx-font-weight: bold;");
        ansattNavnHeader.setStyle("-fx-font-weight: bold;");
    }

    public void printCase(Case newCase) {
        if (this.getChildren().isEmpty()) {
            setChildren();
        }
        setTexts(newCase);
    }

    private void setChildren() {
        this.getChildren().addAll(
                beskrivelseHeader,
                new Text(System.lineSeparator()),
                beskrivelse,
                new Text(System.lineSeparator()),
                new Text(System.lineSeparator()),
                navnHeader,
                new Text(System.lineSeparator()),
                navn,
                new Text(System.lineSeparator()),
                new Text(System.lineSeparator()),
                tlfHeader,
                new Text(System.lineSeparator()),
                tlf,
                new Text(System.lineSeparator()),
                new Text(System.lineSeparator()),
                varenrHeader,
                new Text(System.lineSeparator()),
                varenr,
                new Text(System.lineSeparator()),
                new Text(System.lineSeparator()),
                løsningHeader,
                new Text(System.lineSeparator()),
                løsning,
                new Text(System.lineSeparator()),
                new Text(System.lineSeparator()),
                ansattNavnHeader,
                new Text(System.lineSeparator()),
                ansattNavn
        );
    }

    private void setTexts(Case newCase) {
        beskrivelse.setText(newCase.getBeskrivelse());
        navn.setText(newCase.getNavn());
        tlf.setText(newCase.getTlf());
        varenr.setText(newCase.getVarenr());
        løsning.setText(newCase.getLøsning());
        ansattNavn.setText(newCase.getAnsattNavn());
    }
}
