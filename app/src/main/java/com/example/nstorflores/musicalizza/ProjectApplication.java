package com.example.nstorflores.musicalizza;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tumblr.remember.Remember;

/**
 * Created by Néstor Flores on 1/5/2018.
 */

public class ProjectApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Remember.init(getApplicationContext(), "com.uca.apps.isi.taken");
    }
}
