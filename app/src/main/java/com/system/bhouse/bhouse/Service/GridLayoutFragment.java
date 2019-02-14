package com.system.bhouse.bhouse.Service;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.socks.library.KLog;
import com.system.bhouse.Custom.AppBarStateChangeListener;
import com.system.bhouse.Custom.View.SceneSurface.SceneTextureView;
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
import com.system.bhouse.bhouse.phone.activity.InformationActivity;
import com.system.bhouse.bhouse.setup.MyselfActivity;
import com.system.bhouse.ui.CircleFlowIndicator;
import com.system.bhouse.ui.ViewFlow;
import com.system.bhouse.ui.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
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
public class GridLayoutFragment extends Fragment implements GvMainAdapter.ImageClick,AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = "GridLayoutFragment";

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
        ButterKnife.bind(this,inflate);
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

        initView();

        return inflate;
    }

    private void initView() {
        toolbar.setTitleTextColor(Color.WHITE);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                mDrawerLayout.openDrawer(Gravity.LEFT);
                Intent intent = new Intent(getActivity(), MyselfActivity.class);
               getActivity().startActivity(intent);

            }
        });

        appBar.addOnOffsetChangedListener(this);

        action_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), InformationActivity.class);
                startActivity(intent1);
            }
        });

        action_capture_cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), InformationActivity.class);
                startActivity(intent1);
            }
        });

        initToolBarSrocll();
        setHeight(toolbar);
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        appBar.removeOnOffsetChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        my_textureview.DrawLastBg();
    }

    //                                        增加Appbar效果代码
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * toolbar 测量的高度
     */
    private int height;

    /**
     * toolbar 是否测量宽度了
     */
    private boolean hasMeasured;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    /**
     * toolbar滑动
     */
    @BindView(R.id.app_bar)
    public AppBarLayout appBar;
    /**
     * 大布局背景，遮罩层
     */
    @BindView(R.id.bg_content)
    public View bgContent;
    /**
     * 展开状态下toolbar显示的内容
     */
    @BindView(R.id.include_toolbar_open)
    public View toolbarOpen;
    /**
     * 展开状态下toolbar的遮罩层
     */
    @BindView(R.id.bg_toolbar_open)
    public View bgToolbarOpen;
    /**
     * 收缩状态下toolbar显示的内容
     */
    @BindView(R.id.include_toolbar_close)
    public View toolbarClose;
    /**
     * 收缩状态下toolbar的遮罩层
     */
    @BindView(R.id.bg_toolbar_close)
    public View bgToolbarClose;

    /**
     * 鸡汤字
     */
    @BindView(R.id.tv_content_jitang)
    TextView tv_content_jitang;

    /**
     * coordinatorlayout
     */
    @BindView(R.id.coordinatorlayout)
    CoordinatorLayout coordinatorlayout;

    @BindView(R.id.action_capture)
    ImageView action_capture;
    @BindView(R.id.action_capture_cl)
    ImageView action_capture_cl;

    @BindView(R.id.my_textureview)
    SceneTextureView my_textureview;

    /**
     * AppBar 滑动回调  每次都调用 很很耗性能
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();
        if (offset <= scrollRange / 2) {//当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
            toolbarOpen.setVisibility(View.VISIBLE);
            toolbarClose.setVisibility(View.GONE);
            //根据偏移百分比 计算透明值
            float scale2 = (float) offset / (scrollRange /2);
            int alpha2 = (int) (255 * scale2);
//          bgToolbarOpen.setBackgroundColor(Color.argb(alpha2, 35, 183, 215));
            Log.e(TAG, "alpha2: "+alpha2);

        } else {//当滑动超过一半，收缩状态下toolbar显示内容，根据收缩位置，改变透明值
            toolbarClose.setVisibility(View.VISIBLE);
            toolbarOpen.setVisibility(View.GONE);
            float scale3 = (float) (scrollRange  - offset) / (scrollRange/2);
//            float scale3 = (float) offset / scrollRange;
            int alpha3 = (int) (255 * scale3);
            Log.e(TAG, "alpha3: "+alpha3);
            bgToolbarClose.setBackgroundColor(Color.argb(alpha3, 35, 183, 215));//变到0 alpha3

        }
        //根据偏移百分比计算扫一扫布局的透明度值
        float scale = (float) offset / scrollRange;
        int alpha = (int) (255 * scale);

        Log.e(TAG, "onOffsetChanged: "+offset);

//        bgContent.setBackgroundColor(Color.argb(alpha, 255, 255, 255));

        AppBarStateChangeListener.State state=AppBarStateChangeListener.State.IDLE;
        if (verticalOffset == 0) {
            if (state != AppBarStateChangeListener.State.EXPANDED) {
                state = AppBarStateChangeListener.State.EXPANDED;//修改为展开状态


                getActivity().getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
                setStatusBarColor(getActivity(), R.color.transparent);
            }
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {

            state = AppBarStateChangeListener.State.COLLAPSED;//修改为折叠状态
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }else {
                setStatusBarColor(getActivity(), R.color.material_blue_50);
            }

        } else {
            if (Math.abs(verticalOffset) > height) {

//                bindingView.titleTv.setVisibility(View.VISIBLE);
                float scaleLight =  1- height / (float) Math.abs(verticalOffset);
                if (state != AppBarStateChangeListener.State.IDLE) {

                    if (state == AppBarStateChangeListener.State.COLLAPSED && scaleLight < 0.55) {//由折叠变为展开
//                        bindingView.toolbar.setNavigationIcon(R.drawable.nav_icon_white_return);
                        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        }else {
                            setStatusBarColor(getActivity(), R.color.material_blue_50);
                        }
                    }

                    state = AppBarStateChangeListener.State.IDLE;
                }
                float alphalight = (255 * scaleLight);

            } else {


            }
        }
    }

    /**
     * 修改状态栏颜色，支持4.4以上版本
     * @param activity
     * @param colorId
     */
    public static void setStatusBarColor(Activity activity, int colorId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
//      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
            transparencyBar(activity);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(colorId);
        }
    }

    @TargetApi(19)
    public static void transparencyBar(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        } else
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window =activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    //获取标题栏高度
    private void measureHeight() {
        ViewTreeObserver vto = coordinatorlayout.getViewTreeObserver();

        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (hasMeasured == false) {

                    height = toolbar.getMeasuredHeight();
                    hasMeasured = true;

                }
                return true;
            }
        });
    }

    /**
     * 以Toolbar 以例  扩充toolbar高度
     * @param view
     */
    public void setHeight(View view) {
        // 获取actionbar的高度
        TypedArray actionbarSizeTypedArray = getActivity().obtainStyledAttributes(new int[]{
                android.R.attr.actionBarSize
        });
        float height = actionbarSizeTypedArray.getDimension(0, 0);
        // ToolBar的top值
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        double statusBarHeight = getStatusBarHeight(getActivity());
        lp.height = (int) (statusBarHeight + height);
        view.setPadding(0,(int) statusBarHeight,0, 0);
        view.setLayoutParams(lp);
    }

    private void initToolBarSrocll() {
        measureHeight();
    }


    private double getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

        workflowSection.setmOnItemClickListener(new NewsListSection.onItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
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