import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Server {
    public static void main(String[] args) throws Exception {
    	ArrayList<Socket> socketList=new ArrayList<Socket>();
        //declare server socket to listen for clients on specific port
        ServerSocket currSocket = new ServerSocket();
        try {
   			InetAddress iAddress = InetAddress.getLocalHost();
  			System.out.println(iAddress);
            currSocket = new ServerSocket(4252);
        } catch (Exception e) {
            //
        }
		int i=0;
        //server constantly listens for new connections
        boolean run = true;
        while (run){
        	//count number of connections
			i++;
            //when two connections are maintained, begin threads
			if(i>1){
				Socket connectionSock = currSocket.accept();
				// Add this socket to the list
				socketList.add(connectionSock);
				//pass list so it knows where to send data
				if(socketList.size()>1)
					for(Socket b : socketList)
						new serverThread(b, socketList).start();
			}
        }
    }
}

class serverThread extends Thread {
    private Socket socket = null;
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
        	if(temp == "tictac4252")
        	{
				//print turn number to client
        		out.print("turn"+turn);
        	}
        	else
        	{
            //echo input back to client in all caps for testing
            out.println(temp);
        	}
            //refresh string to see if loop should continue
            temp = in.readLine();
        }
    } catch (Exception e) {
        //e.printStackTrace();
    }
}
}