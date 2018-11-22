package com.system.bhouse.Common.filewidget.resoures;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.MenuItem;
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

    private final static String GBK_CHARSET="gbk";
    private final static String UFT_CHARSET="utf-8";
    private final static int REQUESTCODE_TXT =0x545;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textdetail_layout);
        ButterKnife.bind(this);
        annotationDispayHomeAsUp();
        if (isFilePositive()) {
            textView.setText(Global.readTextFile(mFile,UFT_CHARSET));
            layoutDynamicHistory.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_info:
                final Intent intent = new Intent(this, TxtEditActivity.class);
                intent.putExtra(AttachmentBaseDetailActivity.ARG_ATTACHMENTOBJECT,fileObject);
                startActivityForResult(intent, REQUESTCODE_TXT);
                return true;

            case R.id.action_tranfor:
                textView.setText(Global.readTextFile(mFile,GBK_CHARSET));
                break;

            case R.id.action_copy:
                AttachmentsDownloadDetailActivity.openFile(AttachmentTexTDetailActivity.this,mFile);
                break;

                default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == REQUESTCODE_TXT)
            {
                textView.setText(Global.readTextFile(mFile,UFT_CHARSET));
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
