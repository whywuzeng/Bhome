package com.system.bhouse.bhouse.setup.notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhouwei.library.CustomPopWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import net.qiujuer.italker.common.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.adapter.animator.ScaleItemAnimator;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.bhouse.setup.notification.adapter.MyDividerItemDecoration;
import com.system.bhouse.bhouse.setup.notification.adapter.NotificationSectionAdapter;
import com.system.bhouse.bhouse.setup.notification.bean.NotificationSectionBean;
import com.system.bhouse.bhouse.setup.notification.bean.XGNotification;
import com.system.bhouse.bhouse.setup.notification.bean.XGNotificationSectionEntity;
import com.system.bhouse.bhouse.setup.notification.receiver.NotificationService;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2018-06-11.
 * <p>
 * by author wz
 * 查看消息已处理
 * <p>
 * com.system.bhouse.bhouse.setup.notification
 */
public class MyApprovalProcessed extends WWBackActivity implements OnRefreshListener, OnRefreshLoadMoreListener {
    private RecyclerView mRecyclerView;
    private List<NotificationSectionBean> mySections;
    private int allRecorders = 0;// 全部记录数
    private NotificationService notificationService;
    private NotificationSectionAdapter notificationSectionAdapter;
    private List<XGNotificationSectionEntity> scrollDataWrap;
    private ClassicsHeader LayoutHead;
    private ClassicsFooter LayoutFooter;
    private SmartRefreshLayout layout_smartrefresh;
    private CustomPopWindow mPopWindow;
    private View notDataView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_notification_activity);
        annotationDispayHomeAsUp();
        setActionBarMidlleTitle(getResources().getString(R.string.look_approval));
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        LayoutHead = (ClassicsHeader) findViewById(R.id.layout_head);
        LayoutFooter = (ClassicsFooter) findViewById(R.id.layout_footer);
        layout_smartrefresh = (SmartRefreshLayout) findViewById(R.id.layout_smartrefresh);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyDividerItemDecoration myDividerItemDecoration = new MyDividerItemDecoration();
        mRecyclerView.addItemDecoration(myDividerItemDecoration);
        mRecyclerView.setItemAnimator(new ScaleItemAnimator());
        layout_smartrefresh.setOnRefreshListener(this);
        layout_smartrefresh.setOnLoadMoreListener(this);
        notDataView = getLayoutInflater().inflate(R.layout.notificationcomon_empty_view, (ViewGroup) mRecyclerView.getParent(), false);

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
                Intent intent = new Intent(MyApprovalProcessed.this, MyApprovalDetailActivity.class);
                startActivity(intent);
            }
        });

        mPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(R.layout.smallpopwindow)
                .enableOutsideTouchableDissmiss(true)// 设置点击PopupWindow之外的地方，popWindow不关闭，如果不设置这个属性或者为true，则关闭
                .create();


        notificationSectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_three_dot:
                        view.setTag(position);
                        mPopWindow.showAsDropDown(view, 0, 0);
                        mPopWindow.getPopupWindow().getContentView().findViewById(R.id.ll_popwindow).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPopWindow.onDismiss();
                                scrollDataWrap.remove(position);
                                notificationSectionAdapter.notifyItemRemoved(position);
//                                notificationSectionAdapter.notifyDataSetChanged();

                            }
                        });
                        break;
                }
            }
        });
    }


    //加载数据
    private void getNotifications(String id) {
        // 计算总数据条数
        allRecorders = notificationService.getApprovalCount();
        getNotificationswithouthint(id);
        Toast.makeText(
                this,
                "共" + allRecorders + "条信息,加载了" + notificationSectionAdapter.getData().size()
                        + "条信息", Toast.LENGTH_SHORT).show();
    }

    private void updateNotifications(String id) {
        // 计算总数据条数
        int oldAllRecorders = allRecorders;
        allRecorders = notificationService.getApprovalCount();
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

        scrollDataWrap = getSectionScrollData(id);

        if (scrollDataWrap.isEmpty()) {
            notificationSectionAdapter.setEmptyView(notDataView);
        }
        else {
            // 创建适配器
            notificationSectionAdapter.setNewData(scrollDataWrap);
        }
        if (allRecorders <= lineSize) {
            LayoutFooter.setVisibility(View.GONE);
        }
        else {
        }
        mRecyclerView.setAdapter(notificationSectionAdapter);
    }
    //获取已处理的消息
    private List<XGNotificationSectionEntity> getSectionScrollData(String id) {
        List<XGNotification> scrollData = NotificationService.getInstance(this).getScrollDataForApproval(
                currentPage, lineSize, "0");
        List<XGNotificationSectionEntity> scrollDataWrap = new ArrayList<>();
        for (XGNotification item : scrollData) {
            scrollDataWrap.add(new XGNotificationSectionEntity(item));
        }
        return scrollDataWrap;
    }


    private void appendNotifications(String id) {
        // 计算总数据条数
        allRecorders = notificationService.getApprovalCount();
        // 计算总页数
        pageSize = (allRecorders + lineSize - 1) / lineSize;
        int oldsize = notificationSectionAdapter.getData().size();

        // 更新适配器
        List<XGNotificationSectionEntity> sectionScrollData = getSectionScrollData(id);
        if (sectionScrollData.isEmpty())
        {
            notificationSectionAdapter.setEmptyView(notDataView);
        }else {
            notificationSectionAdapter.getData().addAll(sectionScrollData);
        }
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
        if (currentPage < pageSize) {
            currentPage++;
            //设置显示位置
            appendNotifications(id);
        }
        else {
            layout_smartrefresh.setNoMoreData(true);
        }
    }
}

