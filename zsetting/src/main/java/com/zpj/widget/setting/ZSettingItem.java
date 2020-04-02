package com.zpj.widget.setting;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.zpj.widget.setting.R;
import com.zpj.widget.tinted.TintedImageView;

abstract class ZSettingItem extends BaseSettingItem {

    protected String mTitleText;
    protected float mTitleTextSize;
    protected int mTitleTextColor;

    protected String mInfoText;
    protected float mInfoTextSize;
    protected int mInfoTextColor;

    protected String mRightText;
    protected float mRightTextSize;
    protected int mRightTextColor;

    protected Drawable mLeftIcon;
    protected int mLeftIconTintColor;
    protected Drawable mRightIcon;
    protected int mRightIconTintColor;

    protected boolean showRightArrow;
    protected boolean showInfoButton;
    protected boolean showRightText;
    protected boolean showUnderLine;
    private OnClickListener onInfoButtonClickListener;


    public ZSettingItem(Context context) {
        this(context, null);
    }

    public ZSettingItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZSettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initAttribute(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SimpleSettingItem);
        mTitleText = array.getString(R.styleable.SimpleSettingItem_z_setting_titleText);
        if (TextUtils.isEmpty(mTitleText)) {
            mTitleText = "Title";
        }
        tvTitle.setText(mTitleText);

        mTitleTextSize = array.getDimensionPixelSize(R.styleable.SimpleSettingItem_z_setting_titleTextSize, dp2pxInt(14));
//        mTitleTextSize = array.getDimension(R.styleable.SimpleSettingItem_z_setting_titleTextSize, 14);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
        mTitleTextColor = array.getColor(R.styleable.SimpleSettingItem_z_setting_titleTextColor, Color.parseColor("#222222"));
        tvTitle.setTextColor(mTitleTextColor);

        Drawable background = array.getDrawable(R.styleable.SimpleSettingItem_z_setting_background);
        mLeftIcon = array.getDrawable(R.styleable.SimpleSettingItem_z_setting_leftIcon);
        mLeftIconTintColor = array.getColor(R.styleable.SimpleSettingItem_z_setting_leftIconTint, Color.parseColor("#222222"));
        mRightIconTintColor = array.getColor(R.styleable.SimpleSettingItem_z_setting_rightIconTint, Color.parseColor("#eeeeee"));
//        if (mLeftIcon != null) {
//            mLeftIcon = tintDrawable(mLeftIcon, mLeftIconTintColor);
//        }
        mRightIcon = array.getDrawable(R.styleable.SimpleSettingItem_z_setting_rightIcon);
        array.recycle();


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ZSettingItem);


        mInfoText = a.getString(R.styleable.ZSettingItem_z_setting_infoText);
        if (!TextUtils.isEmpty(mInfoText)) {
            tvInfo.setVisibility(VISIBLE);
            tvInfo.setText(mInfoText);
        }

        mInfoTextSize = a.getDimensionPixelSize(R.styleable.ZSettingItem_z_setting_infoTextSize, dp2pxInt(12));
        tvInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, mInfoTextSize);

        mInfoTextColor = a.getColor(R.styleable.ZSettingItem_z_setting_infoTextColor, Color.parseColor("#808080"));
        tvInfo.setTextColor(mInfoTextColor);

        showUnderLine = a.getBoolean(R.styleable.ZSettingItem_z_setting_showUnderLine, false);
        showRightText = a.getBoolean(R.styleable.ZSettingItem_z_setting_showRightText, false);
        mRightText = a.getString(R.styleable.ZSettingItem_z_setting_rightText);
        mRightTextSize = a.getDimensionPixelSize(R.styleable.ZSettingItem_z_setting_rightTextSize, dp2pxInt(14));
        mRightTextColor = a.getColor(R.styleable.ZSettingItem_z_setting_rightTextColor, Color.parseColor("#808080"));
        showInfoButton = a.getBoolean(R.styleable.ZSettingItem_z_setting_showInfoBtn, false);
        showRightArrow = a.getBoolean(R.styleable.ZSettingItem_z_setting_showRightArrow, true);

        a.recycle();

        if (!showRightText && !TextUtils.isEmpty(mRightText)) {
            showRightText = true;
        }

        if (background == null) {
            TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackground});
            background = typedArray.getDrawable(0);
            typedArray.recycle();
        }

        setBackground(background);
    }

    @Override
    public void inflateLeftIcon(ViewStub viewStub) {
        if (mLeftIcon != null) {
            viewStub.setLayoutResource(R.layout.z_setting_left_icon);
            viewStub.setInflatedId(R.id.iv_left_icon);
            TintedImageView ivLeft = (TintedImageView) viewStub.inflate();
            ivLeft.setImageDrawable(mLeftIcon);
        }
    }

    @Override
    public void inflateRightContainer(ViewStub viewStub) {
        if (!showRightArrow) {
            return;
        }
        viewStub.setLayoutResource(R.layout.z_setting_right_container_arrow);
        viewStub.setInflatedId(R.id.iv_right_icon);
        TintedImageView view = (TintedImageView) viewStub.inflate();
        if (mRightIcon != null) {
            view.setImageDrawable(mRightIcon);
        }
        view.setTint(ColorStateList.valueOf(mRightIconTintColor));
    }

    @Override
    public void inflateInfoButton(ViewStub viewStub) {
        if (showInfoButton) {
            viewStub.setLayoutResource(R.layout.z_setting_info_btn);
            viewStub.setInflatedId(R.id.iv_info_btn);
            View view = viewStub.inflate();
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onInfoButtonClickListener != null) {
                        onInfoButtonClickListener.onClick(v);
                    }
                }
            });
        }
    }

    @Override
    public void inflateRightText(ViewStub viewStub) {
        if (showRightText) {
            viewStub.setLayoutResource(R.layout.z_setting_right_text);
            viewStub.setInflatedId(R.id.tv_right_text);
            TextView view = (TextView) viewStub.inflate();
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);
            if (mRightText != null) {
                view.setText(mRightText);
            }
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            tvTitle.setTextColor(mTitleTextColor);
            tvInfo.setTextColor(mInfoTextColor);
        } else {
            tvTitle.setTextColor(Color.LTGRAY);
            tvInfo.setTextColor(Color.LTGRAY);
        }
        int color = enabled ? mLeftIconTintColor : Color.LTGRAY;
        if (mLeftIcon != null) {
//            mLeftIcon = tintDrawable(mLeftIcon, color);
            if (inflatedLeftIcon instanceof TintedImageView) {
                TintedImageView imageView = ((TintedImageView) inflatedLeftIcon);
                imageView.setImageDrawable(mLeftIcon);
                imageView.setTint(ColorStateList.valueOf(color));
            }
        }
        if (inflatedInfoButton instanceof TintedImageView) {
            TintedImageView imageView = ((TintedImageView) inflatedInfoButton);
            imageView.setImageResource(R.drawable.ic_info_black_24dp);
            imageView.setTint(ColorStateList.valueOf(color));
        }
        if (inflatedRightText instanceof TextView) {
            ((TextView) inflatedRightText).setTextColor(enabled ? mRightTextColor : Color.LTGRAY);
        }
        if (inflatedRightContainer instanceof TintedImageView) {
            TintedImageView imageView = ((TintedImageView) inflatedRightContainer);
            imageView.setTint(ColorStateList.valueOf(enabled ? mRightIconTintColor : Color.LTGRAY));
        }
    }

    public void setTitleText(String mTitleText) {
        this.mTitleText = mTitleText;
        tvTitle.setText(mTitleText);
    }

    public String getTitleText() {
        return mTitleText;
    }

    public void setInfoText(String mInfoText) {
        this.mInfoText = mInfoText;
        tvInfo.setText(mInfoText);
    }

    public void setOnInfoButtonClickListener(OnClickListener onInfoButtonClickListener) {
        this.onInfoButtonClickListener = onInfoButtonClickListener;
    }

    public void setRightText(String mRightText) {

        if (inflatedRightText instanceof TextView) {
            this.mRightText = mRightText;
            ((TextView) inflatedRightText).setText(mRightText);
        }
    }

    public void setLeftIcon(Drawable mLeftIcon) {
        this.mLeftIcon = mLeftIcon;
        if (inflatedLeftIcon == null) {
            inflateLeftIcon(vsLeftIcon);
        } else if (inflatedLeftIcon instanceof ImageView) {
            ((ImageView) inflatedLeftIcon).setImageDrawable(mLeftIcon);
        }
    }

    public String getInfoText() {
        return mInfoText;
    }

    public String getRightText() {
        return mRightText;
    }


//    private Drawable tintDrawable(Drawable drawable, int color) {
//        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
//        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color));
//        return wrappedDrawable;
//    }

}

