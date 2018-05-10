package com.example.nstorflores.musicalizza.modelsDB;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * Created by Néstor Flores on 8/5/2018.
 */

public class AlbumSongsDb {

    @Embedded
    public AlbumDb album;

    @Relation(parentColumn = "id", entityColumn = "album_id", entity = SongDb.class)
    public List<SongDb> songs; // or use simply 'List pets;'

}
