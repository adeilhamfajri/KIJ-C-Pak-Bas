/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_client;

/*import java.net.Socket;*/
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author santen-suru
 */
public class Read implements Runnable {
        
        private Scanner in;//MAKE SOCKET INSTANCE VARIABLE
        String input;
        boolean keepGoing = true;
        ArrayList<String> log;
	
	public Read(Scanner in, ArrayList<String> log)
	{
		this.in = in;
                this.log = log;
	}
    
        @Override
	public void run()//INHERIT THE RUN METHOD FROM THE Runnable INTERFACE
	{
		try
		{
			while (keepGoing)//WHILE THE PROGRAM IS RUNNING
			{						
				if(this.in.hasNext()) {
                                                                   //IF THE SERVER SENT US SOMETHING
                                        input = this.in.nextLine();
                                        //System.out.println(input);
					//PRINT IT OUT
                                        if (input.split(" ")[0].toLowerCase().equals("success")) {
                                            if (input.split(" ")[1].toLowerCase().equals("logout")) {
                                                System.out.println(input);
                                                keepGoing = false;
                                                 
                                            } else if (input.split(" ")[1].toLowerCase().equals("login")) {
                                                System.out.println(input);
                                                log.clear();
                                                log.add("true");
                                                
                                            }
                                        }
                                        else if (input.split(" ")[1].toLowerCase().equals(":")){
                                            
                                            String message = input.split("#")[0];
                                                
                                            //String message = raw.split(" ~~~||~~~")[0];
                                            //String Pubk= raw.split(" ~~~||~~~")[1];//get public key
                                            System.out.println(message);
                                            //System.out.println(Pubk);
                                        }
                                        
                                }
                                
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();//MOST LIKELY WONT BE AN ERROR, GOOD PRACTICE TO CATCH THOUGH
		} 
	}
}
