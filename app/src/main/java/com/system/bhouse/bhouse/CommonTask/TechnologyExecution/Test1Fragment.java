package com.system.bhouse.bhouse.CommonTask.TechnologyExecution;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.CheckStatusBeanImpl;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.base.SubmitStatusBeanImpl;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.BaseFragment.SwipeItemLayout;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.ModuleAssignMentContentMessageActivity_;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity.RelatedDetailBean;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.CommonTask.Widget.TimeLineItemTopBottomDecoration;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.LazyFragment;
import com.system.bhouse.config.Const;
import com.system.bhouse.utils.ValueUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2018-07-17.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution
 */

public class Test1Fragment extends LazyFragment implements ItemTouchListener, BaseQuickAdapter.OnItemChildClickListener {

    @Bind(R.id.my_recycle_view)
    RecyclerView my_recycle_view;

    //data 工序数据
    protected List<String> data=new ArrayList<>();

    //data工序数组
    protected String[] stringArray;

    protected BaseQuickAdapter<RelatedDetailBean, MyBaseViewHolder> adapter;
    private View notDataView;
    private View errorView;
    private ArrayList<RelatedDetailBean> bean;
    private StatusBean mStatusBean;

    private String componentQr;

    private String orderId;

    //初始化布局
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        super.setContentView(R.layout.technology_layout_fragment2);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        my_recycle_view.setLayoutManager(linearLayoutManager);

        mStatusBean=new StatusBean();

        my_recycle_view.addItemDecoration(new TimeLineItemTopBottomDecoration());

        notDataView = inflater.inflate(R.layout.taskcomon_empty_view, (ViewGroup) my_recycle_view.getParent(), false);
        errorView = inflater.inflate(R.layout.taskcommon_error_view, (ViewGroup) my_recycle_view.getParent(), false);

        adapter = new BaseQuickAdapter<RelatedDetailBean, MyBaseViewHolder>(R.layout.multiple_right_menu) {
            @Override
            protected void convert(MyBaseViewHolder helper, RelatedDetailBean item) {
                helper.setText(R.id.tv_title, item.getDocumentName());
                helper.setText(R.id.tv_sub_title, App.Mancompany);
                if (item.getDocumentStatus().equals("已完成")) {
                    helper.setBackgroundRes(R.id.relative_bg, R.drawable.bg_timeline_btn_disable);

                }else{
                    helper.setBackgroundRes(R.id.relative_bg, R.drawable.bg_timeline_btn_normal);
                }
                helper.setGone(R.id.right_menu,item.getDocumentStatus().equals("已完成"));

                helper.setGone(R.id.left_menu,item.getDocumentStatus().equals(""));

                helper.addOnClickListener(R.id.right_menu);
                helper.addOnClickListener(R.id.left_menu);

                SwipeItemLayout layout = (SwipeItemLayout) helper.getView(R.id.swipe_layout);

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (layout.isOpen())
                        {
                            layout.close();
                        }else {
                            layout.open();
                        }
                    }
                });


//                if (Rightview != null) {
//                    Rightview.setOnClickListener(v -> {
////                        mItemTouchListener.onRightMenuClick("right " + helper.getAdapterPosition());
//                        layout.close();
//                    });
//                }
            }
        };
        my_recycle_view.setAdapter(adapter);
        adapter.setOnItemChildClickListener(this);

    }

    @Override
    public void sendRelatedDetail(String componentQr, String orderId, ArrayList<RelatedDetailBean> bean) {
        this.componentQr=componentQr;
        this.orderId=orderId;
        this.bean = bean;

    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        if (ValueUtils.IsFirstValueExist(this.bean))
        {
            adapter.setNewData(this.bean);
        }else {
            adapter.setNewData(this.bean);
            adapter.setEmptyView(notDataView);
        }

    }

    /**
     * 删除 查看 修改 三个按钮的调用
     * @param adapter
     * @param view     The view whihin the ItemView that was clicked
     * @param position The position of the view int the adapter
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId())
        {
            //查看
            case R.id.right_menu:
               if (this.bean.get(position).getDocumentStatus().equals(Const.Completed_STATUS))
               {
                    //审核状态
                   CheckStatusBeanImpl checkStatusBean = new CheckStatusBeanImpl();
                   checkStatusBean.setVisCheckFBtn(true);
                   mStatusBean.setBean(checkStatusBean);
                   mStatusBean.setLookStatus(true);
               }
                ModuleAssignMentContentMessageActivity_.intent(this).HId(this.bean.get(position).getSoutceID()).mStatus(mStatusBean).start();
                break;

            case R.id.left_menu:
                //新建
                ApiWebService.Get_Production_order_Mould_po_Number(getActivity(), new ApiWebService.SuccessCall() {
                    @Override
                    public void SuccessBack(String result) {
                        StatusBean statusBean = new StatusBean();
                        statusBean.setBean(new SubmitStatusBeanImpl().setVisSubmitBtn(true).setVisQRBtn(true));
                        statusBean.setNewStatus(true);
                        ModuleAssignMentContentMessageActivity_.intent(getActivity()).HId("").receiptHnumber(result).componentQr(componentQr).orderId(orderId).mStatus(statusBean).start();
                    }

                    @Override
                    public void ErrorBack(String error) {

                    }
                });
                break;
        }
    }

    protected static class MyBaseViewHolder extends BaseViewHolder {

        public MyBaseViewHolder(View view) {
            super(view);
        }
    }
}
