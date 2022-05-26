package ru.novlk.digitalsignature;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Starter extends Application {
    public static Stage fxStage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Starter.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Цифровая подпись");
        stage.setScene(scene);
        stage.show();
        fxStage=stage;
    }

    public static void main(String[] args) {
        launch();
    }
}
