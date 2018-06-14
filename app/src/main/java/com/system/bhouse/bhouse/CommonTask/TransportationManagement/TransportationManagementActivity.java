package com.system.bhouse.bhouse.CommonTask.TransportationManagement;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.system.bhouse.bhouse.CommonTask.TransportationManagement.CarManagement.CarManagementActivity_;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.ComponentReturns.ComponentReturnsActivity_;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.ContainerRecycle.ContainerRecycleActivity_;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.LoadingCarOrder.LoadingCarOrderActivity_;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.TransportSendGoods.TransportSendGoodsActivity_;
import com.system.bhouse.bhouse.CommonTask.WWTimeLineActivity;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.phone.activity.InformationActivity;

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
@EActivity(R.layout.site_layout_activity)
@OptionsMenu(R.menu.menu_site)
public class TransportationManagementActivity extends WWTimeLineActivity {

    private static final int RESULT_LOCAL = 2;

    @ViewById(R.id.my_recycle_view)
    RecyclerView my_recycle_view;

    @AfterViews
    void initSiteFragment() {
        setActionBarMidlleTitle("运输管理");
        initTimeLineActivity(my_recycle_view,R.array.transportation_labels);

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

    @Override
    protected void IntentToFragment(int position) {
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
}
