package com.mayforever.bibleoffline.content;

import java.util.HashMap;

/**
 * Created by MIS on 3/8/2018.
 */

public class Verse {
    private HashMap<Integer, String> details = null;
    public HashMap<Integer, String> getDetails() {
        return details;
    }
    public Verse(){
        this.details = new HashMap<Integer, String>();
    }
}
