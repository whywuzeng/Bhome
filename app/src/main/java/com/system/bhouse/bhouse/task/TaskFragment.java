package com.system.bhouse.bhouse.task;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.system.bhouse.base.Global;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.TaskFilterFragment;
import com.system.bhouse.bhouse.setup.utils.ListModify;
import com.system.bhouse.bhouse.task.Interface.TaskListParentUpdate;
import com.system.bhouse.bhouse.task.Interface.TaskListUpdate;
import com.system.bhouse.bhouse.task.adpter.base.SaveFragmentPagerAdapter;
import com.system.bhouse.bhouse.task.bean.ProjectObject;
import com.system.bhouse.bhouse.task.view.MyPagerSlidingTabStrip;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


@EFragment(R.layout.fragment_task)
public class TaskFragment extends TaskFilterFragment implements TaskListParentUpdate {

//    final String host = Global.HOST_API + "/projects?pageSize=100&type=all";
//    final String urlTaskCount = Global.HOST_API + "/tasks/projects/count";

    @ViewById
    MyPagerSlidingTabStrip tabs;
    @ViewById(R.id.pagerTaskFragment)
    ViewPager pager;
    @ViewById
    View actionDivideLine;


    ArrayList<ProjectObject> mData = new ArrayList<>();
    ArrayList<ProjectObject> mAllData = new ArrayList<>();
    int pageMargin;
    private PageTaskFragment adapter;
    private boolean fabOpened;

    @AfterViews
    void initTaskFragment() {
        pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());

        tabs.setLayoutInflater(inflater);

//        getNetwork(host, host);

        adapter = new PageTaskFragment(getChildFragmentManager());
        pager.setPageMargin(pageMargin);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                load(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabs.setVisibility(View.VISIBLE);
        actionDivideLine.setVisibility(View.VISIBLE);
        tabs.setViewPager(pager);

//        tabs.setVisibility(View.INVISIBLE);
//        actionDivideLine.setVisibility(View.INVISIBLE);

        initFilterViews();
        showLoading(true);
    }


    @Override
    protected void initFilterViews() {
        super.initFilterViews();
        setHasOptionsMenu(true);
        load(0);
    }

    private void load(int index) {

//        mTaskProjectCountModel = new TaskProjectCountModel();
//
//        if (index == 0) {
//            //全部项目
//            getNetwork(urlTaskCountAll, urlTaskCountAll);
//        } else {
//            ProjectObject mProjectObject = mData.get(index);
//            int userid = MyApp.sUserObject.id;
//            //某个项目
//            getNetwork(String.format(urlTaskSomeCount_owner, mProjectObject.getId(), userid), urlTaskSomeCount_owner);
//            getNetwork(String.format(urlTaskSomeCount_watcher, mProjectObject.getId(), userid), urlTaskSomeCount_watcher);
//            getNetwork(String.format(urlTaskSomeCount_creator, mProjectObject.getId(), userid), urlTaskSomeCount_creator);
//            getNetwork(String.format(urlTaskSomeOther, mProjectObject.getId()), urlTaskSomeOther);
//        }
//        loadLabels();

        Observable.OnSubscribe<Object> objectOnSubscribe = new Observable.OnSubscribe<Object>(){
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onCompleted();
            }

        };

        Observable.create(objectOnSubscribe).delay(5,TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
                showLoading(false);
            }

            @Override
            public void onError(Throwable e) {
                showLoading(false);
            }

            @Override
            public void onNext(Object o) {

            }
        });


    }

    private void openMenu(View v) {
        ObjectAnimator animator=ObjectAnimator.ofFloat(v,"rotation",0,-155,-135);
        animator.setDuration(500);
        animator.start();
        fabOpened=true;
    }

    private void closeMenu(View v) {
        ObjectAnimator animator=ObjectAnimator.ofFloat(v,"rotation",-135,20,0);
        animator.setDuration(500);
        animator.start();
        fabOpened=false;
    }

    private void loadLabels() {

//        if (tabs.getCurrentPosition() == 0) {
//            getNetwork(urlTaskLabel + getRole(), urlTaskLabel);
//        } else {
//            ProjectObject mProjectObject = mData.get(tabs.getCurrentPosition());
//            getNetwork(String.format(urlProjectTaskLabels, mProjectObject.getId()) + getRole(), urlProjectTaskLabels);
//        }
    }


    @Override
    public void onRefresh() {
        int a=1;
    }

    private void initListData() {
        mAllData.clear();
        mData.clear();
        mData.add(new ProjectObject());

        try {
//            JSONObject json = AccountInfo.getGetRequestCacheData(getActivity(), host);
//            jsonToAllData(json.optJSONArray("list"));
//
//            JSONArray jsonArray = AccountInfo.getGetRequestCacheListData(getActivity(), urlTaskCount);
//            jsonToData(jsonArray);

        } catch (Exception e) {
            Global.errorLog(e);
        }
    }

    //新建任务  返回的时候  出现的ID
    @OnActivityResult(ListModify.RESULT_EDIT_LIST)
    void onResultEditList(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
           int task_orderid=  data.getIntExtra("RESULT_GLOBARKEY",0);
            taskListParentUpdate(task_orderid);
        }

    }

    @Override
    public void taskListParentUpdate(int task_orderid) {
        List<WeakReference<Fragment>> array = adapter.getFragments();
        for (WeakReference<Fragment> item : array) {
            Fragment fragment = item.get();
            if (fragment instanceof TaskListUpdate) {
                ((TaskListUpdate) fragment).taskListUpdate(true,task_orderid);
            }
        }
    }

//    //增加任务
//    @OptionsItem
//    protected final void action_add() {
//
////        ProjectObject projectObject = mData.get(pager.getCurrentItem());
////        TaskAddActivity_.intent(this)
////                .mUserOwner(MyApp.sUserObject)
////                .mProjectObject(projectObject)
////                .startForResult(ListModify.RESULT_EDIT_LIST);
//
//    }

    public static class TaskCount {
        public int project;
        public int processing;
        public int done;

        public TaskCount(JSONObject json) throws JSONException {
            project = json.optInt("project");
            processing = json.optInt("processing");
            done = json.optInt("done");
        }
    }

    private class PageTaskFragment extends SaveFragmentPagerAdapter implements MyPagerSlidingTabStrip.IconTabProvider {

        public PageTaskFragment(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mData.get(position).name;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TaskListFragment fragment = (TaskListFragment) super.instantiateItem(container, position);
            fragment.setParent(TaskFragment.this);

            return fragment;

//            return super.instantiateItem(container, position);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public String getPageIconUrl(int position) {
//            Uri uri = Uri.parse("res:///" + R.drawable.allpic);
            return "";
        }

        @Override
        public Fragment getItem(int position) {
//
            TaskListFragment_ fragment = new TaskListFragment_();
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("mMembers", new TaskObject.Members(AccountInfo.loadAccount(getActivity())));
//            bundle.putSerializable("mProjectObject", mData.get(position));
//            bundle.putBoolean("mShowAdd", false);
//
//            bundle.putString("mMeAction", mMeActions[statusIndex]);
//            if (mFilterModel != null) {
//                bundle.putString("mStatus", mFilterModel.status + "");
//                bundle.putString("mLabel", mFilterModel.label);
//                bundle.putString("mKeyword", mFilterModel.keyword);
//            } else {
//                bundle.putString("mStatus", "");
//                bundle.putString("mLabel", "");
//                bundle.putString("mKeyword", "");
//            }
//            fragment.setArguments(bundle);
//
            saveFragment(fragment);
            return fragment;
//            return null;

        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        EventBus.getDefault().unregister(this);
    }

    // 用于处理推送
    public void onEventMainThread(Object object) {

//        if (!(object instanceof EventFilter)) {
//            return;
//        }
//        EventFilter eventFilter = (EventFilter) object;
//        //确定是我的任务筛选
//        if (eventFilter.index == 1) {
//            meActionFilter();
//        }
    }

//    @OptionsItem
//    protected final void action_filter() {
//        actionFilter();
//    }

//    @Override
//    protected void sureFilter() {
//        super.sureFilter();
//        //筛选了状态，相应的筛选标签也变化
//        //getNetwork(urlTaskLabel + getRole(), urlTaskLabel);
//        loadLabels();
//    }
}
