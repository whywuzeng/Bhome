package com.system.bhouse.bhouse.Topic;

import android.content.Intent;
import android.widget.FrameLayout;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBaseFragment;
import com.system.bhouse.bhouse.task.bean.ProjectObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Administrator on 2017-12-27.
 */

@EFragment(R.layout.fragment_topic)
public class TopicFragment extends WWBaseFragment {
    @FragmentArg
    ProjectObject mProjectObject;

    @ViewById
    FrameLayout container;


    @AfterViews
    void init() {  //mProjectObject(mProjectObject)
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
