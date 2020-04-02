package com.zpj.widget.setting;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;

import com.zpj.widget.switcher.BaseSwitcher;
import com.zpj.widget.switcher.OnCheckedChangeListener;

public abstract class CheckableSettingItem extends ZSettingItem {

    protected BaseSwitcher switcher;
    private int normalColor;
    private int checkedColor;

    private boolean isChecked;

    private OnCheckableItemClickListener listener;

    CheckableSettingItem(Context context) {
        this(context, null);
    }

    CheckableSettingItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    CheckableSettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initAttribute(Context context, AttributeSet attrs) {
        super.initAttribute(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CheckableSettingItem);
        normalColor = array.getColor(
                R.styleable.CheckableSettingItem_z_setting_checkable_normalColor,
                getResources().getColor(R.color.switcher_normal_color));
        checkedColor = array.getColor(
                R.styleable.CheckableSettingItem_z_setting_checkable_checkedColor,
                getResources().getColor(R.color.switcher_checked_color));
        array.recycle();
    }

    @Override
    public void onInflate(ViewStub stub, View inflated) {
        super.onInflate(stub, inflated);
        if (stub == vsRightContainer && inflated instanceof BaseSwitcher) {
            switcher = (BaseSwitcher) inflated;
            switcher.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick();
                }
            });
            switcher.setOnColor(checkedColor);
            switcher.setOffColor(normalColor);
            switcher.setEnabled(isEnabled());
        }
    }

    @Override
    public void onItemClick() {
        setChecked(!isChecked);
        if (listener != null) {
            listener.onItemClick(this);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (switcher != null) {
            switcher.setEnabled(enabled);
        }
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
        if (switcher != null) {
            switcher.setOffColor(normalColor);
        }
    }

    public void setCheckedColor(int checkedColor) {
        this.checkedColor = checkedColor;
        if (switcher != null) {
            switcher.setOnColor(checkedColor);
        }
    }

    public void setOnItemClickListener(OnCheckableItemClickListener listener) {
        this.listener = listener;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        setChecked(isChecked, true);
    }

    public void setChecked(boolean isChecked, boolean withAnimation) {
        this.isChecked = isChecked;
        switcher.setChecked(isChecked, withAnimation);
    }
}

