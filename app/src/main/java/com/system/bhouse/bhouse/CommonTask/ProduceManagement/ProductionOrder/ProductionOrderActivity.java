package com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder;

import android.os.Bundle;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;

/**
 * Created by Administrator on 2018-06-19.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder
 */

public class ProductionOrderActivity extends WWBackActivity {

    private ProductionOrderFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmationreceip_activity);
        setActionBarMidlleTitle("生产订单");
        //运行back功能
        annotationDispayHomeAsUp();
        fragment = ProductionOrderFragment_.builder().build();
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }

    }
}
