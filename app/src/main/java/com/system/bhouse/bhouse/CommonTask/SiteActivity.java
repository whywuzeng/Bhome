package com.system.bhouse.bhouse.CommonTask;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.system.bhouse.bhouse.CommonTask.ReplenishmentRequire.ReplenishmentRequireActivity_;
import com.system.bhouse.bhouse.CommonTask.ReturnRequire.ReturnRequireActivity_;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.CommonTask.Widget.TimeLineItemDecoration;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-02-28.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask
 */
@EActivity(R.layout.site_layout_activity)
@OptionsMenu(R.menu.menu_site)
public class SiteActivity extends WWBackActivity implements StepsView.DataClick{

    private static final int RESULT_LOCAL = 2;

    @ViewById(R.id.my_recycle_view)
    RecyclerView my_recycle_view;

    @AfterViews
    void initSiteFragment() {
        setActionBarMidlleTitle("工地管理");
//        viewStepsview.setDataClick(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        my_recycle_view.setLayoutManager(linearLayoutManager);
        my_recycle_view.addItemDecoration(new TimeLineItemDecoration());
        List<String> data=new ArrayList<>();
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        BaseQuickAdapter adapter = new BaseQuickAdapter<String, MyBaseViewHolder>(R.layout.timeline_item){
            @Override
            protected void convert(MyBaseViewHolder helper, String item) {

            }
        };
        adapter.setNewData(data);

        my_recycle_view.setAdapter(adapter);
    }

    static class MyBaseViewHolder extends BaseViewHolder{

        public MyBaseViewHolder(View view) {
            super(view);
        }
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
