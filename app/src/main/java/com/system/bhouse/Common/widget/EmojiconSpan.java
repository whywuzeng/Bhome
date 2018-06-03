package com.system.bhouse.Common.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

import com.system.bhouse.Common.DrawableTool;
import com.system.bhouse.Common.MyImageGetter;
import com.system.bhouse.bhouse.R;



/**
 * Created by chaochen on 14-11-12.
 */
class EmojiconSpan extends DynamicDrawableSpan {
    private final Context mContext;
    private final int mResourceId;
    private Drawable mDrawable;
    private boolean mIsMonkey;

    public EmojiconSpan(Context context, String iconName) {
        super();
        mContext = context;

        String name =null;
//                EmojiFragment.textToMonkdyMap.get(iconName);
        if (name == null) {
            name = iconName;
            mIsMonkey = false;
        } else {
            mIsMonkey = true;
        }

        mResourceId = MyImageGetter.getResourceId(name);
    }

    @Override
    public Drawable getDrawable() {
        if (mDrawable == null) {
            try {
                mDrawable = mContext.getResources().getDrawable(mResourceId);
                DrawableTool.zoomDrwable(mDrawable, mIsMonkey);
            } catch (Exception e) {
            }
        }
        return mDrawable;
    }

    public boolean isDefault() {
        return mResourceId == R.mipmap.ic_launcher;
    }
}