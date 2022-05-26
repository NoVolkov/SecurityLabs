module ru.novlk.shorthand {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens ru.novlk.shorthand to javafx.fxml;
    exports ru.novlk.shorthand;
}