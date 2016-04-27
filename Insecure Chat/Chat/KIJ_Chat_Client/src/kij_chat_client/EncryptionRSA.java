/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_client;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Luffi
 */
public class EncryptionRSA {
    
    public static KeyPair keyPair;

    public EncryptionRSA() {
        try {
                keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
            } catch (NoSuchAlgorithmException e) {
                System.err.println("Algorithm not supported! " + e.getMessage() + "!");
            }
    
    }
    
        
        public String encrypt(String plain, Key privateKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException
        {
            final Cipher cipher = Cipher.getInstance("RSA");
            final String plaintext = plain;
            
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
            String chipertext = new String(Base64.getEncoder().encode(encryptedBytes));
            //System.out.println("encrypted (chipertext) = " + chipertext);
            return chipertext;
        }
        
        public String decrypt(String chipertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
        {
            final Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            byte[] ciphertextBytes = Base64.getDecoder().decode(chipertext.getBytes());
            byte[] decryptedBytes = cipher.doFinal(ciphertextBytes);
            String decryptedString = new String(decryptedBytes);
            //System.out.println("decrypted (plaintext) = " + decryptedString);
//            System.out.println("public = " + keyPair.getPublic());
//            System.out.println("private = " + keyPair.getPrivate());
            return decryptedString;
        }
    
}
