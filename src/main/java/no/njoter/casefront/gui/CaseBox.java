package no.njoter.casefront.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import no.njoter.casefront.Case;
import no.njoter.casefront.util.DateFormatter;

public class CaseBox extends GridPane {

    private Case newCase;

    public CaseBox(Case newCase) {

        this.newCase = newCase;

        TextFlow tidspunktFlow = createTidspunktFlow();
        TextFlow beskrivelseFlow = createBeskrivelseFlow();

        TextFlow navnFlow = new TextFlow();
        TextFlow tlfFlow = new TextFlow();
        TextFlow varenrFlow = new TextFlow();
        TextFlow løsningFlow = new TextFlow();
        TextFlow ansattNavnFlow = new TextFlow();
        VBox infoPane = createInfoPane(navnFlow, tlfFlow, varenrFlow, løsningFlow, ansattNavnFlow);

        setBorders(tidspunktFlow, beskrivelseFlow, navnFlow, tlfFlow, varenrFlow, løsningFlow);

        setColumnConstraints(50, 50);
        this.setPrefWidth(100);

        this.add(tidspunktFlow, 0, 0, 2, 1);
        this.add(beskrivelseFlow, 0, 1);
        this.add(infoPane, 1, 1);
        this.setPrefHeight(100);
    }

    private void setBorders(
            TextFlow tidspunktFlow, TextFlow beskrivelseFlow,
            TextFlow navnFlow, TextFlow tlfFlow, TextFlow varenrFlow, TextFlow løsningFlow
    ) {
        this.setBorder(Styling.setRegularBorder(true, true, true, true));
        tidspunktFlow.setBorder(Styling.setRegularBorder(false, false, true, false));
        beskrivelseFlow.setBorder(Styling.setRegularBorder(false, true, false, false));
        navnFlow.setBorder(Styling.setRegularBorder(false, false, true, false));
        tlfFlow.setBorder(Styling.setRegularBorder(false, false, true, false));
        varenrFlow.setBorder(Styling.setRegularBorder(false, false, true, false));
        løsningFlow.setBorder(Styling.setRegularBorder(false, false, true, false));
    }

    private TextFlow createTidspunktFlow() {
        TextFlow textFlow = new TextFlow();
        textFlow.setPadding(new Insets(5));
        textFlow.setStyle("-fx-background-color: #8BBACA");
        Text text = new Text(DateFormatter.ddmmyyyyFormat(newCase.getTidspunkt()));
        text.setFill(Color.WHITE);
        textFlow.getChildren().add(text);
        return textFlow;
    }

    private TextFlow createBeskrivelseFlow() {
        TextFlow textFlow = new TextFlow();
        textFlow.setPadding(new Insets(5));
        Text text = new Text();
        text.textProperty().bind(newCase.beskrivelseProperty());
        textFlow.getChildren().add(text);
        return textFlow;
    }

    private VBox createInfoPane(
            TextFlow navnFlow, TextFlow tlfFlow, TextFlow varenrFlow, TextFlow løsningFlow, TextFlow ansattNavnFlow
    ) {
        VBox vBox = new VBox();

        Text navnPrefix = new Text("Kundens navn: ");
        Text tlfPrefix = new Text("Tlf: ");
        Text varenrPrefix = new Text("Varenr: ");
        Text løsningPrefix = new Text("Løsning: ");
        Text ansattNavnPrefix = new Text("Ansattes navn: ");

        Text navnBind = new Text();
        Text tlfBind = new Text();
        Text varenrBind = new Text();
        Text løsningBind = new Text();
        Text ansattNavnBind = new Text();

        navnBind.textProperty().bind(newCase.navnProperty());
        tlfBind.textProperty().bind(newCase.tlfProperty());
        varenrBind.textProperty().bind(newCase.varenrProperty());
        løsningBind.textProperty().bind(newCase.løsningProperty());
        ansattNavnBind.textProperty().bind(newCase.ansattNavnProperty());

        navnFlow.getChildren().addAll(navnPrefix, navnBind);
        tlfFlow.getChildren().addAll(tlfPrefix, tlfBind);
        varenrFlow.getChildren().addAll(varenrPrefix, varenrBind);
        løsningFlow.getChildren().addAll(løsningPrefix, løsningBind);
        ansattNavnFlow.getChildren().addAll(ansattNavnPrefix, ansattNavnBind);

        vBox.setPadding(new Insets(0, 5, 0, 5));
        vBox.getChildren().addAll(navnFlow, tlfFlow, varenrFlow, løsningFlow, ansattNavnFlow);
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
