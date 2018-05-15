package com.mayforever.bibleoffline;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mayforever.bibleoffline.content.Bible;
import com.mayforever.bibleoffline.processor.BibleMapper;
import com.mayforever.bibleoffline.processor.LoadingBibleProgress;
import com.mayforever.bibleoffline.raw.KeySharedPref;
import com.mayforever.bibleoffline.raw.RawClass;

import java.io.InputStream;
import java.lang.reflect.Field;

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

public class InitializingBible extends AppCompatActivity {
    private static Bible bibleVersion;
    private ProgressBar progressBar = null;
    public TextView tvLoading = null;
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public static Bible getBibleVersion() {
        return bibleVersion;
    }
    private SharedPreferences sharedPreferences = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_main);
        progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
        bibleVersion = new Bible();
        this.initObject();
//
        initializedBible();

        loadMainIntent();

    }

    private void initObject(){
        sharedPreferences = this.getSharedPreferences(KeySharedPref.KEY_PREF, MODE_PRIVATE);

        tvLoading = (TextView) findViewById(R.id.tvLoading);
    }
    private void initializedBible(){
        Field[] fields=R.raw.class.getFields();
        this.getProgressBar().setMax(fields.length);
        for(int count=0; count < fields.length; count++){
            InputStream inputStream = null;
            try {
                if(sharedPreferences.getBoolean(fields[count].getName(),true)){

                    inputStream = this.getResources().openRawResource(fields[count].getInt(fields[count]));
                    new BibleMapper(this).execute(new RawClass(fields[count].getName(),inputStream));
                }else{
//                    tvLoading.setText("Loading : "+fields[count].getName());
                    this.getProgressBar().setProgress(this.getProgressBar().getProgress()+1);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    private void loadMainIntent(){

        new LoadingBibleProgress(this).execute(bibleVersion);

    }
}
