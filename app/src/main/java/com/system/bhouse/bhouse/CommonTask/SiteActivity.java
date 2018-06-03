package com.system.bhouse.bhouse.CommonTask;

import android.content.Intent;
import android.view.View;

import com.system.bhouse.bhouse.CommonTask.ReplenishmentRequire.ReplenishmentRequireActivity_;
import com.system.bhouse.bhouse.CommonTask.ReturnRequire.ReturnRequireActivity_;
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
 * Created by Administrator on 2018-02-28.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask
 */
@EActivity(R.layout.siteactivity_layout)
@OptionsMenu(R.menu.menu_site)
public class SiteActivity extends WWBackActivity implements StepsView.DataClick{

    private static final int RESULT_LOCAL = 2;

    @ViewById
    StepsView viewStepsview;

    @AfterViews
    void initSiteFragment() {
        setActionBarMidlleTitle("工地管理");
        viewStepsview.setDataClick(this);
    }

    @Override
    public void onDataClick(int position, View v) {
        switch (position)
        {
            case 0:
                ComTaskActivity_.intent(this).start();
                break;
            case 1:
                ConfirmationReceipActivity_.intent(this).start();
                break;
            case 2:
               ReturnRequireActivity_.intent(this).start();
                break;
            case 3:
                ReplenishmentRequireActivity_.intent(this).start();
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
