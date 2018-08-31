package com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;

import com.system.bhouse.base.StatusBean;
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

    public final static String H_ID_EXTRA = "HId";
    public final static String RECEIPT_HNUMBER_EXTRA = "receiptHnumber";
    public final static String M_STATUS_EXTRA = "mStatus";
    public final static String COMPONENT_QR_EXTRA = "componentQr";
    public final static String ORDER_ID_EXTRA = "orderId";
    public final static String WORK_ORDER_ID_EXTRA = "workOrderID";

    private String HId;
    private String receiptHnumber;
    private StatusBean mStatus;
    private String componentQr;
    private String orderId;
    private String workOrderID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityhousecontent_layout);
        Intent intent = getIntent();
        if (intent!=null)
        {
            HId=intent.getStringExtra(H_ID_EXTRA);
            receiptHnumber= intent.getStringExtra(RECEIPT_HNUMBER_EXTRA);
            mStatus= (StatusBean) intent.getSerializableExtra(M_STATUS_EXTRA);
            componentQr=intent.getStringExtra(COMPONENT_QR_EXTRA);
            orderId=intent.getStringExtra(ORDER_ID_EXTRA);
            workOrderID=intent.getStringExtra(WORK_ORDER_ID_EXTRA);
        }
        LoadingIntoWareHouseContentMessageFragment fragment = findFragment(LoadingIntoWareHouseContentMessageFragment.class);
        if (fragment==null)
        {
            loadRootFragment(R.id.rl_contarner,LoadingIntoWareHouseContentMessageFragment.newInstance(HId,receiptHnumber,mStatus,componentQr,orderId,workOrderID));
        }

    }

    //OptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comtask, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
