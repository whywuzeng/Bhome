package com.system.bhouse.bhouse.CommonTask.TransportationManagement.TransportSendGoods;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.base.SubmitStatusBeanImpl;
import com.system.bhouse.bhouse.CommonTask.BaseTaskFragment.BaseCommonFragment;
import com.system.bhouse.bhouse.CommonTask.adapter.PageTaskFragment;
import com.system.bhouse.bhouse.CommonTask.callback.TaskListParentUpdate;
import com.system.bhouse.bhouse.CommonTask.callback.TaskListUpdate;
import com.system.bhouse.bhouse.CommonTask.model.FilterModel;
import com.system.bhouse.bhouse.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Administrator on 2018-03-19.
 * <p>
 * by author wz
 * 运输发货
 * <p>
 * com.system.bhouse.bhouse.ConfirmationReceipt
 */
@EFragment(R.layout.comfragment_task)
public class TransportSendGoodsFragment extends BaseCommonFragment implements TaskListParentUpdate {

    private ConfirmationPageTaskFragment adapter;

    @AfterViews
    void initConfirmationReceiptFragment(){
        initPagerView();
        TransportSendGoodsListFragment confirmationReceiptListFragment = new TransportSendGoodsListFragment_();
        adapter = new ConfirmationPageTaskFragment(getChildFragmentManager(),mFilterModel,statusIndex,confirmationReceiptListFragment);
        pager.setAdapter(adapter);
    }

    //新建的 跳转 ->activity
    @Override
    protected void AddIntentFor() {

        ApiWebService.Get_Sale_Order_Car_so_Number(getActivity(), new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                StatusBean statusBean = new StatusBean();
                statusBean.setBean(new SubmitStatusBeanImpl().setVisSubmitBtn(true));
                statusBean.setNewStatus(true);
                TransportSendGoodsContentMessageActivity_.intent(getActivity()).HId("").receiptHnumber(result).mStatus(statusBean).start();
            }

            @Override
            public void ErrorBack(String error) {

            }
        });

    }

    @Override
    public void taskListParentUpdate() {
        List<WeakReference<Fragment>> array = adapter.getFragments();
        for (WeakReference<Fragment> item : array) {
            Fragment fragment = item.get();
            if (fragment instanceof TaskListUpdate) {
                ((TaskListUpdate) fragment).taskListUpdate(true);
            }
        }
    }

    protected class ConfirmationPageTaskFragment extends PageTaskFragment {

        public ConfirmationPageTaskFragment(FragmentManager fm, FilterModel mFilterModel, int statusIndex, Fragment fragment) {
            super(fm, mFilterModel, statusIndex, fragment);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TransportSendGoodsListFragment fragment = (TransportSendGoodsListFragment) super.instantiateItem(container, position);
            fragment.setParent(TransportSendGoodsFragment.this);
            return fragment;
        }
    }
}
