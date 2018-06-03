package com.system.bhouse.bhouse.CompanyNews;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.system.bhouse.api.manager.RetrofitManager;
import com.system.bhouse.api.manager.service.HostType;
import com.system.bhouse.base.BaseSubscriber;
import com.system.bhouse.base.RequestCallBack;
import com.system.bhouse.bean.NeteastNewsDetail;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.ui.ThreePointLoadingView;
import com.system.bhouse.utils.MeasureUtil;
import com.system.bhouse.utils.TenUtils.GlideUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.Map;

import rx.Subscription;
import rx.functions.Func1;
import zhou.widget.RichText;

/**
 * Created by Administrator on 2018-04-02.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CompanyNews
 */
@EActivity(R.layout.activity_company_news_detail)
public class CompanyNewsDetailActivity extends WWBackActivity implements RequestCallBack<NeteastNewsDetail>,View.OnClickListener {

    private ThreePointLoadingView mLoadingView;
    private ImageView mNewsImageView;
    private TextView mTitleTv;
    private TextView mFromTv;
    private RichText mBodyTv;

    private FloatingActionButton mFab;

    private String mNewsListSrc;
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            // 设置全屏，并且不会Activity的布局让出状态栏的空间
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            showStatusBar(this);
        }

        getWindow().setBackgroundDrawable(null);

        super.onCreate(savedInstanceState);
    }

    // 显示状态栏
    public static void showStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
    }

    @AfterViews
    protected void initView() {

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            // 4.4设置全屏并动态修改Toolbar的位置实现类5.0效果，确保布局延伸到状态栏的效果
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_com);
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
            mlp.topMargin = MeasureUtil.getStatusBarHeight(this);
        }

        final CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//        setActionBarTitle(R.string.news_detail);
        toolbarLayout.setTitle(getString(R.string.news_detail));
        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.accent));
        toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white));

        mNewsImageView = (ImageView) findViewById(R.id.iv_news_detail_photo);

        mLoadingView = (ThreePointLoadingView) findViewById(R.id.tpl_view);
        mLoadingView.setOnClickListener(this);

        mTitleTv = (TextView) findViewById(R.id.tv_news_detail_title);

        mFromTv = (TextView) findViewById(R.id.tv_news_detail_from);

        mBodyTv = (RichText) findViewById(R.id.tv_news_detail_body);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
//        mFab.setOnClickListener(this);

        mNewsListSrc = getIntent().getStringExtra("imgsrc");

        mSubscription  = requestNewsDetail(this, getIntent().getStringExtra("postid"));

    }

    public Subscription requestNewsDetail(final RequestCallBack<NeteastNewsDetail> callback, final String id) {
        return RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO).getNewsDetailObservable(id)
                .map(new Func1<Map<String, NeteastNewsDetail>, NeteastNewsDetail>() {
                    @Override
                    public NeteastNewsDetail call(Map<String, NeteastNewsDetail> map) {
                        return map.get(id);
                    }
                }).subscribe(new BaseSubscriber<>(callback));
    }


    public void initNewsDetail(final NeteastNewsDetail data) {

        if (data.video != null && data.video.size() > 0) {
            final NeteastNewsDetail.VideoEntity video = data.video.get(0);
            final String mp4HdUrl = video.mp4HdUrl;
            final String mp4Url = video.mp4Url;
            if (!TextUtils.isEmpty(mp4HdUrl)) {
                mFab.setImageResource(R.drawable.ic_play_normal);
                mFab.setTag(mp4HdUrl);
            }
            else if (!TextUtils.isEmpty(mp4Url)) {
                mFab.setImageResource(R.drawable.ic_play_normal);
                mFab.setTag(mp4Url);
            }

        }

        if (data.img != null && data.img.size() > 0) {
            // 设置tag用于点击跳转浏览图片列表的时候判断是否有图片可供浏览
            mNewsImageView.setTag(R.id.img_tag, true);
            // 显示第一张图片，通过pixel字符串分割得到图片的分辨率
            String[] pixel = null;
            if (!TextUtils.isEmpty(data.img.get(0).pixel)) {
                // pixel可能为空
                pixel = data.img.get(0).pixel.split("\\*");
            }
            // 图片高清显示，按屏幕宽度为准缩放
            if (pixel != null && pixel.length == 2) {

                // KLog.e(pixel[0] + ";" + pixel[1]);

                final int w = MeasureUtil.getScreenSize(this).x;
                final int h = Integer.parseInt(pixel[1]) * w / Integer.parseInt(pixel[0]);

                // KLog.e(w + ";" + h);

                if (data.img.get(0).src.contains(".gif")) {
                    mNewsImageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            GlideUtils.loadDefaultOverrideNoAnim(data.img.get(0).src, mNewsImageView, w, h, true, null, DiskCacheStrategy.SOURCE);
                        }
                    }, 500);
                }
                else {
                    GlideUtils.loadDefaultOverrideNoAnim(data.img.get(0).src, mNewsImageView, w, h, false, DecodeFormat.PREFER_ARGB_8888, DiskCacheStrategy.ALL);
                }
                } else{
                    GlideUtils.loadDefaultNoAnim(data.img.get(0).src, mNewsImageView, false, DecodeFormat.PREFER_ARGB_8888, DiskCacheStrategy.ALL);
                }

                if (mFab.getTag() == null) {
                    // 以下将数据封装成新浪需要的格式，用于点击跳转到图片浏览
//                    mSinaPhotoDetail = new SinaPhotoDetail();
//                    mSinaPhotoDetail.data = new SinaPhotoDetail.SinaPhotoDetailDataEntity();
//                    mSinaPhotoDetail.data.title = data.title;
//                    mSinaPhotoDetail.data.content = data.digest;
//                    mSinaPhotoDetail.data.pics = new ArrayList<>();
//                    for (NeteastNewsDetail.ImgEntity entiity : data.img) {
//                        SinaPhotoDetail.SinaPhotoDetailPicsEntity sinaPicsEntity = new SinaPhotoDetail.SinaPhotoDetailPicsEntity();
//                        sinaPicsEntity.pic = entiity.src;
//                        sinaPicsEntity.alt = entiity.alt;
//                        sinaPicsEntity.kpic = entiity.src;
//                        if (pixel != null && pixel.length == 2) {
//                            // 新浪分辨率是按100x100这种形式的
//                            sinaPicsEntity.size = pixel[0] + "x" + pixel[1];
//                        }
//                        mSinaPhotoDetail.data.pics.add(sinaPicsEntity);
                    }

            }
            else {
                // 图片详情列表没有数据的时候，取图片列表页面传送过来的图片显示
                mNewsImageView.setTag(R.id.img_tag, false);
                GlideUtils.loadDefault(mNewsListSrc, mNewsImageView, false, DecodeFormat.PREFER_ARGB_8888, DiskCacheStrategy.ALL);
            }

            mTitleTv.setText(data.title);
            // 设置新闻来源和发布时间
            mFromTv.setText(getString(R.string.from, data.source, data.ptime));
            // 新闻内容可能为空
            if (!TextUtils.isEmpty(data.body)) {
                mBodyTv.setRichText(data.body);
            }

        }


    public void showProgress() {
        mLoadingView.play();
    }

    public void hideProgress() {
        mLoadingView.stop();
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.fab) {
//            if (mFab.getTag() != null && mFab.getTag() instanceof String) {
//                Intent intent = new Intent(this, VideoPlayActivity.class);
//                intent.putExtra("videoUrl", (String) mFab.getTag());
//                intent.putExtra("videoName", mTitleTv.getText().toString());
//                ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
//                ActivityCompat.startActivity(this, intent, options.toBundle());
//            }
//            else {
//                if (mNewsImageView.getTag(R.id.img_tag) != null && !(boolean) mNewsImageView.getTag(R.id.img_tag) || mSinaPhotoDetail == null) {
//                    toast("没有图片供浏览哎o(╥﹏╥)o");
//                }
//                else {
//                    Intent intent = new Intent(this, PhotoDetailActivity.class);
//                    intent.putExtra("neteast", mSinaPhotoDetail);
//                    ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
//                    ActivityCompat.startActivity(this, intent, options.toBundle());
//                }
//            }
//        }
    }

    @Override
    public void beforeRequest() {
        showProgress();
    }

    @Override
    public void requestError(String msg) {
        hideProgress();
    }

    @Override
    public void requestComplete() {
        hideProgress();
    }

    @Override
    public void requestSuccess(NeteastNewsDetail data) {
        initNewsDetail(data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}


