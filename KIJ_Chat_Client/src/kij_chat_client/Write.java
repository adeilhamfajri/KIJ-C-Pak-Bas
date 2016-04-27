package kij_chat_client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import sun.misc.BASE64Encoder;
/**
/**
 *
 * @author santen-suru
 */
public class Write implements Runnable {
    private static Object cipher;
    private static Object plaintext;
    private static Object chipertext;
    private Key privateKey;
    private String EncryptMsg;
    private String HashMsg;
    private String EncrytpHashMsg;
    private String Message;
    private String Send;
    private EncryptionRSA encryption = new EncryptionRSA();


  
      
    private Scanner chat;
    private PrintWriter out;
    boolean keepGoing = true;
    ArrayList<String> log;
    private Object AES;
    private String encryptedMsg;
	
    public Write(Scanner chat, PrintWriter out, ArrayList<String> log)
    {
        this.chat = chat;
        this.out = out;
        this.log = log;
    }
        
        //Hash Function
    public String ShaHash(String password) throws NoSuchAlgorithmException{

       MessageDigest md = MessageDigest.getInstance("SHA-256");
       md.update(password.getBytes());

       byte byteData[] = md.digest();

       //convert the byte to hex format method 1
       StringBuffer sb = new StringBuffer();
       for (int i = 0; i < byteData.length; i++) {
        sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
       }

       return sb.toString();
    }
	
	@Override
	public void run()//INHERIT THE RUN METHOD FROM THE Runnable INTERFACE
	{
            try
            {
                while (keepGoing)//WHILE THE PROGRAM IS RUNNING
                {						
                    String input = chat.nextLine();	//SET NEW VARIABLE input TO THE VALUE OF WHAT THE CLIENT TYPED IN

                        if(input.split(" ")[0].toLowerCase().equals("login")){
                           String pass = input.split(" ")[2];
                           String hashpass = ShaHash(pass);
                        
                           Key publicKey = encryption.keyPair.getPublic();
                           privateKey = encryption.keyPair.getPrivate();

                           byte[] pubBytes = publicKey.getEncoded();

                           BASE64Encoder encoder = new BASE64Encoder();
                           String pubKeyStr = encoder.encode(pubBytes);

                           String repl = pubKeyStr.replaceAll("(\\r|\\n|\\r\\n)+", "~");
                           input = input.split(" ")[0]+" "+input.split(" ")[1]+" "+hashpass+" "+repl;
                           out.println(input);//SEND IT TO THE SERVER

                       }
                        
                        
                        String algorithm = "AES";           //tambahan
                                
                        privateKey = newSecretKeySpec(keyValue, algorithm);     //tambahan
                        privateKey = generateKey();             //tambahan
                        encryptedMsg = AES.encrypt(plaintext);          //tambahan
                                
                        out.println("Enkripsi = " + encryptedMsg);      //tambahan
                        
                        out.flush(); //flush stream-nya
                        
                       if (input.contains("logout")) {
                           if (log.contains("true"))
                               keepGoing = false;

                       }
                }
            }
		catch (Exception e)
		{
			e.printStackTrace();//MOST LIKELY WONT BE AN ERROR, GOOD PRACTICE TO CATCH THOUGH
		} 
	}

    private Key generateKey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}