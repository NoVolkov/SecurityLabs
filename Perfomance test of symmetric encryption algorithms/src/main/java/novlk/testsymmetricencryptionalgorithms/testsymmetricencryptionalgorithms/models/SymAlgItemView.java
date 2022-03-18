package novlk.testsymmetricencryptionalgorithms.testsymmetricencryptionalgorithms.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class SymAlgItemView {
    private SimpleStringProperty algorithm;
    private SimpleStringProperty mode;
    private SimpleDoubleProperty tEncrypt;
    private SimpleDoubleProperty tDecrypt;
    public SymAlgItemView(SymAlgItem item){
        this.algorithm=new SimpleStringProperty(item.getAlgorithm());
        this.mode=new SimpleStringProperty(item.getMode());
        this.tEncrypt=new SimpleDoubleProperty(item.gettEn());
        this.tDecrypt=new SimpleDoubleProperty(item.gettDe());
    }

    public SymAlgItemView(String algorithm, String mode, Double tEncrypt, Double tDecrypt) {
        this.algorithm = new SimpleStringProperty(algorithm);
        this.mode = new SimpleStringProperty(mode);
        this.tEncrypt = new SimpleDoubleProperty(tEncrypt);
        this.tDecrypt = new SimpleDoubleProperty(tDecrypt);
    }

    public String getAlgorithm() {
        return algorithm.get();
    }

    public SimpleStringProperty algorithmProperty() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm.set(algorithm);
    }

    public String getMode() {
        return mode.get();
    }

    public SimpleStringProperty modeProperty() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode.set(mode);
    }

    public double getEncrypt() {
        return tEncrypt.get();
    }

    public SimpleDoubleProperty tEncryptProperty() {
        return tEncrypt;
    }

    public void setEncrypt(double tEncrypt) {
        this.tEncrypt.set(tEncrypt);
    }

    public double getDecrypt() {
        return tDecrypt.get();
    }

    public SimpleDoubleProperty tDecryptProperty() {
        return tDecrypt;
    }

    public void setDecrypt(double tDecrypt) {
        this.tDecrypt.set(tDecrypt);
    }
}
