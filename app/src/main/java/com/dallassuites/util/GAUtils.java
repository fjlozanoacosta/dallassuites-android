package com.dallassuites.util;

import android.app.Activity;

import com.dallassuites.dallassuites.InicioApp;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by andres.torres on 3/2/15.
 */


    public class GAUtils {

        public static void sendScreen(Activity a, String screen_name){
            Tracker t = ((InicioApp) a.getApplication()).getTracker(
                    InicioApp.TrackerName.APP_TRACKER);
            t.setScreenName(screen_name);
            t.send(new HitBuilders.AppViewBuilder().build());

        }
    }


