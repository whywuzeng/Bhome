package com.system.bhouse.bhouse.CommonTask;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.system.bhouse.api.ApiWebService;
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

import static com.system.bhouse.base.App.GSMID;
import static com.system.bhouse.base.App.HNumber;
import static com.system.bhouse.base.App.IsSub;
import static com.system.bhouse.base.App.Property;

/**
 * Created by Administrator on 2018-03-19.
 * <p>
 * by author wz
 * 吊装需求
 * <p>
 * com.system.bhouse.bhouse.ConfirmationReceipt
 */
@EFragment(R.layout.comfragment_task)
public class HangRequiretFragment extends BaseCommonFragment implements TaskListParentUpdate {

    private ConfirmationPageTaskFragment adapter;

    @AfterViews
    void initConfirmationReceiptFragment(){
        initPagerView();
        HangRequiretListFragment confirmationReceiptListFragment = new HangRequiretListFragment_();
        adapter = new ConfirmationPageTaskFragment(getChildFragmentManager(),mFilterModel,statusIndex,confirmationReceiptListFragment);
        pager.setAdapter(adapter);

        ApiWebService.Get_Hois_Req_h_Number(getActivity(), new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                HNumber=result;
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, GSMID, Property, IsSub);
    }

    //新建的 跳转 ->activity
    @Override
    protected void AddIntentFor() {
        ComTaskContentMessageActivity_.intent(getActivity()).HId("").IsNew(true).start();
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
            HangRequiretListFragment fragment = (HangRequiretListFragment) super.instantiateItem(container, position);
            fragment.setParent(HangRequiretFragment.this);
            return fragment;
        }
    }
}
