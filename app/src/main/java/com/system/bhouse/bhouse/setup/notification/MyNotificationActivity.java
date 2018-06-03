package com.system.bhouse.bhouse.setup.notification;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.system.bhouse.bean.BProBOM;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.TransportationManagementActivity_;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.entity.MySection;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.bhouse.setup.notification.adapter.MyDividerItemDecoration;
import com.system.bhouse.bhouse.setup.notification.adapter.NotificationSectionAdapter;
import com.system.bhouse.bhouse.setup.notification.bean.NotificationSectionBean;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-05-28.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.setup.notification
 */
@EActivity(R.layout.my_notification_activity)
public class MyNotificationActivity extends WWBackActivity {

    private RecyclerView mRecyclerView;
    private List<NotificationSectionBean> mySections;

    @AfterViews
    public void initCreate() {
        setActionBarMidlleTitle(getResources().getString(R.string.notificationtitle));
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyDividerItemDecoration myDividerItemDecoration = new MyDividerItemDecoration();
        mRecyclerView.addItemDecoration(myDividerItemDecoration);
        List<MySection> sampleData = getSampleData();

        NotificationSectionAdapter notificationSectionAdapter = new NotificationSectionAdapter(R.layout.mynotification_item, R.layout.mynotification_head, sampleData);


        mRecyclerView.setAdapter(notificationSectionAdapter);

        notificationSectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TransportationManagementActivity_.intent(MyNotificationActivity.this).start();
            }
        });
    }

    public static List<MySection> getSampleData() {
        List<MySection> list = new ArrayList<>();
        list.add(new MySection(true,"string",false));
        list.add(new MySection(new BProBOM()));
        list.add(new MySection(new BProBOM()));
        list.add(new MySection(new BProBOM()));
        list.add(new MySection(new BProBOM()));
        list.add(new MySection(new BProBOM()));
        list.add(new MySection(new BProBOM()));
        list.add(new MySection(new BProBOM()));
        list.add(new MySection(new BProBOM()));
        list.add(new MySection(new BProBOM()));
        list.add(new MySection(new BProBOM()));
        list.add(new MySection(new BProBOM()));
        list.add(new MySection(new BProBOM()));
        list.add(new MySection(new BProBOM()));
        return list;
    }
}
