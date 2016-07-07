package me.jimmyshaw.dropbucketlist;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.jimmyshaw.dropbucketlist.adapters.Filter;

// This application configuration class is needed to primaryily setup Realm's configuration. If we don't
// configure Realm on start up then we'll have to configure Realm every time we want to use it
// in any of our activities or fragments.
// Other methods are used to work with shared preferences and to change the overall font typeface.
public class AppDropBucketList extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static void saveFilterToSharePreferences(Context context, int filterOption) {
        // The difference between getPreferences and getSharedPreferences is the later is meant for
        // multiple shared preferences instances and it forces you to provide a file name to specify
        // the one you want.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor Editor = sharedPreferences.edit();
        Editor.putInt("Filter", filterOption);
        // We can commit to shared preferences using commit or apply. Apply is asynchronous and is
        // recommended.
        Editor.apply();
    }

    public static int extractFilterFromSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        // Our default filter option would be none.
        int filterOption = sharedPreferences.getInt("Filter", Filter.NONE);

        return filterOption;
    }

    public static void setWidgetTypeface(Context context, TextView textView) {
        // To use any custom fonts like Raleway, we have to use a special type called Typeface.
        // The createFromAsset method is used because we already have the .ttf font file located
        // in our assets/fonts folder. An asset manager, the first parameter, handles all that
        // goes inside the asset folder. The path to the .ttf file is our second parameter. After
        // capturing the typeface, set it to whichever text view we like.
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/raleway_thin.ttf");
        textView.setTypeface(typeface);
    }

    public static void setWidgetTypeface(Context context, TextView... textViews) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/raleway_thin.ttf");

        for (TextView textView : textViews) {
            textView.setTypeface(typeface);
        }
    }
}
