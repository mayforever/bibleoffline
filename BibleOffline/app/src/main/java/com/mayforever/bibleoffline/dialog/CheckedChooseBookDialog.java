package com.mayforever.bibleoffline.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mayforever.bibleoffline.MainActivity;
import com.mayforever.bibleoffline.R;
import com.mayforever.bibleoffline.raw.BookReference;
import com.mayforever.bibleoffline.raw.Dictionary;
import com.mayforever.bibleoffline.raw.KeySharedPref;

import java.util.ArrayList;

/**
 * Created by MIS on 4/25/2018.
 */

public class CheckedChooseBookDialog extends Dialog
        implements View.OnClickListener{
    private TextView chooseOk = null;
    private LinearLayout llBooks = null;
    private SharedPreferences sharedPreferences = null;
    private ArrayList<String> bookSelected = null;

    public ArrayList<String> getBookSelected() {
        return bookSelected;
    }

    public CheckedChooseBookDialog(@NonNull Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.choose_book);
        this.sharedPreferences = context.getSharedPreferences(KeySharedPref.KEY_PREF, Context.MODE_PRIVATE);
        this.initMainActivity(context);
        this.initObject();
    }

    private void initMainActivity(Context context){

    }
    private void initObject(){

        this.bookSelected = new ArrayList<>();

        this.llBooks = (LinearLayout) this.findViewById(R.id.ll_books);
        this.chooseOk = (TextView) this.findViewById(R.id.chooseOK);
        this.chooseOk.setOnClickListener(this);
    }

    public void loadTestament(String testament){
        String[] books = null;
        switch (testament){
            case Dictionary.NEW_TESTAMENT:
                books = BookReference.NEW_TESTAMENT_BOOK;
                break;
            case Dictionary.OLD_TESTAMENT:
                books = BookReference.OLD_TESTAMENT_BOOK;
                break;
        }
        if(books!=null){
            for(String book: books){
                CheckBox checkBox = new CheckBox(this.getContext());
                final String checkBoxName =book;
                checkBox.setText(checkBoxName);
                bookSelected.add(checkBoxName);
                checkBox.setChecked(sharedPreferences.getBoolean(checkBoxName,true));
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b){
                            bookSelected.add(checkBoxName);
                        }else{
                            bookSelected.remove(checkBoxName);
                        }
                    }
                });
                llBooks.addView(checkBox);
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.chooseOK:
                this.hide();
                break;

        }
    }
}
