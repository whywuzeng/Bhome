package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.BaseFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-07-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.BaseFragment
 */

public abstract class WWSaveFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<WeakReference<Fragment>> list=new ArrayList<>();

    public void save(Fragment fragment) {
        if (fragment == null)
        {
            return;
        }
        for (WeakReference<Fragment> item : list)
        {
            if (item.get()==fragment)
            {
                return;
            }
        }
        list.add(new WeakReference<Fragment>(fragment));
    }

    public List<WeakReference<Fragment>> getFragment(){
        for (int i=list.size()-1;i>=0;i--)
        {
            if (list.get(i).get()==null)
            {
                list.remove(i);
            }
        }
        return list;
    }

    public WWSaveFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
}
