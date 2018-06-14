package com.system.bhouse.bhouse.CommonTask.TransportationManagement.CarManagement;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.base.SubmitStatusBeanImpl;
import com.system.bhouse.bean.CarManageBean;
import com.system.bhouse.bhouse.CommonTask.BaseTaskFragment.BaseCommonFragment;
import com.system.bhouse.bhouse.CommonTask.adapter.PageTaskFragment;
import com.system.bhouse.bhouse.CommonTask.callback.TaskListParentUpdate;
import com.system.bhouse.bhouse.CommonTask.callback.TaskListUpdate;
import com.system.bhouse.bhouse.CommonTask.model.FilterModel;
import com.system.bhouse.bhouse.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-03-19.
 * <p>
 * by author wz
 * 车次管理
 * <p>
 * com.system.bhouse.bhouse.ConfirmationReceipt
 */
@EFragment(R.layout.comfragment_task)
public class CarManagementFragment extends BaseCommonFragment implements TaskListParentUpdate {

    private ConfirmationPageTaskFragment adapter;

    @AfterViews
    void initConfirmationReceiptFragment(){
        initPagerView();
        CarManagementListFragment confirmationReceiptListFragment = new CarManagementListFragment_();
        adapter = new ConfirmationPageTaskFragment(getChildFragmentManager(),mFilterModel,statusIndex,confirmationReceiptListFragment);
        pager.setAdapter(adapter);
        mFilterModel=new FilterModel();
        mFilterModel.isShow=false;

    }

    //新建的 跳转 ->activity
    @Override
    protected void AddIntentFor() {
        StatusBean statusBean = new StatusBean();
        statusBean.setBean(new SubmitStatusBeanImpl().setVisSubmitBtn(true));
        statusBean.setNewStatus(true);
//        CarManagementContentMessageActivity_.intent(getActivity()).HId("").mStatus(statusBean).start();

        ArrayList<CarManageBean> loadedRequires=new ArrayList<>();
        CarManageBean  parcelable=new CarManageBean();
        ApiWebService.Get_Car_No(getActivity(), new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                parcelable.setCarNo(result);
                CarManagementDialogFragment viewUtils = CarManagementDialogFragment.getInstanceFrg(statusBean, parcelable);

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
            CarManagementListFragment fragment = (CarManagementListFragment) super.instantiateItem(container, position);
            fragment.setParent(CarManagementFragment.this);
            return fragment;
        }
    }
}
