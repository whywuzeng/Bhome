package com.system.bhouse.bhouse.task;

import android.support.v4.app.Fragment;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.bhouse.task.fragment.ProjectFragment;
import com.system.bhouse.bhouse.task.fragment.ProjectFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;



@EActivity(R.layout.activity_pick_project)
public class PickProjectActivity extends WWBackActivity {

    @AfterViews
    protected final void initPickProjectActivity() {
//        Fragment fragment = UserProjectListFragment_.builder()
//                .mUserObject(MyApp.sUserObject)
//                .mType(UserProjectListFragment.Type.all_private)
//                .mPickProject(true)
//                .build();

        setActionBarTitle("任务名");

        Fragment fragment = ProjectFragment_.builder()
                .type(ProjectFragment.Type.Pick)
                .build();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

}
