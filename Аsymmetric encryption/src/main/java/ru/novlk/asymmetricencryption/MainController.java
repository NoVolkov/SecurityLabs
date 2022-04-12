package ru.novlk.asymmetricencryption;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Random;

public class MainController {
    private RSAAlgItem algorithm;
    public MainController(){
        algorithm=new RSAAlgItem();
    }

    @FXML
    private Button btn_decryption;

    @FXML
    private Button btn_encryption;

    @FXML
    private Button btn_export;

    @FXML
    private Button btn_import;

    @FXML
    private Label txt_keysExist;

    @FXML
    void onClick_generateKeys(ActionEvent event) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        algorithm.generateKeys();
        btn_export.setDisable(false);
        btn_encryption.setDisable(false);
        btn_decryption.setDisable(false);
        txt_keysExist.setText("Ключи созданы");
    }

    @FXML
    void onClick_export(ActionEvent event) {
        DirectoryChooser dirChooser=new DirectoryChooser();
        dirChooser.setTitle("Выбрать директорию для сохранения ключа");
        String path=dirChooser.showDialog(Starter.fxStage).getPath();
        int randNumber= new Random().nextInt(60000-0)+0;
        try(FileOutputStream file=new FileOutputStream(path+"publicKey_"+randNumber+".pubK")){
            file.write(Base64.getEncoder().encode(algorithm.getPublicKey()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClick_import(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Выбрать директорию для выбора ключа");
        File fileP=fileChooser.showOpenDialog(Starter.fxStage);
        byte[] buffer=new byte[0];
        try(FileInputStream file=new FileInputStream(fileP.getPath())){
            buffer=new byte[file.available()];
            file.read(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        algorithm.setPublicKey(Base64.getDecoder().decode(buffer));
        txt_keysExist.setText(fileP.getName().replace(".pubK",""));
    }

    @FXML
    void onClick_encryption(ActionEvent event) throws IllegalBlockSizeException, BadPaddingException, IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Выбрать файл");
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
        byte[] res=algorithm.encryption(buffer);
//        System.out.println(fileP.getPath());
//        System.out.println(fileP.getName());
//        System.out.println(fileP.getCanonicalPath());
//        System.out.println(fileP.getAbsoluteFile());
//        System.out.println(fileP.getParent());
//        System.out.println(fileP.getAbsolutePath());

        File file=new File(fileP.getParent()+"\\"+fileP.getName()+".priv");
        file.createNewFile();
        try(FileOutputStream fileOut=new FileOutputStream(file)){
            fileOut.write(res,0,res.length);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClick_decryption(ActionEvent event) throws IllegalBlockSizeException, BadPaddingException, IOException {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Выбрать файл");
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
        byte[] res=algorithm.decryption(buffer);
        String fileName= "_"+fileP.getName().replace(".priv","");
        File file=new File(fileP.getParent()+"\\"+fileName);
        file.createNewFile();
        try(FileOutputStream fileOut=new FileOutputStream(file)){
            fileOut.write(res,0,res.length);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
