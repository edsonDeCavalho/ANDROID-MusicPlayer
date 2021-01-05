package com.edsonk.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayLists extends AppCompatActivity {
    private Button btn_createNewList,btn_back;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_lists);
        title=(TextView)findViewById(R.id.textView9);
        btn_createNewList=(Button)findViewById(R.id.button5);
        btn_back=(Button)findViewById(R.id.button4);

    }

    public void goCreateNewPlaylist(View view){
        Intent i=new Intent(this,CreateNewPlyaList.class);
        startActivity(i);
    }
    public void back(View view){
        finish();
    }
}