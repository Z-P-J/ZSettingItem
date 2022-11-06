package com.zpj.widget.setting;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;


/**
 * @author Z-P-J
 */
public class SimpleSettingItem extends AppCompatTextView {

    protected String mTitleText;
    protected float mTitleTextSize;
    protected int mTitleTextColor;

    protected Drawable mLeftIcon;
    protected Drawable mRightIcon;

    protected int mLeftIconTintColor;
    protected int mRightIconTintColor;

    public SimpleSettingItem(Context context) {
        this(context, null);
    }

    public SimpleSettingItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleSettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (getMinimumHeight() == 0) {
            setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.z_setting_item_min_height));
        }
//        if (getPaddingStart() == 0 || getPaddingEnd() == 0) {
//            int padding = getResources().getDimensionPixelSize(R.dimen.z_setting_item_default_padding);
//            setPadding(padding, getPaddingTop(), padding, getPaddingBottom());
//        }
        int paddingH = getPaddingStart();
        int paddingV = getPaddingTop();
        int padding = getResources().getDimensionPixelSize(R.dimen.z_setting_item_default_padding);
        if (getPaddingStart() == 0 || getPaddingEnd() == 0) {
            paddingH = padding;
//            setPadding(padding, getPaddingTop(), padding, getPaddingBottom());
        }
        if (getPaddingTop() == 0 || getPaddingBottom() == 0) {
            paddingV = padding / 2;
        }
        setPadding(paddingH, paddingV, paddingH, paddingV);

        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
//        getPaint().setFakeBoldText(true);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SimpleSettingItem);
        mTitleText = array.getString(R.styleable.SimpleSettingItem_z_setting_titleText);
        if (TextUtils.isEmpty(mTitleText)) {
            mTitleText = "Title";
        }
        setText(mTitleText);

        mTitleTextSize = array.getDimension(R.styleable.SimpleSettingItem_z_setting_titleTextSize, 14);

        setTextSize(mTitleTextSize);

        mTitleTextColor = array.getColor(R.styleable.SimpleSettingItem_z_setting_titleTextColor, Color.parseColor("#222222"));

        setTextColor(mTitleTextColor);

        Drawable background = array.getDrawable(R.styleable.SimpleSettingItem_z_setting_background);

        mLeftIcon = array.getDrawable(R.styleable.SimpleSettingItem_z_setting_leftIcon);
        mRightIcon = array.getDrawable(R.styleable.SimpleSettingItem_z_setting_rightIcon);

        mLeftIconTintColor = array.getColor(R.styleable.SimpleSettingItem_z_setting_leftIconTint, Color.TRANSPARENT);
        mRightIconTintColor = array.getColor(R.styleable.SimpleSettingItem_z_setting_rightIconTint, Color.LTGRAY);

        array.recycle();

        if (mRightIcon == null) {
            mRightIcon = getResources().getDrawable(R.drawable.ic_enter_bak);
        }

        mLeftIcon = tintDrawable(mLeftIcon, mLeftIconTintColor);
        mRightIcon = tintDrawable(mRightIcon, mRightIconTintColor);

        setCompoundDrawables(mLeftIcon, null, mRightIcon, null);
        setCompoundDrawablePadding(getPaddingStart());

        setGravity(Gravity.CENTER_VERTICAL);

        if (background == null) {
            TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackground});
            background = typedArray.getDrawable(0);
            typedArray.recycle();
//            TypedValue typedValue = new TypedValue();
//            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
//            int[] attribute = new int[]{android.R.attr.selectableItemBackground};
//            TypedArray typedArray = context.getTheme().obtainStyledAttributes(typedValue.resourceId, attribute);
//            background = typedArray.getDrawable(0);
//            typedArray.recycle();
        }

        setBackground(background);

    }

    public void setLeftIcon(Drawable mLeftIcon) {
        this.mLeftIcon = tintDrawable(mLeftIcon, mLeftIconTintColor);
        setCompoundDrawables(this.mLeftIcon, null, mRightIcon, null);
    }

    public void setRightIcon(Drawable mRightIcon) {
        this.mRightIcon = tintDrawable(mRightIcon, mRightIconTintColor);
        setCompoundDrawables(mLeftIcon, null, this.mRightIcon, null);
    }

    private Drawable tintDrawable(Drawable drawable, int color) {
        if (drawable != null) {
            int size = Utils.dp2pxInt(getContext(), 24);
            drawable.setBounds(0, 0, size, size);
            final Drawable wrappedDrawable = DrawableCompat.wrap(drawable.mutate());
            if (color != Color.TRANSPARENT) {
                DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color));
            }
            return wrappedDrawable;
        }
        return null;
    }


}

