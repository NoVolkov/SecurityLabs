module ru.novlk.asymmetricencryption {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens ru.novlk.asymmetricencryption to javafx.fxml;
    exports ru.novlk.asymmetricencryption;
}