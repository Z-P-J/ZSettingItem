package com.zpj.widget.setting;

import android.content.Context;

class Utils {

    public static float dp2px(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * density(context);
    }

    public static float px2dp(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return px / density(context);
    }

    public static float density(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }


    public static int dp2pxInt(Context context, float dp) {
        return (int) (dp2px(context, dp) + 0.5f);
    }

    public static int px2dpInt(Context context, float px) {
        return (int) (px2dp(context, px) + 0.5f);
    }


    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int dp2sp(Context context, int dp) {
        return px2sp(context, dp2px(context, dp));
    }

    public static int sp2dp(Context context, int sp) {
        return px2dpInt(context, sp2px(context, sp));
    }

}
