/**
*	UDP Client Program
*	Connects to a UDP Server
*	Receives a line of input from the keyboard and sends it to the server
*	Receives a response from the server and displays it.
*
*	@author: James Senior
@	version: 0.1
*/

import java.io.*;
import java.net.*;

class Red {
    public static void main(String args[]) throws Exception
    {
	BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	DatagramSocket clientSocket = new DatagramSocket();
	InetAddress IPAddress = InetAddress.getByName("localhost");
	byte[] sendData = new byte[1024];
	byte[] receiveData = new byte[1024];
	int state = 0;
	String response = "";
        DatagramPacket sendPacket = new DatagramPacket("HELLO Red".getBytes(), "HELLO Red".getBytes().length, IPAddress, 9876);
	clientSocket.send(sendPacket);
	while (state < 3){
	    
	    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	    clientSocket.receive(receivePacket);
	    response = new String(receivePacket.getData());
	    switch (state){
	    case 0: 
		if (response.substring(0,3).equals("100")) {
		    System.out.println("FROM SERVER: " + response);
		    state = 1; //You are first client. wait for second client to connect
		}
		else if (response.substring(0,3).equals("200")){
		    System.out.println("FROM SERVER: " + response);
		    state = 2; //you are second client. Wait for message from first client
		}
		break;
	    case 1:
		state = 2; //transition to state 2: chat mode
		break;
	    case 2:
		if (response.length()>=7 && response.substring(0,7).equals("Goodbye")){
		    System.out.println("FROM SERVER: " + response);
		    state = 3;
		    break;
		}
	        else if (response.substring(0,3).equals("300")){
		    System.out.println("Begin Chat");
		    sendData = (inFromUser.readLine()).getBytes();
		    sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		    clientSocket.send(sendPacket);
		}
		break;
	    }
	}
	clientSocket.close();
    }
}
