package com.system.bhouse.bhouse.task;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.bhouse.task.adpter.base.SaveFragmentPagerAdapter;
import com.system.bhouse.ui.IndexViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Administrator on 2017-12-7.
 */

@EActivity(R.layout.task_activity)
public class TaskActivity extends WWBackActivity {
    @ViewById(R.id.main_fragment)
     IndexViewPager viewPager;

    @AfterViews
    void InitOnCreate()
    {
        setActionBarMidlleTitle("全部任务");
        viewPager.setAdapter(new MyFragmentIndexViewPager(getSupportFragmentManager()));

    }

    class MyFragmentIndexViewPager extends SaveFragmentPagerAdapter{

        public MyFragmentIndexViewPager(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            TaskFragment_ taskFragment = new TaskFragment_();
            saveFragment(taskFragment);
            return taskFragment;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }

}
