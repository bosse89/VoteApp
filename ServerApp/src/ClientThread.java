import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;



public class ClientThread extends Thread{
	private PrintWriter mOut;
	private BufferedReader in;
	private Socket client;
	private TCPServer.OnMessageReceived messageListener;
	private int id;
	private String curStr;
	
	public ClientThread(Socket client1, TCPServer.OnMessageReceived messageListener2, int id1){
		client = client1;
		messageListener=messageListener2;
		id=id1;
		curStr="";
	}
	
	
	public PrintWriter getMout(){
		
		return mOut;
	}
	public String getVote(){
		
		return curStr;
	}
	public void run(){
		System.out.println("Startring a thread run()");
		try{
		try {
			
			//sends the message to the client
			mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
			
			//read the message received from client
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			Boolean running = true;
			
			
        	
        	//in this while we wait to receive messages from client (it's an infinite loop)
            //this while it's like a listener for messages
        	while (running) {
        		String message = in.readLine();
        		if (message != null && messageListener != null) {
                    //call the method messageReceived from ServerBoard class
        			curStr=message;
                    messageListener.messageReceived(message,id);
                }
        		
            	
            }
			
			
		} catch (IOException e) {
			System.out.println("S: Error");
			e.printStackTrace();
		}
		finally {
        	
        	client.close();
            System.out.println("S: Done.");
        	
        }
		}
		catch (Exception e) {
            System.out.println("S: Error");
            e.printStackTrace();
        }
		
	}
	
}
