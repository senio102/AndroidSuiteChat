import java.net.*;
import java.io.*;
public class Server {
    public static void main(String[] args) throws Exception {
        //declare server socket to listen for clients on specific port
        ServerSocket currSocket = new ServerSocket();
        try {
            currSocket = new ServerSocket(6789);
        } catch (Exception e) {
            //
        }
        //server constantly listens for new connections
        boolean run = true;
        while (run){
            //when new connection joins, new thread begins
            new serverThread(currSocket.accept()).start();
        }
    }
}

class serverThread extends Thread {
    private Socket socket = null;
    //store current socket on thread to maintain data
    public serverThread(Socket socket) {
        this.socket = socket;
    }
    //begin communication with specific connection
    public void run() {
    try {
        //declare out/in
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //determine whether there is any input from client
        String temp = in.readLine();
        //if input not empty, print
        while (temp.isEmpty()==false) {
            System.out.println("From client: "+temp);
            //echo input back to client in all caps for testing
            out.println("From server: "+temp.toUpperCase());
            //refresh string to see if loop should continue
            temp = in.readLine();
        }
    } catch (Exception e) {
        //e.printStackTrace();
    }
}}