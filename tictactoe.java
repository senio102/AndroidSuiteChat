package com.example.james.suitechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//class to handle tic tac toe
public class tictactoe extends AppCompatActivity implements View.OnClickListener {
    //declare buttons for game
    Button a1, a2, a3, b1, b2, b3, c1, c2, c3;
    //array to enable easy iteration of buttons
    Button[] bArray;
    //stringbuilder to store values of each field
    StringBuilder engine = new StringBuilder("000000000");
    //bools to determine if X or O and whose turn
    boolean firstTurn = false;
    boolean gonow=false;
    DataOutputStream out;
    BufferedReader in;
    tcp curr = establishConnect.tcpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);
        //initialize buttons to xml file
        a1 = (Button) findViewById(R.id.a1);
        a2 = (Button) findViewById(R.id.a2);
        a3 = (Button) findViewById(R.id.a3);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        c1 = (Button) findViewById(R.id.c1);
        c2 = (Button) findViewById(R.id.c2);
        c3 = (Button) findViewById(R.id.c3);
        //initialize array
        bArray = new Button[]{a1, a2, a3, b1, b2, b3, c1, c2, c3};
        //declare output stream
        try {
            out = new DataOutputStream(curr.clientSocket.getOutputStream());
            //out.writeBytes("tictac4252");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //set click listener for each button
        for(Button b: bArray)
            b.setOnClickListener(this);
        //declare thread to listen for input and update the grid based off of the string
        //also updates user turn, game over, etc
        Thread t = new Thread() {
            TextView turn= (TextView) findViewById(R.id.turnview);
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(gonow==true)
                                    turn.setText("Your turn!");
                                else
                                    turn.setText("Other user's turn.");
                                //searches string for 1's or 2's, each represents either X or O
                                for(int i=0;i<9;i++)
                                {
                                    if(engine.charAt(i)=='1')
                                    {
                                        bArray[i].setText("X");
                                    }
                                    if(engine.charAt(i)=='2')
                                    {
                                        bArray[i].setText("O");
                                    }
                                }
                                //algorithm to determine if game is over
                                if(((engine.charAt(0)==engine.charAt(1)&& engine.charAt(1)==engine.charAt(2)) && engine.charAt(0)!='0')
                                        || ((engine.charAt(3)==engine.charAt(4)&& engine.charAt(4)==engine.charAt(5)) && engine.charAt(3)!='0')
                                        || ((engine.charAt(6)==engine.charAt(7)&& engine.charAt(7)==engine.charAt(8)) && engine.charAt(6)!='0')
                                        || ((engine.charAt(0)==engine.charAt(4)&& engine.charAt(4)==engine.charAt(8)) && engine.charAt(8)!='0')
                                        || ((engine.charAt(2)==engine.charAt(4)&& engine.charAt(4)==engine.charAt(6)) && engine.charAt(6)!='0')
                                        || ((engine.charAt(0)==engine.charAt(3)&& engine.charAt(3)==engine.charAt(6)) && engine.charAt(6)!='0')
                                        || ((engine.charAt(1)==engine.charAt(4)&& engine.charAt(4)==engine.charAt(7)) && engine.charAt(7)!='0')
                                        || ((engine.charAt(2)==engine.charAt(5)&& engine.charAt(5)==engine.charAt(8)) && engine.charAt(8)!='0'))
                                {
                                    turn.setText("GAME OVER!");
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
        //thread to listen for data from other game
        final Thread inThread = new Thread() {
            @Override
            public void run() {
                // while the client is connected, listen for messages from server, add to message list
                try {
                    //tell server you want to play tic tac toe
                    out.writeBytes("tictac" + '\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while(curr.clientSocket.isConnected()) {
                    try {
                        in = new BufferedReader(new InputStreamReader(curr.clientSocket.getInputStream()));
                        String temp = in.readLine();
                        //if temp contains turn, it is the first message from server determining who goes first
                        if (temp.contains("turn")) {
                            //if temp has 1 after "turn", you are first turn. else, you are second
                            if (temp.contains("1")) {
                                firstTurn = true;
                                gonow=true;
                            }
                            temp="";
                            //if you receive new string, update the position of that button in the string
                        } else {
                            for (int i = 0; i < 9; i++) {
                                engine.setCharAt(i, temp.charAt(i));
                                //your turn now
                                gonow=true;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        };
        inThread.start();
    }
    //if user clicks grid it updates the string , then sends to server
    public void onClick(View v) {
        if(gonow==true) {
            switch (v.getId()) {
                case R.id.a1:
                    if (firstTurn == true) {
                        engine.setCharAt(0, '1');
                        a1.setText("X");
                    } else if (firstTurn == false) {
                        engine.setCharAt(0, '2');
                        a1.setText("O");
                    }
                    // your code here
                    break;
                case R.id.a2:
                    if (firstTurn == true) {
                        engine.setCharAt(1, '1');
                        a2.setText("X");
                    } else if (firstTurn == false) {
                        engine.setCharAt(1, '2');
                        a2.setText("O");
                    }
                    break;
                case R.id.a3:
                    if (firstTurn == true) {
                        engine.setCharAt(2, '1');
                        a3.setText("X");
                    } else if (firstTurn == false) {
                        engine.setCharAt(2, '2');
                        a3.setText("O");
                    }
                    // your code here
                    break;
                case R.id.b1:
                    if (firstTurn == true) {
                        engine.setCharAt(3, '1');
                        b1.setText("X");
                    } else if (firstTurn == false) {
                        engine.setCharAt(3, '2');
                        b1.setText("O");
                    }
                    break;
                case R.id.b2:
                    if (firstTurn == true) {
                        engine.setCharAt(4, '1');
                        b2.setText("X");
                    } else if (firstTurn == false) {
                        engine.setCharAt(4, '2');
                        b2.setText("O");
                    }
                    // your code here
                    break;
                case R.id.b3:
                    if (firstTurn == true) {
                        engine.setCharAt(5, '1');
                        b3.setText("X");
                    } else if (firstTurn == false) {
                        engine.setCharAt(5, '2');
                        b3.setText("O");
                    }
                    break;
                case R.id.c1:
                    if (firstTurn == true) {
                        engine.setCharAt(6, '1');
                        c1.setText("X");
                    } else if (firstTurn == false) {
                        engine.setCharAt(6, '2');
                        c1.setText("O");
                    }
                    // your code here
                    break;
                case R.id.c2:
                    if (firstTurn == true) {
                        engine.setCharAt(7, '1');
                        c2.setText("X");
                    } else if (firstTurn == false) {
                        engine.setCharAt(7, '2');
                        c2.setText("O");
                    }
                    break;
                case R.id.c3:
                    if (firstTurn == true) {
                        engine.setCharAt(8, '1');
                        c3.setText("X");
                    } else if (firstTurn == false) {
                        engine.setCharAt(8, '2');
                        c3.setText("O");
                    }
                    break;
            }
            try {
                out.writeBytes(engine.toString() + '\n');
                gonow = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}