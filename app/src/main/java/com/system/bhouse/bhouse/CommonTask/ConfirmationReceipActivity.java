package com.system.bhouse.bhouse.CommonTask;

import com.system.bhouse.bhouse.CommonTask.ConfirmationReceip.ConfirmationReceipFragment;
import com.system.bhouse.bhouse.CommonTask.ConfirmationReceip.ConfirmationReceipFragment_;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by Administrator on 2017-12-7.
 * 收货确认
 */

@EActivity(R.layout.confirmationreceip_activity)
public class ConfirmationReceipActivity extends WWBackActivity {
    private ConfirmationReceipFragment taskFragment;

    @AfterViews
    void InitOnCreate() {
        setActionBarMidlleTitle("收货确认");
//        viewPager.setAdapter(new MyFragmentIndexViewPager(getSupportFragmentManager()));
        taskFragment = ConfirmationReceipFragment_.builder().build();
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
