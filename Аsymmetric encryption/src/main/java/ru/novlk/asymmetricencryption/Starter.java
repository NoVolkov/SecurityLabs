package ru.novlk.asymmetricencryption;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Starter extends Application {
    public static Stage fxStage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Starter.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Ассиметричное шифрование");
        stage.setScene(scene);
        stage.show();
        fxStage=stage;
    }

    public static void main(String[] args) {
        launch();
    }
}
