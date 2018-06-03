package com.system.bhouse.bhouse.Service.NewsListUI;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.ui.sectioned.Section;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-10-19.
 */

public class TaskListSection extends Section {

    public TaskListSection() {
        super(R.layout.tasklist_header_layout, R.layout.tasklist_item_resource, R.layout.layout_home_recommend_empty, R.layout.layout_home_recommend_empty);
    }

    @Override
    public int getContentItemsTotal() {
        return 4;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        TaskItemResViewHolder taskItemResViewHolder = new TaskItemResViewHolder(view);
        return taskItemResViewHolder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TaskItemResViewHolder)
        {

            TaskItemResViewHolder holder1 = (TaskItemResViewHolder) holder;
            if (position==1) {
                holder1.tvTaskTitle.setText("催收尾款");
                holder1.tvTaskTextcontent.setTextColor(Color.rgb(255,0,0));
                holder1.tvTaskTextcontent.setText( String.valueOf("2017年10月18日截止"));

            }else  if (position==2) {
                holder1.tvTaskTitle.setText("枫丹白露要开建");
                holder1.tvTaskTextcontent.setText(String.valueOf("2017年10月18日截止"));
            }else  if (position==3) {
                holder1.tvTaskTitle.setText("将客户反馈的问题整理");
                holder1.tvTaskTextcontent.setText(String.valueOf("2017年10月22日截止"));
            }else if (position==0)
            {
                holder1.tvTaskTextcontent.setTextColor(Color.rgb(255,0,0));
            }
        }
    }

    static class TaskItemResViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_task_title)
        TextView tvTaskTitle;
        @Bind(R.id.tv_task_textcontent)
        TextView tvTaskTextcontent;

        TaskItemResViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
