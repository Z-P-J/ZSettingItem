package com.zpj.setting.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zpj.widget.setting.SwitchSettingItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwitchSettingItem item = findViewById(R.id.test);
        item.setEnabled(false);
        item.setChecked(true);
    }

}
