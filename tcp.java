package com.example.james.suitechat;
import java.net.*;

//this class has a main responsibility to hold the socket object.
class tcp
{
    //declare variables for connection
    static String ipAddress;
    static int portNum;
    static Socket clientSocket;
    //constructor for when referring to class
    tcp(String ip, int port)
    {
        ipAddress = ip;
        portNum = port;
    }
    //connect method to establish a TCP connectoin to server
    public static void connect() throws Exception
    {
        clientSocket = new Socket(ipAddress, portNum);
    }
}