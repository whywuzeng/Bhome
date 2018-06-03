package com.system.bhouse.bhouse.task.adpter.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-11-9.
 */

public abstract class SaveFragmentPagerAdapter extends FragmentStatePagerAdapter{

    private List<WeakReference<Fragment>> mList=new ArrayList<>();

    public SaveFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    public List<WeakReference<Fragment>> getFragments()
    {
        for (int i=mList.size()-1; i>=0; i--)
        {
            if (mList.get(i).get()==null)
            {
                mList.remove(i);
            }
        }
        return mList;
    }

    protected void saveFragment(Fragment fragment)
    {
        if (fragment==null)
        {
            return;
        }

        for (WeakReference<Fragment> item: mList)
        {
            if (item.get()==fragment)
            {
                return;
            }
        }

        mList.add(new WeakReference(fragment));

    }

}
