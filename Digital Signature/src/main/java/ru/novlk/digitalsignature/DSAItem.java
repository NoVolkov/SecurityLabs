package ru.novlk.digitalsignature;

import java.security.*;

public class DSAItem {
    private KeyPair keyPair;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private Signature sign;
    private Signature verify;
    public DSAItem() throws NoSuchAlgorithmException, InvalidKeyException {
        initKeys();
        sign=Signature.getInstance("SHA256WithDSA");
        verify=Signature.getInstance("SHA256WithDSA");
        sign.initSign(keyPair.getPrivate(),new SecureRandom());
        verify.initVerify(keyPair.getPublic());
    }
    private void initKeys() throws NoSuchAlgorithmException {
        keyPair= KeyPairGenerator.getInstance("DSA").generateKeyPair();

    }
    public byte[] createDS(byte[] file) throws SignatureException {
        sign.update(file);
        return sign.sign();
    }
    public boolean verifyDS(byte[] file, byte[] DS)throws SignatureException{
        verify.update(file);
        return verify.verify(DS);
    }
}
