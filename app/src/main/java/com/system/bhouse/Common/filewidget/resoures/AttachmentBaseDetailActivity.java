package com.system.bhouse.Common.filewidget.resoures;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.system.bhouse.Common.filewidget.databean.AttachmentFileObject;
import com.system.bhouse.Common.filewidget.save.FileSaveHelp;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.bhouse.setup.utils.FileUtil;

import java.io.File;

/**
 * Created by Administrator on 2018-11-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.Common.filewidget.resoures
 */
public class AttachmentBaseDetailActivity extends WWBackActivity {

    public static final String ARG_ATTACHMENTOBJECT = "arg_attachmentobject";

    protected AttachmentFileObject fileObject;
    protected File mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        mFile= getFileName();
    }

    private void getData() {
        final Intent intent = getIntent();
        if (intent!=null)
        {
            fileObject= (AttachmentFileObject) intent.getSerializableExtra(ARG_ATTACHMENTOBJECT);
        }
    }

    protected File getFileName(){
        final String downPath = FileSaveHelp.getSettingDownPath(this);
        if (fileObject!=null)
        {
           return  FileUtil.getDestinationInExternalPublicDir(downPath,fileObject.getName());
        }
        return null;
    }

    protected boolean isFilePositive(){
        if (mFile!=null&&mFile.exists()&&mFile.isFile())
        {
            // 根据文件名字 正确存在
            return true;
        }
        return false;
    }

    //调用时机 是否正确
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mFile!=null)
        {
            super.onCreateOptionsMenu(menu);
        }
        getMenuInflater().inflate(getMenuRes(),menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected int getMenuRes() {
        return R.menu.project_attachment_base;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        switch (itemId)
        {
            case R.id.action_info:

                break;
            case R.id.action_download:

                break;
            case R.id.action_copy:

                break;
            case R.id.action_delete:

                break;
            default:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
