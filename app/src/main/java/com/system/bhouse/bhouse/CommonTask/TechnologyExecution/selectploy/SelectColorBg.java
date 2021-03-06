package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.R;

/**
 * Created by Administrator on 2018-07-09.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy
 */

public class SelectColorBg implements SelectPloy,UnSelectPloy,DisablePloy {
    @Override
    public void selectBg(View view) {
        if (view==null)
        {
            return;
        }
        RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.rl_content_layout);
        Drawable drawable = App.getContextApp().getResources().getDrawable(R.drawable.bg_timeline_btn_select);
        rel.setBackground(drawable);
        TextView textTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView subTitle = (TextView) view.findViewById(R.id.tv_sub_title);

        textTitle.setTextColor(App.getContextApp().getResources().getColor(R.color.white));
        subTitle.setTextColor(App.getContextApp().getResources().getColor(R.color.white));
    }

    @Override
    public void UnSelectbg(View view) {
        if (view==null)
        {
            return;
        }
        RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.rl_content_layout);
        Drawable drawable = App.getContextApp().getResources().getDrawable(R.drawable.bg_timeline_btn_normal);
        rel.setBackground(drawable);
        TextView textTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView subTitle = (TextView) view.findViewById(R.id.tv_sub_title);
        textTitle.setTextColor(App.getContextApp().getResources().getColor(R.color.font_6));
        subTitle.setTextColor(App.getContextApp().getResources().getColor(R.color.common_color_text_30));
    }

    @Override
    public void Disablebg(View view) {
        if (view==null)
        {
            return;
        }
        RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.rl_content_layout);
        Drawable drawable = App.getContextApp().getResources().getDrawable(R.drawable.bg_timeline_btn_disable);
        rel.setBackground(drawable);
        TextView textTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView subTitle = (TextView) view.findViewById(R.id.tv_sub_title);
        textTitle.setTextColor(App.getContextApp().getResources().getColor(R.color.font_6));
        subTitle.setTextColor(App.getContextApp().getResources().getColor(R.color.common_color_text_30));
    }
}
