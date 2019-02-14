package com.system.bhouse.Common.filewidget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-11-09.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.Common.filewidget
 */
public class FileListHeadItem extends FrameLayout {

    @BindView(R.id.iv_file_reload)
    ImageView ivFileReload;
    @BindView(R.id.iv_download_canceld)
    ImageView ivDownloadCanceld;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_floder_name)
    TextView tvFloderName;

    public FileListHeadItem(@NonNull Context context) {
        this(context, null);
    }

    public FileListHeadItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FileListHeadItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.project_attachment_recycle_head, this);
        progressBar=findViewById(R.id.progressBar);
        ivFileReload=findViewById(R.id.iv_file_reload);
        ivDownloadCanceld=findViewById(R.id.iv_download_canceld);
        tvFloderName=findViewById(R.id.tv_floder_name);
        progressBar.setVisibility(GONE);
    }

    public void setData(Param data){
        String fileName = data.file.getName();
        tvFloderName.setText(fileName);

    }


    @OnClick(R.id.iv_file_reload)
    public void onIvFileReloadClicked() {
        ivFileReload.setVisibility(GONE);
        //上传的借口

    }

    @OnClick(R.id.iv_download_canceld)
    public void onIvDownloadCanceldClicked() {
        //取消上传
        ViewGroup parent = (ViewGroup) getParent();
        if (parent!=null)
        {
            parent.removeView(FileListHeadItem.this);
        }
    }

    public static class Param{
        public String dir;
        public String url;
        public File file;

        public Param(String dir, String url, File file) {
            this.dir = dir;
            this.url = url;
            this.file = file;
        }
    }
}
