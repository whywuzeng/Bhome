package com.system.bhouse.bhouse.CommonTask.TransportationManagement.CarManagement;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.base.SubmitStatusBeanImpl;
import com.system.bhouse.bean.CarManageBean;
import com.system.bhouse.bhouse.CommonTask.BaseTaskFragment.BaseCommonListFragment;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.TenUtils.L;
import com.system.bhouse.utils.TenUtils.T;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by Administrator on 2018-03-19.
 * <p>
 * by author wz
 * 车次管理
 * <p>
 * com.system.bhouse.bhouse.ConfirmationReceipt
 */
@EFragment(R.layout.fragment_task_list)
public class CarManagementListFragment extends BaseCommonListFragment<CarManagementLoadingAdapter,CarManageBean> implements CarManagementLoadingAdapter.onItemClickListener {

    CarManagementLoadingAdapter mAdapter;

    //接收更新数据
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == REFRESH_DATA) {
                ArrayList<CarManageBean> loadedRequires = msg.getData().getParcelableArrayList(LOADEDREQUIREKEY);
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
    public final static String ITEMDATA="itemdata";
    public final static String ITEMSTATUS="itemstatus";

    @AfterViews
    public void initConfirmation()
    {
        initRefreshLayout();
        mAdapter = new CarManagementLoadingAdapter(mData);
        baseCommoninitView(mAdapter);
        mAdapter.setDataList(mData);
        listView.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(this);
    }


    //加载主数据
    protected void loadData() {
        if (mUpdateAll) {
            mUpdateAll = false;

            ApiWebService.Get_Car_list_Json(getActivity(), new ApiWebService.SuccessCall() {

                @Override
                public void SuccessBack(String result) {
                    L.e(result);
                    ArrayList<CarManageBean> loadedRequires = App.getAppGson().fromJson(result, new TypeToken<List<CarManageBean>>() {
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
            }, TextUtils.isEmpty(mLabel) ? 50 : Integer.valueOf(mLabel), TextUtils.isEmpty(mKeyword) ? "" : mKeyword, App.GSMID, App.Property, App.IsSub, App.USER_INFO);

        }

//        ApiWebService.Get_Car_listView_Json(getActivity(), new ApiWebService.SuccessCall() {
//            @Override
//            public void SuccessBack(String result) {
//
//            }
//
//            @Override
//            public void ErrorBack(String error) {
//
//            }
//        },"b976a632d2554aa19eaf8f648218da8e",App.GSMID,App.Property,App.IsSub);
    }

    //刷新本数据，以及根据业务获取，字段。Hnumber->订单编号
    @Override
    protected void AddDeleteUpdateListData(Object object) {
        String[] split = object.toString().split("result:");
        if (object.toString().contains("tvSubmitAction"))
        {

        }
        if (!TextUtils.isEmpty(split[1]))
        T.showShort(getActivity(),split[1]);
        //重新加载所有
        mUpdateAll = true;
        onRefresh();
    }

    //list Item的点击事件
    @Override
    public void ItemClick(CarManagementLoadingAdapter.ItemViewHolder holder, int position) {
        mNeedUpdate = true;
        StatusBean statusBean = new StatusBean();
        statusBean.setBean(new SubmitStatusBeanImpl());
        statusBean.setLookStatus(true);
        statusBean.setModifyStatus(true);

        ApiWebService.Get_Car_listView_Json(mData.get(position).getID(), App.GSMID,App.Property,App.IsSub).subscribe(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                CarManageBean bean=new CarManageBean();
                CarManagementDialogFragment viewUtils = CarManagementDialogFragment.getInstanceFrg(statusBean, bean);

                ShowCarManagementDialog(viewUtils);
            }

            @Override
            public void onNext(Object o) {
                ArrayList<CarManageBean> loadedRequires = App.getAppGson().fromJson(o.toString(), new TypeToken<List<CarManageBean>>() {
                }.getType());
                CarManagementDialogFragment viewUtils = CarManagementDialogFragment.getInstanceFrg(statusBean, loadedRequires.get(0));
                ShowCarManagementDialog(viewUtils);
            }
        });

    }

    private void ShowCarManagementDialog(CarManagementDialogFragment viewUtils) {
        if (viewUtils != null && viewUtils.getDialog() != null
                && viewUtils.getDialog().isShowing()) {
            //dialog is showing so do something
            viewUtils.dismiss();
        }
        else {
            //dialog is not showing
            viewUtils.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "");
        }
    }

}
