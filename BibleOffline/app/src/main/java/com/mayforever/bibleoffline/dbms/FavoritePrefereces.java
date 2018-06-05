package com.mayforever.bibleoffline.dbms;

import android.content.SharedPreferences;

public class FavoritePrefereces {
    // private SharedPreferences sharedPreferences = null;
    public static void addFavorite(String book, int chapter, int verse, int color,SharedPreferences sharedPreferences){
        // this.sharedPreferences.
        SharedPreferences.Editor  editor = sharedPreferences.edit();
        editor.putInt(book+"\\|\\|"+chapter+"\\|\\|"+verse,color);
        editor.apply();
    }

    public static int getFavoriteColor(String book, int chapter, int verse,SharedPreferences sharedPreferences){
        return sharedPreferences.getInt(book+"\\|\\|"+chapter+"\\|\\|"+verse, 0);
    }
}
