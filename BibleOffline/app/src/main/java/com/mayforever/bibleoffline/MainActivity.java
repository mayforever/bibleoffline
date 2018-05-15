package com.mayforever.bibleoffline;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mayforever.bibleoffline.content.Verse;
import com.mayforever.bibleoffline.dbms.FavoritePrefereces;
import com.mayforever.bibleoffline.dialog.ChooseColorDialog;
import com.mayforever.bibleoffline.dialog.ChooseTestamentDialog;
import com.mayforever.bibleoffline.dialog.ChooseVersionDialog;
import com.mayforever.bibleoffline.dialog.ChooseVersionToLoad;
import com.mayforever.bibleoffline.dialog.SettingDialog;
import com.mayforever.bibleoffline.raw.BookReference;
import com.mayforever.bibleoffline.raw.DefaultColor;
import com.mayforever.bibleoffline.raw.KeySharedPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
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
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        {
//    public Bible getBibleVersion() {
//        return bibleVersion;
//    }
    private LinearLayout linearLayoutDetails = null;

    public Spinner getBookSpinner() {
        return bookSpinner;
    }

    public Spinner getChapterSpinner() {
        return chapterSpinner;
    }

    //    private Bible bibleVersion = null;
    private Spinner bookSpinner = null;
    private Spinner chapterSpinner = null;
    private SharedPreferences sharedPreferences = null;


    // private Dialog chooseVersion  = null;
    private ChooseVersionDialog chooseVersionDialog= null;
    private ChooseVersionToLoad chooseVersionToLoadDialog= null;
    private ChooseTestamentDialog chooseTestamentDialog = null;
    // private Dialog settingDialog  = null;
    private SettingDialog settingDialog = null;

    private static int DEFAULT_FONT_SIZE = 16;
    private final static boolean DEFAULT_DARK_MODE = false;
    private Dialog dialog =null;
    private TextView spinnerTextView = null;
    private FloatingActionButton fabColor = null;
    private FloatingActionButton fabCopy= null;
    private HashMap<Integer, TextView>  mapTextview = null;
    private ArrayList<String> selectedVerse = null;
    private ChooseColorDialog chooseColorDialog = null;
    private ClipboardManager clipboardManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        inializedObject();
        if (ifBibleDefaultWasNotLoad()){
            initBookSpinner();
            initChapterSpinner();
        }


    }

    private void inializedObject (){
        this.mapTextview = new HashMap<Integer, TextView>();
        this.selectedVerse = new ArrayList<String>();

        this.sharedPreferences = getSharedPreferences("view", Context.MODE_PRIVATE);

        this.linearLayoutDetails = (LinearLayout) findViewById(R.id.ll_details);


        this.bookSpinner = (Spinner) findViewById(R.id.spinnerBook);
//        initBookSpinner();
        this.bookSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KeySharedPref.KEY_BOOK,bookSpinner.getSelectedItem().toString());
//                        editor.putString(KeySharedPref.KEY_CHAPTER,data.getStringExtra(KeySharedPref.KEY_CHAPTER));
                        editor.apply();
                        initChapterSpinner();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );

        this.chapterSpinner= (Spinner) findViewById(R.id.spinnerChapter);
//        initChapterSpinner();
        this.chapterSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString(KeySharedPref.KEY_BOOK,data.getStringExtra(KeySharedPref.KEY_BOOK));
                        editor.putString(KeySharedPref.KEY_CHAPTER,chapterSpinner.getSelectedItem().toString());
                        editor.apply();
                       initializeLLDetails();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );


//       in choose version dialog
        this.chooseVersionDialog = new ChooseVersionDialog(this);

//        in choose testament dialog
        this.chooseTestamentDialog = new ChooseTestamentDialog(this);

//        in setting Dialog
        this.settingDialog = new SettingDialog(this);
        // in choose version to load
        this.chooseVersionToLoadDialog = new ChooseVersionToLoad(this);


        this.spinnerTextView = (TextView) findViewById(R.id.spinnerTextView);
;
        this.chooseColorDialog = new ChooseColorDialog(this);
        this.fabColor = (FloatingActionButton) findViewById(R.id.fabColor);
        this.fabColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseColorDialog.show();
            }
        });
        this.fabCopy = (FloatingActionButton) findViewById(R.id.fabCopy);
        this.fabCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clipboardCopy = getBookSpinner().getSelectedItem().toString()+ " : " +
                        getChapterSpinner().getSelectedItem().toString() + "\n\n\n";
                for(String index : selectedVerse){
                    TextView textView = mapTextview.get(Integer.parseInt(index));
                    clipboardCopy+=textView.getText().toString() +"\n\n";
                    selectedVerse.remove(index);
                }
                ClipData clipData = ClipData.newPlainText("copy verse",clipboardCopy);
                clipboardManager.setPrimaryClip(clipData);
                initializeLLDetails();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            settingDialog.showThisDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_download_bible) {
            this.chooseVersionToLoadDialog.show();
        } else if (id == R.id.nav_version) {
            this.chooseVersionDialog.show();
        } else if (id == R.id.nav_testament) {
            this.chooseTestamentDialog.show();
        } else if (id == R.id.nav_search) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,SearchActivity.class);
            this.startActivityForResult(intent,100);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean ifBibleDefaultWasNotLoad(){
        return InitializingBible.getBibleVersion().getBible().
                containsKey(sharedPreferences.getString(KeySharedPref.KEY_VERSION,"darby"));


    }
    public void initBookSpinner(){
        ArrayAdapter<String> adapter = null;
        switch (sharedPreferences.getString(KeySharedPref.KEY_TESTAMENT,"All")){
            case "All":
                adapter = new ArrayAdapter<String>(this,
                        R.layout.spinner_text_view, BookReference.BOOK);
                break;
            case "New Testament":
                adapter = new ArrayAdapter<String>(this,
                        R.layout.spinner_text_view, BookReference.NEW_TESTAMENT_BOOK);
                break;
            case "Old Testament":
                adapter = new ArrayAdapter<String>(this,
                        R.layout.spinner_text_view, BookReference.OLD_TESTAMENT_BOOK);
            break;
        }

        adapter.setDropDownViewResource(R.layout.spinner_text_view);
        this.bookSpinner.setAdapter(adapter);
        switch (sharedPreferences.getString(KeySharedPref.KEY_TESTAMENT,"All")){
            case "All":
                getBookSpinner().setSelection(adapter.getPosition(sharedPreferences.getString(KeySharedPref.KEY_BOOK,"Genesis")));
                break;
            case "New Testament":
                getBookSpinner().setSelection(adapter.getPosition(sharedPreferences.getString(KeySharedPref.KEY_BOOK,"Matthew")));
                break;
            case "Old Testament":
                getBookSpinner().setSelection(adapter.getPosition(sharedPreferences.getString(KeySharedPref.KEY_BOOK,"Genesis")));
                break;
        }
    }

    public void initChapterSpinner(){
        // this.chapterSpinner
        String book = null;
        switch (sharedPreferences.getString(KeySharedPref.KEY_TESTAMENT,"All")){
            case "All":
                book= sharedPreferences.getString(KeySharedPref.KEY_BOOK,"Genesis");
                break;
            case "New Testament":
                book= sharedPreferences.getString(KeySharedPref.KEY_BOOK,"Matthew");
                break;
            case "Old Testament":
                book= sharedPreferences.getString(KeySharedPref.KEY_BOOK,"Genesis");
                break;
        }
        String[] arrN = new String[InitializingBible.getBibleVersion().getBible().get(sharedPreferences.getString("version","darby"))
                .getBook().get(book).getChapter().size()];
        int countArrN = 0;
        Map<Integer, Verse> treeMap = new TreeMap<Integer, Verse>(InitializingBible.getBibleVersion().getBible()
                .get(sharedPreferences.getString(KeySharedPref.KEY_VERSION,"darby"))
                .getBook().get(book).getChapter());
        for (Map.Entry<Integer, Verse> entry : treeMap.entrySet())
        {
            arrN[countArrN] = entry.getKey()+"";
            countArrN++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_text_view, arrN);
        adapter.setDropDownViewResource(R.layout.spinner_text_view);
        this.chapterSpinner.setAdapter(adapter);
        getChapterSpinner().setSelection(adapter.getPosition(sharedPreferences.getString(KeySharedPref.KEY_CHAPTER,"1")));
    }

    public void initializeLLDetails(){
        this.linearLayoutDetails.removeAllViews();
        mapTextview.clear();
        if(sharedPreferences.getBoolean(KeySharedPref.KEY_NIGHT_MODE, DEFAULT_DARK_MODE)){
            this.chapterSpinner.setBackgroundColor(DefaultColor.getColor(DefaultColor.BLACK));
            this.bookSpinner.setBackgroundColor(DefaultColor.getColor(DefaultColor.BLACK));
        }else{
            this.chapterSpinner.setBackgroundColor(DefaultColor.getColor(DefaultColor.WHITE));
            this.bookSpinner.setBackgroundColor(DefaultColor.getColor(DefaultColor.WHITE));
        }
        final String book = this.bookSpinner.getSelectedItem().toString();
        final int chapter = Integer.parseInt(this.chapterSpinner.getSelectedItem().toString());
        TreeMap<Integer, String> treeMap = new TreeMap<>(InitializingBible.getBibleVersion().getBible()
                .get(sharedPreferences.getString(KeySharedPref.KEY_VERSION,"darby"))
                .getBook().get(this.bookSpinner.getSelectedItem().toString()).getChapter()
                .get(Integer.parseInt(this.chapterSpinner.getSelectedItem().toString()))
                .getDetails());
        Iterator<Map.Entry<Integer, String>> it = treeMap.entrySet().iterator();
        if(selectedVerse.isEmpty()){
            this.fabColor.setVisibility(View.INVISIBLE);
            this.fabCopy.setVisibility(View.INVISIBLE);
        }
        while (it.hasNext()) {
            Map.Entry<Integer, String> pair = it.next();
            TextView textView = new TextView(this);
            final int favoriteColor = FavoritePrefereces.getFavoriteColor(book,chapter,pair.getKey(),sharedPreferences);
            textView.setText(pair.getKey()+"."+pair.getValue());
            if(sharedPreferences.getBoolean(KeySharedPref.KEY_NIGHT_MODE, DEFAULT_DARK_MODE)){
                for(int i = 0;i<this.bookSpinner.getAdapter().getViewTypeCount();i++){
                    if(favoriteColor == 0){
                        textView.setTextColor(DefaultColor.getColor(DefaultColor.WHITE));
                    }else{
                        textView.setTextColor(favoriteColor);
                    }

                    textView.setBackgroundColor(DefaultColor.getColor(DefaultColor.BLACK));

                }
            }else{
                for(int i = 0;i<this.bookSpinner.getAdapter().getViewTypeCount();i++){
                    textView.setTextColor(DefaultColor.getColor(DefaultColor.BLACK));
                    if(favoriteColor == 0){
                        textView.setBackgroundColor(DefaultColor.getColor(DefaultColor.WHITE));
                    }else{
                        textView.setBackgroundColor(favoriteColor);
                    }


                }
            }

            textView.setTextSize(sharedPreferences.getFloat(KeySharedPref.KEY_FONT_SIZE,DEFAULT_FONT_SIZE));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView thisTextView = (TextView) view;
                    String[] versesDetails = thisTextView.getText().toString().split("\\.");
                    int color = favoriteColor;
                    if(sharedPreferences.getBoolean(KeySharedPref.KEY_NIGHT_MODE, DEFAULT_DARK_MODE)){
                        if(thisTextView.getTextColors().getDefaultColor()==DefaultColor.getColor(DefaultColor.HIGHLIGHT)){
                            thisTextView.setTextColor(color);
                            selectedVerse.remove(versesDetails[0]);
                        }else{
                            thisTextView.setTextColor(DefaultColor.getColor(DefaultColor.HIGHLIGHT));
                            selectedVerse.add(versesDetails[0]);
                        }


                    }else{
                        ColorDrawable colorDrawable = (ColorDrawable) thisTextView.getBackground();
                        if(colorDrawable.getColor()==DefaultColor.getColor(DefaultColor.HIGHLIGHT)){
                            thisTextView.setBackgroundColor(color);
                            selectedVerse.remove(versesDetails[0]);
                        }else{
                            thisTextView.setBackgroundColor(DefaultColor.getColor(DefaultColor.HIGHLIGHT));
                            selectedVerse.add(versesDetails[0]);

                        }


                    }
                    if(selectedVerse.isEmpty()){
                        fabColor.setVisibility(View.INVISIBLE);
                        fabCopy.setVisibility(View.INVISIBLE);
                    }else{
                        fabColor.setVisibility(View.VISIBLE);
                        fabCopy.setVisibility(View.VISIBLE);
                    }
                }
            });
            this.linearLayoutDetails.addView(textView);
            mapTextview.put(pair.getKey(),textView);
        }
    }


    public void setColorAllFavorite(int color){
        String book = this.bookSpinner.getSelectedItem().toString();
        int chapter = Integer.parseInt(this.chapterSpinner.getSelectedItem().toString());
        for(String index: selectedVerse){
            FavoritePrefereces.addFavorite(book, chapter, Integer.parseInt(index),color,sharedPreferences);
        }
        selectedVerse.clear();
    }

    private boolean pressedDoubleToExit= false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(pressedDoubleToExit){
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
            this.pressedDoubleToExit = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    pressedDoubleToExit=false;
                }
            }, 2000);
        }

        return false;
    }



            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if(requestCode == 100 && resultCode == RESULT_OK){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KeySharedPref.KEY_BOOK,data.getStringExtra(KeySharedPref.KEY_BOOK));
                    editor.putString(KeySharedPref.KEY_CHAPTER,data.getStringExtra(KeySharedPref.KEY_CHAPTER));
                    editor.apply();
                    initBookSpinner();
                    initChapterSpinner();
                }
            }
        }

