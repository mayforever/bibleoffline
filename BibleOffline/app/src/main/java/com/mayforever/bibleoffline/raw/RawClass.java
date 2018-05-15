package com.mayforever.bibleoffline.raw;

import java.io.InputStream;

/**
 * Created by MIS on 3/7/2018.
 */

public class RawClass
{
    public InputStream getIs() {
        return is;
    }

    public String getRawName() {
        return rawName;
    }

    private InputStream is;
    private String rawName;
    public RawClass(String rawName, InputStream is) {
        this.rawName = rawName;
        this.is = is;
    }
}
