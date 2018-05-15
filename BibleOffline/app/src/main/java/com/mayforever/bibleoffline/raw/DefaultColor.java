package com.mayforever.bibleoffline.raw;

import android.graphics.Color;

/**
 * Created by MIS on 4/7/2018.
 */

public class DefaultColor {
    public static int getColor(int[] color){
        return Color.rgb(color[0],color[1],color[2]);
    }
    public final static int[] BLACK = {29,29,29};
    public final static int[] WHITE = {255,255,255};
    public final static int[] HIGHLIGHT = {255,255,0};
    public final static int[] LAWN_GREEN = {124,252,0};
    public final static int[] RED = {255,102,102};
    public final static int[] BROWN = {255,255,204};
    public final static int[] BLUE_GREEN = {153,255,255};
    public final static int[] VIOLET = {204,153,255};
    public final static int[] BLUE = {153,255,255};
    public final static int[] PINK = {255, 153, 204};
}
