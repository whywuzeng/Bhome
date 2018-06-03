package com.system.bhouse.bhouse.ImageSelect;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.socks.library.KLog;
import com.system.bhouse.base.BaseActivity;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.AppManager;
import com.system.bhouse.utils.FileSizeUtil;
import com.system.bhouse.utils.PicUtils;
import com.system.bhouse.utils.ProgressUtils;
import com.system.bhouse.utils.UploadFileTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.lijunguan.imgselector.ImageSelector;

import static com.system.bhouse.base.App.Filesize;
import static com.system.bhouse.base.App.MobileKey;
import static com.system.bhouse.base.App.USER_INFO;

/**
 * Created by Administrator on 2016-5-6.
 * ClassName: com.system.bhouse.textapplication.module.imageSelect.ui
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */

public class ImageSelectActivity extends BaseActivity {
    @Bind(R.id.topfanhuititle_textview_zhende)
    TextView topfanhuititleTextviewZhende;
    @Bind(R.id.fanhui_lin)
    LinearLayout llLin;
    private Toolbar toolbar;
    private Button btnimageSelect;
    private Button btnimageSelecttouxiang;
    private boolean isAvatorModel;
    private RecyclerView mRecyclerView;
    private SelectedImgAdapter mAdapter;
    private RelativeLayout mRlAvator;
    private ImageView mIvAvator;
    private Button btn_imageSelectshangchuan;
    private RoundedBitmapDrawable circularBitmapDrawable;
    private List<String> imagePath;

    private String ccid;
    private String statusid;
    private String ccNumber;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_imageselect;
    }

    @Override
    protected String getToolbarTitle() {
        return "选择图片";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        initview();
        initimageSelect();
        topfanhuititleTextviewZhende.setText("选择图片");
    }

    public void initimageSelect() {
        btnimageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getimage(true);
            }
        });

        btnimageSelecttouxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getimage(false);
            }
        });


        btn_imageSelectshangchuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //从选择图片获得链接
                if (imagePath != null) {

                    if (USER_INFO != null) {
                        List<String> params = new ArrayList<>();
                        params.add(ccid);
                        params.add(statusid);
                        params.add(MobileKey);
                        params.add(USER_INFO);
                        if (mAdapter.getInflate() != null) {
                            if (TextUtils.isEmpty(((EditText)mAdapter.getInflate().findViewById(R.id.et_file_name)).getText().toString())) {
                                Toast.makeText(ImageSelectActivity.this, R.string.tupianisnull, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else {
                                //加入名字到参数里
                                params.add(((EditText)mAdapter.getInflate().findViewById(R.id.et_file_name)).getText().toString());
                            }
                        }
                        //判断图片的尺寸
                        if(!(0>= Filesize)) {
                            if(FileSizeUtil.getFileOrFilesSize(imagePath.get(0), 1) > Filesize){

                            }
                        }

                        UploadFileTask uploadFileTask = new UploadFileTask(ImageSelectActivity.this);
                        List<String> list_newpath=new ArrayList<String>();

//                            ImageView viewById = (ImageView) mAdapter.getInflate().findViewById(R.id.iv_selectimage);
//                            Drawable drawable = viewById.getDrawable();
//                            Bitmap bitmap = ViewUtil.drawableToBitmap(drawable);
//                            String sdCardPath="";
//                            if(SDCardUtils.isSDCardEnable()){
//                                 sdCardPath = SDCardUtils.getSDCardPath();
//                            }
//                             sdCardPath= sdCardPath+"/Bhouse/";
//
//                            list_newpath.add(sdCardPath + params.get(4)+".png");
//                            File dirFile = new File(sdCardPath);
//                            if(!dirFile.exists()){
//                                dirFile.mkdir();
//                            }
//                            try {
//                                File myCaptureFile = new File(sdCardPath + params.get(4)+".png");
//                                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
//                                bitmap.compress(Bitmap.CompressFormat.PNG, 80, bos);
//                                bos.flush();
//                                bos.close();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }

                        File file = new File(imagePath.get(0));

//                      PicUtils.setPicsmall(drawable, list_newpath, params.get(4));
                        PicUtils.setPicsmallFile(file,list_newpath, params.get(4));
                        //激活访问网络的线程
                        uploadFileTask.execute(list_newpath, params);
                        uploadFileTask.setPutStringListener(new UploadFileTask.PutStringListener() {
                            @Override
                            public void putStringFormate(String s) {
                                Toast.makeText(ImageSelectActivity.this, s, Toast.LENGTH_LONG).show();
                                ProgressUtils.DisMissProgress();
                                try {
                                    AppManager.getAppManager().finishActivity(ImageSelectActivity.this);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    if (!ImageSelectActivity.this.isFinishing()) {
                                        ImageSelectActivity.this.finish();
                                    }
                                }

                            }
                        });
                    }
                    else {
                        Toast.makeText(ImageSelectActivity.this, "参数失效,重新登录", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    KLog.e("circularBitmapDrawable 为空");
                    Toast.makeText(ImageSelectActivity.this, R.string.pleaseselect, Toast.LENGTH_SHORT).show();
                }
            }
        });

        llLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AppManager.getAppManager().finishActivity(ImageSelectActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (!ImageSelectActivity.this.isFinishing()) {
                        ImageSelectActivity.this.finish();
                    }
                }
            }
        });

    }

    public void getimage(boolean ismulti) {
        ImageSelector imageSelector = ImageSelector.getInstance();
        loadConfig(imageSelector, ismulti).startSelect(this);


    }

    private ImageSelector loadConfig(ImageSelector imageSelector, boolean ismulti) {
        if (ismulti) {
            imageSelector.setSelectModel(ImageSelector.MULTI_MODE);
            isAvatorModel = false;
        }
        else {
            imageSelector.setSelectModel(ImageSelector.AVATOR_MODE);
            isAvatorModel = true;
        }
        imageSelector.setToolbarColor(ContextCompat.getColor(this, R.color.material_blue_a700));

        imageSelector.setShowCamera(true);

        imageSelector.setMaxCount(Integer.parseInt(1 + ""));
        imageSelector.setGridColumns(Integer.parseInt(3 + ""));
        return imageSelector;
    }

    public void initview() {
        initialize();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ccNumber = getIntent().getStringExtra("ccNumber");
        mAdapter = new SelectedImgAdapter(Glide.with(this), this, ccNumber);
        mRecyclerView.setAdapter(mAdapter);

        ccid = getIntent().getStringExtra("ccid");
        statusid = getIntent().getStringExtra("statusid");
    }

    private void initialize() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnimageSelect = (Button) findViewById(R.id.btn_imageSelect);
        btnimageSelecttouxiang = (Button) findViewById(R.id.btn_imageSelecttouxiang);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRlAvator = (RelativeLayout) findViewById(R.id.rl_avator);
        mIvAvator = (ImageView) findViewById(R.id.iv_avator);
        btn_imageSelectshangchuan = (Button) findViewById(R.id.btn_imageSelectshangchuan);
    }


    /**
     * 得到选择的图片路径集合
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.REQUEST_SELECT_IMAGE) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> imagesPath = data.getStringArrayListExtra(ImageSelector.SELECTED_RESULT);
                if (isAvatorModel && imagesPath != null) {
                    mRlAvator.setVisibility(View.VISIBLE);
                    Glide.with(this)
                            .load(imagesPath.get(0))
                            .asBitmap()
                            .into(new BitmapImageViewTarget(mIvAvator) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    circularBitmapDrawable =
                                            RoundedBitmapDrawableFactory.create(getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
//                                    circularBitmapDrawable.setCornerRadius(100);
                                    mIvAvator.setImageDrawable(circularBitmapDrawable);

//                                    RxBus.get().post("imagetouxiang",circularBitmapDrawable);

                                }
                            });

                }
                else if (imagesPath != null) {
                    mRlAvator.setVisibility(View.GONE);
                    mAdapter.refreshData(imagesPath);
                }
                imagePath = new ArrayList<>();
                imagePath.addAll(imagesPath);
            }
        }
    }

    public void onClickimage(View v) {
        /**
         * 这里会出问题
         */
        try {
            AppManager.getAppManager().finishActivity(this);
        } catch (Exception e) {
            e.printStackTrace();
            if (!this.isFinishing()) {
                this.finish();
            }
        }
    }

    static class SelectedImgAdapter extends RecyclerView.Adapter<SelectedImgAdapter.ImgViewHolder> {

        List<String> mData = new ArrayList<>();

        private RequestManager mRequestManager;
        private Context context;
        private String ccNumber;
        private  Bitmap resource;

        public Bitmap getResource() {
            return resource;
        }

        public void setResource(Bitmap resource) {
            this.resource = resource;
        }

        public RelativeLayout getInflate() {
            return inflate;
        }

        public void setInflate(RelativeLayout inflate) {
            this.inflate = inflate;
        }

        private  RelativeLayout inflate;

        public SelectedImgAdapter(RequestManager requestManager, Context context, String ccNumber) {
            this.mRequestManager = requestManager;
            this.context = context;
            this.ccNumber = ccNumber;
        }

        @SuppressWarnings("ReturnOfInnerClass")
        @Override
        public ImgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            ImageView imageView = new ImageView(parent.getContext());
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setLayoutParams(new GridLayoutManager.LayoutParams(parent.getWidth() / 3, parent.getWidth() / 3));
//            return new ImgViewHolder(imageView);

            RelativeLayout inflate = (RelativeLayout) View.inflate(this.context, R.layout.imageselect_item, null);
            setInflate(inflate);
            return new ImgViewHolder(inflate);
        }



        @Override
        public void onBindViewHolder(ImgViewHolder holder, int position) {
            mRequestManager
                    .load(mData.get(position))
                    .into(holder.iv_selectimage);

//            Drawable drawable = holder.iv_selectimage.getDrawable();
//            Bitmap bitmap = ViewUtil.drawableToBitmap(drawable);
//            setResource(bitmap);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new java.util.Date());
            holder.et_file_name.setHint(ccNumber + "-" + date);
            holder.et_file_name.setText(ccNumber + "-" + date);
        }


        @Override
        public int getItemCount() {
            return mData.size();
        }

        static class ImgViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.et_file_name)
            EditText et_file_name;
            @Bind(R.id.iv_selectimage)
             ImageView iv_selectimage;

            public ImgViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        public void refreshData(List<String> data) {
            mData = data;
            notifyDataSetChanged();
        }

    }

}

