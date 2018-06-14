package com.system.bhouse.fragment;

import android.content.Intent;
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
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.TransportationManagementActivity_;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.adapter.animator.ScaleItemAnimator;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBaseFragment;
import com.system.bhouse.bhouse.setup.notification.MyApprovalProcessed;
import com.system.bhouse.bhouse.setup.notification.adapter.MyDividerItemDecoration;
import com.system.bhouse.bhouse.setup.notification.adapter.NotificationSectionAdapter;
import com.system.bhouse.bhouse.setup.notification.bean.NotificationSectionBean;
import com.system.bhouse.bhouse.setup.notification.bean.XGNotification;
import com.system.bhouse.bhouse.setup.notification.bean.XGNotificationSectionEntity;
import com.system.bhouse.bhouse.setup.notification.receiver.NotificationService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-06-07.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.fragment
 */
@EFragment(R.layout.my_notification_fragment)
public class MyApprovalNotificationFragment extends WWBaseFragment implements OnRefreshListener,OnRefreshLoadMoreListener {

    @ViewById(R.id.rv_list)
    RecyclerView mRecyclerView;
    private List<NotificationSectionBean> mySections;
    private int allRecorders = 0;// 全部记录数
    private NotificationService notificationService;
    private NotificationSectionAdapter notificationSectionAdapter;
    private List<XGNotificationSectionEntity> scrollDataWrap;
    @ViewById(R.id.layout_head)
     ClassicsHeader LayoutHead;
    @ViewById(R.id.layout_footer)
     ClassicsFooter LayoutFooter;
    @ViewById( R.id.layout_smartrefresh)
     SmartRefreshLayout layout_smartrefresh;
    private CustomPopWindow mPopWindow;
    private View notDataView;

    @AfterViews
    public void initCreate() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyDividerItemDecoration myDividerItemDecoration = new MyDividerItemDecoration();
        mRecyclerView.addItemDecoration(myDividerItemDecoration);
        mRecyclerView.setItemAnimator(new ScaleItemAnimator());
        layout_smartrefresh.setOnRefreshListener(this);
        layout_smartrefresh.setOnLoadMoreListener(this);

        View headView = getActivity().getLayoutInflater().inflate(R.layout.mynotification_head, mRecyclerView, false);

        notDataView = getActivity().getLayoutInflater().inflate(R.layout.notificationcomon_empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        ImageView imgview = (ImageView) notDataView.findViewById(R.id.iv_tip);
        imgview.setImageResource(R.drawable.check_nodata);

        notificationService = NotificationService.getInstance(getActivity());

        notificationSectionAdapter = new NotificationSectionAdapter(R.layout.mynotification_item, R.layout.mynotification_head, scrollDataWrap);
        notificationSectionAdapter.addHeaderView(headView);
        getNotifications(id);


        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到已处理
                Intent intent = new Intent(getActivity(), MyApprovalProcessed.class);
                startActivity(intent);
                return;
            }
        });

        notificationSectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position==0)
                {
                }
                ImageView viewById = (ImageView) view.findViewById(R.id.iv_redpoint_item);
                viewById.setVisibility(View.GONE);
                XGNotification item = scrollDataWrap.get(position).t;
                item.setNews(false);
//                notificationService.update(item);
//                EventBus.getDefault().post(item);
                TransportationManagementActivity_.intent(getActivity()).start();
            }
        });

        mPopWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(R.layout.smallpopwindow)
                .enableOutsideTouchableDissmiss(true)// 设置点击PopupWindow之外的地方，popWindow不关闭，如果不设置这个属性或者为true，则关闭
                .create();


        notificationSectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId())
                {
                    case R.id.iv_three_dot:
                        view.setTag(position);
                        mPopWindow.showAsDropDown(view,0,0);
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
        allRecorders = notificationService.getNotApprovalCount();
        getNotificationswithouthint(id);
        Toast.makeText(
                getActivity(),
                "共" + allRecorders + "条信息,加载了" + notificationSectionAdapter.getData().size()
                        + "条信息", Toast.LENGTH_SHORT).show();
    }

    private void updateNotifications(String id) {
        // 计算总数据条数
        int oldAllRecorders = allRecorders;
        allRecorders = notificationService.getNotApprovalCount();
        getNotificationswithouthint(id);
        Toast.makeText(
                getActivity(),
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
        if (scrollDataWrap.isEmpty()) {
            //notdataVIew 已经有父亲了 。不然添加
            if (notDataView.getParent()==null)
            notificationSectionAdapter.addFooterView(notDataView);
        }
        else {
            // 创建适配器
            notificationSectionAdapter.setNewData(scrollDataWrap);
        }

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
        List<XGNotification> scrollData = NotificationService.getInstance(getActivity()).getScrollDataForApproval(
                currentPage , lineSize, "1");
        List<XGNotificationSectionEntity> scrollDataWrap =new ArrayList<>();
        for (XGNotification item:scrollData)
        {
            scrollDataWrap.add(new XGNotificationSectionEntity(item));
        }
        return scrollDataWrap;
    }


    private void appendNotifications(String id) {
        // 计算总数据条数
        allRecorders = notificationService.getNotApprovalCount();
        // 计算总页数
        pageSize = (allRecorders + lineSize - 1) / lineSize;
        int oldsize = notificationSectionAdapter.getData().size();

        List<XGNotificationSectionEntity> sectionScrollData = getSectionScrollData(id);
        if (sectionScrollData.isEmpty())
        {   //notdataVIew 已经有父亲了 。不然添加
            if (notDataView.getParent()==null)
            notificationSectionAdapter.addFooterView(notDataView);
        }else {
            notificationSectionAdapter.getData().addAll(sectionScrollData);
        }
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
                getActivity(),
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
        }else{
            layout_smartrefresh.setNoMoreData(true);
        }
    }
}
