package no.njoter.casefront;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.njoter.casefront.gui.MainWindow;

import java.io.IOException;

public class Main extends Application {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    @Override
    public void start(Stage stage) throws IOException {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setPrefSize(1000, 600);
        Scene scene = new Scene(mainWindow);
        scene.getStylesheets().add("stylesheet.css");
        stage.setTitle("CaseFront");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}