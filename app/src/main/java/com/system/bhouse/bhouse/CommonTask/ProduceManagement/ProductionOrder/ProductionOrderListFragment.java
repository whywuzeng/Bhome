package com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.bhouse.CommonTask.BaseTaskFragment.BaseCommonListFragment;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder.ProductionOrderLoadingAdapter.onItemClickListener;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.entity.productionOrderBean;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.TenUtils.L;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-03-19.
 * <p>
 * by author wz
 * 生产订单
 * <p>
 * com.system.bhouse.bhouse.ConfirmationReceipt
 */
@EFragment(R.layout.fragment_task_list)
public class ProductionOrderListFragment extends BaseCommonListFragment<ProductionOrderLoadingAdapter,productionOrderBean> implements onItemClickListener {

    ProductionOrderLoadingAdapter mAdapter;

    //接收更新数据
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == REFRESH_DATA) {
                setRefreshing(false);
                ArrayList<productionOrderBean> loadedRequires = msg.getData().getParcelableArrayList(LOADEDREQUIREKEY);
                if (loadedRequires.size()==0)
                {
                    if (mNoData) {
                        mAdapter.setEmptyView(notDataView);
                        AllCopymData.clear();
                        AllCopymData.addAll(loadedRequires);
                        mData.clear();
                        mAdapter.setDataList(mData);
                        mNoData=false;
                    }
                }else if (loadedRequires.size() > 0) {
                    AllCopymData.clear();
                    AllCopymData.addAll(loadedRequires);
                    mData.clear();
                    SequenceData();
                }
            }
        }
    };

    @AfterViews
    public void initConfirmation()
    {
        initRefreshLayout();
        mAdapter = new ProductionOrderLoadingAdapter(mData);
        baseCommoninitView(mAdapter);
        mAdapter.setDataList(mData);
        listView.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(this);
    }


    //加载主数据
    protected void loadData() {
        if (mUpdateAll) {
            mUpdateAll = false;
            ApiWebService.Get_Production_order_Json(getActivity(), new ApiWebService.SuccessCall() {

                @Override
                public void SuccessBack(String result) {
                    L.e(result);
                    ArrayList<productionOrderBean> loadedRequires = App.getAppGson().fromJson(result, new TypeToken<List<productionOrderBean>>() {
                    }.getType());

                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(LOADEDREQUIREKEY, loadedRequires);
                    message.setData(bundle);
                    message.what = REFRESH_DATA;
                    handler.sendMessage(message);
                }

                @Override
                public void ErrorBack(String error) {
                    if (mError) {
                        mError=false;
                        mAdapter.setEmptyView(errorView);
                    }
                    L.e(error);
                }
            }, TextUtils.isEmpty(mLabel) ? 50 : Integer.valueOf(mLabel), TextUtils.isEmpty(mStatus) ? "提交" : mStatus, TextUtils.isEmpty(mKeyword) ? "" : mKeyword);

        }

        ApiWebService.Get_Production_orderView_Json(getActivity(), new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {

            }

            @Override
            public void ErrorBack(String error) {

            }
        },"16c5357b9c6f41198c783cf755e9cf4c");
    }

    //刷新本数据，以及根据业务获取，字段。Hnumber->订单编号
    @Override
    protected void AddDeleteUpdateListData(Object object) {
        if (object.toString().contains("tvSubmitAction"))
        {
        }
        //重新加载所有
        mUpdateAll = true;
        onRefresh();
    }

    //list Item的点击事件
    @Override
    public void ItemClick(ProductionOrderLoadingAdapter.ItemViewHolder holder, int position) {
        mNeedUpdate = true;
        StatusBean statusBean = getStatusBean();
        statusBean.getBean().setVisModifyBtn(true).setVisSubmitBtn(true).setVisQRBtn(true);
        if (DefaultStatus.equals("提交")) {
        }
        statusBean.setLookStatus(true);
        ProductionOrderContentMessageActivity_.intent(getParentFragment()).HId(mData
                .get(position).getID() + "").receiptHnumber(mData.get(position).gethNumbe()).mStatus(statusBean).start();
    }

}
