package com.system.bhouse.bhouse.CommonTask.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.CommonTask.callback.FilterListener;
import com.system.bhouse.bhouse.CommonTask.model.FilterModel;
import com.system.bhouse.bhouse.CommonTask.model.TaskLabelModel;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.utils.LabelNumPickerFragment;
import com.system.bhouse.bhouse.task.view.TaskAttrItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by afs on 2016/12/12.
 */

public class DrawerLayoutHelper {

    private DrawerLayout drawerLayout;
    private Context mContext;
    private FilterModel mFilterModel;
    private int font2;
    private int green;
    private boolean showLabelCount;

    public DrawerLayoutHelper() {

    }

    public static DrawerLayoutHelper getInstance() {
        return new DrawerLayoutHelper();
    }

    /**
     * @param context
     * @param drawerLayout
     * @param filterModel
     * @param filterListener
     */
    public void initData(Context context, DrawerLayout drawerLayout, FilterModel filterModel, FilterListener filterListener) {
        this.mContext = context;
        this.mFilterModel = filterModel;
        this.showLabelCount = false;
        font2 = mContext.getResources().getColor(R.color.font_2);
        green = mContext.getResources().getColor(R.color.green);


        this.drawerLayout = drawerLayout;
        this.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                App.hideSoftKeyboard((Activity) mContext);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        View reset = drawerLayout.findViewById(R.id.tv_reset);

        this.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
        reset.setOnClickListener(v -> {
            //假如有搜索条件才重置
            if (filterListener != null && (!TextUtils.isEmpty(filterModel.keyword) || !TextUtils.isEmpty(filterModel.label) || filterModel.status != 0)) {
                filterListener.callback(new FilterModel());
            }
            dismiss();
        });

        EditText etSearch = initKeyword(filterListener);

        iniStatus(filterListener, etSearch);
        iniLabels(filterListener, etSearch);
        iniSure(filterListener,etSearch);
        // 自动生成，很多label
        iniLayoutShowStatus(filterModel.isShow);
    }

    //控制右边条目的显示
    private void iniLayoutShowStatus(boolean isShow) {
        LinearLayout LLstatus = (LinearLayout) drawerLayout.findViewById(R.id.ll_status);
        if (LLstatus!=null&&!isShow)
        LLstatus.setVisibility(View.GONE);
    }

    private void iniSure(FilterListener filterListener, EditText etSearch) {
        TextView tvSure = (TextView) drawerLayout.findViewById(R.id.tv_sure);
        TaskAttrItem viewLabel = (TaskAttrItem) drawerLayout.findViewById(R.id.layoutBeginline);
        if (mFilterModel.label!=null&&TextUtils.isEmpty(mFilterModel.label))
        {
            viewLabel.setText2(mFilterModel.label);
        }

        viewLabel.setOnClickListener(v -> {
            LabelNumPickerFragment labelNumPickerFragment = new LabelNumPickerFragment();
            labelNumPickerFragment.setCallBack(provinceStr -> {
                String keyword = etSearch.getText().toString();
                FilterModel filterModel = new FilterModel();
                filterModel.label=provinceStr;
                filterModel.keyword=keyword;
                viewLabel.setText2(filterModel.label);

                try {
                    getTopMessage(filterModel);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            });
            labelNumPickerFragment.show(((FragmentActivity)mContext).getSupportFragmentManager(), "labelPicker");
            ((FragmentActivity)mContext).getSupportFragmentManager().executePendingTransactions();
        });
        tvSure.setOnClickListener(v -> {
            if (filterListener != null) {
                String keyword = etSearch.getText().toString();
                this.mFilterModel.keyword=keyword;
                filterListener.callback(this.mFilterModel);
            }
            dismiss();
        });
    }

    private void setActionDown(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    App.hideSoftKeyboard(((Activity) mContext));
                }
                return false;
            }
        });
    }

    @NonNull
    private EditText initKeyword(FilterListener filterListener) {
        EditText etSearch = (EditText) drawerLayout.findViewById(R.id.et_search);

//        if (mFilterModel != null && !TextUtils.isEmpty(mFilterModel.keyword)) {
//            mFilterModel.keyword = "";
//        }
        etSearch.setText(mFilterModel.keyword);
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (filterListener != null) {
                    String keyword = etSearch.getText().toString().trim();
                    filterListener.callback(mFilterModel.status != 0 ?
                            new FilterModel(mFilterModel.status, keyword) :
                            new FilterModel(mFilterModel.label, keyword));
                }
                dismiss();
            }
            return false;
        });
        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    App.hideSoftKeyboard(((Activity) mContext));
                }
            }
        });
        return etSearch;
    }

    private void dismiss() {
        if (drawerLayout == null) return;

        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
        this.mFilterModel=null;
        App.hideSoftKeyboard(((Activity) mContext));
    }

    private void iniLabels(FilterListener filterListener, EditText etSearch) {
        if (mFilterModel == null || mFilterModel.labelModels == null || mFilterModel.labelModels.size() == 0) {
            return;
        }

        LinearLayout llLabels = (LinearLayout) drawerLayout.findViewById(R.id.ll_labels);
        llLabels.removeAllViews();
        List<String> labelModels = new ArrayList<>();

        int len = mFilterModel.labelModels.size();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        for (int i = 0; i < len; i++) {
            final TaskLabelModel item = mFilterModel.labelModels.get(i);
            if (labelModels.contains(item.name)) {
                continue;
            }
            labelModels.add(item.name);
            //兼容新接口
            if (item.all == 0) {
                item.all = item.count;
            }

            TextView labelItem = (TextView) layoutInflater.inflate(R.layout.dialog_task_filter_label_item, null);
            String str = showLabelCount ? String.format("%s (%d/%d)", item.name, item.processing, item.all) : item.name;
            labelItem.setText(str);
            setLeftDrawable(labelItem, item.color, item.name.equals(mFilterModel.label));
            labelItem.setOnClickListener(v -> {
                String keyword = etSearch.getText().toString();
                if (filterListener != null) {
                    filterListener.callback(new FilterModel(item.name, keyword));
                }
                dismiss();
            });
            setActionDown(labelItem);
            llLabels.addView(labelItem);

        }
    }

    private void iniStatus(FilterListener filterListener, EditText etSearch) {
        String[] taskStr = {"提交", "审核"};
        int[] taskViews = {R.id.tv_task_doing, R.id.tv_task_done};
        TextView[] taskViewNum = {(TextView) drawerLayout.findViewById(taskViews[0]),(TextView) drawerLayout.findViewById(taskViews[1])};

        for (int i = 0; i < taskStr.length; i++) {

            TextView taskView = taskViewNum[i];
            String txt = taskStr[i];
            if (i == 0) {
                if (mFilterModel != null && mFilterModel.statusTaskDoing > 0) {
                    txt += String.format(" (%d)", mFilterModel.statusTaskDoing);
                }
            } else {
                if (mFilterModel != null && mFilterModel.statusTaskDone > 0) {
                    txt += String.format(" (%d)", mFilterModel.statusTaskDone);
                }
            }
            taskView.setText(txt);
            setActionDown(taskView);
            int status = i + 1;
            taskView.setOnClickListener(v -> {
                String keyword = etSearch.getText().toString();


                try {
                    getTopMessage(new FilterModel(status,keyword));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                for (int b = 0; b < taskViewNum.length; b++) {
                    if (taskViewNum[b] == taskView) {
                        taskViewNum[b].setTextColor(green);
//                        setRightDrawable(taskViewNum[b], R.drawable.ic_task_status_list_check);
                    }
                    else {
                        taskViewNum[b].setTextColor(font2);
                        setRightDrawable(taskView, 0);
                    }
                }

            });

            //初始化状态
            boolean isCheck = mFilterModel != null && mFilterModel.status == status;
            taskView.setTextColor(!isCheck ? font2 : green);
            if (isCheck) {
//                setRightDrawable(taskView, R.drawable.ic_task_status_list_check);
            } else {
                setRightDrawable(taskView, 0);
            }
        }
    }


    /**
     * @param textView
     * @param resId
     */
    public void setRightDrawable(TextView textView, int resId) {
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0);
    }

    public void setLeftDrawable(TextView textView, String color, boolean isChecked) {

        if (mContext == null) {
            return;
        }

        final Drawable originalBitmapDrawable = mContext.getResources().getDrawable(R.drawable.ic_project_topic_label).mutate();
        Drawable right = isChecked ? mContext.getResources().getDrawable(R.drawable.ic_task_status_list_check) : null;

        ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor(color));
        textView.setCompoundDrawablesWithIntrinsicBounds(tintDrawable(originalBitmapDrawable, colorStateList), null, right, null);
        textView.setTextColor(!isChecked ? font2 : green);
    }

    public Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

//    public int status;//任务状态，进行中的为1，已完成的为2
//    public String label;//任务标签
//    public String keyword;//根据关键字筛选任务
    public void getTopMessage(FilterModel filterModel) throws NullPointerException {

        if (filterModel==null)
            return;

        if (filterModel.status>0)
        {
            this.mFilterModel.status=filterModel.status;
        }else if (checkLabel(filterModel.label))
        {
            this.mFilterModel.label=filterModel.label;
        }else if (TextUtils.isEmpty(filterModel.keyword))
        {
            this.mFilterModel.keyword=filterModel.keyword;
        }


    }

    public boolean checkLabel(String labelmsg){
        if (TextUtils.isEmpty(labelmsg))
            return false;
        String[] labels={"50","200","500"};
        for (int i=0;i<labels.length;i++)
        {
            if (labelmsg.equals(labels[i]))
            {
                return true;
            }
        }
        return false;
    }

}
