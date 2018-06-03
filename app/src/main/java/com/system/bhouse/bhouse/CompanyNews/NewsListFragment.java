package com.system.bhouse.bhouse.CompanyNews;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.socks.library.KLog;
import com.system.bhouse.api.manager.RetrofitManager;
import com.system.bhouse.api.manager.service.Api;
import com.system.bhouse.api.manager.service.HostType;
import com.system.bhouse.base.BaseSpacesItemDecoration;
import com.system.bhouse.base.BaseSubscriber;
import com.system.bhouse.base.RequestCallBack;
import com.system.bhouse.bean.NeteastNewsSummary;
import com.system.bhouse.bhouse.CompanyNews.adapter.BaseRecyclerViewHolder;
import com.system.bhouse.bhouse.CompanyNews.adapter.NewsListBaseRecyclerAdapter;
import com.system.bhouse.bhouse.CompanyNews.adapter.OnEmptyClickListener;
import com.system.bhouse.bhouse.CompanyNews.adapter.OnItemClickAdapter;
import com.system.bhouse.bhouse.CompanyNews.adapter.OnLoadMoreListener;
import com.system.bhouse.bhouse.CompanyNews.common.DataLoadType;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.RefreshBaseFragment;
import com.system.bhouse.utils.ClickUtils;
import com.system.bhouse.utils.MeasureUtil;
import com.system.bhouse.utils.TenUtils.GlideUtils;
import com.system.bhouse.utils.TenUtils.T;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * ClassName: NewsListFragment<p>
 * Author: oubowu<p>
 * Fuction: 新闻列表界面<p>
 * CreateDate: 2016/2/17 20:50<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
@EFragment(R.layout.fragment_company_news_list)
public class NewsListFragment extends RefreshBaseFragment implements RequestCallBack<List<NeteastNewsSummary>> {
    protected int mPosition;

    protected static final String NEWS_ID = "news_id";
    protected static final String NEWS_TYPE = "news_type";
    protected static final String POSITION = "position";

    protected String mNewsId;
    protected String mNewsType;

    private NewsListBaseRecyclerAdapter<NeteastNewsSummary> mAdapter;

    @ViewById(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @ViewById(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refresh_layout;

    private boolean mIsRefresh;
    private int mStartPage;
    private Subscription mSubscription;
    private boolean mHasInit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNewsId = getArguments().getString(NEWS_ID);
            mNewsType = getArguments().getString(NEWS_TYPE);
            mPosition = getArguments().getInt(POSITION);
        }

    }

    public static NewsListFragment newInstance(String newsId, String newsType, int position) {
        NewsListFragment fragment = new NewsListFragment_();
        Bundle bundle = new Bundle();
        bundle.putString(NEWS_ID, newsId);
        bundle.putString(NEWS_TYPE, newsType);
        bundle.putInt(POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @AfterViews
    protected void initNewsListFragmentView() {

        initRefreshLayout();
        requestNewsList(this, mNewsType, mNewsId, mStartPage);
    }

    public Subscription requestNewsList(final RequestCallBack<List<NeteastNewsSummary>> callback, String type, final String id, int startPage) {
        KLog.e("新闻列表：" + type + ";" + id);
        return RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO)
                .getNewsListObservable(type, id, startPage)
                .flatMap(
                        new Func1<Map<String, List<NeteastNewsSummary>>, Observable<NeteastNewsSummary>>() {
                            // map得到list转换成item
                            @Override
                            public Observable<NeteastNewsSummary> call(Map<String, List<NeteastNewsSummary>> map) {
                                if (id.equals(Api.HOUSE_ID)) {
                                    // 房产实际上针对地区的它的id与返回key不同
                                    return Observable.from(map.get("北京"));
                                }
                                return Observable.from(map.get(id));
                            }
                        })
                .toSortedList(new Func2<NeteastNewsSummary, NeteastNewsSummary, Integer>() {
                    // 按时间先后排序
                    @Override
                    public Integer call(NeteastNewsSummary neteastNewsSummary, NeteastNewsSummary neteastNewsSummary2) {
                        return neteastNewsSummary2.ptime.compareTo(neteastNewsSummary.ptime);
                    }
                }).subscribe(new BaseSubscriber<>(callback));
    }


    public void beforeRequest() {
        if (!mHasInit) {
            mHasInit = true;

        }
    }

    public void requestError(String e) {
        refresh_layout.setRefreshing(false);
        updateNewsList(null, e, mIsRefresh ? DataLoadType.TYPE_REFRESH_FAIL : DataLoadType.TYPE_LOAD_MORE_FAIL);
    }

    public void requestSuccess(List<NeteastNewsSummary> data) {
        if (data != null) {
            mStartPage += 20;
        }
        updateNewsList(data, "", mIsRefresh ? DataLoadType.TYPE_REFRESH_SUCCESS : DataLoadType.TYPE_LOAD_MORE_SUCCESS);

    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    @Override
    public void requestComplete() {
        refresh_layout.setRefreshing(false);
    }

    public void refreshData() {
        mStartPage = 0;
        mIsRefresh = true;
        mSubscription = requestNewsList(this, mNewsType, mNewsId, mStartPage);
    }

    public void loadMoreData() {
        mIsRefresh = false;
        mSubscription = requestNewsList(this, mNewsType, mNewsId, mStartPage);
    }

    public void updateNewsList(final List<NeteastNewsSummary> data, String errorMsg, @DataLoadType.DataLoadTypeChecker int type) {

        if (mAdapter == null) {
            initNewsList(data);
        }

        mAdapter.showEmptyView(false, "");

        switch (type) {
            case DataLoadType.TYPE_REFRESH_SUCCESS:
                refresh_layout.setRefreshing(true);
                mAdapter.enableLoadMore(true);
                mAdapter.setData(data);
                break;
            case DataLoadType.TYPE_REFRESH_FAIL:
                refresh_layout.setRefreshing(true);
                mAdapter.enableLoadMore(false);
                mAdapter.showEmptyView(true, errorMsg);
                mAdapter.notifyDataSetChanged();
                break;
            case DataLoadType.TYPE_LOAD_MORE_SUCCESS:
                mAdapter.loadMoreSuccess();
                if (data == null || data.size() == 0) {
                    mAdapter.enableLoadMore(null);
                    T.showfunShort(getActivity(),"全部加载完毕");
                    return;
                }
                mAdapter.addMoreData(data);
                break;
            case DataLoadType.TYPE_LOAD_MORE_FAIL:
                mAdapter.loadMoreFailed(errorMsg);
                break;
        }
    }


    private void initNewsList(final List<NeteastNewsSummary> data) {
        // mAdapter为空肯定为第一次进入状态
        mAdapter = new NewsListBaseRecyclerAdapter<NeteastNewsSummary>(getActivity(), data) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_news_summary;
            }

            @Override
            public void bindData(BaseRecyclerViewHolder holder, int position, NeteastNewsSummary item) {
                GlideUtils.loadDefault(item.imgsrc, holder.getImageView(R.id.iv_news_summary_photo), null, null, DiskCacheStrategy.RESULT);
                holder.getTextView(R.id.tv_news_summary_title).setText(item.title);
                holder.getTextView(R.id.tv_news_summary_digest).setText(item.digest);
                holder.getTextView(R.id.tv_news_summary_ptime).setText(item.ptime);
            }
        };

        mAdapter.setOnItemClickListener(new OnItemClickAdapter() {
            @Override
            public void onItemClick(View view, int position) {

                if (ClickUtils.isFastDoubleClick()) {
                    return;
                }

                // imgextra不为空的话，无新闻内容，直接打开图片浏览
                KLog.e(mAdapter.getData().get(position).title + ";" + mAdapter.getData().get(position).postid);

                view = view.findViewById(R.id.iv_news_summary_photo);

                if (mAdapter.getData().get(position).postid == null) {
                    T.showfunShort(getActivity(),"此新闻浏览不了哎╮(╯Д╰)╭");
                    return;
                }

                // 跳转到新闻详情
                if (!TextUtils.isEmpty(mAdapter.getData().get(position).digest)) {
                    Intent intent = new Intent(getActivity(), CompanyNewsDetailActivity_.class);
                    intent.putExtra("postid", mAdapter.getData().get(position).postid);
                    intent.putExtra("imgsrc", mAdapter.getData().get(position).imgsrc);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.iv_news_summary_photo), "photos");
                        getActivity().startActivity(intent, options.toBundle());
                    } else {
                        //让新的Activity从一个小的范围扩大到全屏
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth()/* / 2*/, view.getHeight()/* / 2*/, 0, 0);
                        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                    }
                } else {
                    // 以下将数据封装成新浪需要的格式，用于点击跳转到图片浏览
//                    mSinaPhotoDetail = new SinaPhotoDetail();
//                    mSinaPhotoDetail.data = new SinaPhotoDetail.SinaPhotoDetailDataEntity();
//                    mSinaPhotoDetail.data.title = mAdapter.getData().get(position).title;
//                    mSinaPhotoDetail.data.content = "";
//                    mSinaPhotoDetail.data.pics = new ArrayList<>();
//                    // 天啊，什么格式都有 --__--
//                    if (mAdapter.getData().get(position).ads != null) {
//                        for (NeteastNewsSummary.AdsEntity entiity : mAdapter.getData().get(position).ads) {
//                            SinaPhotoDetail.SinaPhotoDetailPicsEntity sinaPicsEntity = new SinaPhotoDetail.SinaPhotoDetailPicsEntity();
//                            sinaPicsEntity.pic = entiity.imgsrc;
//                            sinaPicsEntity.alt = entiity.title;
//                            sinaPicsEntity.kpic = entiity.imgsrc;
//                            mSinaPhotoDetail.data.pics.add(sinaPicsEntity);
//                        }
//                    } else if (mAdapter.getData().get(position).imgextra != null) {
//                        for (NeteastNewsSummary.ImgextraEntity entiity : mAdapter.getData().get(position).imgextra) {
//                            SinaPhotoDetail.SinaPhotoDetailPicsEntity sinaPicsEntity = new SinaPhotoDetail.SinaPhotoDetailPicsEntity();
//                            sinaPicsEntity.pic = entiity.imgsrc;
//                            sinaPicsEntity.kpic = entiity.imgsrc;
//                            mSinaPhotoDetail.data.pics.add(sinaPicsEntity);
//                        }
//                    }
//
//                    Intent intent = new Intent(getActivity(), PhotoDetailActivity.class);
//                    intent.putExtra("neteast", mSinaPhotoDetail);
//                    ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
//                    ActivityCompat.startActivity(getActivity(), intent, options.toBundle());

                }
            }
        });

        mAdapter.setOnEmptyClickListener(new OnEmptyClickListener() {
            @Override
            public void onEmptyClick() {
                refreshData();
            }
        });

        mAdapter.setOnLoadMoreListener(10, new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                loadMoreData();
                // mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
            }
        });

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new BaseSpacesItemDecoration(MeasureUtil.dip2px(getActivity(), 4)));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(250);
        mRecyclerView.getItemAnimator().setMoveDuration(250);
        mRecyclerView.getItemAnimator().setChangeDuration(250);
        mRecyclerView.getItemAnimator().setRemoveDuration(250);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
