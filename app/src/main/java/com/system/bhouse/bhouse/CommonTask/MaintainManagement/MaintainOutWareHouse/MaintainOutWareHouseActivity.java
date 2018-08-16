package com.system.bhouse.bhouse.CommonTask.MaintainManagement.MaintainOutWareHouse;

import android.os.Bundle;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;

/**
 * Created by Administrator on 2017-12-7.
 * 养护出库
 */

public class MaintainOutWareHouseActivity extends WWBackActivity {

    private MaintainOutWareHouseFragment taskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmationreceip_activity);
        setActionBarMidlleTitle("养护出库");
        //运行back功能
        annotationDispayHomeAsUp();
        taskFragment = MaintainOutWareHouseFragment_.builder().build();
        if (taskFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, taskFragment).commit();
        }
    }
}
