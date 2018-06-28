package com.system.bhouse.bhouse.CommonTask.MaterialControl.PlateMaterial;

import android.os.Bundle;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;

/**
 * Created by Administrator on 2017-12-7.
 * 完工入库
 */

public class PlateMaterialActivity extends WWBackActivity {

    private PlateMaterialFragment taskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmationreceip_activity);
        setActionBarMidlleTitle("完工入库");
        //运行back功能
        annotationDispayHomeAsUp();
        taskFragment = PlateMaterialFragment_.builder().build();
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
