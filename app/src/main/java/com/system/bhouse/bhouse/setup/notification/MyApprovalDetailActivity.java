package com.system.bhouse.bhouse.setup.notification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.bhouse.setup.notification.DetailFragment.DetailProcessFragment;
import com.system.bhouse.bhouse.task.adpter.base.SaveFragmentPagerAdapter;
import com.system.bhouse.bhouse.task.view.MyPagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-06-11.
 * <p>
 * by author wz
 * 表单详情的界面
 * <p>
 * com.system.bhouse.bhouse.setup.notification
 */

public class MyApprovalDetailActivity extends WWBackActivity {

    MyPagerSlidingTabStrip myPagerSlidingTabStrip;
    ViewPager pager;
    private List<Fragment> fragments;
    private DetailProcessFragment processFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approval_detail_activity);
        LayoutInflater from = LayoutInflater.from(this);
        myPagerSlidingTabStrip=(MyPagerSlidingTabStrip) findViewById(R.id.tabs);
        pager=(ViewPager)findViewById(R.id.pagerDetailFragment);
        myPagerSlidingTabStrip.setLayoutInflater(from);
        fragments = new ArrayList<>();
        processFragment = new DetailProcessFragment();
        fragments.add(processFragment);
        DetailPagerAdapter detailPagerAdapter = new DetailPagerAdapter(getSupportFragmentManager(),fragments);
        pager.setAdapter(detailPagerAdapter);

        myPagerSlidingTabStrip.setViewPager(pager);
    }

    class DetailPagerAdapter extends SaveFragmentPagerAdapter {

        List<Fragment> fragments;

        public DetailPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
              saveFragment(fragments.get(position));
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }
}
