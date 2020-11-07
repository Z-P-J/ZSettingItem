package com.zpj.setting.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zpj.widget.setting.CommonSettingItem;
import com.zpj.widget.setting.SwitchSettingItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwitchSettingItem item = findViewById(R.id.test);
        item.setEnabled(false);
        item.setChecked(true);

        CommonSettingItem test = findViewById(R.id.item_test);
        test.setInfoTextMaxLine(2);
        test.setInfoText("sdrgfth\nsdgffhj\nsdgftfhhggrdgfhfgdfh");
        test.setTitleTextSize(16);
        test.setTitleText("dfgh\nsdfg\nsdfhg\nsfg");
        test.setTitleTextMaxLine(1);
        test.setRightText("dfgh");
//        test.setRightTextColor(Color.BLUE);
    }

}
