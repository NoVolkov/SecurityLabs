package novlk.testsymmetricencryptionalgorithms.testsymmetricencryptionalgorithms.models;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;

public class SymAlgItem {
    private Cipher encryptCipher;
    private Cipher decryptCipher;
    private Key key;
    private IvParameterSpec ivSpec;
    private String algorithm;
    private String mode;
    private Double tEn;
    private Double tDe;
    public SymAlgItem(){

    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getMode() {
        return mode;
    }

    public Double gettEn() {
        return tEn;
    }

    public Double gettDe() {
        return tDe;
    }

    public void initAlgorithm(String transformation) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException {
        try {
            encryptCipher=Cipher.getInstance(transformation);
            decryptCipher=Cipher.getInstance(transformation);

        } catch (Exception e) {
            throw new RuntimeException("Неверная строка трансформации.");
        }


        String[] r=transformation.split("/");
        algorithm=r[0];
        mode=r[1];
        KeyGenerator keyGen=KeyGenerator.getInstance(r[0]);
        if(algorithm.equals("TripleDES")){
            keyGen.init(112);
        }else{
            if(algorithm.equals("DES")){
                keyGen.init(56);
            }else {
                keyGen.init(256);
            }
        }
        key=keyGen.generateKey();

        initMode(r[1]);
    }
    private void initIvSpec(String algorithm) throws NoSuchAlgorithmException {
        byte[] rnd=new byte[0];

        switch (algorithm){
            case "TripleDES":
            case "RC2":
            case "Blowfish":
            case "DES":rnd=new byte[8];break;
            default:rnd=new byte[16];
        }
        SecureRandom.getInstanceStrong().nextBytes(rnd);

        ivSpec=new IvParameterSpec(rnd);
    }
    private void initMode(String mode) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        switch(mode){
            case "CBC":
                initIvSpec(algorithm);
                encryptCipher.init(Cipher.ENCRYPT_MODE,key,ivSpec);
                decryptCipher.init(Cipher.DECRYPT_MODE,key,ivSpec);
                break;
            case "ECB":
                encryptCipher.init(Cipher.ENCRYPT_MODE,key);
                decryptCipher.init(Cipher.DECRYPT_MODE,key);
                break;
            case "OFB":
                break;
            default: throw new RuntimeException("Для данного режима "+mode+" решения не предусмотрено.");
        }
    }

    public void run(byte[] input) throws IllegalBlockSizeException, BadPaddingException {
        long[] res=new long[2];
        long l1=System.currentTimeMillis();
        byte[] encrypted=encryptCipher.doFinal(input);
        long l2=System.currentTimeMillis();
        decryptCipher.doFinal(encrypted);
        long l3=System.currentTimeMillis();
        tEn=(double)(l2-l1)/1000;
        tDe=(double)(l3-l2)/1000;

        //encryptCipher=null;
    }
}
