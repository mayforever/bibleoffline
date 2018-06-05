package com.mayforever.bibleoffline.processor;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;

import com.mayforever.bibleoffline.InitializingBible;
import com.mayforever.bibleoffline.MainActivity;
import com.mayforever.bibleoffline.content.Bible;
import com.mayforever.bibleoffline.content.Book;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by MIS on 3/23/2018.
 */

public class LoadingBibleProgress extends AsyncTask<Bible, Integer, Integer>{

    private InitializingBible aca = null;
    public LoadingBibleProgress(AppCompatActivity initializingBible) {
        if (initializingBible instanceof InitializingBible){
            this.aca = (InitializingBible) initializingBible;
        }

    }

    @Override
    protected Integer doInBackground(Bible... bibles) {
        Map<String, Book> treeMap = new TreeMap<String, Book>(bibles[0].getBible());

        // int i = 0;
        for (Map.Entry<String, Book> entry : treeMap.entrySet())
        {
            while(!entry.getValue().isStatus()){
//                System.out.println(entry.getKey());
//                this.aca.tvLoading.setText(entry.getKey());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // i++;
            // this.aca.getProgressBar().setProgress(i);

        }
        return null;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(Integer integer) {
        // super.onPostExecute(integer);

        Intent intent = new Intent();
        intent.setClass(aca,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        aca.startActivity(intent);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
       // super.onProgressUpdate(values);

    }
}
