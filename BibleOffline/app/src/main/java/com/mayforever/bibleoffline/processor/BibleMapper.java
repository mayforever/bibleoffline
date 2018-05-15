package com.mayforever.bibleoffline.processor;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.mayforever.bibleoffline.InitializingBible;
import com.mayforever.bibleoffline.MainActivity;
import com.mayforever.bibleoffline.content.Chapter;
import com.mayforever.bibleoffline.content.Verse;
import com.mayforever.bibleoffline.raw.RawClass;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

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

public class BibleMapper extends AsyncTask<RawClass, Integer, Integer> {
    // private JSONObject jsonBible = null;
    // private int valueIndex = 0;
    private FileOutputStream fos;
    private AppCompatActivity aca = null;
    // ProgressDialog progress;
    private InitializingBible initializingBible = null;
    private String bookThatRun = null;
    public BibleMapper(AppCompatActivity aca){
        // this.jsonBible = new JSONObject();
        this.aca = aca;
//        progress=new ProgressDialog(aca);
//        progress.setMessage("Loading Resources");
//        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progress.setIndeterminate(false);
//        progress.setMax(100);
        if (aca instanceof InitializingBible){
            initializingBible = (InitializingBible)aca;
        }
    }
    private static Object[] getStringFromInputStream(InputStream is) {
        JSONObject jsonDerbyBible = new JSONObject();


        BufferedReader br = null;
        // StringBuilder sb = new StringBuilder();

        ArrayList<String>  alVerses= new ArrayList<>();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                alVerses.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Object[] verses =  alVerses.toArray();

        return verses;

    }



    @Override
    protected void onPreExecute() {
//        progress.setProgress(0);
//        progress.show();
//        InitializingBible initializingBible = null;
//        if (aca instanceof InitializingBible){
//            initializingBible = (InitializingBible)aca;
//        }
//        initializingBible.tvLoading.setText("Loading : "+fields[count].getName());
    }
    @Override
    protected Integer doInBackground(RawClass... rc) {
        // Bible book = new BookReference();
        // int result = 99;
        this.bookThatRun = rc[0].getRawName();
        this.initializingBible.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initializingBible.tvLoading.setText("Loading : "+bookThatRun);
            }
        });
//
        Object[] verses = getStringFromInputStream(rc[0].getIs());
        // JSONArray jsonArrayVerses = new JSONArray();
        MainActivity mainActivity = null;
        if (aca instanceof MainActivity)
            mainActivity = (MainActivity) aca;
        if (!InitializingBible.getBibleVersion().getBible().containsKey(rc[0].getRawName())){
            InitializingBible.getBibleVersion().getBible().put(rc[0].getRawName(), new com.mayforever.bibleoffline.content.Book());
            if (InitializingBible.getBibleVersion().getBible().containsKey(rc[0].getRawName())){
                int versesLength = verses.length;
                // System.out.println(result);
                int i = 0;

                for(Object verse: verses){
                    if (i == 0){
                        String[] books = verse.toString().split(",");
//                        System.out.println(Arrays.toString(books));
                        InitializingBible.getBibleVersion().getBible().get(rc[0].getRawName()).initBook(books);
                        i++;
                        continue;
                    }
                    i++;
                    String[] verseDetails = verse.toString().split("\\|\\|");
                    if(verseDetails.length != 4){
                        continue;
                    }
                    String book =  InitializingBible.getBibleVersion().getBible().get(rc[0].getRawName())
                            .getBookMap().get(verseDetails[0]);
                    // System.out.println(book);

                    while (true){

                        if (InitializingBible.getBibleVersion().getBible().get(rc[0].getRawName()).getBook()
                                .containsKey(book)){

                            if(InitializingBible.getBibleVersion().getBible().get(rc[0].getRawName()).getBook()
                                    .get(book).getChapter().containsKey(Integer.parseInt(verseDetails[1]))){

                                if (InitializingBible.getBibleVersion().getBible().get(rc[0].getRawName()).getBook()
                                        .get(book).getChapter().get(Integer.parseInt(verseDetails[1]))
                                        .getDetails().containsKey(Integer.parseInt(verseDetails[2])))
                                {
                                    // System.out.println(verseDetails[0]+"||"+verseDetails[1]+"||"+verseDetails[2]+"||"+verseDetails[3]);

                                    break;}
                                else{
                                    InitializingBible.getBibleVersion().getBible().get(rc[0].getRawName()).getBook()
                                            .get(book).getChapter().get(Integer.parseInt(verseDetails[1]))
                                            .getDetails().put(Integer.parseInt(verseDetails[2]),verseDetails[3]);}
                                // System.out.println(verseDetails[3]);
                                // break;
                            }else{
                                InitializingBible.getBibleVersion().getBible().get(rc[0].getRawName()).getBook()
                                        .get(book).getChapter()
                                        .put(Integer.parseInt(verseDetails[1]),new Verse());
                            }
                        }else{
                            InitializingBible.getBibleVersion().getBible().get(rc[0].getRawName()).getBook()
                                    .put(book,new Chapter());
                        }
                    }
                    // result = 0;
                    double percent = ((double)i/(double)versesLength)*(double)100;
                    this.publishProgress((int)percent);

                }
                InitializingBible.getBibleVersion().getBible().get(rc[0].getRawName()).setStatus(true);
                return i;
            }
        }

        this.publishProgress(100);

        return 999999999;
    }
    @Override
    protected void onPostExecute(Integer result) {
//        InitializingBible initializingBible = null;
//        if (aca instanceof InitializingBible){
//            initializingBible = (InitializingBible)aca;
//        }
        initializingBible.getProgressBar().setProgress(initializingBible.getProgressBar().getProgress()+1);

    }
}
