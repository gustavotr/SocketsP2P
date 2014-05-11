package util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;

public class Criptografia {

    private final static String ALGORITMO = "RSA/ECB/NoPadding";

    /**
     * Retorna um KeyPair. KeyPair.getPrivate() recupera a chave privada e
     * KeyPair.getPublic() recupera a chave pública
     * 
     * @return KeyPair
     */
    public static KeyPair geraKeyPair() {
        KeyPair pair = null;
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITMO);
            final SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(128, random);
//            keyGen.initialize(2048, random);
            pair = keyGen.generateKeyPair();
        } catch (Exception e) {
            System.out.println("Erro geração de key pair: " + e.getLocalizedMessage());
        }
        return pair;
    }

    /**
     * Encripta array de bytes utilizando chave privada
     * 
     * @param mensagem
     * @param privateKey
     * @return byte[] contendo mensagem encriptada
     */
    public static byte[] encriptarComChavePrivada(byte[] mensagem, PrivateKey privateKey) {
        byte[] cipherData = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            cipherData = cipher.doFinal(mensagem);
        } catch (Exception ex) {
            System.out.println("Erro Encrypt com chave privada: " + ex.getLocalizedMessage());
        }
        return cipherData;
    }

    /**
     * Encripta array de bytes utilizando chave publica
     * 
     * @param mensagem
     * @param publicKey
     * @return byte[] contendo mensagem encriptada
     */
    public static byte[] encriptarComChavePublica(byte[] mensagem, PublicKey publicKey) {
        byte[] cipherData = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipherData = cipher.doFinal(mensagem);
        } catch (Exception ex) {
            System.out.println("Erro Encrypt com chave publica: " + ex.getLocalizedMessage());
        }
        return cipherData;
    }

    /**
     * Decripta um arrey de bytes utilizando chave privada
     * 
     * @param msgEncriptada
     * @param privateKey
     * @return byte[] contendo mensagem decriptada
     */
    public static byte[] decriptarComChavePrivada(byte[] msgEncriptada, PrivateKey privateKey) {
        byte[] cipherData = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            cipherData = cipher.doFinal(msgEncriptada);
        } catch (Exception ex) {
            System.out.println("Erro Decrypt com chave privada: " + ex.getLocalizedMessage());
        }
        return cipherData;
    }

    /**
     * Decripta um arrey de bytes utilizando chave pública
     * 
     * @param msgEncriptada
     * @param publicKey
     * @return byte[] contendo mensagem decriptada
     */
    public static byte[] decriptarComChavePublica(byte[] msgEncriptada, PublicKey publicKey) {
        byte[] cipherData = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            cipherData = cipher.doFinal(msgEncriptada);
        } catch (Exception ex) {
            System.out.println("Erro Decrypt com chave pública: " + ex.getLocalizedMessage());
        }
        return cipherData;
    }
}
