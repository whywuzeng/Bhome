package com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouse;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.bhouse.CommonTask.BaseTaskFragment.BaseCommonListFragment;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouse.Bean.LoadingIntoWareHouseBean;
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
 * 台车模具分离
 * <p>
 * com.system.bhouse.bhouse.ConfirmationReceipt
 */
@EFragment(R.layout.fragment_task_list)
public class LoadingIntoWareHouseListFragment extends BaseCommonListFragment<LoadingIntoWareHouseLoadingAdapter,LoadingIntoWareHouseBean> implements LoadingIntoWareHouseLoadingAdapter.onItemClickListener {

    LoadingIntoWareHouseLoadingAdapter mAdapter;

    //默认是 提交保存 。即可在此数组进行修改
    String[] ContentTitle= new String[]{"", "提交", "审核"};

    //接收更新数据
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == REFRESH_DATA) {
                setRefreshing(false);
                ArrayList<LoadingIntoWareHouseBean> loadedRequires = msg.getData().getParcelableArrayList(LOADEDREQUIREKEY);
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
        mAdapter = new LoadingIntoWareHouseLoadingAdapter(mData);
        baseCommoninitView(mAdapter);
        mAdapter.setDataList(mData);
        listView.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(this);
        setStatusContent(ContentTitle);
    }


    //加载主数据
    protected void loadData() {
        if (mUpdateAll) {
            mUpdateAll = false;
            ApiWebService.Get_Production_order_Container_Json(getActivity(), new ApiWebService.SuccessCall() {

                @Override
                public void SuccessBack(String result) {
                    L.e(result);
                    ArrayList<LoadingIntoWareHouseBean> loadedRequires = App.getAppGson().fromJson(result, new TypeToken<List<LoadingIntoWareHouseBean>>() {
                    }.getType());

                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(LOADEDREQUIREKEY, loadedRequires);
                    message.setData(bundle);
                    message.what = REFRESH_DATA;
                    handler.sendMessage(message);


//                    taskFragmentLoading(false);
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
//                    taskFragmentLoading(false);
                    if (isRefreshing()) {
                        setRefreshing(false);
                    }
                }
            }, TextUtils.isEmpty(mLabel) ? 50 : Integer.valueOf(mLabel),TextUtils.isEmpty(mStatus) ? ContentTitle[1] : mStatus, TextUtils.isEmpty(mKeyword) ? "" : mKeyword);

//            ApiWebService.Get_Production_order_ContainerView_Json(getActivity(), new ApiWebService.SuccessCall() {
//                @Override
//                public void SuccessBack(String result) {
//
//                }
//
//                @Override
//                public void ErrorBack(String error) {
//
//                }
//            },"eae810bb6ae14879aa8c45235ceea2c4");
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
    public void ItemClick(LoadingIntoWareHouseLoadingAdapter.ItemViewHolder holder, int position) {
        mNeedUpdate = true;
        StatusBean statusBean = getStatusBean();
        if (DefaultStatus.equals(ContentTitle[1])) {
            statusBean.getBean().setVisModifyBtn(true);
        }
        statusBean.setLookStatus(true);
        LoadingIntoWareHouseContentMessageActivity_.intent(getParentFragment()).HId(mData
                .get(position).getID() + "").receiptHnumber(mData.get(position).gethNumbe()).mStatus(statusBean).start();
    }

}
