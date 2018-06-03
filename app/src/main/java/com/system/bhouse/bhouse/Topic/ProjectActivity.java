package com.system.bhouse.bhouse.Topic;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by Administrator on 2017-12-28.
 */

@EActivity(R.layout.activity_project_parent)
public class ProjectActivity extends WWBackActivity{


    @AfterViews
    void initProjectActivity()
    {

        TopicFragment fragment = TopicFragment_.builder().build();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment,"");
        fragmentTransaction.commit();
    }

}
