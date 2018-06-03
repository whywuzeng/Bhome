package com.system.bhouse.bhouse.CommonTask.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;

import com.system.bhouse.bhouse.CommonTask.model.FilterModel;
import com.system.bhouse.bhouse.task.adpter.base.SaveFragmentPagerAdapter;

/**
 * Created by Administrator on 2018-03-19.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.ConfirmationReceipt
 */

public class PageTaskFragment extends SaveFragmentPagerAdapter  {

    private FilterModel mFilterModel;

    protected final String[] mMeActions = new String[]{"owner", "watcher", "creator"};

    private int statusIndex;

    private Fragment fragment;

    public PageTaskFragment(FragmentManager fm,FilterModel mFilterModel,int statusIndex,Fragment fragment) {
        super(fm);
        this.mFilterModel=mFilterModel;
        this.statusIndex=statusIndex;
        this.fragment=fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
//        bundle.putSerializable("mMembers", new TaskObject.Members(AccountInfo.loadAccount(getActivity())));
//        bundle.putSerializable("mProjectObject", "");
        bundle.putBoolean("mShowAdd", false);

        bundle.putString("mMeAction", mMeActions[statusIndex]);
        if (mFilterModel != null) {
            bundle.putString("mStatus", mFilterModel.status + "");
            bundle.putString("mLabel", mFilterModel.label);
            bundle.putString("mKeyword", mFilterModel.keyword);
        } else {
            bundle.putString("mStatus", "");
            bundle.putString("mLabel", "");
            bundle.putString("mKeyword", "");
        }
        fragment.setArguments(bundle);

        saveFragment(fragment);

        return fragment;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
