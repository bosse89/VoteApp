import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
 
/**
 * The class extends the Thread class so we can receive and send messages at the same time
 */
public class TCPServer extends Thread {
 
    public static final int SERVERPORT = 4444;
    private boolean running = false;
    private PrintWriter[] mOut;
    private OnMessageReceived messageListener;
    ArrayList<ClientThread> cts;
    
    public static void main(String[] args) {
 
        //opens the window where the messages will be received and sent
        ServerBoard frame = new ServerBoard();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
 
    }
 
    
    public OnMessageReceived getMesLis(){
    	
    	return messageListener;
    }
    
    /**
     * Constructor of the class
     * @param messageListener listens for the messages
     */
    public TCPServer(OnMessageReceived messageListener) {
        this.messageListener = messageListener;
    }
 
 
    /**
     * Method to send the messages from server to client
     * @param message the message sent by the server
     */
    public void sendMessage(String message){
    	
    	//So here we would have to go through the whole list of clients
    	for(int i=0;i<cts.size();i++){
			if (cts.get(i).getMout() != null && !cts.get(i).getMout().checkError()) {
				cts.get(i).getMout().println(message);
				cts.get(i).getMout().flush();
		    }
    	}
    	
    	
    	
    	
    }
 
    @Override
    public void run() {
        super.run();
 
        running = true;
 
        try {
            System.out.println("S: Connecting...");
 
            //create a server socket. A server socket waits for requests to come in over the network.
            ServerSocket serverSocket = new ServerSocket(SERVERPORT);
 
            //create client socket... the method accept() listens for a connection to be made to this socket and accepts it.
            cts= new ArrayList<ClientThread>();
            Socket client;
            ClientThread ct;
            int size=0;
            while(running){
            	//accept new client sockets
            	System.out.println("S: Waiting for acceptance...");
            	client = serverSocket.accept();
            	System.out.println("S: Recieving...");
            	
            	
            	ct = new ClientThread(client, messageListener,size);
                ct.start();
                
                cts.add(ct);
                size=cts.size();
                client = new Socket();
                
            }
            
 
        } catch (Exception e) {
            System.out.println("S: Error");
            e.printStackTrace();
        }
 
    }
 
 
    //Declare the interface. The method messageReceived(String message) will must be implemented in the ServerBoard
    //class at on startServer button click
    public interface OnMessageReceived {
        public void messageReceived(String message, int id);
    }
    public String getStatus(){
    	
    	int size =cts.size();
    	int nYes=0;
    	int nNo=0;
    	int nKinda=0;
    	int nIdle=0;
    	String temp;
    	for(int i=0;i<size;i++){
    		temp = cts.get(i).getVote();
    		if(temp.equals("y")){
    			nYes++;
    		}
    		else if(temp.equals("k")){
    			nKinda++;
    		}
    		else if(temp.equals("n")){
    			nNo++;
    		}
    		else if(temp.equals("")){
    			nIdle++;
    		}
    	}
    	String status = "Total users: "+size+ "\n"+"nYes: "+nYes+"\n" +"nKinda: "+nKinda+"\n"+"nNo: "+nNo+"\n"+"nIdle: "+nIdle;
    	
    	return status;
    }
}