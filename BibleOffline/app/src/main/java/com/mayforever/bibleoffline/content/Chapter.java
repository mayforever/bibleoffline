package com.mayforever.bibleoffline.content;

import java.util.HashMap;

/**
 * Created by MIS on 3/7/2018.
 */

public class Chapter {
    public HashMap<Integer, Verse> getChapter() {
        return chapter;
    }

    private HashMap<Integer, Verse> chapter = null;

    public Chapter() {
        this.chapter = new HashMap<Integer, Verse>();
    }
}
