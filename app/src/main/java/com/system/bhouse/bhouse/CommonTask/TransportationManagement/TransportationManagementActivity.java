package com.system.bhouse.bhouse.CommonTask.TransportationManagement;

import android.content.Intent;
import android.view.View;

import com.system.bhouse.bhouse.CommonTask.TransportationManagement.CarManagement.CarManagementActivity_;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.ComponentReturns.ComponentReturnsActivity_;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.ContainerRecycle.ContainerRecycleActivity_;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.LoadingCarOrder.LoadingCarOrderActivity_;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.TransportSendGoods.TransportSendGoodsActivity_;
import com.system.bhouse.bhouse.CommonTask.component.StepsView;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.phone.activity.InformationActivity;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Administrator on 2018-04-09.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TransportationManagement
 */
@EActivity(R.layout.transportationactivity_layout)
@OptionsMenu(R.menu.menu_site)
public class TransportationManagementActivity extends WWBackActivity implements StepsView.DataClick{

    private static final int RESULT_LOCAL = 2;

    @ViewById
    StepsView viewStepsview;

    @AfterViews
    void initSiteFragment() {
        setActionBarMidlleTitle("运输管理");
        viewStepsview.setDataClick(this);
    }

    @Override
    public void onDataClick(int position, View v) {
        switch (position)
        {
            case 0:
                CarManagementActivity_.intent(this).start();
                break;
            case 1:
                LoadingCarOrderActivity_.intent(this).start();
                break;
            case 2:
                TransportSendGoodsActivity_.intent(this).start();
                break;
            case 3:
                ComponentReturnsActivity_.intent(this).start();
                break;
            case 4:
                ContainerRecycleActivity_.intent(this).start();
                break;
        }
    }

    @OptionsItem
    protected final void action_organize(){

        Intent intent2=new Intent(this,InformationActivity.class);
        this.startActivityForResult(intent2,RESULT_LOCAL);
    }

    @OnActivityResult(RESULT_LOCAL)
    void resultSelectUser(int resultCode, @OnActivityResult.Extra String val)
    {
        if (resultCode == RESULT_OK) {
//            T.showfunShort(this, val);
        }
    }

}
