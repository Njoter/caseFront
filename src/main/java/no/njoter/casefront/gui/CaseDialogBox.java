package no.njoter.casefront.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import no.njoter.casefront.Case;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class CaseDialogBox {

    private static Case newCase;
    private static TextArea beskrivelseArea = new TextArea();
    private static TextField kundeNavnField = new TextField();
    private static TextField tlfField = new TextField();
    private static TextField varenrField = new TextField();
    private static TextArea løsningArea = new TextArea();
    private static TextField ansattNavnField = new TextField();
    private static Label notValidLabel = new Label("");

    public static Case display(Case existingCase) {

        newCase = existingCase;
        if (newCase != null) {
            beskrivelseArea = new TextArea(newCase.getBeskrivelse());
            kundeNavnField = new TextField(newCase.getNavn());
            tlfField = new TextField(newCase.getTlf());
            varenrField = new TextField(newCase.getVarenr());
            løsningArea = new TextArea(newCase.getLøsning());
            ansattNavnField = new TextField(newCase.getAnsattNavn());
        }

        // Stage
        Stage window = new Stage();
        window.setTitle("Ny Case");
        window.setWidth(800);
        window.setHeight(600);

        // Buttons
        Button okBtn = new Button("ok");
        Button avbrytBtn = new Button("Avbryt");
        okBtn.setPrefWidth(100);
        avbrytBtn.setPrefWidth(100);

        // Panes
        VBox root = new VBox();
        GridPane grid = createGrid();
        HBox buttonPane = createButtonPane(okBtn, avbrytBtn);

        // Settings
        root.setPadding(new Insets(20));
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);
        notValidLabel.setTextFill(Color.RED);

        // Event handlers
        okBtn.setOnAction(e -> {
            if (validateFields()) {
                setCaseFields();
                clearFields();
                window.close();
            }
        });
        
        avbrytBtn.setOnAction(e -> {
            closeDialog(window);
        });

        window.setOnCloseRequest(e -> {
            closeDialog(window);
        });

        // Add children and set scene
        root.getChildren().addAll(grid, buttonPane, notValidLabel);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("stylesheet.css");
        window.setScene(scene);
        window.showAndWait();
        return newCase;
    }

    private static void closeDialog(Stage window) {
        if (validateAvbryt()) {
            clearFields();
            window.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Er du sikker på at du vil avbryte?\n" +
                    "Informasjonen vil ikke bli lagret.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                clearFields();
                window.close();
            }
        }
    }

    private static void clearFields() {
        ArrayList<TextInputControl> fieldsArray = populateFieldsArray();
        for (TextInputControl field : fieldsArray) {
            field.clear();
        }
    }

    private static boolean validateAvbryt() {
        ArrayList<TextInputControl> fieldsArray = populateFieldsArray();
        for (TextInputControl field : fieldsArray) {
            if (!field.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static void setCaseFields() {
        removeSemiColons();
        newCase = new Case(
                beskrivelseArea.getText(),
                kundeNavnField.getText(),
                tlfField.getText(),
                varenrField.getText(),
                løsningArea.getText(),
                ansattNavnField.getText(),
                LocalDateTime.now()
        );
    }

    private static void removeSemiColons() {
        // Semicolons need to be removed because they are the regex for splitting the string when
        // reading from file
        beskrivelseArea.setText(beskrivelseArea.getText().replace(";", "."));
        kundeNavnField.setText(kundeNavnField.getText().replace(";", "."));
        tlfField.setText(tlfField.getText().replace(";", "."));
        varenrField.setText(varenrField.getText().replace(";", "."));
        løsningArea.setText(løsningArea.getText().replace(";", "."));
        ansattNavnField.setText(ansattNavnField.getText().replace(";", "."));
    }

    private static boolean validateFields() {
        boolean valid = true;
        if (beskrivelseArea.getText().isEmpty()) {
            valid = false;
        } else if (ansattNavnField.getText().isEmpty()) {
            valid = false;
        }
        if (!valid) {
            notValidLabel.setText("Alle felt merket med * må fylles ut.");
        }
        return valid;
    }

    private static ArrayList<TextInputControl> populateFieldsArray() {
        ArrayList<TextInputControl> fieldsArray = new ArrayList<>();
        fieldsArray.add(beskrivelseArea);
        fieldsArray.add(kundeNavnField);
        fieldsArray.add(tlfField);
        fieldsArray.add(varenrField);
        fieldsArray.add(løsningArea);
        fieldsArray.add(ansattNavnField);
        return fieldsArray;
    }

    private static HBox createButtonPane(Button okBtn, Button avbrytBtn) {
        HBox pane = new HBox();
        pane.setSpacing(20);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(okBtn, avbrytBtn);
        return pane;
    }

    private static GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        beskrivelseArea.setPromptText("* Beskrivelse: (Hva gjelder casen?)");
        kundeNavnField.setPromptText("Kundens navn:");
        tlfField.setPromptText("tlf:");
        varenrField.setPromptText("Varenummer:");
        løsningArea.setPromptText("Løsning: (Hva har kunden fått beskjed om?)");
        ansattNavnField.setPromptText("* Ansattes navn:");

        grid.add(beskrivelseArea, 0, 0);
        grid.add(kundeNavnField, 0, 1);
        grid.add(tlfField, 0, 2);
        grid.add(varenrField, 0, 3);
        grid.add(løsningArea, 0, 4);
        grid.add(ansattNavnField, 0, 5);

        return grid;
    }
}
