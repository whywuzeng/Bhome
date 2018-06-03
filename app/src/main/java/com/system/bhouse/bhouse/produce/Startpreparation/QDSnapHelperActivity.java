package com.system.bhouse.bhouse.produce.Startpreparation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.ViewGroup;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.produce.adpter.QDRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * Created by Administrator on 2017-9-6.
 */

public class QDSnapHelperActivity extends AppCompatActivity {

    @Bind(R.id.pagerWrap)
    ViewGroup mPagerWrap;

    RecyclerView mRecyclerView;
    LinearLayoutManager mPagerLayoutManager;
    QDRecyclerViewAdapter mRecyclerViewAdapter;
    SnapHelper mSnapHelper;
    private ArrayList<String> listarrayExtra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pagerlayoutmanager);
        ButterKnife.bind(this);

        getDataIntent();
        initTopBar();

        mRecyclerView = new RecyclerView(this);
        mPagerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mPagerLayoutManager);

        mRecyclerViewAdapter = new QDRecyclerViewAdapter(listarrayExtra,this);
//        mRecyclerViewAdapter.setItemCount(10);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mPagerWrap.addView(mRecyclerView);

        mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            listarrayExtra = intent.getStringArrayListExtra("listarrayExtra");


        }
    }

    private void initTopBar() {
//        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//        // 切换其他情况的按钮
//        mTopBar.addRightImageButton(R.mipmap.icon_topbar_overflow, R.id.topbar_right_change_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showBottomSheetList();
//            }
//        });
//
//        mTopBar.setTitle("图片");
    }

    private void showBottomSheetList() {
//        new QMUIBottomSheet.BottomListSheetBuilder(this)
//                .addItem("水平方向")
//                .addItem("垂直方向")
//                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
//                    @Override
//                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
//                        dialog.dismiss();
//                        switch (position) {
//                            case 0:
//                                mPagerLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                                break;
//                            case 1:
//                                mPagerLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                })
//                .build()
//                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
