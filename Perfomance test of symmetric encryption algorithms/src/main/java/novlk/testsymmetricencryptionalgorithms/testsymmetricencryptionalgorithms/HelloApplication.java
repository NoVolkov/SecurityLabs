package novlk.testsymmetricencryptionalgorithms.testsymmetricencryptionalgorithms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage javaFXC;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Тест симметричных методов шифрования");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        javaFXC=stage;
    }

    public static void main(String[] args) {
        launch();
    }

}