package com.edsonk.musicplayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.edsonk.musicplayer.utils.MySQLHelper;

import java.util.ArrayList;

/**
 *
 */
public class AccsessLocal {
    private String databseName="bdPlayList.sqlite";
    private Integer version=1;
    private MySQLHelper accesBD;
    private SQLiteDatabase bd;
    private static final String TAG = "AccsessLocal";

    /**
     * Constructer
     * @param context
     */
    public AccsessLocal(Context context){
        accesBD = new MySQLHelper(context,databseName,null,version);
    }

    /**
     * Add of a PlayList in the database
     * @param playlist
     */
    public void aDDToPlayList(PlayListData playlist){
        bd=accesBD.getWritableDatabase();
        int[] a=new int[300];
        a=playlist.getSongs();
        for(int i=0;i<playlist.getSongs().length;i++){
            String req="insert into Playlists2(name_of_list,song) values";
            req+="('"+playlist.getListName()+"',"+a[i]+")";
            bd.execSQL(req);
            Log.i(TAG, "List insertion " + req );
            req=null;
        }
    }

    public ArrayList<String> getPlayLists(){
        String[] listNames=new String[30];
        ArrayList<String> db_data_list = new ArrayList();
        bd=accesBD.getReadableDatabase();
        String req="select distinct name_of_list from Playlists2";
        Cursor cursor=null;
        cursor=bd.rawQuery(req,null);
        int i =0;
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do {
                listNames[i] =(String)cursor.getString(0);
                db_data_list.add(listNames[i]);
                Log.i(TAG, "List cherche " + listNames[i] );
                i++;
            } while (cursor.moveToNext());
            cursor.close();
        }
        return db_data_list;
    }

    public ArrayList<Integer> getSongs(String list){
        Log.i(TAG, "En recherche : ");
        int[] listSongs=new int[30];
        ArrayList<Integer> db_data_list = new ArrayList();
        bd=accesBD.getReadableDatabase();
        String req="select distinct song from Playlists2 where name_of_list='"+list+"'";
        Cursor cursor=null;
        cursor=bd.rawQuery(req,null);
        int i =0;
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do {
                listSongs[i] =(Integer)cursor.getInt(0);
                db_data_list.add(listSongs[i]);
                Log.i(TAG, "List Songs : " + listSongs[i] );
                i++;
            } while (cursor.moveToNext());
            cursor.close();
        }
        return db_data_list;
    }


}
