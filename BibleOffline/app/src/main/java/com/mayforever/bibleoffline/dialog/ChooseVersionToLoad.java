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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mayforever.bibleoffline.MainActivity;
import com.mayforever.bibleoffline.R;
import com.mayforever.bibleoffline.raw.KeySharedPref;

import java.lang.reflect.Field;

/**
 * Created by MIS on 4/23/2018.
 */

public class ChooseVersionToLoad extends Dialog
    implements View.OnClickListener{
    private MainActivity mainActivity = null;
    private SharedPreferences sharedPreferences = null;
    private LinearLayout linearLayout = null;
    private TextView versionOK = null;
    public ChooseVersionToLoad(@NonNull Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.choose_version_to_load);

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
    public void initObject(){
        this.sharedPreferences = this.mainActivity.getSharedPreferences(KeySharedPref.KEY_PREF, Context.MODE_PRIVATE);

        linearLayout = (LinearLayout) this.findViewById(R.id.ll_version);

        this.versionOK = (TextView) this.findViewById(R.id.chooseOK);
        this.versionOK.setOnClickListener(this);
        loadAllVersion();
    }

    private void loadAllVersion(){
        Field[] fields=R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){
            System.out.println("Raw Asset: " + fields[count].getName());
            CheckBox checkBox = new CheckBox(this.getContext());

            final String checkBoxName =fields[count].getName();
            checkBox.setText(checkBoxName);
            checkBox.setChecked(sharedPreferences.getBoolean(checkBoxName,true));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(checkBoxName, b);
                    editor.apply();
                }
            });
            linearLayout.addView(checkBox);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chooseOK:
                this.hide();
                break;
        }
    }
}
