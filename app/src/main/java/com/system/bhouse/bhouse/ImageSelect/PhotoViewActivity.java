package com.system.bhouse.bhouse.ImageSelect;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.system.bhouse.base.BaseActivity;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.AppManager;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2016-5-23.
 * ClassName: com.system.bhouse.bhouse.ImageSelect
 * Author:Administrator
 * Fuction: 显示单张图片的activity
 * UpdateUser:
 * UpdateDate:
 */
public class PhotoViewActivity extends BaseActivity {

    @BindView(R.id.topfanhuititle_textview_zhende)
    TextView topfanhuititleTextviewZhende;
    @BindView(R.id.photoview)
    PhotoView photoview;
    @BindView(R.id.fanhui_lin)
    LinearLayout fanhuiLin;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_photoview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
//        getIntent().getStringExtra("");

        String pic_url = this.getIntent().getStringExtra("pic_url.get(i).Url");

        if(Build.VERSION.SDK_INT>=23){

//            .setDecodeFormat(DecodeFormat.PREFER_ARGB_8888)

            Glide.with(this).load(pic_url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    photoview.setImageDrawable(resource);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                }
            });
        }else{
            Glide.with(this).load(pic_url).asBitmap().diskCacheStrategy(DiskCacheStrategy.RESULT).placeholder(R.mipmap.ic_loading_small_bg)
                    .error(R.mipmap.ic_fail).fitCenter().into(photoview);
        }

        topfanhuititleTextviewZhende.setText(R.string.chakanshangchuantupian);
    }

    @OnClick(R.id.fanhui_lin)
    public void onClick() {
        try {
            AppManager.getAppManager().finishActivity(this);
        } catch (Exception e) {
            e.printStackTrace();
            if (!this.isFinishing()) {
                this.finish();
            }
        }
    }




    public class LoggingListener<T, R> implements RequestListener<T, R> {
        @Override public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
            android.util.Log.d("GLIDE", String.format(Locale.ROOT,
                    "onException(%s, %s, %s, %s)", e, model, target, isFirstResource), e);
            return false;
        }
        @Override public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
            android.util.Log.d("GLIDE", String.format(Locale.ROOT,
                    "onResourceReady(%s, %s, %s, %s, %s)", resource, model, target, isFromMemoryCache, isFirstResource));
            return false;
        }
    }
}
