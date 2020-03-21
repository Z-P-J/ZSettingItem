# ZSettingItem
 Item for android setting.

 ## ScreenShot
 ![截图](/img/screenshot.jpg)

## Usage

 ```gradle
 implementation 'com.zpj.widget:ZSettingItem:1.0.1'
 ```

 ```xml
 <com.zpj.widget.setting.SwitchSettingItem
             android:layout_width="match_parent"
             android:layout_height="wrap_content"
             app:z_setting_titleText="省流模式"
             app:z_setting_infoText="开启该选项将进入省流模式"
             app:z_setting_leftIcon="@mipmap/ic_launcher"
             app:z_setting_checkable_checkedColor="@color/colorPrimary"
             app:z_setting_checkable_normalColor="@color/colorAccent"
             />


 <com.zpj.widget.setting.CommonSettingItem
             android:layout_width="match_parent"
             android:layout_height="wrap_content"
             app:z_setting_titleText="关于应用"
             app:z_setting_infoText="更多关于应用的信息"
             app:z_setting_showInfoBtn="true"
             app:z_setting_titleTextColor="@color/colorAccent"
             app:z_setting_infoTextColor="@android:color/black"
             />
 ```
