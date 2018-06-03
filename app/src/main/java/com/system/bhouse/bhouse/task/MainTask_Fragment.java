package com.system.bhouse.bhouse.task;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.LoadingFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


/**
 * Created by Administrator on 2017-12-6.
 */
@EFragment(R.layout.maintask_fragment)
public class MainTask_Fragment extends LoadingFragment implements View.OnClickListener {

    @ViewById(R.id.tv_alltask_bottom)
    TextView tv_alltask_bottom;
    @ViewById(R.id.iv_left_img_task)
    ImageView iv_left_img_task;


    private FragmentManager fragmentManager;

    @AfterViews
    void initTaskFragment() {
        fragmentManager = getFragmentManager();
        tv_alltask_bottom.setOnClickListener(this);
        iv_left_img_task.setOnClickListener(this);
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_alltask_bottom:
                TaskActivity_.intent(getActivity()).start();
                break;
            case R.id.iv_left_img_task:
                TaskAddActivity_.intent(getActivity()).start();
                break;
        }
    }

}
