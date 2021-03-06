package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.BaseFragment.SwipeItemLayout;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.Custom.DrawableCenterTextView;
import com.system.bhouse.bhouse.R;

/**
 * Created by Administrator on 2018-07-09.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy
 */

public class TechnologSelectColorBg implements SelectPloy,UnSelectPloy,DisablePloy,DisableRecallPloy,NoAssociationPloy {

    //已选中item Bg
    @Override
    public void selectBg(View view) {
        if (view==null)
        {
            return;
        }
        RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.rl_content_layout);
        RelativeLayout relativeLayout_view_bg= (RelativeLayout)view.findViewById(R.id.relativeLayout_view_bg);
        DrawableCenterTextView rightremove_menu = (DrawableCenterTextView) view.findViewById(R.id.rightremove_menu);

        DrawableCenterTextView right_menu = (DrawableCenterTextView) view.findViewById(R.id.right_menu);
        rightremove_menu.setVisibility(View.GONE);
        right_menu.setVisibility(View.GONE);

        Drawable drawable = App.getContextApp().getResources().getDrawable(R.drawable.bg_timeline_btn_select);
        rel.setBackground(drawable);
        relativeLayout_view_bg.setBackground(drawable);
        TextView textTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView subTitle = (TextView) view.findViewById(R.id.tv_sub_title);

        textTitle.setTextColor(App.getContextApp().getResources().getColor(R.color.white));
        subTitle.setTextColor(App.getContextApp().getResources().getColor(R.color.white));
    }

    /**
     * 没选择
     * @param view
     */
    @Override
    public void UnSelectbg(View view) {
        if (view==null)
        {
            return;
        }
        RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.rl_content_layout);
        RelativeLayout relativeLayout_view_bg= (RelativeLayout)view.findViewById(R.id.relativeLayout_view_bg);
        Drawable drawable = App.getContextApp().getResources().getDrawable(R.drawable.bg_timeline_btn_normal);
//        DrawableCenterTextView DetailMenutv = (DrawableCenterTextView) view.findViewById(R.id.rightDetail_menu);
//        DetailMenutv.setVisibility(View.GONE);
        rel.setBackground(drawable);
        relativeLayout_view_bg.setBackground(drawable);
        TextView textTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView subTitle = (TextView) view.findViewById(R.id.tv_sub_title);
        textTitle.setTextColor(App.getContextApp().getResources().getColor(R.color.font_6));
        subTitle.setTextColor(App.getContextApp().getResources().getColor(R.color.common_color_text_30));
    }

    /**
     * 已完成
     * @param view
     */

    @Override
    public void Disablebg(View view) {
        if (view==null)
        {
            return;
        }
        RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.rl_content_layout);
        RelativeLayout relativeLayout_view_bg= (RelativeLayout)view.findViewById(R.id.relativeLayout_view_bg);
        DrawableCenterTextView rightremove_menu = (DrawableCenterTextView) view.findViewById(R.id.rightremove_menu);

        DrawableCenterTextView right_menu = (DrawableCenterTextView) view.findViewById(R.id.right_menu);
        rightremove_menu.setVisibility(View.GONE);
        right_menu.setVisibility(View.GONE);
        Drawable drawable = App.getContextApp().getResources().getDrawable(R.drawable.bg_timeline_btn_disable);
        rel.setBackground(drawable);
        relativeLayout_view_bg.setBackground(drawable);
        TextView textTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView subTitle = (TextView) view.findViewById(R.id.tv_sub_title);
        textTitle.setTextColor(App.getContextApp().getResources().getColor(R.color.font_6));
        subTitle.setTextColor(App.getContextApp().getResources().getColor(R.color.common_color_text_30));
    }

    public void performItemLayoutopen(View view){
        SwipeItemLayout itemLayout = (SwipeItemLayout) view.findViewById(R.id.swipe_layout);
        if (itemLayout.isOpen())
        {
            itemLayout.close();
        }else{
            itemLayout.open();
        }
    }

    public void performItemLayoutClose(View view){
        SwipeItemLayout itemLayout = (SwipeItemLayout) view.findViewById(R.id.swipe_layout);
        if (itemLayout.isOpen())
        {
            itemLayout.close();
        }
    }

    /**
     * 单独给 启动工序 上一个 撤回工序 做的界面  //已选中上一个Item
     * @param view
     */
    @Override
    public void DisableRecall(View view) {
        if (view==null)
        {
            return;
        }
        RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.rl_content_layout);
        RelativeLayout relativeLayout_view_bg= (RelativeLayout)view.findViewById(R.id.relativeLayout_view_bg);
        Drawable drawable = App.getContextApp().getResources().getDrawable(R.drawable.bg_timeline_btn_disable);
        rel.setBackground(drawable);
        relativeLayout_view_bg.setBackground(drawable);

        DrawableCenterTextView rightremove_menu = (DrawableCenterTextView) view.findViewById(R.id.rightremove_menu);
        DrawableCenterTextView right_menu_menu = (DrawableCenterTextView) view.findViewById(R.id.right_menu);
        rightremove_menu.setVisibility(View.VISIBLE);
        right_menu_menu.setVisibility(View.GONE);

        TextView textTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView subTitle = (TextView) view.findViewById(R.id.tv_sub_title);
        textTitle.setTextColor(App.getContextApp().getResources().getColor(R.color.font_6));
        subTitle.setTextColor(App.getContextApp().getResources().getColor(R.color.common_color_text_30));
    }

    /**
     * 无关联Item 显示策略
     */
    @Override
    public void NoAssociationBg(View view) {
        if (view==null)
        {
            return;
        }
        RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.rl_content_layout);
        RelativeLayout relativeLayout_view_bg= (RelativeLayout)view.findViewById(R.id.relativeLayout_view_bg);
        Drawable drawable = App.getContextApp().getResources().getDrawable(R.drawable.bg_timeline_btn_disable);
//        rel.setBackground(drawable);
//        relativeLayout_view_bg.setBackground(drawable);

        DrawableCenterTextView rightremove_menu = (DrawableCenterTextView) view.findViewById(R.id.rightremove_menu);
        DrawableCenterTextView right_menu_menu = (DrawableCenterTextView) view.findViewById(R.id.right_menu);
        rightremove_menu.setVisibility(View.VISIBLE);
        right_menu_menu.setVisibility(View.VISIBLE);

        TextView textTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView subTitle = (TextView) view.findViewById(R.id.tv_sub_title);
//        textTitle.setTextColor(App.getContextApp().getResources().getColor(R.color.font_6));
//        subTitle.setTextColor(App.getContextApp().getResources().getColor(R.color.common_color_text_30));
    }
}
