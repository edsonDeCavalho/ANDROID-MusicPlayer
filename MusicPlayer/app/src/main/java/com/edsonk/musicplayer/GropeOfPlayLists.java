package com.edsonk.musicplayer;

import java.io.Serializable;

public class GropeOfPlayLists implements Serializable {
    private PlayListData[] playlists;
    private int nomberOfPlayLists;

    public GropeOfPlayLists() {
        this.playlists = new PlayListData[300];
        this.nomberOfPlayLists = 0;
    }

    public PlayListData[] getPlaylists() {
        return playlists;
    }

    public void setPlaylists(PlayListData[] playlists) {
        this.playlists = playlists;
    }

    public int getNomberOfPlayLists() {
        return nomberOfPlayLists;
    }

    public void setNomberOfPlayLists(int nomberOfPlayLists) {
        this.nomberOfPlayLists = nomberOfPlayLists;
    }

    public void aDDPlyaList(PlayListData playListData){
        this.nomberOfPlayLists++;
        playlists[nomberOfPlayLists]=playListData;
    }
}
