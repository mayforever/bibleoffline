package com.mayforever.bibleoffline.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.mayforever.bibleoffline.MainActivity;
import com.mayforever.bibleoffline.R;
import com.mayforever.bibleoffline.raw.DefaultColor;
/**
 * Created by John Aaron C. Valencia on 4/2/2018.
 */

/**
 * Copyright 2018 John Aaron C. Valencia

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
public class ChooseColorDialog extends Dialog implements View.OnClickListener{
    private MainActivity mainActivity = null;
    private View viewRed = null;
    private View viewLawnGreen = null;
    private View viewViolet = null;
    private View viewBrown = null;
    private View viewOrange = null;
    private View viewBlue = null;
    private int favColor = 0;

    private TextView colorCancel = null;
    private TextView colorDefault = null;
    public ChooseColorDialog(@NonNull Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.choose_color_layout);

        // this.setTitle("Choose Color");
        this.initMainActivity(context);
        this.initObject();
    }

    private void initMainActivity(Context context){
        if (context instanceof MainActivity && context != null){
            this.mainActivity = (MainActivity)context;
        }else{
            Toast.makeText(context, "Invalid Main Activity", Toast.LENGTH_SHORT).show();
        }
    }
    private void initObject(){
        this.viewViolet = (View) this.findViewById(R.id.viewViolet);
        this.viewViolet.setOnClickListener(this);
        this.viewViolet.setBackgroundColor(DefaultColor.getColor(DefaultColor.VIOLET));
        this.viewRed = (View) this.findViewById(R.id.viewRed);
        this.viewRed.setOnClickListener(this);
        this.viewRed.setBackgroundColor(DefaultColor.getColor(DefaultColor.RED));
        this.viewOrange = (View) this.findViewById(R.id.viewOrange);
        this.viewOrange.setOnClickListener(this);
        this.viewOrange.setBackgroundColor(DefaultColor.getColor(DefaultColor.PINK));
        this.viewBrown = (View) this.findViewById(R.id.viewBrown);
        this.viewBrown.setOnClickListener(this);
        this.viewBrown.setBackgroundColor(DefaultColor.getColor(DefaultColor.BROWN));
        this.viewBlue = (View) this.findViewById(R.id.viewBlue);
        this.viewBlue.setOnClickListener(this);
        this.viewBlue.setBackgroundColor(DefaultColor.getColor(DefaultColor.BLUE));
        this.viewLawnGreen = (View) this.findViewById(R.id.viewLawnGreen);
        this.viewLawnGreen.setOnClickListener(this);
        this.viewLawnGreen.setBackgroundColor(DefaultColor.getColor(DefaultColor.LAWN_GREEN));
        this.colorCancel = (TextView) this.findViewById(R.id.chooseCancel);
        this.colorCancel.setOnClickListener(this);
        this.colorDefault = (TextView) this.findViewById(R.id.chooseDefault);
        this.colorDefault.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.viewViolet:
                favColor = DefaultColor.getColor(DefaultColor.VIOLET);

                break;
            case R.id.viewRed:
                favColor = DefaultColor.getColor(DefaultColor.RED);
                break;
            case R.id.viewOrange:
                favColor = DefaultColor.getColor(DefaultColor.PINK);
                break;
            case R.id.viewBrown:
                favColor = DefaultColor.getColor(DefaultColor.BROWN);
                break;
            case R.id.viewBlue:
                favColor = DefaultColor.getColor(DefaultColor.BLUE);
                break;
            case R.id.viewLawnGreen:
                favColor = DefaultColor.getColor(DefaultColor.LAWN_GREEN);
                break;
            case R.id.chooseDefault:
                favColor = 0;
                break;
            case R.id.chooseCancel:
                this.hide();
                return;
                // break;
        }
        this.mainActivity.setColorAllFavorite(favColor);
        this.mainActivity.initializeLLDetails();
        this.hide();
    }
}
