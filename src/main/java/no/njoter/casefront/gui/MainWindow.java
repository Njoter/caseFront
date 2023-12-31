package no.njoter.casefront.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import no.njoter.casefront.Case;
import no.njoter.casefront.util.CaseReader;
import no.njoter.casefront.util.CaseWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;

public class MainWindow extends BorderPane {

    public static final String FOLDER_PATH = "src/main/resources/caser/";
    private TextField searchField;
    private ObservableList<CaseBox> caseBoxList;
    private FilteredList<CaseBox> filteredList;
    private ListView<CaseBox> listView;
    private SelectedCaseFlow caseTextFlow;
    private Case selectedCase;
    private final int CUSTOM_BUTTON_WIDTH = 125;
    private final int CUSTOM_BUTTON_HEIGHT = 50;
    private final CaseDialogBox caseDialogBox = new CaseDialogBox();

    public MainWindow() {

        ArrayList<Case> cases = loadCasesFromFile();
        populateCaseBoxList(cases);

        Button newCaseBtn = setCreateCaseBtn();
        Button editCaseBtn = setEditCaseBtn();
        Button deleteCaseBtn = setDeleteCaseBtn();

        searchField = createSearchField();

        HBox topLayout = createTopLayout();
        VBox leftLayout = createLeftLayout(newCaseBtn);
        GridPane centerLayout = createCenterLayout();
        ScrollPane caseList = createCaseList();
        ScrollPane selectedCasePane = createSelectedCasePane();
        HBox buttonsPane = createButtonsPane();

        setCaseListContent(caseList);

        topLayout.getChildren().addAll(searchField);
        buttonsPane.getChildren().addAll(editCaseBtn, deleteCaseBtn);
        centerLayout.add(caseList, 0, 0, 1, 2);
        centerLayout.add(selectedCasePane, 1, 0);
        centerLayout.add(buttonsPane, 1, 1);

        this.setTop(topLayout);
        this.setLeft(leftLayout);
        this.setCenter(centerLayout);
    }

    private ArrayList<Case> loadCasesFromFile() {
        ArrayList<Case> cases = new ArrayList<>();
        CaseReader.readFromFile(cases, FOLDER_PATH);
        sortCasesByLocalDateTime(cases);
        return cases;
    }

    private void sortCasesByLocalDateTime(ArrayList<Case> cases) {
        Comparator<Case> comparator = new Comparator<Case>() {
            @Override
            public int compare(Case o1, Case o2) {
                if (o1.getTidspunkt().isBefore(o2.getTidspunkt())) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };
        Collections.sort(cases, comparator);
    }

    private void populateCaseBoxList(ArrayList<Case> cases) {
        ArrayList<CaseBox> caseBoxes = new ArrayList<>();
        for (Case newCase : cases) {
            new CaseBox(newCase);
            caseBoxes.add(new CaseBox(newCase));
        }
        caseBoxList = FXCollections.observableList(caseBoxes);
    }

    private Button setCreateCaseBtn() {
        Button button = new Button("Lag ny case");
        button.setPrefSize(CUSTOM_BUTTON_WIDTH, CUSTOM_BUTTON_HEIGHT);
        button.setOnAction(e -> {
            if (!caseDialogBox.isRunning()) {
                Case newCase = new Case();
                newCase = caseDialogBox.display(newCase);
                if (newCase != null) {
                    caseBoxList.add(0, new CaseBox(newCase));
                    CaseWriter.writeToFile(newCase, FOLDER_PATH);
                }
            }
        });
        return button;
    }

    private Button setEditCaseBtn() {
        Button button = new Button("Rediger case");
        button.setPrefSize(CUSTOM_BUTTON_WIDTH, CUSTOM_BUTTON_HEIGHT);
        button.setOnAction(e -> {
            if (!caseDialogBox.isRunning()) {
                if (selectedCase != null) {
                    Case newCase = caseDialogBox.display(selectedCase);
                    if (newCase != null) {
                        CaseWriter.writeToFile(newCase, FOLDER_PATH);
                    }
                }
            }
        });
        return button;
    }

    private Button setDeleteCaseBtn() {
        Button button = new Button("Slett case");
        button.setPrefSize(CUSTOM_BUTTON_WIDTH, CUSTOM_BUTTON_HEIGHT);
        button.setOnAction(e -> {
            if (selectedCase != null) {
                deleteCase(selectedCase);
            }
        });
        return button;
    }

    private TextField createSearchField() {
        TextField textField = new TextField();
        textField.setPromptText("Søk på hva som helst");
        textField.textProperty().addListener(observable -> {
            filteredList = new FilteredList<>(caseBoxList, i ->
                    i.getNewCase().getFullText().toLowerCase().contains(searchField.getText().toLowerCase()));
            listView.setItems(filteredList);
        });
        return textField;
    }

    private void deleteCase(Case existingCase) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Er du sikker på at du vil slette case?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            CaseWriter.deleteFile(selectedCase, FOLDER_PATH);
            removeCaseFromCaseBoxList(selectedCase);
        }
    }

    private HBox createTopLayout() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(20));
        hBox.setSpacing(10);
        return hBox;
    }

    private GridPane createCenterLayout() {
        GridPane pane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        col1.setPercentWidth(50);
        col2.setPercentWidth(50);
        row1.setPercentHeight(75);
        row2.setPercentHeight(25);
        pane.getColumnConstraints().addAll(col1, col2);
        pane.getRowConstraints().addAll(row1, row2);
        return pane;
    }

    private VBox createLeftLayout(Button createCaseBtn) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(20);
        Image logo = createLogo();
        ImageView imageView = new ImageView(logo);
        vBox.getChildren().addAll(imageView, createCaseBtn);
        return vBox;
    }

    private Image createLogo() {
        try {
            FileInputStream inputStream = new FileInputStream("src/main/resources/jysk-logo.png");
            return new Image(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private ScrollPane createCaseList() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        return scrollPane;
    }

    private ScrollPane createSelectedCasePane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        GridPane.setHgrow(scrollPane, Priority.ALWAYS);
        GridPane.setVgrow(scrollPane, Priority.ALWAYS);
        scrollPane.setMaxWidth(Double.MAX_VALUE);
        scrollPane.setMaxHeight(Double.MAX_VALUE);
        caseTextFlow = new SelectedCaseFlow();
        scrollPane.setStyle("-fx-background: white");
        scrollPane.setContent(caseTextFlow);
        return scrollPane;
    }

    private HBox createButtonsPane() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        return hBox;
    }

    private void removeCaseFromCaseBoxList(Case newCase) {
        for (int i = 0; i < caseBoxList.size(); i++) {
            if (caseBoxList.get(i).getNewCase().getTidspunkt() == newCase.getTidspunkt()) {
                caseBoxList.remove(i);
                return;
            }
        }
    }

    private void setCaseListContent(ScrollPane scrollPane) {
        listView = new ListView<>();
        filteredList = new FilteredList<>(caseBoxList);
        listView.setItems(filteredList);
        scrollPane.setContent(listView);

        // Event handler
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CaseBox>() {
            @Override
            public void changed(ObservableValue<? extends CaseBox> observableValue, CaseBox caseBox, CaseBox t1) {
                if (!listView.getSelectionModel().isEmpty()) {
                    selectedCase = listView.getSelectionModel().getSelectedItem().getNewCase();
                    caseTextFlow.printCase(selectedCase);
                }
            }
        });
    }

    public CaseDialogBox getCaseDialogBox() {
        return caseDialogBox;
    }
}
