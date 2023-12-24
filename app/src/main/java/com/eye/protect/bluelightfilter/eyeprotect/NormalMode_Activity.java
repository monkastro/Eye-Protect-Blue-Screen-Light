package com.eye.protect.bluelightfilter.eyeprotect;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class NormalMode_Activity extends Activity {

    RadioButton btnOff;
    RadioButton btnOn;
    SharedPreferences.Editor editor;

    public Button refresh;
    SharedPreferences sharedPreferences;
    private CheckBox startVideoAdsMuted;
    RadioGroup toggle;

    public TextView videoStatus;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_normal);

        this.toggle = (RadioGroup) findViewById(R.id.toggle);
        this.btnOff = (RadioButton) findViewById(R.id.off);
        this.btnOn = (RadioButton) findViewById(R.id.on);
        this.sharedPreferences = getSharedPreferences("lightDimmer", 0);
        this.editor = this.sharedPreferences.edit();
        if (this.sharedPreferences.getBoolean("NameOfThingToSaveN", false)) {
            this.btnOn.setChecked(true);
        }
        this.toggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.off :
                        NormalMode_Activity normalMode_Activity = NormalMode_Activity.this;
                        normalMode_Activity.stopService(new Intent(normalMode_Activity.getApplicationContext(), NormalService.class));
                        NormalMode_Activity.this.editor.putBoolean("NameOfThingToSaveN", false).apply();
                        return;
                    case R.id.on :
                        NormalMode_Activity.this.stopAllServices();
                        NormalMode_Activity.this.startService(new Intent(NormalMode_Activity.this.getApplicationContext(), NormalService.class));
                        NormalMode_Activity.this.editor.putBoolean("NameOfThingToSaveN", true).apply();
                        return;
                    default:
                        return;
                }
            }
        });
    }


    public void stopAllServices() {
        this.editor.putBoolean("NameOfThingToSaveR", false).apply();
        this.editor.putBoolean("NameOfThingToSaveStress", false).apply();
        this.editor.putBoolean("NameOfThingToSaveD", false).apply();
        this.editor.putBoolean("NameOfThingToSaveSleep", false).apply();
        stopService(new Intent(getApplicationContext(), IntensityService.class));
        stopService(new Intent(getApplicationContext(), DarkService.class));
        stopService(new Intent(getApplicationContext(), ReadingService.class));
        stopService(new Intent(getApplicationContext(), SleepService.class));
        stopService(new Intent(getApplicationContext(), StressfreeService.class));
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

}
