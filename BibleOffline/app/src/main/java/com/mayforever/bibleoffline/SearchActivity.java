package com.mayforever.bibleoffline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mayforever.bibleoffline.content.Verse;
import com.mayforever.bibleoffline.dialog.CheckedChooseBookDialog;
import com.mayforever.bibleoffline.raw.Dictionary;
import com.mayforever.bibleoffline.raw.KeySharedPref;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by MIS on 4/23/2018.
 */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    private CheckBox checkBoxOld = null;
    private CheckBox checkBoxNew = null;
    private Button buttonNew  = null;
    private Button buttonOld = null;
    private EditText etSearch = null;
    private LinearLayout llSearchResult = null;
    private CheckedChooseBookDialog oldTestamentBookChooseDialog = null;
    private CheckedChooseBookDialog newTestamentBookChooseDialog = null;
    private SharedPreferences sharedPreferences = null;
    private Button buttonSearch = null;
    private ArrayList<String> bookToSearch = null;


    public ArrayList<String> getBookToSearch() {
        return bookToSearch;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.search_layout);
        this.initObject();
    }

    public void initObject(){
        sharedPreferences = this.getSharedPreferences(KeySharedPref.KEY_PREF,MODE_PRIVATE);
        this.bookToSearch = new ArrayList<>();
        checkBoxNew = (CheckBox) this.findViewById(R.id.checkboxNewTestament);
        checkBoxOld = (CheckBox) this.findViewById(R.id.checkboxOldTestament);
        buttonNew = (Button) this.findViewById(R.id.buttonNewTestamentLoad);
        buttonNew.setOnClickListener(this);
        buttonOld = (Button) this.findViewById(R.id.checkboxOldTestament);
        buttonOld.setOnClickListener(this);
        etSearch = (EditText) this.findViewById(R.id.etSearch);
        etSearch.setOnClickListener(this);
        llSearchResult = (LinearLayout) this.findViewById(R.id.ll_search_result);
        this.oldTestamentBookChooseDialog = new CheckedChooseBookDialog(this);
        this.oldTestamentBookChooseDialog.loadTestament(Dictionary.OLD_TESTAMENT);
        this.newTestamentBookChooseDialog = new CheckedChooseBookDialog(this);
        this.newTestamentBookChooseDialog.loadTestament(Dictionary.NEW_TESTAMENT);
        this.buttonSearch = (Button) this.findViewById(R.id.buttonSearch);
        this.buttonSearch.setOnClickListener(this);
        if(sharedPreferences.getBoolean(KeySharedPref.KEY_NIGHT_MODE,false)){
            
        }
        initiateCheckButton();
    }
    public void initiateCheckButton(){
        if(sharedPreferences.getString(KeySharedPref.KEY_TESTAMENT, Dictionary.DEFAULT).equals(Dictionary.NEW_TESTAMENT)){
            this.buttonOld.setEnabled(false);
            this.checkBoxOld.setEnabled(false);
            this.checkBoxNew.setEnabled(true);
            this.buttonNew.setEnabled(true);
        }else
        if(sharedPreferences.getString(KeySharedPref.KEY_TESTAMENT, Dictionary.DEFAULT).equals(Dictionary.OLD_TESTAMENT)){
            this.checkBoxNew.setEnabled(false);
            this.buttonNew.setEnabled(false);
            this.buttonOld.setEnabled(true);
            this.checkBoxOld.setEnabled(true);
        }else{
            this.checkBoxNew.setEnabled(true);
            this.buttonNew.setEnabled(true);
            this.buttonOld.setEnabled(true);
            this.checkBoxOld.setEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonNewTestamentLoad:
                this.newTestamentBookChooseDialog.show();
                break;
            case R.id.buttonOldTestamentLoad:
                this.oldTestamentBookChooseDialog.show();
                break;
            case R.id.buttonSearch:
                if(this.etSearch.getText().toString().length() >= 4){
                    search();
                }else{
                    Toast.makeText(SearchActivity.this, "Please make sure that the search " +
                            "text is greater than or equal to 4 letter", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
    public void search(){
        if(this.checkBoxOld.isChecked()){
            this.bookToSearch.addAll(this.oldTestamentBookChooseDialog.getBookSelected());

        }
        String version = sharedPreferences.getString(KeySharedPref.KEY_VERSION,"darby");
        if(this.checkBoxNew.isChecked()){
            this.bookToSearch.addAll(this.newTestamentBookChooseDialog.getBookSelected());
        }
        llSearchResult.removeAllViews();
        // this.bookToSearch.addAll(this.newTestamentBookChooseDialog.getBookSelected());
            for(final String book: bookToSearch){
                TreeMap<Integer, Verse> treeMapChapter = new TreeMap<Integer, Verse>(InitializingBible.getBibleVersion().getBible()
                        .get(version).getBook().get(book).getChapter());
//                System.out.println("the search book is :" +book);
                Iterator<Map.Entry<Integer, Verse>> itChapter = treeMapChapter.entrySet().iterator();
                while (itChapter.hasNext()){
                    Map.Entry<Integer, Verse> pairChapter = itChapter.next();
                    final int keyChapter = pairChapter.getKey();
                    TreeMap<Integer, String> treeMapVerse = new TreeMap<Integer, String>(InitializingBible.getBibleVersion().getBible()
                            .get(version).getBook().get(book).getChapter().get(pairChapter.getKey()).getDetails());
                    Iterator<Map.Entry<Integer, String>> itVerse = treeMapVerse.entrySet().iterator();
                    while (itVerse.hasNext()){
                        Map.Entry<Integer, String> pairDetails = itVerse.next();

                        if(pairDetails.getValue().contains( this.etSearch.getText().toString())){
                            TextView textView = new TextView(this);

                            String verses = pairDetails.getValue().replace(etSearch.getText(),
                                    "<b>"+etSearch.getText()+"</b>");
                            Spanned htmlVerses = Html.fromHtml(verses);
                            textView.setText(book +" " + pairChapter.getKey()+":"+
                                    pairDetails.getKey()+" "+ htmlVerses);
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent();
                                    intent.putExtra(KeySharedPref.KEY_BOOK,book);
                                    intent.putExtra(KeySharedPref.KEY_CHAPTER,keyChapter+"");
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            });
                            llSearchResult.addView(textView);
                        }

                    }
                }
            }
        // }

    }
}
