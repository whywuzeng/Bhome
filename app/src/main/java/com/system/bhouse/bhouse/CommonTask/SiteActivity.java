package com.system.bhouse.bhouse.CommonTask;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.system.bhouse.bhouse.CommonTask.ReplenishmentRequire.ReplenishmentRequireActivity_;
import com.system.bhouse.bhouse.CommonTask.ReturnRequire.ReturnRequireActivity_;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.phone.activity.InformationActivity;

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
@EActivity(R.layout.site_layout_activity)
@OptionsMenu(R.menu.menu_site)
public class SiteActivity extends WWTimeLineActivity {

    private static final int RESULT_LOCAL = 2;

    @ViewById(R.id.my_recycle_view)
    RecyclerView my_recycle_view;

    @AfterViews
    void initSiteFragment() {
        setActionBarMidlleTitle("工地管理");
        initTimeLineActivity(my_recycle_view,R.array.site_labels);
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

}
