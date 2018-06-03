package com.system.bhouse.bhouse.CommonTask.TransportationManagement.LoadingCarOrder;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by Administrator on 2017-12-7.
 * 装车订单
 */

@EActivity(R.layout.confirmationreceip_activity)
public class LoadingCarOrderActivity extends WWBackActivity {
    private LoadingCarOrderFragment taskFragment;

    @AfterViews
    void InitOnCreate() {
        setActionBarMidlleTitle("装车订单");
//        viewPager.setAdapter(new MyFragmentIndexViewPager(getSupportFragmentManager()));
        taskFragment = LoadingCarOrderFragment_.builder().build();
        if (taskFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, taskFragment).commit();
        }

    }
}

//class MyFragmentIndexViewPager extends SaveFragmentPagerAdapter {
//
//    public MyFragmentIndexViewPager(FragmentManager fm) {
//        super(fm);
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        ComTaskFragment taskFragment = new ComTaskFragment_();
//        saveFragment(taskFragment);
//        return taskFragment;
//    }
//
//    @Override
//    public int getCount() {
//        return 1;
//    }
//}
