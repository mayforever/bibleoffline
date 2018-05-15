package com.mayforever.bibleoffline.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mayforever.bibleoffline.MainActivity;
import com.mayforever.bibleoffline.R;
import com.mayforever.bibleoffline.raw.KeySharedPref;
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
public class SettingDialog extends Dialog implements View.OnClickListener,SeekBar.OnSeekBarChangeListener{
    private SeekBar sbFontSize = null;
    private TextView tvFontSize = null;
    private TextView settingOK = null;
    private TextView settingCancel = null;
    private ToggleButton tbDarkMode = null;
    MainActivity mainActivity = null;
    private final static int MIN_SIZE = 10;
    private final static int DEFAULT_SIZE = 15;
    private SharedPreferences sharedPreferences = null;
    public SettingDialog(@NonNull Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.costumize_view);
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
        sharedPreferences = this.mainActivity.getSharedPreferences("view", Context.MODE_PRIVATE);

        this.tvFontSize = (TextView) findViewById( R.id.tvFontSize);
        this.sbFontSize = (SeekBar) findViewById(R.id.seekBar);
        this.sbFontSize.setMax(16);
        float font_size = sharedPreferences.getFloat(KeySharedPref.KEY_FONT_SIZE,DEFAULT_SIZE);
        this.sbFontSize.setProgress((int)font_size - MIN_SIZE);
        this.sbFontSize.setOnSeekBarChangeListener(this);
        this.settingOK = (TextView) findViewById(R.id.chooseOK);
        this.settingOK.setOnClickListener(this);
        this.settingCancel = (TextView) findViewById(R.id.chooseCancel);
        this.settingCancel.setOnClickListener(this);
        this.tbDarkMode = (ToggleButton) findViewById(R.id.tbNightMode);
        boolean isDark = sharedPreferences.getBoolean(KeySharedPref.KEY_NIGHT_MODE, false);
        this.tbDarkMode.setChecked(isDark);
    }

    public void showThisDialog(){
        float font_size = sharedPreferences.getFloat(KeySharedPref.KEY_FONT_SIZE,DEFAULT_SIZE);
        this.tvFontSize.setText("Font Size : "+font_size);
        this.tvFontSize.setTextSize(font_size);
        this.show();
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        tvFontSize.setText("Font Size : "+(i+ MIN_SIZE));
        tvFontSize.setTextSize(i+MIN_SIZE);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chooseOK:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat(KeySharedPref.KEY_FONT_SIZE,sbFontSize.getProgress()+MIN_SIZE);
                editor.putBoolean(KeySharedPref.KEY_NIGHT_MODE,tbDarkMode.isChecked());
                editor.apply();
                this.mainActivity.initializeLLDetails();
                this.hide();

                break;
            case R.id.chooseCancel:
                this.hide();
                break;
        }
    }
}
