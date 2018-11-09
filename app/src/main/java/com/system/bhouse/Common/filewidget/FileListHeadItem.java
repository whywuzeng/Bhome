package com.system.bhouse.Common.filewidget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2018-11-09.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.Common.filewidget
 */
public class FileListHeadItem extends FrameLayout {

    public FileListHeadItem(@NonNull Context context) {
        this(context,null);
    }

    public FileListHeadItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FileListHeadItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        inflate(context,R.layout.project_attachment_recycle_head,null);
    }


}
