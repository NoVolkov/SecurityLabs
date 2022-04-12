module ru.novlk.digitalsignature {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.novlk.digitalsignature to javafx.fxml;
    exports ru.novlk.digitalsignature;
}