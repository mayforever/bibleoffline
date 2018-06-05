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

import com.mayforever.bibleoffline.MainActivity;
import com.mayforever.bibleoffline.R;
import com.mayforever.bibleoffline.raw.KeySharedPref;

public class ChooseTestamentDialog extends Dialog implements View.OnClickListener {
    private MainActivity mainActivity = null;
    private RadioGroup radioTestament  = null;
    private TextView testamentOK = null;
    private TextView testamentCancel = null;
    private SharedPreferences sharedPreferences = null;
    public ChooseTestamentDialog(@NonNull Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.choose_testament);

        this.initMainActivity(context);
        this.initObject();
    }
    private void initObject(){
        this.sharedPreferences = this.mainActivity.getSharedPreferences("view", Context.MODE_PRIVATE);

        this.radioTestament = (RadioGroup) findViewById(R.id.radio_version);
        this.testamentOK = (TextView) findViewById(R.id.chooseOK);
        this.testamentOK.setOnClickListener(this);
        this.testamentCancel = (TextView) findViewById(R.id.chooseCancel);
        this.testamentCancel.setOnClickListener(this);
        this.viewTestament();
    }
    private void initMainActivity(Context context){
        if (context instanceof MainActivity && context != null){
            this.mainActivity = (MainActivity)context;
        }else{
            Toast.makeText(context, "Invalid Main Activity", Toast.LENGTH_SHORT).show();
        }
    }
    private void viewTestament(){
        String[] arrN = {"New Testament","Old Testament","All"};
        int countArrN = 0;
        this.radioTestament.removeAllViews();

        for(int i=0;i<arrN.length;i++){
            RadioButton rb=new RadioButton(this.mainActivity); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(arrN[i]);
            this.radioTestament.addView(rb);
            if(arrN[i].equals(sharedPreferences.getString(KeySharedPref.KEY_TESTAMENT,"All"))){
                radioTestament.check(rb.getId());
            }
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chooseOK:
                RadioButton r = (RadioButton) findViewById(radioTestament.getCheckedRadioButtonId());
                String selectedtext = r.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KeySharedPref.KEY_TESTAMENT,selectedtext);
                editor.apply();
                // this.chooseTestament.hide();
                this.mainActivity.initBookSpinner();
                this.hide();

                break;
            case R.id.chooseCancel:
                this.hide();
                break;
        }
    }
}
