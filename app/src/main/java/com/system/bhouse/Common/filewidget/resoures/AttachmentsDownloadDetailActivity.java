package com.system.bhouse.Common.filewidget.resoures;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.system.bhouse.Common.Global;
import com.system.bhouse.Common.filewidget.BaseFileDownActivity;
import com.system.bhouse.Common.filewidget.databean.AttachmentFileObject;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.utils.FileUtil;
import com.system.bhouse.utils.blankutils.ToastUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-11-15.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.Common.filewidget.resoures
 */
public class AttachmentsDownloadDetailActivity extends BaseFileDownActivity {

    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.tv_download)
    TextView tvDownload;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;
    @Bind(R.id.iv_download_canceld)
    ImageView ivDownloadCanceld;
    @Bind(R.id.rl_downlaod)
    RelativeLayout rlDownlaod;
    @Bind(R.id.btn_download)
    TextView btnDownload;
    @Bind(R.id.btn_open)
    TextView btnOpen;
    @Bind(R.id.btnLeft)
    TextView btnLeft;
    @Bind(R.id.btnRight)
    TextView btnRight;
    @Bind(R.id.iconTxt)
    TextView iconTxt;
    @Bind(R.id.main_layout)
    LinearLayout mainLayout;
    @Bind(R.id.layout_dynamic_history)
    ConstraintLayout layoutDynamicHistory;
    private SharedPreferences share;
    public static final String ARG_ATTACHMENTOBJECT = "arg_attachmentobject";
    public static final String ARG_ATTACHFILEPATH = "arg_attachfilepath";
    private AttachmentFileObject attachFileObject;
    private String mFilePath;
    private SharedPreferences shareListDown;
    private SharedPreferences.Editor editList;
    String downloadFormat = "下载中...(%s/%s)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_detaillayout);
        ButterKnife.bind(this);
        getData();
        share = this.getSharedPreferences(FileUtil.DOWNLOAD_SETTING, MODE_PRIVATE);
        setActionBarMidlleTitle(attachFileObject.getName());
        shareListDown = this.getSharedPreferences(FileUtil.DOWNLOAD_LIST, MODE_PRIVATE);
        editList = shareListDown.edit();
        if (attachFileObject != null) {
            bindView();
        }
    }

    private void bindView() {
        icon.setVisibility(View.VISIBLE);
        icon.setImageResource(attachFileObject.getIconResourceId());
        iconTxt.setVisibility(View.GONE);
        name.setText(attachFileObject.getName());
        content.setText(Global.HumanReadableFilesize(attachFileObject.getSize()));
        tvDownload.setText(String.format(downloadFormat, Global.HumanReadableFilesize(0), Global.HumanReadableFilesize(attachFileObject.getSize())));
        progressbar.setMax(attachFileObject.getSize());

        mainLayout.setVisibility(View.VISIBLE);

        btnDownload.setVisibility(View.GONE);
        rlDownlaod.setVisibility(View.GONE);
        btnOpen.setVisibility(View.VISIBLE);
        layoutDynamicHistory.setVisibility(View.VISIBLE);

    }

    private void getData() {
        final Intent intent = this.getIntent();
        if (intent != null) {
            attachFileObject = (AttachmentFileObject) intent.getSerializableExtra(ARG_ATTACHMENTOBJECT);
            mFilePath = intent.getStringExtra(ARG_ATTACHFILEPATH);
        }
    }

    /**
     * 更新UI 取数据，的时候取到byte字节
     */
    @Override
    protected void checkFileDownloadStatus() {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    @OnClick(R.id.iv_download_canceld)
    public void onIvDownloadCanceldClicked() {
    }

    @OnClick(R.id.btn_download)
    public void onBtnDownloadClicked() {
    }

    @OnClick(R.id.btn_open)
    public void onBtnOpenClicked() {
         File file = new File(mFilePath);
        file= new File("/storage/emulated/0/storage/emulated/0/bhouseDownoad"+File.separator+"2018win7OA兼容模式调整.flv");
        if (file.exists()&&file.isFile())
        {
            openFile(file);
        }else {
            ToastUtils.showShort("无法打开文件");
        }
    }

    private void openFile(File file) {
        final Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);

        final Uri uri = Uri.fromFile(file);

        final MimeTypeMap mime = MimeTypeMap.getSingleton();
        final int index = file.getName().lastIndexOf('.') + 1;
        final String ext = file.getName().substring(index).toLowerCase();
        final String type = mime.getMimeTypeFromExtension(ext);

        intent.setDataAndType(uri,type);

        try {
            this.startActivity(intent);
            return;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            ToastUtils.showShort("无法找到打开这个文件的应用");
        }
    }

    @OnClick(R.id.btnLeft)
    public void onBtnLeftClicked() {
    }

    @OnClick(R.id.btnRight)
    public void onBtnRightClicked() {
    }
}
