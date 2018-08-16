package com.system.bhouse.bhouse.Service;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.system.bhouse.adpter.GalleryViewflowAdapter;
import com.system.bhouse.api.manager.RetrofitManager;
import com.system.bhouse.api.manager.service.Api;
import com.system.bhouse.api.manager.service.HostType;
import com.system.bhouse.base.BaseSubscriber;
import com.system.bhouse.base.RequestCallBack;
import com.system.bhouse.bean.NeteastNewsSummary;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProduceActivity;
import com.system.bhouse.bhouse.CommonTask.SiteActivity_;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.TransportationManagementActivity_;
import com.system.bhouse.bhouse.CompanyNews.CompanyNewsDetailActivity_;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.Service.NewsListUI.NewsListSection;
import com.system.bhouse.bhouse.Service.NewsListUI.TaskListSection;
import com.system.bhouse.bhouse.Service.adpter.GvMainAdapter;
import com.system.bhouse.ui.CircleFlowIndicator;
import com.system.bhouse.ui.ViewFlow;
import com.system.bhouse.ui.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Administrator on 2016-6-15.
 * ClassName: com.system.bhouse.bhouse.Service
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate: 2016.8.17
 * 主界面的 main 的九宫格界面  加了viewflow;
 */
public class GridLayoutFragment extends Fragment implements GvMainAdapter.ImageClick {
    private GridView GvMain;
    private ViewFlow mHomeViewflow;
    private CircleFlowIndicator mHomeViewflowindic;
    private ArrayList<String> mNameList ;
    private ArrayList<Integer> mDrawableList ;
    private String[] stringArray;
    private int[] PicsID={R.drawable.more_btn_other,R.drawable.inbox_btn_check_in_normal,R.drawable.weight_btn_card_normal,R.drawable.inbox_btn_traceless_normal,R.mipmap.caigou,R.mipmap.kucun,R.mipmap.xiaoshou,R.mipmap.zichan};
    private SectionedRecyclerViewAdapter mRecyclerViewAdapter;
    private SectionedRecyclerViewAdapter mRecyclerViewAdapter_task;


    private RecyclerView rv_listview;
    private RecyclerView rv_listview_task;
    private List<NeteastNewsSummary> beanList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_gridlayout, container, false);
        GvMain= (GridView)inflate.findViewById(R.id.GvMain);
        mHomeViewflow=(ViewFlow)inflate.findViewById(R.id.mHomeViewflow);
        mHomeViewflowindic=(CircleFlowIndicator)inflate.findViewById(R.id.mHomeViewflowindic);
        rv_listview=(RecyclerView)inflate.findViewById(R.id.rv_listview);
        rv_listview_task=(RecyclerView)inflate.findViewById(R.id.rv_listview_task);
        initViewFlow();
        setData();
        GvMainAdapter gvMainAdapter = new GvMainAdapter(getActivity(), mNameList, mDrawableList);
        GvMain.setAdapter(gvMainAdapter);
        GvMain.setClickable(false);
        gvMainAdapter.setmImageClick(this);

        return inflate;
    }

    private void initViewFlow() {
        int[] pic={R.mipmap.yuandazhugong,R.mipmap.tuceng_2};
        GalleryViewflowAdapter adapter=new GalleryViewflowAdapter(this.getActivity(),pic);

        mHomeViewflow.setClickable(false);
        mHomeViewflow.setFocusable(false);
        mHomeViewflow.setAdapter(adapter);
        mHomeViewflow.setmSideBuffer(pic.length);
        mHomeViewflow.setFlowIndicator(mHomeViewflowindic);
        mHomeViewflow.setTimeSpan(2000);
        mHomeViewflow.setSelection(pic.length * 10);
        mHomeViewflow.startAutoFlowTimer();
        mHomeViewflowindic.setVisibility(View.VISIBLE);
        
        initNewsList();
        initTaskList();
    }

    private void initTaskList() {

        rv_listview_task.addItemDecoration(new SimpleDividerDecoration(getActivity()));
        rv_listview_task.addItemDecoration(new LeftDividerDecoration(getActivity()));

        mRecyclerViewAdapter_task = new SectionedRecyclerViewAdapter();

        GridLayoutManager manager=new GridLayoutManager(getActivity(),1);
        rv_listview_task.setLayoutManager(manager);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mRecyclerViewAdapter_task.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 1;
                    default:
                        return 1;
                }
            }
        });


        rv_listview_task.setAdapter(mRecyclerViewAdapter_task);

        TaskListSection workflowSection = new TaskListSection();

        mRecyclerViewAdapter_task.addSection(workflowSection);

        mRecyclerViewAdapter_task.notifyDataSetChanged();
    }

    public class SimpleDividerDecoration extends RecyclerView.ItemDecoration {

        private int dividerHeight;
        private Paint dividerPaint;


        public SimpleDividerDecoration(Context context) {
            dividerPaint = new Paint();
            dividerPaint.setColor(context.getResources().getColor(R.color.litte_gray));
            dividerHeight = context.getResources().getDimensionPixelSize(R.dimen.divider_height);

        }


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = dividerHeight;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int childCount = parent.getChildCount();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            for (int i = 0; i < childCount - 1; i++) {
                View view = parent.getChildAt(i);
                float top = view.getBottom();
                float bottom = view.getBottom() + dividerHeight;
                c.drawRect(left, top, right, bottom, dividerPaint);
            }
        }


    }


    public class LeftDividerDecoration extends RecyclerView.ItemDecoration {
        private Paint leftPaint;
        private int leftPaintWidth;

        public LeftDividerDecoration(Context context) {

            leftPaint = new Paint();
            leftPaint.setColor(context.getResources().getColor(R.color.material_yellow_800));
            leftPaintWidth = context.getResources().getDimensionPixelSize(R.dimen.leftpaint_width);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                int pos = parent.getChildAdapterPosition(child);
                boolean isLeft = pos % 2 == 0;
                if (pos!=0) {
                    float left = child.getLeft();
                    float right = left + leftPaintWidth ;
                    float top = child.getTop();
                    float bottom = child.getBottom();
                    c.drawRect(left, top, right, bottom, leftPaint);
                }
            }
        }
    }


    private void initNewsList() {
        rv_listview.addItemDecoration(new SimpleDividerDecoration(getActivity()));
        mRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager manager=new GridLayoutManager(getActivity(),1);
        rv_listview.setLayoutManager(manager);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mRecyclerViewAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 1;
                    default:
                        return 1;
                }
            }
        });


        rv_listview.setAdapter(mRecyclerViewAdapter);


        NewsListSection workflowSection = new NewsListSection(beanList,getActivity());

        workflowSection.setMoreClickListener(new NewsListSection.OnMoreClickListener() {
                                                 @Override
                                                 public void OnMoreClick() {
                                                     if (onMoreClickListener!=null) {
                                                         onMoreClickListener.onMoreClick();
                                                     }
                                                 }
                                             });

        workflowSection.setmOnItemClickListener((view,position) -> {
            // 跳转到新闻详情
            if (!TextUtils.isEmpty(workflowSection.getNewsListBeens().get(position).digest)) {
                Intent intent = new Intent(getActivity(), CompanyNewsDetailActivity_.class);
                intent.putExtra("postid", workflowSection.getNewsListBeens().get(position).postid);
                intent.putExtra("imgsrc", workflowSection.getNewsListBeens().get(position).imgsrc);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.iv_news_summary_photo), "photos");
                    getActivity().startActivity(intent, options.toBundle());
                } else {
                    //让新的Activity从一个小的范围扩大到全屏
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth()/* / 2*/, view.getHeight()/* / 2*/, 0, 0);
                    ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                }
            }
        });
        mRecyclerViewAdapter.addSection(workflowSection);
        mRecyclerViewAdapter.notifyDataSetChanged();

        getData();
    }


    private void getData(){
        requestNewsList(new RequestCallBack<List<NeteastNewsSummary>>() {
            @Override
            public void beforeRequest() {

            }

            @Override
            public void requestError(String msg) {

            }

            @Override
            public void requestComplete() {

            }

            @Override
            public void requestSuccess(List<NeteastNewsSummary> data) {
                beanList.clear();
                    beanList.addAll(data.subList(0, 3));
                    mRecyclerViewAdapter.notifyDataSetChanged();
            }
        },"headline","T1348647909107", 0);
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


    private void setData() {
        mNameList = new ArrayList<String>();
        mDrawableList = new ArrayList<>();
        Resources resources = getActivity().getResources();
        stringArray = resources.getStringArray(R.array.colortext);
        for(String s :stringArray) {
            mNameList.add(s);
        }

        mDrawableList.add(R.drawable.more_btn_other);
        mDrawableList.add(R.drawable.inbox_btn_check_in_normal);
        mDrawableList.add(R.drawable.weight_btn_card_normal);
        mDrawableList.add(R.drawable.inbox_btn_report_normal);
        mDrawableList.add(R.drawable.inbox_btn_traceless_normal);
        mDrawableList.add(R.drawable.more_btn_forward);
//        mDrawableList.add(R.mipmap.kaohe);
//        mDrawableList.add(R.mipmap.daixu);
//        mDrawableList.add(R.mipmap.gongzuohuibao);
//        mDrawableList.add(R.mipmap.wodewenjian);
        mDrawableList.add(R.mipmap.jiahao);

    }

    //主界面的那些九宫格的跳转
    @Override
    public void setPosition(int position) {
        Intent intent;
        switch (position) {
            case 5:
                Toast.makeText(GridLayoutFragment.this.getActivity(), R.string.shangweikaifa, Toast.LENGTH_SHORT).show();

                break;
            case 1:
                 intent = new Intent(GridLayoutFragment.this.getActivity(), TransportationManagementActivity_.class);
                startActivity(intent);

                break;
            case 0:
                //生产执行
                //成本分析
//                intent = new Intent(GridLayoutFragment.this.getActivity(), CostAnalysisMainActivity.class);
//                startActivity(intent);

//                intent = new Intent(GridLayoutFragment.this.getActivity(), ProductionManage_.class);
//                startActivity(intent);

                //工地管理-//吊装需求
                SiteActivity_.intent(this).start();

                break;
            case 3:

                break;
            case 4:
                break;
            case 2:
                //生产管理
               intent = new Intent(getActivity(), ProduceActivity.class);
               startActivity(intent);

                break;
            default:
//                intent = new Intent(GridLayoutFragment.this.getActivity(), CheckedActivity.class);
//                startActivity(intent);

//                Toast.makeText(GridLayoutFragment.this.getActivity(), R.string.shangweikaifa, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void setOnMoreClickListener(OnMoreClickListener onMoreClickListener) {
        this.onMoreClickListener = onMoreClickListener;
    }

    public OnMoreClickListener onMoreClickListener;

   public interface OnMoreClickListener{
        void onMoreClick();
    }
}

//运输管理
//  ProductionManage_.intent(GridLayoutFragment.this.getActivity()).start();