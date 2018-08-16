package com.system.bhouse.bhouse.CommonTask.MaintainManagement.StationCarDetachModule;

import android.os.Bundle;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;

/**
 * Created by Administrator on 2017-12-7.
 * 台车模具分离
 */

public class StationCarDetachModuleActivity extends WWBackActivity {

    private StationCarDetachModuleFragment taskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmationreceip_activity);
        setActionBarMidlleTitle("台车模具分离");
        //运行back功能
        annotationDispayHomeAsUp();
        taskFragment = StationCarDetachModuleFragment_.builder().build();
        if (taskFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, taskFragment).commit();
        }
    }
}
