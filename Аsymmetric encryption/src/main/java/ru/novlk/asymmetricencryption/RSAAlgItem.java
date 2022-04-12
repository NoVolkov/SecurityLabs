package ru.novlk.asymmetricencryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class RSAAlgItem {
    private KeyPair keyPair;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private Cipher encryptCipher;
    private Cipher decryptCipher;

    public void generateKeys() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        KeyPairGenerator generator=KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);//generator`s size of 2048 bits

        keyPair=generator.generateKeyPair();
        privateKey=keyPair.getPrivate();
        publicKey=keyPair.getPublic();
        //initCiphers();
    }
    public byte[] getPublicKey(){
        return publicKey.getEncoded();
    }

    public void setPublicKey(byte[] inputBytesPublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec=new X509EncodedKeySpec(inputBytesPublicKey);
        this.publicKey=keyFactory.generatePublic(publicKeySpec);
    }
    public byte[] encryption(byte[] inputFile) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher encrypt=Cipher.getInstance("RSA");
        encrypt.init(Cipher.ENCRYPT_MODE,publicKey);
        return encrypt.doFinal(inputFile);
    }
    public byte[] decryption(byte[] inputFile)  {
        byte[] res=new byte[0];
        try {
            Cipher decrypt=Cipher.getInstance("RSA");
            decrypt.init(Cipher.DECRYPT_MODE,privateKey);
            res= decrypt.doFinal(inputFile);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return res;
    }
    private void initCiphers() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        encryptCipher=Cipher.getInstance("RSA");
        decryptCipher=Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE,publicKey);
        decryptCipher.init(Cipher.DECRYPT_MODE,privateKey);
    }
}
