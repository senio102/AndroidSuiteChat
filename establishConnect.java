package com.example.james.suitechat;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//class that actually calls the TCPClient class
public class establishConnect extends AppCompatActivity {
    //declare new instance of TCPClient (technically just maintaining a socket)
    public static tcp tcpClient;
    String ip;
    String port;
    //just GUI stuff, connecting "CONNECT" button to method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establish_connect);
        Button connectButton = (Button) findViewById(R.id.connectButton);
        connectButton.setOnClickListener(connect);
    }
    //Handles a click event on first screen, once clicked it calls connect class to establish connection
    private View.OnClickListener connect = new View.OnClickListener() {
        public void onClick(View v) {
            //takes values from GUI boxes and enters them into method
            EditText ipBox = (EditText)findViewById(R.id.ipText);
            ip = ipBox.getText().toString();
            EditText portBox = (EditText)findViewById(R.id.portText);
            port = portBox.getText().toString();
            //calls connect class to execute
            new connect().execute("");
        }
    };

    //class extending AsyncTask, meaning a task can run in the background of the current activity
    class connect extends AsyncTask<String, String, tcp>
    {
        protected tcp doInBackground(String... message) {
            //declares a new client instance with ip and port variables
            tcpClient = new tcp(ip, Integer.parseInt(port));
            //try to establish socket
            try {
                tcpClient.connect();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            //if succesful, continues to the messaging activity
            startMessage();
            return null;
        }
    }

    //messaging activity after connect success
    public void startMessage() {
        Intent intent;
        //calling message class/activity
        intent = new Intent(this, message.class);
        startActivity(intent);
    }
}

