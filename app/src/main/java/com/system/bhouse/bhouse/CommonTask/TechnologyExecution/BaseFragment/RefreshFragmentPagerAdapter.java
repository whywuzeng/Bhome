package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.BaseFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2018-07-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.BaseFragment
 */

public class RefreshFragmentPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager fm;

    public RefreshFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm=fm;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
