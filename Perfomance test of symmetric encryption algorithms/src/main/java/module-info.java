module novlk.testsymmetricencryptionalgorithms.testsymmetricencryptionalgorithms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens novlk.testsymmetricencryptionalgorithms.testsymmetricencryptionalgorithms to javafx.fxml;
    exports novlk.testsymmetricencryptionalgorithms.testsymmetricencryptionalgorithms;
}