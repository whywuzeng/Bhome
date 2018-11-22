package com.system.bhouse.Common.filewidget.resoures;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.system.bhouse.Common.Global;
import com.system.bhouse.bhouse.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-11-21.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.Common.filewidget.resoures
 */
public class TxtEditActivity extends AttachmentBaseDetailActivity {

    @Bind(R.id.editText)
    EditText editText;

    private final static String GBK_CHARSET="gbk";
    private final static String UFT_CHARSET="utf-8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txtedit);
        ButterKnife.bind(this);
        annotationDispayHomeAsUp();
        if (isFilePositive()) {
            editText.setText(Global.readTextFile(mFile,GBK_CHARSET));
        }
    }

    @Override
    protected int getMenuRes() {
        return R.menu.menu_user_edit;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_edit:
                writeFile(mFile,editText.getText().toString());
                setResult(RESULT_OK);
                onBackPressed();
                break;
                default:

                    break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void writeFile(File file,String content){
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            final String contentByte = new String(content.getBytes(), UFT_CHARSET);
            fileOutputStream.write(contentByte.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
