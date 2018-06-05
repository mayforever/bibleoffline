package com.mayforever.bibleoffline.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mayforever.bibleoffline.InitializingBible;
import com.mayforever.bibleoffline.MainActivity;
import com.mayforever.bibleoffline.R;
import com.mayforever.bibleoffline.content.Book;
import com.mayforever.bibleoffline.raw.KeySharedPref;

import java.util.Map;
import java.util.TreeMap;

public class ChooseVersionDialog extends Dialog implements View.OnClickListener{
    private TextView versionOK = null;
    private TextView versionCancel = null;
    private RadioGroup radioVersion  = null;
    MainActivity mainActivity = null;
    private SharedPreferences sharedPreferences = null;


    public ChooseVersionDialog(@NonNull Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.choose_version);

        this.initMainActivity(context);
        this.initObject();
    }

    private void initObject(){
        this.sharedPreferences = this.mainActivity.getSharedPreferences("view", Context.MODE_PRIVATE);

        this.radioVersion = (RadioGroup) findViewById(R.id.radio_version);
        this.versionOK = (TextView) findViewById(R.id.chooseOK);
        this.versionOK.setOnClickListener(this);
        this.versionCancel = (TextView) findViewById(R.id.chooseCancel);
        this.versionCancel.setOnClickListener(this);
        this.viewVersion();
    }
    private void initMainActivity(Context context){
        if (context instanceof MainActivity && context != null){
            this.mainActivity = (MainActivity)context;
        }else{
            Toast.makeText(context, "Invalid Main Activity", Toast.LENGTH_SHORT).show();
        }
    }
    public void viewVersion(){
        String[] arrN = new String[InitializingBible.getBibleVersion().getBible().size()];
        int countArrN = 0;
        System.out.println(InitializingBible.getBibleVersion().getBible());
        Map<String, Book>
                treeMap = new TreeMap<>(InitializingBible.getBibleVersion().getBible());
        for (Map.Entry<String, com.mayforever.bibleoffline.content.Book> entry : treeMap.entrySet())
        {
            arrN[countArrN] = entry.getKey();
            countArrN++;
            //
        }

        this.radioVersion.removeAllViews();

        for(int i=0;i<arrN.length;i++){
            RadioButton rb=new RadioButton(this.mainActivity); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(arrN[i]);
            this.radioVersion.addView(rb);
            if(arrN[i].equals(sharedPreferences.getString(KeySharedPref.KEY_VERSION,"darby"))){
                radioVersion.check(rb.getId());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chooseOK:
                RadioButton r = (RadioButton) findViewById(radioVersion.getCheckedRadioButtonId());
                String selectedtext = r.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KeySharedPref.KEY_VERSION, selectedtext);
                editor.apply();
                if   (this.mainActivity.getBookSpinner().getSelectedItem()!=null){
                    SharedPreferences.Editor editorForSpinner = sharedPreferences.edit();
                    String bookSelected = this.mainActivity.getBookSpinner().getSelectedItem().toString();
                    editor.putString(KeySharedPref.KEY_BOOK, bookSelected);
                    String chapterSelected = this.mainActivity.getChapterSpinner().getSelectedItem().toString();
                    editor.putString(KeySharedPref.KEY_CHAPTER, chapterSelected);
                    this.mainActivity.initializeLLDetails();
                    editorForSpinner.apply();
                }else{
                    this.mainActivity.initBookSpinner();
                    this.mainActivity.initChapterSpinner();
                }


                this.hide();

                break;
            case R.id.chooseCancel:
                this.hide();
                break;
        }
    }
}
