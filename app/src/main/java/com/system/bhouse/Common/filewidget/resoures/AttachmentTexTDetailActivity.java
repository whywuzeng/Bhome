package com.system.bhouse.Common.filewidget.resoures;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.system.bhouse.Common.Global;
import com.system.bhouse.bhouse.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-11-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.Common.filewidget.resoures
 */
public class AttachmentTexTDetailActivity extends AttachmentBaseDetailActivity {

    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.layout_dynamic_history)
    ConstraintLayout layoutDynamicHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textdetail_layout);
        ButterKnife.bind(this);
        if (isFilePositive()) {
            textView.setText(Global.readTextFile(mFile));
            layoutDynamicHistory.setVisibility(View.GONE);
        }

    }
}
