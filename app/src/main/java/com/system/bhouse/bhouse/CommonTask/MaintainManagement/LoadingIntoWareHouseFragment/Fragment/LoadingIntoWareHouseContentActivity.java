package com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Base.MySupportActivity;
import com.system.bhouse.bhouse.R;

/**
 * Created by Administrator on 2018-08-23.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment
 */

public class LoadingIntoWareHouseContentActivity extends MySupportActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityhousecontent_layout);
        LoadingIntoWareHouseContentMessageFragment fragment = findFragment(LoadingIntoWareHouseContentMessageFragment.class);
        if (fragment==null)
        {
            loadRootFragment(R.id.rl_contarner,);
        }
    }
}
