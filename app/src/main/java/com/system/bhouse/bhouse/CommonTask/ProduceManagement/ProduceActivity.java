package com.system.bhouse.bhouse.CommonTask.ProduceManagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment.LoadingIntoWareHouseActivity;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.MaintainIntoWareHouse.MaintainIntoWareHouseActivity;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.MaintainOutWareHouse.MaintainOutWareHouseActivity;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.StationCarDetachModule.StationCarDetachModuleActivity;
import com.system.bhouse.bhouse.CommonTask.MaterialControl.FinishedStorage.FinishedStorageActivity;
import com.system.bhouse.bhouse.CommonTask.MaterialControl.PickingOutLibary.PickingOutLibaryActivity;
import com.system.bhouse.bhouse.CommonTask.MaterialControl.PlateMaterial.PlateMaterialActivity;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder.ProductionOrderActivity;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.MultipleItemQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.entity.MultipleItem;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.entity.ProduceItemDataBean;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.entity.SectionMultipleItem;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.TechnologyExecutionActivity;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.phone.activity.InformationActivity;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-06-20.
 * <p>
 * by author wz
 * 生产工作台
 * <p>
 * com.system.bhouse.bhouse.CommonTask.ProduceManagement
 */

public class ProduceActivity extends WWBackActivity implements BaseQuickAdapter.OnItemChildClickListener{

    private static final String TAG = "ProduceActivity";
    
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private static final int RESULT_LOCAL = 2;

    public static final int RESULT_COMPONENT=2<<2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce);
        ButterKnife.bind(this);
        annotationDispayHomeAsUp();
        setActionBarMidlleTitle("生产工作台");
        final List<SectionMultipleItem> data = getData();
        final MultipleItemQuickAdapter multipleItemQuickAdapter = new MultipleItemQuickAdapter(this, data);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.addItemDecoration(new TimeLineItemTopBottomDecoration());
        multipleItemQuickAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return data.get(position).getSpanSize();
            }
        });
        recyclerView.setAdapter(multipleItemQuickAdapter);
        multipleItemQuickAdapter.setOnItemChildClickListener(this);

    }

    private List<SectionMultipleItem> getData(){

        List<SectionMultipleItem> list = new ArrayList<>();
        list.add(new SectionMultipleItem(true, "生产管理"));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.inbox_btn_approval_normal, "生产订单"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
        list.add(new SectionMultipleItem(true, "物料管控"));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.dm_btn_document_press, "领料出库"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.changeteam_tip_placeholder, "完工入库"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.inbox_btn_traceless_normal, "托盘配料"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
        list.add(new SectionMultipleItem(true, "工艺管理"));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.more_btn_pc, "工艺执行"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
        list.add(new SectionMultipleItem(true, "养护管理"));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.more_btn_forward, "养护入库"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.inbox_btn_vote_normal, "养护出库"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
        list.add(new SectionMultipleItem(true, "其他"));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.agora_handup_on, "台车模具分离"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
        list.add(new SectionMultipleItem(new ProduceItemDataBean(R.drawable.college_img_public_normal, "装柜入库"), MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
//            list.add(new SectionMultipleItem(MultipleItem.IMG_TEXT_SPAN_SIZE, MultipleItem.TEXT_SPAN_SIZE, "content"));
//            list.add(new SectionMultipleItem(MultipleItem.TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE));
//            list.add(new SectionMultipleItem(MultipleItem.IMG_TEXT_SPAN_SIZE, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));
//            list.add(new SectionMultipleItem(MultipleItem.TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));
        return list;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent;
        switch (position){
            case 1:
                intent=new Intent(this, ProductionOrderActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent=new Intent(this,PickingOutLibaryActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent=new Intent(this, FinishedStorageActivity.class);
                startActivity(intent);
                break;
            case 5:
               intent= new Intent(this, PlateMaterialActivity.class);
               startActivity(intent);
               break;
            case 7:
               intent = new Intent(ProduceActivity.this, TechnologyExecutionActivity.class);
               startActivity(intent);
               break;
            case 9:
                intent = new Intent(ProduceActivity.this, MaintainIntoWareHouseActivity.class);
                startActivity(intent);
                break;
            case 10:
                intent = new Intent(ProduceActivity.this, MaintainOutWareHouseActivity.class);
                startActivity(intent);
                break;
            case 12:
                intent = new Intent(ProduceActivity.this, StationCarDetachModuleActivity.class);
                startActivity(intent);
                break;
            case 13:
               intent= new Intent(ProduceActivity.this,LoadingIntoWareHouseActivity.class);
               startActivity(intent);
               break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_COMPONENT:
                 QrCodeComponent(resultCode,data);
                break;
        }
    }

    private void QrCodeComponent(int resultCode, Intent data) {
        if (resultCode== Activity.RESULT_OK)
        {
            Bundle bundle = data.getBundleExtra("bundle");
            String resultQr = bundle.getString("result");
            int extraPosition = bundle.getInt("position");

            ApiWebService.Get_Pro_Working_Main_poid_byprid_QR_Code_Json(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    //[{"订单ID":"08776473ea6a4c0ea7a3291d4aa0d359","订单编号":"SCDD-7-201807-0008"}]
                    String orderID = null;
                    String orderNumber = null;
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                         orderID = jsonObject.optString("订单ID", "");
                         orderNumber = jsonObject.optString("订单编号", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        if (!TextUtils.isEmpty(orderID)&&!TextUtils.isEmpty(orderNumber))
                        {

                            Intent intent = new Intent(ProduceActivity.this, TechnologyExecutionActivity.class);
                            intent.putExtra(TechnologyExecutionActivity.RESULTQRCOMPONENT,resultQr);
                            intent.putExtra(TechnologyExecutionActivity.ORDER_ID,orderID);
                            startActivity(intent);

                        }
                    }
                }

                @Override
                public void ErrorBack(String error) {

                }
            }, resultQr);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_site,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId==R.id.action_organize) {
            action_organize();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void action_organize() {
        Intent intent2=new Intent(this,InformationActivity.class);
        this.startActivityForResult(intent2,RESULT_LOCAL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: " );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: " );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: " );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: " );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: " );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " );
    }
}
