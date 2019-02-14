package com.system.bhouse.bhouse.produce.Startpreparation;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.produce.adpter.OrderDetailSection;
import com.system.bhouse.bhouse.produce.bean.ProductionOrderDetail;
import com.system.bhouse.ui.sectioned.SectionedRecyclerViewAdapter;
import com.system.bhouse.utils.ProgressUtils;

import java.util.ArrayList;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

/**
 * Created by Administrator on 2017-9-5.
 */

public class StartdetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private SectionedRecyclerViewAdapter mRecyclerViewAdapter;

    @BindView(R.id.rl_tv_more)
    TextView rl_tv_more;

    private ArrayList<ProductionOrderDetail> mProductionOrderDetails=new ArrayList<>();
    private String resultCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startdetail_activity);
        bind(this);
        initview();
        initRecyclerView();
        initData();
    }

    private void initData() {
                ApiWebService.Get_Production_order_list_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                Log.i("dsds","dsds");
                Gson gson = new Gson();

                ProductionOrderDetail[] shengfens = gson.fromJson(result, new TypeToken<ProductionOrderDetail[]>() {
                }.getType());

                ProgressUtils.DisMissProgress();
//                ArrayList<ProductionOrderDetail> userMidPerms = new ArrayList<>();
                for (int i = 0; i < shengfens.length; i++) {
                    mProductionOrderDetails.add(shengfens[i]);
                }

                refreshAction();
            }

            @Override
            public void ErrorBack(String error) {
                Log.i("dsds","dsds");

            }
        },resultCode, App.MID, App.Property, App.IsSub);
    }

    private void refreshAction() {

//        OrderDetailSection mOrderDetailSection = new OrderDetailSection(mProductionOrderDetails);
//        mRecyclerViewAdapter.addSection(mOrderDetailSection);


        mRecyclerViewAdapter.notifyDataSetChanged();

    }

    private void initRecyclerView() {

        mRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mRecyclerViewAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 1;
                    default:
                        return 1;
                }
            }
        });

        recyclerView.setAdapter(mRecyclerViewAdapter);

//        mProductionOrderDetails

        OrderDetailSection mOrderDetailSection = new OrderDetailSection(mProductionOrderDetails);

        mRecyclerViewAdapter.addSection(mOrderDetailSection);

        mRecyclerViewAdapter.notifyDataSetChanged();

    }



    private void initview() {
        Intent intent = getIntent();
        resultCode = intent.getStringExtra("result");

        rl_tv_more.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.rl_tv_more)
        {
            onBackPressed();
        }
    }

    private class MyItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, 3);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
