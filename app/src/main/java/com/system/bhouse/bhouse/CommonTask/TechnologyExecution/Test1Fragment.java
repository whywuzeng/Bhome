package com.system.bhouse.bhouse.CommonTask.TechnologyExecution;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.BaseFragment.SwipeItemLayout;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity.RelatedDetailBean;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.CommonTask.Widget.TimeLineItemTopBottomDecoration;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.LazyFragment;
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

public class Test1Fragment extends LazyFragment implements ItemTouchListener{

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

    //初始化布局
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        super.setContentView(R.layout.technology_layout_fragment2);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        my_recycle_view.setLayoutManager(linearLayoutManager);

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
                TextView Rightview = (TextView) helper.getView(R.id.right_menu);
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
    }

    @Override
    public void sendRelatedDetail(ArrayList<RelatedDetailBean> bean) {
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

    protected static class MyBaseViewHolder extends BaseViewHolder {

        public MyBaseViewHolder(View view) {
            super(view);
        }
    }
}
