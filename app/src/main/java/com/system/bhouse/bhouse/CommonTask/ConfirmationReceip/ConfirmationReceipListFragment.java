package com.system.bhouse.bhouse.CommonTask.ConfirmationReceip;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.bean.ConfirmationReceBean;
import com.system.bhouse.bhouse.CommonTask.BaseTaskFragment.BaseCommonListFragment;
import com.system.bhouse.bhouse.CommonTask.adapter.ConfirmationLoadingAdapter;
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
 * 收货确认
 * <p>
 * com.system.bhouse.bhouse.ConfirmationReceipt
 */
@EFragment(R.layout.fragment_task_list)
public class ConfirmationReceipListFragment extends BaseCommonListFragment<ConfirmationLoadingAdapter,ConfirmationReceBean> implements ConfirmationLoadingAdapter.onItemClickListener {

    ConfirmationLoadingAdapter mAdapter;

    //接收更新数据
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == REFRESH_DATA) {
                setRefreshing(false);
                ArrayList<ConfirmationReceBean> loadedRequires = msg.getData().getParcelableArrayList(LOADEDREQUIREKEY);
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
        mAdapter = new ConfirmationLoadingAdapter(mData);
        baseCommoninitView(mAdapter);
        mAdapter.setDataList(mData);
        listView.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(this);
    }


    //加载主数据
    protected void loadData() {
        if (mUpdateAll) {
            mUpdateAll = false;
            ApiWebService.Get_Sale_Order_Car_qr_Json(getActivity(), new ApiWebService.SuccessCall() {

                @Override
                public void SuccessBack(String result) {
                    L.e(result);
                    ArrayList<ConfirmationReceBean> loadedRequires = App.getAppGson().fromJson(result, new TypeToken<List<ConfirmationReceBean>>() {
                    }.getType());

                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(LOADEDREQUIREKEY, loadedRequires);
                    message.setData(bundle);
                    message.what = REFRESH_DATA;
                    handler.sendMessage(message);


                    taskFragmentLoading(false);
                    if (isRefreshing()) {
                        setRefreshing(false);
                    }
                }

                @Override
                public void ErrorBack(String error) {
                    if (mError) {
                        mError=false;
                        mAdapter.setEmptyView(errorView);
                    }
                    L.e(error);
                    taskFragmentLoading(false);
                    if (isRefreshing()) {
                        setRefreshing(false);
                    }
                }
            }, TextUtils.isEmpty(mLabel) ? 50 : Integer.valueOf(mLabel), TextUtils.isEmpty(mStatus) ? "提交" : mStatus, TextUtils.isEmpty(mKeyword) ? "" : mKeyword, App.GSMID, App.Property, App.IsSub, App.USER_INFO);
        }
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
    public void ItemClick(ConfirmationLoadingAdapter.ItemViewHolder holder, int position) {
        mNeedUpdate = true;
        StatusBean statusBean = getStatusBean();
        if (DefaultStatus.equals("提交")) {
            statusBean.getBean().setVisModifyBtn(true);
        }
        statusBean.setLookStatus(true);
        ConfirmationContentMessageActivity_.intent(getParentFragment()).HId(mData
                .get(position).getID() + "").receiptHnumber(mData.get(position).receiptHnumber).mStatus(statusBean).start();
    }

}
