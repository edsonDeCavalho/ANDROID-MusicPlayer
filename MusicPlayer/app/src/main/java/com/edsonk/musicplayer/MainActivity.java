package com.edsonk.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btn_allMusic,btn_player,btn_misicLists;
    private TextView title;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title=(TextView)findViewById(R.id.textView9);
        btn_allMusic=(Button)findViewById(R.id.allMusic);
        btn_player=(Button)findViewById(R.id.musicPlayer);
        btn_misicLists=(Button)findViewById(R.id.lists);
        btn_misicLists=(Button)findViewById(R.id.lists);
    }

    public void goToListOfMusic(View view) {
        Intent intent = new Intent(this, AllMusics.class);
        startActivity(intent);
    }

    public void goToPlayActivity(View view){
        Intent intent = new Intent(this, Play.class);
        startActivity(intent);
    }

    public void goToPlayLists(View view) {
        Intent intent = new Intent(this, PlayLists.class);
        startActivity(intent);
    }
}