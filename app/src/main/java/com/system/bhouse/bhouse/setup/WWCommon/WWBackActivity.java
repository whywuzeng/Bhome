package com.system.bhouse.bhouse.setup.WWCommon;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.system.bhouse.bhouse.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

/**
 * Created by Administrator on 2017-10-30.
 * 处理返回
 */
@EActivity(R.layout.activity_base_annotation)
public class WWBackActivity extends WWBaseActivity {

   protected  Toolbar toolbar_com;

    @AfterViews
    protected final void annotationDispayHomeAsUp() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        ToolbarDispayHomeAsUp();
    }

    @OptionsItem(android.R.id.home)
    protected  void annotaionClose() {
        onBackPressed();
    }

    protected void ToolbarDispayHomeAsUp()
    {
        

        toolbar_com=(Toolbar)findViewById(R.id.toolbar_com);

        if (toolbar_com!=null)
        {
            setSupportActionBar(toolbar_com);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (toolbar_com!=null) {

            toolbar_com.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void setToolBarMenuOnclick(Toolbar.OnMenuItemClickListener onclick) {
        toolbar_com.setOnMenuItemClickListener(onclick);

    }
}
