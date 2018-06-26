package com.system.bhouse.bhouse.CommonTask.BaseTaskFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.bhouse.base.CheckStatusBeanImpl;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.base.SubmitStatusBeanImpl;
import com.system.bhouse.bhouse.CommonTask.Widget.LRecyclerView;
import com.system.bhouse.bhouse.CommonTask.Widget.LoadingAdapter;
import com.system.bhouse.bhouse.CommonTask.callback.TaskListParentUpdate;
import com.system.bhouse.bhouse.CommonTask.callback.TaskListUpdate;
import com.system.bhouse.bhouse.CommonTask.event.EventFilterDetail;
import com.system.bhouse.bhouse.CommonTask.utils.BlankViewHelp;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.RefreshBaseFragment;
import com.system.bhouse.bhouse.task.bean.ProjectObject;
import com.system.bhouse.bhouse.task.bean.TaskObject;
import com.system.bhouse.utils.TenUtils.L;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.WeakHashMap;

import de.greenrobot.event.EventBus;

@EFragment
public abstract class BaseCommonListFragment<T extends LoadingAdapter,B> extends RefreshBaseFragment implements TaskListUpdate {

    public static final String LOADEDREQUIREKEY = "loadedrequirekey";

    @FragmentArg
    protected boolean mShowAdd = false;

    protected int REFRESH_DATA = 0x4564654;

    protected T mAdapter;

   protected   ArrayList<B> mData = new ArrayList<>();
   protected   ArrayList<B> AllCopymData = new ArrayList<>();

    @FragmentArg
    protected String mMeAction;
    @FragmentArg
    protected String mStatus;
    @FragmentArg
    protected String mLabel;
    @FragmentArg
    protected String mKeyword;

    @FragmentArg
    protected TaskObject.Members mMembers;
    @FragmentArg
    protected ProjectObject mProjectObject;

    @ViewById
    protected View blankLayout;
    @ViewById
    protected LRecyclerView listView;


    @StringArrayRes
    protected String[] task_titles;

    protected boolean mNeedUpdate = true;

    int mSectionId;

    final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    int mTaskCount[] = new int[2];
    protected boolean mUpdateAll = true;
    String urlAll = "";
    View.OnClickListener onClickRetry = v -> onRefresh();
    String mToday = "";
    String mTomorrow = "";
    WeakHashMap<View, Integer> mOriginalViewHeightPool = new WeakHashMap<>();
    private TaskListParentUpdate mParent; //更新所有fragment
//    private View listFooter;

    private int limitLenght = 5; //默认每次加载五个
    protected View notDataView;
    protected View errorView;
    protected boolean mError = true;
    protected boolean mNoData = true;

    public void setParent(TaskListParentUpdate parent) {
        mParent = parent;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        //setHasOptionsMenu(true);
    }

    protected  boolean SequenceData() {
        ArrayList<B> subListmdataResult = getSubListmdataResult(mData, AllCopymData);
        if (subListmdataResult.size()==AllCopymData.size())
        {
            List<B> loadedRequires = AllCopymData.subList(mData.size(), AllCopymData.size());
            mData.addAll(loadedRequires);
            mAdapter.setDataList(mData);
            return true;
        }else if (subListmdataResult.size()==mData.size()) {
            mAdapter.setDataList(mData);
        }
        return  false;
    }

    protected void baseCommoninitView(T mAdapter) {
        View rootView = getView();
        blankLayout = rootView.findViewById(R.id.blankLayout);
        listView = (LRecyclerView) rootView.findViewById(R.id.listView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(linearLayoutManager);
        this.mAdapter=mAdapter;
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.taskcomon_empty_view, (ViewGroup) listView.getParent(), false);
        errorView = getActivity().getLayoutInflater().inflate(R.layout.taskcommon_error_view, (ViewGroup) listView.getParent(), false);

        initLRecycleViewLoading();
        initUrlAndLoadData();
    }

    @Override
    public void taskListUpdate(boolean must) {
        if (must) {
            mNeedUpdate = true;
        }
        mNoData=true;
        mError=true;

        if (mNeedUpdate) {
            mNeedUpdate = false;
            initSetting();
            loadData();
        }
    }

    @Override
    public void onRefresh() {
        if (listView != null)
            listView.finishLoading();
        if (mAdapter != null)
            mAdapter.setStatus(LoadingAdapter.STATUS_LOADED);
        initSetting();
        loadData();
        mNoData = true;
        mError = true;
    }

    @OptionsItem
    public void action_add() {
        mNeedUpdate = true;
    }

    protected <T> ArrayList<T> getSubListmdataResult(ArrayList<T> mChildData, ArrayList<T> AllData) {
        limitLenght = 5;
        if (mChildData.size() >= AllData.size()) {

            return AllData;
        }
        if (mChildData.size() + limitLenght >= AllData.size()) {
            return AllData;
        }
        List<T> loadedRequires = AllData.subList(mChildData.size(), mChildData.size() + limitLenght);
        mChildData.addAll(loadedRequires);
        return mChildData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mNeedUpdate = true;
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @AfterViews
    protected void initTaskListFragment() {

        Calendar calendar = Calendar.getInstance();
        mToday = mDateFormat.format(calendar.getTimeInMillis());
        mTomorrow = mDateFormat.format(calendar.getTimeInMillis() + 1000 * 60 * 60 * 24);
        mNeedUpdate = true;

        updateFootStyle();
    }

    private void initLRecycleViewLoading() {
        listView.setOnLoadingListener(() -> {

            if (isRefreshing()){
                listView.finishLoading();
                return;
            }
            if (mAdapter.getStatus()==LoadingAdapter.STATUS_LOADCOMPLETE)
            {
                listView.finishLoading();
                return;
            }
            mAdapter.setStatus(LoadingAdapter.STATUS_LOADING);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (getActivity() == null) {
                        listView.finishLoading();
                        return;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listView.finishLoading();
                            mAdapter.setStatus(LoadingAdapter.STATUS_LOADED);
                            if (SequenceData()&&isVisBottom(listView))
                            {
                                mAdapter.setStatus(LoadingAdapter.STATUS_LOADCOMPLETE);
                            }
                        }
                    });
                }
            }).start();
        });
    }

    private boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        }
        else {
            return false;
        }
    }

    private void initUrlAndLoadData() {
        urlAll = "";

        taskListUpdate(true);
//        taskFragmentLoading(true);
    }

    private void updateFootStyle() {
    }


    protected abstract void loadData();

    protected void initSetting() {
        //网络初始化
//        super.initSetting();

        mTaskCount[0] = 0;
        mTaskCount[1] = 0;

        mSectionId = 0;
        mUpdateAll = true;
    }

    public void taskFragmentLoading(boolean isLoading) {
        BlankViewHelp.setBlankLoading(blankLayout, isLoading);
        if (isLoading) {
            listView.setVisibility(View.INVISIBLE);
        }
        else {
            listView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    /**
     *  String[] statusContent = {"", "提交", "审核"};
     */
    protected void setStatusContent(String[] statusContent) {
        this.statusContent = statusContent;
    }

    String[] statusContent = {"", "提交", "审核"};



    //筛选后刷新  刷新这个listFragment
    public void onEventMainThread(Object object) {
        if (object instanceof EventFilterDetail) {
            EventFilterDetail eventFilter = (EventFilterDetail) object;
            mMeAction = eventFilter.meAction;

            mStatus = statusContent[TextUtils.isEmpty(eventFilter.status) ? 2 : Integer.valueOf(eventFilter.status)];
            mLabel = eventFilter.label;
            mKeyword = eventFilter.keyword;

            //重新加载所有
            mUpdateAll = true;
            initUrlAndLoadData();
        }
        else if (object.toString().contains("刷新")) {

            AddDeleteUpdateListData(object);
        }
    }

    protected abstract void AddDeleteUpdateListData(Object object);

    protected String DefaultStatus;
    @NonNull
    protected StatusBean getStatusBean() {
        DefaultStatus= TextUtils.isEmpty(mStatus) ? statusContent[1] : mStatus;
        StatusBean statusBean = new StatusBean();
        //从初始化  或者  后台请求 得到状态
        if (DefaultStatus.equals(statusContent[1])) {
            SubmitStatusBeanImpl submitStatusBean = new SubmitStatusBeanImpl();
            submitStatusBean.setVisCheckBtn(true).setVisDeleteBtn(true);
            statusBean.setBean(submitStatusBean);
        }
        else if (DefaultStatus.equals(statusContent[2])) {
            CheckStatusBeanImpl checkStatusBean = new CheckStatusBeanImpl();
            checkStatusBean.setVisCheckFBtn(true);
            statusBean.setBean(checkStatusBean);
        }
        else {
            L.e("提交 和 审核值的标志位变化了");
        }
        return statusBean;
    }
}
