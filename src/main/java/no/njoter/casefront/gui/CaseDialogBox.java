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

    private static Case originalCase;
    private static Case newCase;
    private static Label notValidLabel;
    private static Stage window;
    private static TextArea beskrivelseArea;
    private static TextField kundeNavnField;
    private static TextField tlfField;
    private static TextField varenrField;
    private static TextArea løsningArea;
    private static TextField ansattNavnField;

    public static Case display(Case passedCase) {

        if (passedCase != null) {
            originalCase = copyOriginalCase(passedCase);
        }
        newCase = passedCase;

        window = createWindow();

        beskrivelseArea = setBeskrivelseArea();
        kundeNavnField = setKundenavnField();
        tlfField = setTlfField();
        varenrField = setVarenrField();
        løsningArea = setLøsningArea();
        ansattNavnField = setAnsattNavnField();

        notValidLabel = new Label("");
        notValidLabel.setTextFill(Color.RED);

        Button okBtn = setOkBtn();
        Button avbrytBtn = setAvbrytBtn();

        VBox root = createRoot();
        GridPane grid = createGrid();
        HBox buttonPane = createButtonPane(okBtn, avbrytBtn);

        window.setOnCloseRequest(e -> {
            closeDialog(window);
        });

        root.getChildren().addAll(grid, buttonPane, notValidLabel);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("stylesheet.css");
        window.setScene(scene);
        window.showAndWait();
        return newCase;
    }

    private static Case copyOriginalCase(Case passedCase) {
        Case originalCase = new Case();
        originalCase.setBeskrivelse(passedCase.getBeskrivelse());
        originalCase.setNavn(passedCase.getNavn());
        originalCase.setTlf(passedCase.getTlf());
        originalCase.setVarenr(passedCase.getVarenr());
        originalCase.setLøsning(passedCase.getLøsning());
        originalCase.setAnsattNavn(passedCase.getAnsattNavn());
        return originalCase;
    }

    private static VBox createRoot() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private static Stage createWindow() {
        Stage stage = new Stage();
        stage.setTitle("Ny Case");
        stage.setWidth(800);
        stage.setHeight(600);

        return stage;
    }

    private static TextArea setBeskrivelseArea() {
        TextArea textArea = new TextArea();
        textArea.setPromptText("* Beskrivelse: (Hva gjelder casen?)");
        textArea.textProperty().bindBidirectional(newCase.beskrivelseProperty());
        return textArea;
    }

    private static TextField setKundenavnField() {
        TextField textField = new TextField();
        textField.setPromptText("Kundens navn:");
        textField.textProperty().bindBidirectional(newCase.navnProperty());
        return textField;
    }

    private static TextField setTlfField() {
        TextField textField = new TextField();
        textField.setPromptText("tlf:");
        textField.textProperty().bindBidirectional(newCase.tlfProperty());
        return textField;
    }

    private static TextField setVarenrField() {
        TextField textField = new TextField();
        textField.setPromptText("Varenummer:");
        textField.textProperty().bindBidirectional(newCase.varenrProperty());
        return textField;
    }

    private static TextArea setLøsningArea() {
        TextArea textArea = new TextArea();
        textArea.setPromptText("Løsning: (Hva har kunden fått beskjed om?)");
        textArea.textProperty().bindBidirectional(newCase.løsningProperty());
        return textArea;
    }

    private static TextField setAnsattNavnField() {
        TextField textField = new TextField();
        textField.setPromptText("* Ansattes navn:");
        textField.textProperty().bindBidirectional(newCase.ansattNavnProperty());
        return textField;
    }

    private static Button setOkBtn() {
        Button button = new Button("Ok");
        button.setPrefWidth(100);

        button.setOnAction(e -> {
            if (validateFields()) {
                removeSemiColons();
                if (newCase.getTidspunkt() == null) {
                    newCase.setTidspunkt(LocalDateTime.now());
                }
                newCase.setFullText();
                window.close();
            }
        });
        return button;
    }

    private static Button setAvbrytBtn() {
        Button button = new Button("Avbryt");
        button.setPrefWidth(100);

        button.setOnAction(e -> {
            closeDialog(window);
        });
        return button;
    }

    private static void closeDialog(Stage window) {
        if (validateAvbryt()) {
            if (newCase != null) {
                setCaseFieldsToOriginalState();
                newCase = null;
            }
            window.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Er du sikker på at du vil avbryte?\n" +
                    "Endringer vil ikke bli lagret.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (newCase != null) {
                    setCaseFieldsToOriginalState();
                    newCase = null;
                }
                window.close();
            }
        }
    }

    private static void setCaseFieldsToOriginalState() {
        beskrivelseArea.setText(originalCase.getBeskrivelse());
        kundeNavnField.setText(originalCase.getNavn());
        tlfField.setText(originalCase.getTlf());
        varenrField.setText(originalCase.getVarenr());
        løsningArea.setText(originalCase.getLøsning());
        ansattNavnField.setText(originalCase.getAnsattNavn());
    }

    private static boolean validateAvbryt() {
        ArrayList<TextInputControl> fieldsArray = populateFieldsArray();
        for (TextInputControl field : fieldsArray) {
            if (field.getText() != null) {
                return false;
            }
        }
        return true;
    }

    private static void removeSemiColons() {
        // Semicolons need to be removed because they are the regex for splitting the string when
        // reading from file
        if (beskrivelseArea.getText() != null) {
            beskrivelseArea.setText(beskrivelseArea.getText().replace(";", "."));
        }
        if (kundeNavnField.getText() != null) {
            kundeNavnField.setText(kundeNavnField.getText().replace(";", "."));
        }
        if (tlfField.getText() != null) {
            tlfField.setText(tlfField.getText().replace(";", "."));
        }
        if (varenrField.getText() != null) {
            varenrField.setText(varenrField.getText().replace(";", "."));
        }
        if (løsningArea.getText() != null) {
            løsningArea.setText(løsningArea.getText().replace(";", "."));
        }
        if (ansattNavnField.getText() != null) {
            ansattNavnField.setText(ansattNavnField.getText().replace(";", "."));
        }
    }

    private static boolean validateFields() {
        boolean valid = true;

        if (beskrivelseArea.getText() == null) {
            valid = false;
        } else if (beskrivelseArea.getText().isEmpty()) {
            valid = false;
        }

        if (ansattNavnField.getText() == null) {
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

        grid.add(beskrivelseArea, 0, 0);
        grid.add(kundeNavnField, 0, 1);
        grid.add(tlfField, 0, 2);
        grid.add(varenrField, 0, 3);
        grid.add(løsningArea, 0, 4);
        grid.add(ansattNavnField, 0, 5);

        return grid;
    }
}