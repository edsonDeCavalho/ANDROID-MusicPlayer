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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateNewPlyaList extends AppCompatActivity {
    private Button btn_home,btn_create,btn_cancel;
    private TextView title;
    private int j=0;
    private static final int MY_PERMISSION_REQUEST=0;
    private ArrayList<String> list;
    private ArrayList<String> AllSongs;
    private String[] myStrings;
    private ListView listview;
    private ArrayAdapter<String> adapter;
    private String[] myStrings1;
    private String itemSelected ="Slected Items : \n ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_plya_list);
        title=(TextView)findViewById(R.id.textView2);
        btn_home=(Button)findViewById(R.id.button6);
        btn_create=(Button)findViewById(R.id.button2);
        btn_cancel=(Button)findViewById(R.id.button3);
        listview=(ListView)findViewById(R.id.listViewMusics);

        if(ContextCompat.checkSelfPermission(CreateNewPlyaList.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(CreateNewPlyaList.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(CreateNewPlyaList.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            }else{
                ActivityCompat.requestPermissions(CreateNewPlyaList.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
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
                    if(ContextCompat.checkSelfPermission(CreateNewPlyaList.this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ){
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
        String itemSelected="Slected Items : \n ";
        list=new ArrayList<>();
        AllSongs=new ArrayList<>();
        myStrings=new String[6000];
        getAllMusics();
        adapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked,list );
        listview.setAdapter(adapter);
        listview.setLongClickable(true);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         String hola="";
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView v = (CheckedTextView) view;
                boolean currentCheck = v.isChecked();
                export(i);
            }
        });
    }

    public void export(int i){
        String hola2=list.get(i);
        this.itemSelected+=hola2;
    }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_check,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item_done){
           // String itemSelected="Slected Items : \n ";
            for(int i=0;i<listview.getCount();i++){
                if(listview.isItemChecked(i)){
                    //itemSelected +=listview.getItemAtPosition(i) +"\n";
                }
            }
            Toast.makeText(this,itemSelected,Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void goHome(View view){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void cancel(View view){
        finish();
    }
}