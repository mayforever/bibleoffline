package com.mayforever.bibleoffline.content;

import java.util.HashMap;

/**
 * Created by MIS on 3/7/2018.
 */

public class Bible {
    public HashMap<String, Book> getBible() {
        return bible;
    }

    private HashMap<String, Book> bible = null;
    public Bible() {
        this.bible = new HashMap<String, Book>();
    }
}
