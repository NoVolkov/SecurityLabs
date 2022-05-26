package ru.novlk.digitalsignature;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Random;

public class ViewController {
    private byte[] bufferFile;
    private byte[] bufferDS;
    private DSAItem dsaItem;
    @FXML
    private Label txt_DSFilePath;

    @FXML
    private Label txt_filePath;

    @FXML
    private Label txt_hashSumFile;

    @FXML
    private Label txt_verifyDS;

    public ViewController() throws NoSuchAlgorithmException, InvalidKeyException {
        dsaItem=new DSAItem();
    }
    @FXML
    void onClick_openFile(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Выбрать файл");
        File fileP=fileChooser.showOpenDialog(Starter.fxStage);

        try(FileInputStream file=new FileInputStream(fileP.getPath())){
            bufferFile=new byte[file.available()];
            file.read(bufferFile);

            txt_filePath.setText(fileP.getPath());
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void onClick_createDS(ActionEvent event) {
        if(bufferFile==null || bufferFile.length==0){
            txt_filePath.setText("Файл не выбран");
            return;
        }
        DirectoryChooser dirChooser=new DirectoryChooser();
        dirChooser.setTitle("Выбрать директорию для сохранения ключа");
        String path=dirChooser.showDialog(Starter.fxStage).getPath();
        int randNumber=new Random().nextInt(60000-0)+0;
        try(FileOutputStream file=new FileOutputStream(path+"DS_"+randNumber+".ds")){
            file.write(Base64.getEncoder().encode(dsaItem.createDS(bufferFile)));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (SignatureException e){
            e.printStackTrace();
        }
    }

    @FXML
    void onClick_openDS(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Выбрать цифровую подпись");
        File fileP=fileChooser.showOpenDialog(Starter.fxStage);
        byte[] buffer=new byte[0];
        try(FileInputStream file=new FileInputStream(fileP.getPath())){
            buffer=new byte[file.available()];
            file.read(buffer);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferDS=Base64.getDecoder().decode(buffer);
        txt_DSFilePath.setText(fileP.getPath());
    }

    @FXML
    void onClick_vefify(ActionEvent event) {
        if(bufferFile==null || bufferFile.length==0){
            txt_filePath.setText("Файл не выбран");
            return;
        }
        if(bufferDS==null || bufferDS.length==0){
            txt_filePath.setText("Пусто");
            return;
        }
        try {
            if(dsaItem.verifyDS(bufferFile,bufferDS)){
                txt_verifyDS.setText("Подпись файла подтверждёна");
                return;
            }
            txt_verifyDS.setText("Подпись файла не подтверждёна!!!");
        }catch (SignatureException e){
            e.printStackTrace();
        }
    }
    @FXML
    void onClick_resetBuffer(ActionEvent event){
        bufferDS=null;
        bufferFile=null;
        txt_filePath.setText("Файл не выбран");
        txt_DSFilePath.setText("Пусто");
        txt_verifyDS.setText("НЕ проверено");
    }
}
