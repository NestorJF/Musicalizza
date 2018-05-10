package com.example.nstorflores.musicalizza.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.nstorflores.musicalizza.Dao.AlbumDao;
import com.example.nstorflores.musicalizza.Dao.ArtistDao;
import com.example.nstorflores.musicalizza.Dao.GenreDao;
import com.example.nstorflores.musicalizza.Dao.ImageDao;
import com.example.nstorflores.musicalizza.Dao.SongDao;
import com.example.nstorflores.musicalizza.modelsAPI.SongCreate;
import com.example.nstorflores.musicalizza.modelsDB.AlbumDb;
import com.example.nstorflores.musicalizza.modelsDB.ArtistDb;
import com.example.nstorflores.musicalizza.modelsDB.GenreDb;
import com.example.nstorflores.musicalizza.modelsDB.ImageDb;
import com.example.nstorflores.musicalizza.modelsDB.SongDb;

/**
 * Created by User on 12/04/2018.
 */

@Database(entities = {SongDb.class, ArtistDb.class, GenreDb.class, ImageDb.class, AlbumDb.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    public abstract SongDao songDao();
    public abstract AlbumDao albumDao();
    public abstract ArtistDao artistDao();
    public abstract GenreDao genreDao();
    public abstract ImageDao imageDao();
}
