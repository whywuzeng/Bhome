package com.system.bhouse.bhouse.task.adpter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.system.bhouse.bhouse.task.adpter.base.SaveFragmentPagerAdapter;
import com.system.bhouse.bhouse.task.fragment.ProjectFragment;
import com.system.bhouse.bhouse.task.fragment.ProjectListFragment;
import com.system.bhouse.bhouse.task.fragment.ProjectListFragment_;

/**
 * Created by Administrator on 2017-11-9.
 */

public class MyProjectPagerAdapter extends SaveFragmentPagerAdapter {

    private ProjectFragment projectFragment;

    public MyProjectPagerAdapter(ProjectFragment projectFragment,FragmentManager fm) {
        super(fm);
        this.projectFragment=projectFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return projectFragment.program_title[position];
    }

    @Override
    public Fragment getItem(int position) {


        ProjectListFragment fragment = new ProjectListFragment_();

//        fragment.setPos(position);
//        fragment.setTitle(projectFragment.program_title[position]);

        Bundle bundle = new Bundle();
//        bundle.putSerializable("mData", getChildData(position));
//        bundle.putSerializable("type", projectFragment.type);
        fragment.setArguments(bundle);

        saveFragment(fragment);

        return fragment;
    }

    @Override
    public int getCount() {
        return projectFragment.program_title.length+1;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }


}
