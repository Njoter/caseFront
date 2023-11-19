package no.njoter.casefront.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import no.njoter.casefront.Case;
import no.njoter.casefront.util.DateFormatter;

public class CaseBox extends GridPane {

    private Case newCase;

    public CaseBox(Case newCase) {

        this.newCase = newCase;

        Label tidspunkt = new Label(DateFormatter.ddmmyyyyFormat(newCase.getTidspunkt()));
        Label beskrivelse = new Label(newCase.getBeskrivelse());
        Label navnField = new Label("Kundens navn: " + newCase.getNavn());
        Label tlfField = new Label("tlf: " + newCase.getTlf());
        Label varenrField = new Label("Varenr: " + newCase.getVarenr());
        Label løsningField = new Label("Løsning: " + newCase.getLøsning());
        Label ansattNavnField = new Label("Ansattes navn: " + newCase.getAnsattNavn());

        StackPane tidspunktPane = createTidspunktPane(tidspunkt);
        StackPane beskrivelsePane = createBeskrivelsePane(beskrivelse);
        VBox infoPane = createInfoPane(navnField, tlfField, varenrField, løsningField, ansattNavnField);

        setColumnConstraints(50, 50);
        this.setBorder(Styling.setRegularBorder(true, true, true, true));

        this.add(tidspunktPane, 0, 0, 2, 1);
        this.add(beskrivelsePane, 0, 1);
        this.add(infoPane, 1, 1);
        this.setPrefHeight(100);
    }

    private StackPane createTidspunktPane(Label tidspunkt) {
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.BASELINE_LEFT);
        stackPane.setPadding(new Insets(5, 5, 5, 5));
        stackPane.getChildren().add(tidspunkt);
        return stackPane;
    }

    private StackPane createBeskrivelsePane(Label beskrivelse) {
        beskrivelse.setWrapText(true);
        StackPane stackPane = new StackPane();
        beskrivelse.prefWidthProperty().bind(stackPane.widthProperty());
        stackPane.setAlignment(Pos.TOP_LEFT);
        stackPane.setBorder(Styling.setRegularBorder(true, true, false, false));
        stackPane.setPadding(new Insets(5, 5, 5, 5));
        stackPane.getChildren().add(beskrivelse);
        return stackPane;
    }

    private VBox createInfoPane(
            Label navnField, Label tlfField, Label varenrField, Label løsningField, Label ansattNavnField
    ) {
        VBox vBox = new VBox();
        // Set Borders
        navnField.setBorder(Styling.setRegularBorder(true, false, true, false));
        tlfField.setBorder(Styling.setRegularBorder(false, false, true, false));
        varenrField.setBorder(Styling.setRegularBorder(false, false, true, false));
        løsningField.setBorder(Styling.setRegularBorder(false, false, true, false));

        navnField.prefWidthProperty().bind(vBox.widthProperty());
        tlfField.prefWidthProperty().bind(vBox.widthProperty());
        varenrField.prefWidthProperty().bind(vBox.widthProperty());
        løsningField.prefWidthProperty().bind(vBox.widthProperty());
        ansattNavnField.prefWidthProperty().bind(vBox.widthProperty());
        vBox.setPadding(new Insets(0, 5, 0, 5));
        vBox.getChildren().addAll(navnField, tlfField, varenrField, løsningField, ansattNavnField);
        return vBox;
    }

    private void setColumnConstraints(int percent1, int percent2) {
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(percent1);
        col2.setPercentWidth(percent2);
        this.getColumnConstraints().addAll(col1, col2);
    }

    public Case getNewCase() {
        return newCase;
    }
}
