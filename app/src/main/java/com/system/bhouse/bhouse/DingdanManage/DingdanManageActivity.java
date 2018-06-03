package com.system.bhouse.bhouse.DingdanManage;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.system.bhouse.bhouse.ConameActivity;
import com.system.bhouse.bhouse.OrderInputActivity;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.SaleAnalysisActivity;
import com.system.bhouse.utils.AppManager;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-4-15.
 * ClassName: com.system.bhouse.bhouse.DingdanManage
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class DingdanManageActivity extends AutoLayoutActivity {

    private RecyclerView mRecyclerView;
    private DingManagerStaggeredAdapter mSaleAnalysisStaggeredAdapter;
    private List<Integer> mDatas = new ArrayList<>();
    private LinearLayout fanhui_lin;

    private int[] Sin = {R.string.dingdanluru, R.string.dingdanmingxiluru, R.string.dingdanchaxun
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dingdanmanageactivity);
        AppManager.getAppManager().addActivity(this);
        fanhui_lin = (LinearLayout) findViewById(R.id.fanhui_lin);
        initdata();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview_dingdanmanagectivity);
        mSaleAnalysisStaggeredAdapter = new DingManagerStaggeredAdapter(this, mDatas);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mSaleAnalysisStaggeredAdapter);
        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        initEvent();
    }

    private void initEvent() {

        mSaleAnalysisStaggeredAdapter.setOnItemClickLitener(new DingManagerStaggeredAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(SaleAnalysisActivity.this, position + " click", Toast.LENGTH_SHORT).show();
                Intent intent;
                switch (position) {
                    case 1:
                        intent = new Intent(DingdanManageActivity.this, OrderInputActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
//                        intent = new Intent(DingdanManageActivity.this, BankqueryActivity.class);
//                        startActivity(intent);
                        break;
                    case 0:
                        //合同订单录入
                        intent = new Intent(DingdanManageActivity.this, ConameActivity.class);
                        startActivity(intent);
                        break;
                    case 3:

                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    default:
                        Toast.makeText(DingdanManageActivity.this, "errro", 0).show();
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

        fanhui_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 这里会出问题
                 */
                try {
                    AppManager.getAppManager().finishActivity(DingdanManageActivity.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (!DingdanManageActivity.this.isFinishing()) {
                        DingdanManageActivity.this.finish();
                    }
                }
            }
        });

    }

    private void initdata() {

        for (int i = 0; i < Sin.length; i++) {
            mDatas.add(Sin[i]);
        }
    }


}
