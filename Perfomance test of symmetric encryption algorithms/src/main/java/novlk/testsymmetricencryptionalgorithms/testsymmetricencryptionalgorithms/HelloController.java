package novlk.testsymmetricencryptionalgorithms.testsymmetricencryptionalgorithms;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import novlk.testsymmetricencryptionalgorithms.testsymmetricencryptionalgorithms.models.SymAlgItem;
import novlk.testsymmetricencryptionalgorithms.testsymmetricencryptionalgorithms.models.SymAlgItemView;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HelloController {



    @FXML
    private Button btn_startTest;

    @FXML
    private Label filePath;

    @FXML
    private TableView<SymAlgItemView> tbl_output;

    private String[] algorithms={"AES","Blowfish","RC2","DES","TripleDES"};
    private String[] modes={"CBC","ECB"};
    private String padding="PKCS5Padding";

    private ObservableList<SymAlgItemView> views;
    private String pathFileString;
    @FXML
    private void initialize(){
        views= FXCollections.observableArrayList();

        TableColumn<SymAlgItemView, String> algCol=new TableColumn<>("Алгоритм");
        algCol.setCellValueFactory(cellData->cellData.getValue().algorithmProperty());
        TableColumn<SymAlgItemView, String> modeCol=new TableColumn<>("Режим");
        modeCol.setCellValueFactory(cellData->cellData.getValue().modeProperty());
        TableColumn<SymAlgItemView, Double> tEnCol=new TableColumn<>("Время кодирования");
        tEnCol.setCellValueFactory(cellData->cellData.getValue().tEncryptProperty().asObject());
        TableColumn<SymAlgItemView, Double> tDeCol=new TableColumn<>("Время декодирования");
        tDeCol.setCellValueFactory(cellData->cellData.getValue().tDecryptProperty().asObject());
        tbl_output.getColumns().addAll(algCol,modeCol,tEnCol,tDeCol);
    }

    @FXML
    void onClick_openFile(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Выбрать файл");
        File file=fileChooser.showOpenDialog(HelloApplication.javaFXC);
        this.pathFileString=file.getPath();
        filePath.setText(file.getPath());
        btn_startTest.setDisable(false);
    }

    @FXML
    void onClick_startTest(ActionEvent event) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] buffer = new byte[0];
        try(FileInputStream file=new FileInputStream(pathFileString)){
            buffer=new byte[file.available()];
            file.read(buffer,0,buffer.length);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        SymAlgItem s=new SymAlgItem();
        views.clear();

        for(String alg:algorithms){
            for(String mode:modes){
                s.initAlgorithm(alg+"/"+mode+"/"+padding);
                s.run(buffer);
                addSymAlgItemVeiw(s);
            }
        }
        updateTable();
    }

    private void updateTable(){
        tbl_output.getItems().clear();
        tbl_output.getItems().addAll(views);
    }
    private void addSymAlgItemVeiw(SymAlgItem s){
        views.add(new SymAlgItemView(s));
    }
}
