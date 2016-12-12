package com.example.james.suitechat;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
//class for main menu after connecting
public class MenuActivity extends AppCompatActivity {
    //button for messenger or tic tac toe
    Button button1;
    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //initialize buttons and listeners
        button1 = (Button) findViewById(R.id.messageButt);
        button2 = (Button) findViewById(R.id.ticButt);
        button1.setOnClickListener(connect);
        button2.setOnClickListener(tic);
        ;
    }
    private View.OnClickListener connect = new View.OnClickListener() {
        public void onClick(View v) {
            //takes values from GUI boxes and enters them into method
            //calls connect class to execute
            new beginMessage().execute("");
        }
    };

    class beginMessage extends AsyncTask<String, String, tcp>
    {
        protected tcp doInBackground(String... message) {
            //if succesful, continues to the messaging activity
            startMessage();
            return null;
        }
    }
    public void startMessage() {
        Intent intent;
        //calling message class/activity
        intent = new Intent(this, message.class);
        startActivity(intent);
    }


    private View.OnClickListener tic = new View.OnClickListener() {
        public void onClick(View v) {
            //calls tic tac toe class to execute
            new beginTic().execute("");
        }
    };

    class beginTic extends AsyncTask<String, String, tcp>
    {
        protected tcp doInBackground(String... message) {
            //if succesful, continues to the tic tac toe activity
            startTic();
            return null;
        }
    }
    public void startTic() {
        Intent intent;
        //calling tic tac toe class/activity
        intent = new Intent(this, tictactoe.class);
        startActivity(intent);
    }
}
