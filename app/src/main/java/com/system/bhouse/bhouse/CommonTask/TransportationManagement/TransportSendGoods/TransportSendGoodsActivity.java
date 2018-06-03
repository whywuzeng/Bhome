package com.system.bhouse.bhouse.CommonTask.TransportationManagement.TransportSendGoods;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by Administrator on 2017-12-7.
 * 运输发货
 */

@EActivity(R.layout.confirmationreceip_activity)
public class TransportSendGoodsActivity extends WWBackActivity {
    private TransportSendGoodsFragment taskFragment;

    @AfterViews
    void InitOnCreate() {
        setActionBarMidlleTitle("运输发货");
//        viewPager.setAdapter(new MyFragmentIndexViewPager(getSupportFragmentManager()));
        taskFragment = TransportSendGoodsFragment_.builder().build();
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
