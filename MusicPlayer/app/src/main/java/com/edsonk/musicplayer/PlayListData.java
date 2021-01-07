package com.edsonk.musicplayer;

import java.io.Serializable;

public class PlayListData implements Serializable {

    private String listName;
    private int[] songs;
    private String id;

    public PlayListData(String id,String listName, int[] songs) {
        this.listName = listName;
        this.songs = songs;
        this.id=id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public int[] getSongs() {
        return songs;
    }

    public void setSongs(int[] songs) {
        this.songs = songs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
