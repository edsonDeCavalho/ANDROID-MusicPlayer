package com.edsonk.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Play extends AppCompatActivity {

    private int replay=2;
    private TextView textView;
    private static final int MY_PERMISSION_REQUEST=0;
    private Button play_pause,btn_replay;
    private ImageView iv;
    private int j=0;
    private Intent mIntent;
    private MediaPlayer actualSong;
    ArrayList<String> list;
    ArrayList<String> AllSongs;
    String[] myStrings;
    ListView listview;
    ArrayAdapter<String> adapter;
    private String[] myStrings1;
    private int position;
    private int playPosition;
    private int action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        textView=(TextView)findViewById(R.id.textView);
        play_pause=(Button)findViewById(R.id.btnPlay);
        btn_replay=(Button)findViewById(R.id.replay);
        iv=(ImageView)findViewById(R.id.imageView);

        if( savedInstanceState != null ) {
            playPosition = savedInstanceState.getInt("positionPlay");
        }

        if(ContextCompat.checkSelfPermission(Play.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(Play.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(Play.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            }else{
                ActivityCompat.requestPermissions(Play.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            }
        }else{
            faire();
        }
    }

    public void getAllMusics(){
        ContentResolver contentResolver = getContentResolver();
        Uri songsU= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor Songcursor = contentResolver.query(songsU,null,null,null,null);

        if(songsU != null && Songcursor.moveToFirst()){
            int songTitle = Songcursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = Songcursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songPath = Songcursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do{
                String currentTitle = Songcursor.getString(songTitle);
                String currentArtist = Songcursor.getString(songArtist);
                String currentPath = Songcursor.getString(songPath);
                AllSongs.add(currentPath);
                myStrings[j]=currentPath;
                list.add(currentTitle);
                j++;
            }while(Songcursor.moveToNext());
        }
    }
    public void getplay(MediaPlayer a){
        a.start();
    }

    public void getStop(MediaPlayer a){
        a.stop();
    }
    public void startSong() {
        //if (actualSong != null) {
          //  if (actualSong.isPlaying()) {
            //    actualSong.stop();
              //  getAllMusics();
            //} else {

                this.mIntent = getIntent();
                this.position = mIntent.getIntExtra("SONG_KEY", 0);
                this.actualSong = MediaPlayer.create(this, Uri.parse(myStrings[position]));
                actualSong.start();
                textView.setText(String.valueOf(position));
            //}
        //}
    }
    public void faire(){
        list=new ArrayList<>();
        AllSongs=new ArrayList<>();
        myStrings=new String[6000];
        getAllMusics();
        startSong();
        textView.setText(String.valueOf(position));
    }
    public void Next(View view){
        if(position < 12){
            if(actualSong.isPlaying()){
                actualSong.stop();
                getAllMusics();
                position++;
                this.actualSong= MediaPlayer.create(this,Uri.parse(myStrings[position]));
                //textView.setText(position);
                actualSong.start();
                textView.setText(String.valueOf(position));
                getAllMusics();

            } else {
                getAllMusics();
                position++;
                startSong();
            }
        } else{
            Toast.makeText(this, "There is no more songs", Toast.LENGTH_SHORT).show();
        }
    }

    public void PlayPause(View view){
        if(actualSong.isPlaying()){
            actualSong.pause();
            getAllMusics();
            play_pause.setBackgroundResource(R.drawable.reproducir);
            Toast.makeText(this,"Pause",Toast.LENGTH_SHORT).show();
        }
        else{
            actualSong.start();
            getAllMusics();
            play_pause.setBackgroundResource(R.drawable.pausa);
            Toast.makeText(this,"Play",Toast.LENGTH_SHORT).show();
        }
    }

    public void Replay(View view){
        if(replay==1){
            btn_replay.setBackgroundResource(R.drawable.no_repetir);
            Toast.makeText(this, "Don't repeat", Toast.LENGTH_SHORT).show();
            actualSong.setLooping(false);
            replay=2;
        }
        else{
            btn_replay.setBackgroundResource(R.drawable.repetir);
            Toast.makeText(this, "Repeat", Toast.LENGTH_SHORT).show();
            actualSong.setLooping(true);
            replay=1;
        }
    }


    public void Before(View view){
        if(position >=1){
            if(actualSong.isPlaying()){
                actualSong.stop();
                getAllMusics();
                this.position--;
                actualSong= MediaPlayer.create(this,Uri.parse(myStrings[position]));
                textView.setText(String.valueOf(position));
                actualSong.start();
                textView.setText(String.valueOf(position));
                getAllMusics();
            }
            else{
                position--;
                startSong();
            }
        }
        else{
            Toast.makeText(this, "There is no more songs", Toast.LENGTH_SHORT).show();
        }
    }
    public void back(View view){
        finish();
    }
    public void backToMenu(View view){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        playPosition = actualSong.getCurrentPosition();
        actualSong.pause();
        outState.putInt("positionPlay", playPosition);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }
    public void onResume() {
        super.onResume();
        actualSong.seekTo(playPosition);
        actualSong.start(); //Or use resume() if it doesn't work. I'm not sure
    }
}