package com.example.nstorflores.musicalizza.modelsDB;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.example.nstorflores.musicalizza.modelsAPI.Album;
import com.example.nstorflores.musicalizza.modelsAPI.Song;

import java.util.List;

/**
 * Created by Néstor Flores on 9/5/2018.
 */

public class SongsAlbumDb {

    @Embedded
    private SongDb songDb;

    @Relation(parentColumn = "album_id", entityColumn = "id", entity = AlbumDb.class)
    private List<AlbumDb> albumDb; //


    public SongDb getSongDb() {
        return songDb;
    }

    public void setSongDb(SongDb songDb) {
        this.songDb = songDb;
    }

    public List<AlbumDb> getAlbumDb() {
        return albumDb;
    }

    public void setAlbumDb(List<AlbumDb> albumDb) {
        this.albumDb = albumDb;
    }
}
