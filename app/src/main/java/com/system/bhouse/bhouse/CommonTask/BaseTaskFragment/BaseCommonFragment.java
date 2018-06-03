package com.system.bhouse.bhouse.CommonTask.BaseTaskFragment;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.system.bhouse.bhouse.CommonTask.ComTaskFilterFragment;
import com.system.bhouse.bhouse.CommonTask.event.EventFilter;
import com.system.bhouse.bhouse.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2018-03-19.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.ConfirmationReceipt
 */
@EFragment
@OptionsMenu(R.menu.comfragment_task)
public abstract class BaseCommonFragment extends ComTaskFilterFragment {

   protected ViewPager pager;

    protected  View actionDivideLine;

    int pageMargin;

    protected void initPagerView()
    {
        View rootView = getView();
        pager= (ViewPager)rootView.findViewById(R.id.pagerTaskFragment);
        actionDivideLine= rootView.findViewById(R.id.actionDivideLine);

        pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());

        pager.setPageMargin(pageMargin);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        actionDivideLine.setVisibility(View.INVISIBLE);
    }

    @AfterViews
   public void initTaskFragment() {
        initFilterViews();
        showLoading(true);
    }

    @Override
    protected void initFilterViews() {
        super.initFilterViews();
        setHasOptionsMenu(true);
    }

    @Override
    public void onRefresh() {
    }



    //新增 吊装需求
    @OptionsItem
    protected final void action_add() {
        AddIntentFor();
    }
    protected abstract void AddIntentFor();


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

    // 用于处理推送
    public void onEventMainThread(Object object) {
        if (!(object instanceof EventFilter)) {
            return;
        }
        EventFilter eventFilter = (EventFilter) object;
        if (eventFilter.index == 1) {
            meActionFilter();
        }
    }

    @OptionsItem
    protected final void action_filter() {
        actionFilter();
    }

    @Override
    protected void sureFilter() {
        super.sureFilter();
        //筛选了状态，相应的筛选标签也变化
        //getNetwork(urlTaskLabel + getRole(), urlTaskLabel);
        //界面的点击 这个在这里更新弗雷的ui更新

    }
}
