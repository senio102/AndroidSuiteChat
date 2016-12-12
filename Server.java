import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.net.*;


//james senior
public class Server
{
	// Maintain list of all client sockets for broadcast
	private ArrayList<Socket> socketList;
    public Server()
	{
		socketList = new ArrayList<Socket>();
	}

	private void getConnection()
	{
		// Wait for a connection from the client
		try
		{
       			InetAddress iAddress = InetAddress.getLocalHost();
      			System.out.println(iAddress);
			ServerSocket serverSock = new ServerSocket(4252);
			// This is an infinite loop, the user will have to shut it down
			// using control-c
			int i=0;
			while (true)
			{
				i++;
				// Send to ClientHandler the socket and arraylist of all sockets
				if(i>1){
					Socket connectionSock = serverSock.accept();
					// Add this socket to the list
					socketList.add(connectionSock);
					if(socketList.size()>1)
						for(Socket b : socketList)
							new serverThread(b, socketList).start();
				}
			}
			// Will never get here, but if the above loop is given
			// an exit condition then we'll go ahead and close the socket
			//serverSock.close();
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args)
	{
		Server server = new Server();
		server.getConnection();
	}
	class serverThread extends Thread {
	    Socket socket = null;
		ArrayList<Socket> socketList;
		int turn = 0;
	    //store current socket on thread to maintain data
	    public serverThread(Socket socket, ArrayList<Socket> i) {
	        this.socket = socket;
			socketList=i;
	    }
	    //begin communication with specific connection
	    public void run() {
	    try {
			System.out.println(socketList.size());
	        //declare out/in
	    	Server j;
	    	PrintWriter out= new PrintWriter(socketList.get(1).getOutputStream(), true);
	    	if(socket == socketList.get(0)){
	    		out = new PrintWriter(socketList.get(1).getOutputStream(), true);
	    		turn = 1;
	    	}
	    	if(socket == socketList.get(1)){
	    		out = new PrintWriter(socketList.get(0).getOutputStream(), true);
	    		turn = 2;
	    	}
	        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        //determine whether there is any input from client
	        String temp = in.readLine();
	        //if input not empty, print
	        while (temp.isEmpty()==false) {
	        	if(temp.contains("tictac"))
	        	{
	        		out.println("turn"+turn);
	        	}
	        	else
	        	{
	            //echo input back to client in all caps for testing
	            out.println(temp);
	            //refresh string to see if loop should continue
	        	}
	            temp = in.readLine();
	        }
	    } catch (Exception e) {
	        //e.printStackTrace();
	    }
	}}
}