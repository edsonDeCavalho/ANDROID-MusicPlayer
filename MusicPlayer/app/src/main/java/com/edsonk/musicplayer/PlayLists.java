package com.edsonk.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.edsonk.musicplayer.utils.Serializable;

import java.util.ArrayList;

public class PlayLists extends AppCompatActivity {
    private Button btn_createNewList,btn_back;
    private TextView title;
    private String PlaylistFile="playListFile";
    private GropeOfPlayLists gropeOfPlayLists;
    private static AccsessLocal acsesLocal;
    private Context context;
    private String []   ListDePlayLists;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> songss;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_lists);
        //recupeSerialize();
        title=(TextView)findViewById(R.id.textView9);
        btn_back=(Button)findViewById(R.id.button4);
        init();
    }
    public void goCreateNewPlaylist(View view){
        Intent i=new Intent(this,CreateNewPlyaList.class);
        startActivity(i);
    }

    public void  init(){
        songss=new ArrayList<String>();
        ListDePlayLists=new String[30];
        ListDePlayLists[1]="Hello";
        acsesLocal= new AccsessLocal(this);
        songss=acsesLocal.getPlayLists();
        listView=(ListView)findViewById(R.id.listvieww);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songss);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openPlayerActivity(i);
            }
        });

    }

    public void openPlayerActivity(int i){
        String list=(String)this.songss.get(i).toString();
        Intent intent = new Intent(this, List.class);
        intent.putExtra("LIST",list);
        startActivity(intent);

    }
    public void back(View view){
        finish();
    }

}