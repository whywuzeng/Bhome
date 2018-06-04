package com.system.bhouse.bhouse.setup.notification;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.TransportationManagementActivity_;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.bhouse.setup.notification.adapter.MyDividerItemDecoration;
import com.system.bhouse.bhouse.setup.notification.adapter.NotificationSectionAdapter;
import com.system.bhouse.bhouse.setup.notification.bean.NotificationSectionBean;
import com.system.bhouse.bhouse.setup.notification.bean.XGNotification;
import com.system.bhouse.bhouse.setup.notification.bean.XGNotificationSectionEntity;
import com.system.bhouse.bhouse.setup.notification.receiver.NotificationService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2018-05-28.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.setup.notification
 */
@EActivity(R.layout.my_notification_activity)
public class MyNotificationActivity extends WWBackActivity implements OnRefreshListener,OnRefreshLoadMoreListener{

    private RecyclerView mRecyclerView;
    private List<NotificationSectionBean> mySections;
    private int allRecorders = 0;// 全部记录数
    private NotificationService notificationService;
    private NotificationSectionAdapter notificationSectionAdapter;
    private List<XGNotificationSectionEntity> scrollDataWrap;
    private ClassicsHeader LayoutHead;
    private ClassicsFooter LayoutFooter;
    private SmartRefreshLayout layout_smartrefresh;

    @AfterViews
    public void initCreate() {
        setActionBarMidlleTitle(getResources().getString(R.string.notificationtitle));
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        LayoutHead=(ClassicsHeader)findViewById(R.id.layout_head);
        LayoutFooter=(ClassicsFooter)findViewById(R.id.layout_footer);
        layout_smartrefresh=(SmartRefreshLayout)findViewById(R.id.layout_smartrefresh);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyDividerItemDecoration myDividerItemDecoration = new MyDividerItemDecoration();
        mRecyclerView.addItemDecoration(myDividerItemDecoration);
        layout_smartrefresh.setOnRefreshListener(this);
        layout_smartrefresh.setOnLoadMoreListener(this);

        notificationService = NotificationService.getInstance(this);

        notificationSectionAdapter = new NotificationSectionAdapter(R.layout.mynotification_item, R.layout.mynotification_head, scrollDataWrap);

        getNotifications(id);
        notificationSectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ImageView viewById = (ImageView) view.findViewById(R.id.iv_redpoint_item);
                viewById.setVisibility(View.GONE);
                XGNotification item = scrollDataWrap.get(position).t;
                item.setNews(false);
                notificationService.update(item);
                EventBus.getDefault().post(item);
                TransportationManagementActivity_.intent(MyNotificationActivity.this).start();
            }
        });
    }
    //加载数据
    private void getNotifications(String id) {
        // 计算总数据条数
        allRecorders = notificationService.getCount();
        getNotificationswithouthint(id);
        Toast.makeText(
                this,
                "共" + allRecorders + "条信息,加载了" + notificationSectionAdapter.getData().size()
                        + "条信息", Toast.LENGTH_SHORT).show();
    }

    private void updateNotifications(String id) {
        // 计算总数据条数
        int oldAllRecorders = allRecorders;
        allRecorders = notificationService.getCount();
        getNotificationswithouthint(id);
        Toast.makeText(
                this,
                "共" + allRecorders + "条信息,更新了"
                        + (allRecorders - oldAllRecorders) + "条新信息",
                Toast.LENGTH_SHORT).show();
        layout_smartrefresh.finishRefresh(200);
    }

    private static final int lineSize = 10;// 每次显示数
    private int pageSize = 1;// 默认共1页
    private int currentPage = 1;// 默认第一页
    private String id = "";// 查询条件

    private void getNotificationswithouthint(String id) {
        // 计算总页数
        pageSize = (allRecorders + lineSize - 1) / lineSize;

        scrollDataWrap=getSectionScrollData(id);

        // 创建适配器
        notificationSectionAdapter.setNewData(scrollDataWrap);
        if (allRecorders <= lineSize) {
//            bloadLayout.setVisibility(View.GONE);
//            bloadInfo.setHeight(0);
//            bloadLayout.setMinimumHeight(0);
            LayoutFooter.setVisibility(View.GONE);
        } else {
//            if (mRecyclerView.getFooterViewsCount() < 1) {
//                bloadLayout.setVisibility(View.VISIBLE);
//                bloadInfo.setHeight(50);
//                bloadLayout.setMinimumHeight(100);
//            }
        }
        mRecyclerView.setAdapter(notificationSectionAdapter);
    }

    private List<XGNotificationSectionEntity> getSectionScrollData(String id) {
        List<XGNotification> scrollData = NotificationService.getInstance(this).getScrollData(
                currentPage , lineSize, id);
       List<XGNotificationSectionEntity> scrollDataWrap =new ArrayList<>();
        for (XGNotification item:scrollData)
        {
            scrollDataWrap.add(new XGNotificationSectionEntity(item));
        }
        return scrollDataWrap;
    }


    private void appendNotifications(String id) {
        // 计算总数据条数
        allRecorders = notificationService.getCount();
        // 计算总页数
        pageSize = (allRecorders + lineSize - 1) / lineSize;
        int oldsize = notificationSectionAdapter.getData().size();

        // 更新适配器
        notificationSectionAdapter.getData().addAll(getSectionScrollData(id));
        // 如果到了最末尾则去掉"正在加载"
//        if (allRecorders == notificationSectionAdapter.getData().size()) {
//            bloadInfo.setHeight(0);
//            bloadLayout.setMinimumHeight(0);
//            bloadLayout.setVisibility(View.GONE);
//        } else {
//            bloadInfo.setHeight(50);
//            bloadLayout.setMinimumHeight(100);
//            bloadLayout.setVisibility(View.VISIBLE);
//        }
        Toast.makeText(
                this,
                "共" + allRecorders + "条信息,加载了"
                        + (notificationSectionAdapter.getData().size() - oldsize) + "条信息",
                Toast.LENGTH_SHORT).show();
        // 通知改变
        notificationSectionAdapter.notifyDataSetChanged();
        layout_smartrefresh.finishLoadMore(200);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        updateNotifications(id);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (currentPage<pageSize)
        {
            currentPage++;
            //设置显示位置
            appendNotifications(id);
        }
    }
}
