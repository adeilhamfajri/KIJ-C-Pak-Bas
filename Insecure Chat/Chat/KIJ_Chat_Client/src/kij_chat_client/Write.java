/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author santen-suru
 */
public class Write implements Runnable {
    private static Object cipher;
    private static Object plaintext;
    private static Object chipertext;
    private Key privateKey;
    private String EncryptMsg;
    private String EncrytpHashMsg;
    private String Message;
    private String Send;
    private EncryptionRSA encryption = new EncryptionRSA();
    
    
	private Scanner chat;
        private PrintWriter out;
        boolean keepGoing = true;
        ArrayList<String> log;
        
        /*public String PRIVATE_KEY_FILE = "C:/keys/private_";
        public String PUBLIC_KEY_FILE = "C:/keys/public_";
        public String line;
        public String result="";*/
        
	public Write(Scanner chat, PrintWriter out, ArrayList<String> log)
	{
		this.chat = chat;
                this.out = out;
                this.log = log;
	}
        
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
        
        /*public String generateKey(String keyname) throws NoSuchAlgorithmException, IOException{
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            final KeyPair key = keyGen.generateKeyPair();
            
            PRIVATE_KEY_FILE += keyname+".key";
            PUBLIC_KEY_FILE += keyname+".key";
            
            File privateKeyFile = new File(PRIVATE_KEY_FILE);
            File publicKeyFile = new File(PUBLIC_KEY_FILE);
            
            if (privateKeyFile.getParentFile() != null) {
                privateKeyFile.getParentFile().mkdirs();
            }
            privateKeyFile.createNewFile();
            
            if (publicKeyFile.getParentFile() != null) {
                publicKeyFile.getParentFile().mkdirs();
            }
            publicKeyFile.createNewFile();
            
            ObjectOutputStream privateKeyOS = new ObjectOutputStream(
                new FileOutputStream(privateKeyFile));
            privateKeyOS.writeObject(key.getPrivate());
            privateKeyOS.close();
            
            ObjectOutputStream publicKeyOS = new ObjectOutputStream(
                new FileOutputStream(publicKeyFile));
            publicKeyOS.writeObject(key.getPublic());
            publicKeyOS.close();
            
           /* FileReader fileReader = new FileReader(PUBLIC_KEY_FILE);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine())!=null ){
                result+=line;
            }
            return key.getPublic().toString();
        }*/
        
        
        
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
                                    //String coba = generateKey(input.split(" ")[1]);
                                   
                                    //get public and get private
                                    Key publicKey = encryption.keyPair.getPublic();
                                    privateKey = encryption.keyPair.getPrivate();
                                    
                                    byte[] pubBytes = publicKey.getEncoded();
                                    
                                    BASE64Encoder encoder = new BASE64Encoder();
                                    String pubKeyStr = encoder.encode(pubBytes);
                                    
                                    String repl = pubKeyStr.replaceAll("(\\r|\\n|\\r\\n)+", "~");
                                    input = input.split(" ")[0]+" "+input.split(" ")[1]+" "+hashpass+" "+repl;
                                    out.println(input);//SEND IT TO THE SERVER
                                }
                                else if(input.split(" ")[0].toLowerCase().equals("pm")){
                                    //encrypt message and encypt hash message
                                    Message = input.split(" ",3)[2];
                                    EncryptMsg = encryption.encrypt(Message,privateKey); 
                                    EncrytpHashMsg = ShaHash(Message);
                                    EncrytpHashMsg = encryption.encrypt(EncrytpHashMsg, privateKey);
                                    
                                    Send = input.split(" ")[0]+" "+input.split(" ")[1]+" "+EncryptMsg+"&"+EncrytpHashMsg;
                                    out.println(Send);//SEND IT TO THE SERVER
                                    //System.out.println(Message);
                                }
				out.flush();//FLUSH THE STREAM
                                
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

}
