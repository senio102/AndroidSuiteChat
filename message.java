package com.example.james.suitechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class message extends AppCompatActivity {
    //pass current socket that is already connected
    tcp curr = establishConnect.tcpClient;
    //declare streams/buttons
    DataOutputStream out;
    BufferedReader in;
    Button button1;
    //list to hold messages sent and received
    private ListView mainListView ;
    //adapter to allow dynamic sizing of list
    private ArrayAdapter<String> listAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        //list view to hold messages, using an adapter
        mainListView = (ListView) findViewById(R.id.mainListView);
        ArrayList<String> messages = new ArrayList<String>();
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, messages);
        mainListView.setAdapter( listAdapter );
        button1 = (Button) findViewById(R.id.sendButton);
        try {
            //declare output stream
            out = new DataOutputStream(curr.clientSocket.getOutputStream());
            //begin thread to listen for messages from client
            final Thread inThread = new Thread() {
                @Override
                public void run() {
                    // while the client is connected, listen for messages from server, add to message list
                    while(curr.clientSocket.isConnected()) try {
                        in = new BufferedReader(new InputStreamReader(curr.clientSocket.getInputStream()));
                        listAdapter.add(in.readLine());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            };
            inThread.start();
            //on click event listener for sending messages to server
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //switch to handle click, probbaly not necessary but maybe useful in further features
                    switch (v.getId()) {
                        case R.id.sendButton:
                            //take text from textbox
                            EditText sendText = (EditText)findViewById(R.id.sendText);
                            String text = sendText.getText().toString();
                            try {
                                //write text from box
                                out.writeBytes(text + '\n');
                                //add to message listview to show full conversation
                                listAdapter.add("Client: " + text);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
