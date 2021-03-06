package com.zpj.widget.setting;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

abstract class ZSettingItem extends BaseSettingItem {

    protected String mTitleText;
    protected float mTitleTextSize;
    protected int mTitleTextColor;

    protected String mInfoText;
    protected float mInfoTextSize;
    protected int mInfoTextColor;

    protected TextView tvRight;
    protected String mRightText;
    protected float mRightTextSize;
    protected int mRightTextColor;

    protected Drawable mLeftIcon;
    protected int mLeftIconTintColor;
    protected Drawable mRightIcon;
    protected int mRightIconTintColor;
    protected Drawable mArrowIcon;
    protected int mArrowIconTintColor;

    protected boolean showRightArrow;
    protected boolean showRightButton;
    protected boolean showRightText;
    protected boolean showUnderLine;
    private OnClickListener onRightButtonClickListener;


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

        mTitleTextSize = array.getDimensionPixelSize(R.styleable.SimpleSettingItem_z_setting_titleTextSize, Utils.dp2pxInt(context, 14));
//        mTitleTextSize = array.getInt(R.styleable.SimpleSettingItem_z_setting_titleTextSize, 14);
//        mTitleTextSize = dp2sp((int) mTitleTextSize);
//        mTitleTextSize = array.getDimension(R.styleable.SimpleSettingItem_z_setting_titleTextSize, 14);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
        mTitleTextColor = array.getColor(R.styleable.SimpleSettingItem_z_setting_titleTextColor, Color.parseColor("#222222"));
        tvTitle.setTextColor(mTitleTextColor);

        Drawable background = array.getDrawable(R.styleable.SimpleSettingItem_z_setting_background);
        mLeftIcon = array.getDrawable(R.styleable.SimpleSettingItem_z_setting_leftIcon);
        mLeftIconTintColor = array.getColor(R.styleable.SimpleSettingItem_z_setting_leftIconTint, Color.TRANSPARENT);
        mRightIconTintColor = array.getColor(R.styleable.SimpleSettingItem_z_setting_rightIconTint, Color.TRANSPARENT);
        mRightIcon = array.getDrawable(R.styleable.SimpleSettingItem_z_setting_rightIcon);
        array.recycle();


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ZSettingItem);

        mArrowIconTintColor = array.getColor(R.styleable.ZSettingItem_z_setting_arrowIconTint, Color.LTGRAY);
        mArrowIcon = array.getDrawable(R.styleable.ZSettingItem_z_setting_arrowIcon);


        mInfoText = a.getString(R.styleable.ZSettingItem_z_setting_infoText);
        if (!TextUtils.isEmpty(mInfoText)) {
            tvInfo.setVisibility(VISIBLE);
            tvInfo.setText(mInfoText);
        }

        mInfoTextSize = a.getDimensionPixelSize(R.styleable.ZSettingItem_z_setting_infoTextSize, Utils.dp2pxInt(context, 12));
        tvInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, mInfoTextSize);

        mInfoTextColor = a.getColor(R.styleable.ZSettingItem_z_setting_infoTextColor, Color.parseColor("#808080"));
        tvInfo.setTextColor(mInfoTextColor);

        showUnderLine = a.getBoolean(R.styleable.ZSettingItem_z_setting_showUnderLine, false);
        showRightText = a.getBoolean(R.styleable.ZSettingItem_z_setting_showRightText, false);
        mRightText = a.getString(R.styleable.ZSettingItem_z_setting_rightText);
        mRightTextSize = a.getDimensionPixelSize(R.styleable.ZSettingItem_z_setting_rightTextSize, Utils.dp2pxInt(context, 14));
        mRightTextColor = a.getColor(R.styleable.ZSettingItem_z_setting_rightTextColor, Color.parseColor("#808080"));
        showRightButton = a.getBoolean(R.styleable.ZSettingItem_z_setting_showRightBtn, false);
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
            ImageView ivLeft = (ImageView) viewStub.inflate();
            ivLeft.setImageDrawable(mLeftIcon);
            if (mLeftIconTintColor != Color.TRANSPARENT) {
                ivLeft.setColorFilter(mLeftIconTintColor);
            }
        }
    }

    @Override
    public void inflateRightContainer(ViewStub viewStub) {
        if (!showRightArrow) {
            return;
        }
        viewStub.setLayoutResource(R.layout.z_setting_right_container_arrow);
        viewStub.setInflatedId(R.id.iv_right_icon);
        ImageButton view = (ImageButton) viewStub.inflate();
//        if (mRightIcon != null) {
//            view.setImageDrawable(mRightIcon);
//        }
//        view.setTint(ColorStateList.valueOf(mRightIconTintColor));
        if (mArrowIcon != null) {
            view.setImageDrawable(mArrowIcon);
        }
        if (mArrowIconTintColor != Color.TRANSPARENT) {
            view.setColorFilter(mArrowIconTintColor);
        }
    }

    @Override
    public void inflateInfoButton(ViewStub viewStub) {
        if (showRightButton) {
            viewStub.setLayoutResource(R.layout.z_setting_info_btn);
            viewStub.setInflatedId(R.id.iv_info_btn);
            ImageView view = (ImageView) viewStub.inflate();
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRightButtonClickListener != null) {
                        onRightButtonClickListener.onClick(v);
                    }
                }
            });
            if (mRightIcon != null) {
                view.setImageDrawable(mRightIcon);
            }
            if (mRightIconTintColor != Color.TRANSPARENT) {
                view.setColorFilter(mRightIconTintColor);
            }
        }
    }

    @Override
    public void inflateRightText(ViewStub viewStub) {
        if (showRightText) {
            viewStub.setLayoutResource(R.layout.z_setting_right_text);
            viewStub.setInflatedId(R.id.tv_right_text);
            tvRight = (TextView) viewStub.inflate();
            tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);
            tvRight.setTextColor(mRightTextColor);
            if (mRightText != null) {
                tvRight.setText(mRightText);
            }
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            tvTitle.setTextColor(mTitleTextColor);
            tvInfo.setTextColor(mInfoTextColor);
            if (tvRight != null) {
                tvRight.setTextColor(mRightTextColor);
            }
        } else {
            tvTitle.setTextColor(Color.LTGRAY);
            tvInfo.setTextColor(Color.LTGRAY);
            if (tvRight != null) {
                tvRight.setTextColor(Color.LTGRAY);
            }
        }
        int color = enabled ? mLeftIconTintColor : Color.LTGRAY;
        if (mLeftIcon != null) {
//            mLeftIcon = tintDrawable(mLeftIcon, color);
            if (inflatedLeftIcon instanceof ImageView) {
                ImageView imageView = ((ImageView) inflatedLeftIcon);
                imageView.setImageDrawable(mLeftIcon);
                imageView.setColorFilter(color);
            }
        }
        if (inflatedInfoButton instanceof ImageView) {
            ImageView imageView = ((ImageView) inflatedInfoButton);
            if (mRightIcon != null) {
                imageView.setImageResource(R.drawable.ic_info_black_24dp);
            }
            if (enabled && mRightIconTintColor == Color.TRANSPARENT) {
                imageView.clearColorFilter();
            } else {
                imageView.setColorFilter(enabled ? mRightIconTintColor : Color.LTGRAY);
            }
        }
        if (inflatedRightText instanceof TextView) {
            ((TextView) inflatedRightText).setTextColor(enabled ? mRightTextColor : Color.LTGRAY);
        }
        if (inflatedRightContainer instanceof ImageButton) {
            ImageButton btnRight = ((ImageButton) inflatedRightContainer);
//            btnRight.setTint(ColorStateList.valueOf(enabled ? mRightIconTintColor : Color.LTGRAY));
            if (enabled && mArrowIconTintColor == Color.TRANSPARENT) {
                btnRight.clearColorFilter();
            } else {
                btnRight.setColorFilter(enabled ? mArrowIconTintColor : Color.LTGRAY);
            }
        }
    }

    public TextView getTitleTextView() {
        return tvTitle;
    }

    public void setTitleText(String mTitleText) {
        this.mTitleText = mTitleText;
        tvTitle.setText(mTitleText);
    }

    public void setTitleTextSize(float size) {
        this.mTitleTextSize = size;
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setTitleTextColor(int color) {
        this.mTitleTextColor = color;
        tvTitle.setTextColor(color);
    }

    public void setTitleTextMaxLine(int line) {
        tvTitle.setMaxLines(line);
    }

    public String getTitleText() {
        return mTitleText;
    }

    public TextView getInfoTextView() {
        return tvInfo;
    }

    public void setInfoText(String mInfoText) {
        this.mInfoText = mInfoText;
        tvInfo.setText(mInfoText);
    }

    public String getInfoText() {
        return mInfoText;
    }

    public void setInfoTextSize(float size) {
        this.mInfoTextSize = size;
        tvInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setInfoTextColor(int color) {
        this.mInfoTextColor = color;
        tvInfo.setTextColor(color);
    }

    public void setInfoTextMaxLine(int line) {
        tvInfo.setMaxLines(line);
    }

    public TextView getRightTextView() {
        return tvRight;
    }

    public void setRightText(String text) {
        this.mRightText = text;
        tvRight.setText(text);
    }

    public String getRightText() {
        return mRightText;
    }

    public void setRightTextSize(float size) {
        this.mRightTextSize = size;
        tvRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setRightTextColor(int color) {
        this.mRightTextColor = color;
        tvRight.setTextColor(color);
    }

    public void setRightTextMaxLine(int line) {
        tvRight.setMaxLines(line);
    }

    public void setOnRightButtonClickListener(OnClickListener onRightButtonClickListener) {
        this.onRightButtonClickListener = onRightButtonClickListener;
    }

//    public void setRightText(String mRightText) {
//
//        if (inflatedRightText instanceof TextView) {
//            this.mRightText = mRightText;
//            ((TextView) inflatedRightText).setText(mRightText);
//        }
//    }

    public void setLeftIcon(Drawable mLeftIcon) {
        this.mLeftIcon = mLeftIcon;
        if (inflatedLeftIcon == null) {
            inflateLeftIcon(vsLeftIcon);
        } else if (inflatedLeftIcon instanceof ImageView) {
            ((ImageView) inflatedLeftIcon).setImageDrawable(mLeftIcon);
        }
    }

    public void setRightIcon(Drawable mRightIcon) {
//        this.mRightIcon = mRightIcon;
//        if (inflatedRightContainer == null) {
//            inflateRightContainer(vsRightContainer);
//        } else if (inflatedRightContainer instanceof ImageView) {
//            ((ImageView) inflatedRightContainer).setImageDrawable(mRightIcon);
//        }
        this.mRightIcon = mRightIcon;
        if (inflatedInfoButton instanceof ImageView) {
            ((ImageView) inflatedRightContainer).setImageDrawable(mRightIcon);
        } else {
            inflateInfoButton(vsInfoButton);
        }
    }

    public void setArrowIcon(Drawable arrowIcon) {
        this.mArrowIcon = arrowIcon;
        if (inflatedRightContainer instanceof ImageView) {
            ((ImageView) inflatedRightContainer).setImageDrawable(arrowIcon);
        } else {
            inflateRightContainer(vsRightContainer);
        }
    }

}

