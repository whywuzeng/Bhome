package com.system.bhouse.bhouse.phone.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.system.bhouse.base.rxlife.RxCompatActivity;
import com.system.bhouse.bhouse.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018-10-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.phone.activity
 */

public class ScanResultActivity extends RxCompatActivity {

    private int position=0;

    ArrayList<Class> fragments = new ArrayList<Class>(Arrays.asList(
        ScanResultParentFragment.class
    ));

    ArrayList<String>  project_activity_action_list=new ArrayList<>(Arrays.asList(
        "scan_result"
    ));

    List<WeakReference<Fragment>> mFragments=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanresult_layout);

        setActionBarMidlleTitle("扫描详情");

        selectFragment(position);

    }

    private void selectFragment(int position) {
        Fragment fragment;
        final Bundle bundle = new Bundle();
        final Class aClass = fragments.get(position);
        try {
            fragment= (Fragment) aClass.newInstance();
            fragment.setArguments(bundle);

            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,fragment,project_activity_action_list.get(position));
            ft.commit();

            mFragments.add(new WeakReference<Fragment>(fragment));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
