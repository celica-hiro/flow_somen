package com.example.android.GameScreen;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bluetoothchat.MainActivity;
import com.example.android.bluetoothchat.R;

public class TopScreen extends Activity implements View.OnClickListener{
    Button sendButton;
    Button reciveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_screen);

        sendButton = (Button)findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);

        reciveButton = (Button)findViewById(R.id.receive_button);
        reciveButton.setOnClickListener(this);

    }

    public void onClick(View view){
        Intent moveMainActivityIntent = new Intent(this ,MainActivity.class);
        if(view==sendButton){
            moveMainActivityIntent.putExtra("CURRENTSCREEN", 1);
        }
        else if(view==reciveButton){
            moveMainActivityIntent.putExtra("CURRENTSCREEN", 2);
        }
        startActivity(moveMainActivityIntent);
    }
}
