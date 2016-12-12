import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.net.*;


public class Server
{
	// keep list of sockets to allow alternating between the two
	private ArrayList<Socket> socketList;
	public Server()
	{
		//new socketlist to keep track of seperate instances
		socketList = new ArrayList<Socket>();
	}
	private void getConnection()
	{
		try
		{
			//print current ip for testing
			InetAddress iAddress = InetAddress.getLocalHost();
			System.out.println(iAddress);
			//new server socket on port
			ServerSocket serverSock = new ServerSocket(4252);
			//counter to keep track of clients
			int i=0;
			while (true)
			{
				i++;
				//only is called when there are 2 connections, otherwise keeps looping
				if(i>1){
					//accepts after 2 succesful connections are maintained
					Socket connectionSock = serverSock.accept();
					// Add this socket to the list
					socketList.add(connectionSock);
					//pass socketlist to client instance so it has the ability to determine who to send to
					if(socketList.size()>1)
						for(Socket b : socketList)
							new serverThread(b, socketList).start();
				}
			}
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	//class to hold the connections
	class serverThread extends Thread {
		Socket socket = null;
		ArrayList<Socket> socketList;
		//turn variable for tic tac toe
		int turn = 0;
		//store current socket on thread to maintain data
		public serverThread(Socket socket, ArrayList<Socket> i) {
			this.socket = socket;
			socketList=i;
		}
		//begin communication with specific connection
		public void run() {
			try {
				//print connections 
				System.out.println(socketList.size());
				//declare out/in
				Server j;
				PrintWriter out= new PrintWriter(socketList.get(1).getOutputStream(), true);
				//declare out to opposite client (avoid echoing), also assign turns for tic tac toe
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
					//client sends "tictac" whenver game is prompted
					if(temp.contains("tictac"))
					{
						//print turn number to client
						out.println("turn"+turn);
					}
					else
					{
						out.println(temp);
						//refresh string to see if loop should continue
					}
					temp = in.readLine();
				}
			} catch (Exception e) {
				//
			}
		}
	}
	//main
	public static void main(String[] args)
	{
		Server server = new Server();
		server.getConnection();
	}
}
