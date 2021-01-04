package com.edsonk.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllMusics extends AppCompatActivity {

    int j=0;
    private static final int MY_PERMISSION_REQUEST=0;
    ArrayList<String> list;
    ArrayList<String> AllSongs;
    String[] myStrings;
    ListView listview;
    ArrayAdapter<String> adapter;
    private String[] myStrings1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_musics);
        if(ContextCompat.checkSelfPermission(AllMusics.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(AllMusics.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(AllMusics.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            }else{
                ActivityCompat.requestPermissions(AllMusics.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
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

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST : {
                if((grantResults.length > 0) &&  grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(AllMusics.this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ){
                        Toast.makeText(this,"Permission granted!", Toast.LENGTH_SHORT).show();
                        faire();
                    }
                }else{
                    Toast.makeText(this,"No permission granted!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

    public void faire(){
        listview = (ListView) findViewById(R.id.listView);
        list=new ArrayList<>();
        AllSongs=new ArrayList<>();
        myStrings=new String[6000];
        getAllMusics();
        adapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openPlayerActivity(i);
            }
        });
    }

    private void openPlayerActivity(int position)
    {
        Intent i=new Intent(this,Play.class);
        i.putExtra("SONG_KEY",position);
        startActivity(i);
    }

    public void Back(View view){
        this.finish();
    }

}