//package com.system.bhouse.bhouse.produce.Startpreparation;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Rect;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.bzh.recycler.ExRecyclerView;
//import com.bzh.recycler.ExViewHolder;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.system.bhouse.api.ApiWebService;
//import com.system.bhouse.bhouse.R;
//import com.system.bhouse.bhouse.produce.adpter.StartExCommonAdapter;
//import com.system.bhouse.bhouse.produce.bean.ProductionOrder;
//import com.system.bhouse.bhouse.produce.bean.String2Bean;
//import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
//import com.system.bhouse.utils.ProgressUtils;
//import com.system.bhouse.utils.TenUtils.T;
//
//import org.androidannotations.annotations.AfterViews;
//import org.androidannotations.annotations.EActivity;
//import org.androidannotations.annotations.Extra;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.ButterKnife;
//import io.github.lijunguan.imgselector.ImageSelector;
//
//import static com.system.bhouse.base.App.IsSub;
//import static com.system.bhouse.base.App.MID;
//import static com.system.bhouse.base.App.Property;
//
///**
// * Created by Administrator on 2017-9-4.
// */
//@EActivity(R.layout.startpre_activity)
//public class StartpreActivity extends WWBackActivity implements View.OnClickListener {
//
//
//    private static int REQUST_CODE = 0;
//
//    private ExRecyclerView mRecyclerView;
//
//    @Extra("Iscomplete")
//    String Iscomplete;
//
//    private Button btn_dingdansaomiao;
//
//    private EditText usernumber_danCode;
//
//    private EditText usernumber_planstartDate;
//
//    private EditText usernumber_plancloseDate;
//
//    private EditText usernumber_danStatus;
//
//    private EditText usernumber_zhidanpeople;
//
//    private EditText usernumber_shenhepeople;
//
//    private TextView rl_tv_more;
//
//    private String result = "";
//
//    private String iscomplete;
//    @AfterViews
//    protected void onCreate() {
//
//        ButterKnife.bind(this);
//        Intent intent = getIntent();
//        if (intent!=null)
//        {
//            iscomplete = intent.getStringExtra("Iscomplete");
//        }
//        initview();
//        initToolbar();
////        initGroupList();
//
//    }
//
//    private void initWebserviceData(String DDcode) {
//
//
//        ApiWebService.Get_Production_order_Bill_Json(this, new ApiWebService.SuccessCall() {
//            @Override
//            public void SuccessBack(String result) {
//
//                boolean empty = TextUtils.isEmpty(result);
//
//                if (result==null||TextUtils.isEmpty(result)||result.equals("[]")) {
//                    T.showLong(StartpreActivity.this, String2Bean.IsNUll);
//                    return;
//                }
//
//                Gson gson = new Gson();
//
//                ProductionOrder[] shengfens = gson.fromJson(result, new TypeToken<ProductionOrder[]>() {
//                }.getType());
//
//                ProgressUtils.DisMissProgress();
//                ArrayList<ProductionOrder> userMidPerms = new ArrayList<>();
//                for (int i = 0; i < shengfens.length; i++) {
//                    userMidPerms.add(shengfens[i]);
//                }
//
//                initDatas(userMidPerms);
//            }
//
//            @Override
//            public void ErrorBack(String error) {
//                Log.i("dsds", "dsds");
//            }
//        }, DDcode, MID, Property, IsSub);
//
//    }
//
//    private void initDatas(ArrayList<ProductionOrder> userMidPerms) {
//
//
//        for (int i = 0; i < userMidPerms.size(); i++) {
//            ProductionOrder productionOrder = userMidPerms.get(0);
//            usernumber_danCode.setText(productionOrder.getDingdanbianhao() + "");
//            usernumber_planstartDate.setText(productionOrder.getStartriqi() + "");
//            usernumber_plancloseDate.setText(productionOrder.getCloseriqi() + "");
//            usernumber_danStatus.setText(productionOrder.getDanstatus() + "");
//            usernumber_zhidanpeople.setText(productionOrder.getZhidanpeople() + "");
//            usernumber_shenhepeople.setText(productionOrder.getShenhepeople() + "");
//
//        }
//    }
//
//    private void initGroupList() {
//        setData();
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.addItemDecoration(new MyItemDecoration());
//
//        StartExCommonAdapter adapter = new StartExCommonAdapter(this) {
//
//            @Override
//            protected void convert(ExViewHolder viewHolder, BaseInfoEntity item, int yCurrentDataIndex) {
//
////                if (item instanceof  BaseInfoEntity&&null!=viewHolder.getView(R.id.tv_film_name)) {
////                    viewHolder.setText(R.id.tv_film_name, strs[position]);
////                    viewHolder.setText(R.id.tv_film_publish_time, strs[position]);
////
////                    ( (ImageView)viewHolder.getView(R.id.xiaoshou_left_img)).setBackgroundResource(ids[position]);
////
////                }
//                Log.i("", "");
//            }
//        };
//
////        adapter.setData(data);
////
////        BaseInfoEntity model1=new BaseInfoEntity();
////        model1.setName("生产执行管控");
////        model1.ADviewID=12;
////        adapter.setData(model1,0);
//
//        mRecyclerView.setAdapter(adapter);
//    }
//
//    private List<BaseInfoEntity> data;
//
//    private void setData() {
//        data = new ArrayList<>();
//
//        for (int i = 0; i < 2; i++) {
//            BaseInfoEntity infoEntity = new BaseInfoEntity();
//            infoEntity.setName("===shuju " + i);
//            data.add(infoEntity);
//        }
//
//    }
//
//    private void showSimpleBottomSheetList() {
////        new QMUIBottomSheet.BottomListSheetBuilder(this)
////                .addItem("从相册里选择")
////                .addItem("照相机去扫码")
////                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
////                    @Override
////                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
////                        dialog.dismiss();
////                        if (position == 1) {
////                            Intent intent = new Intent(StartpreActivity.this, CaptureActivity.class);
////                            startActivityForResult(intent, REQUST_CODE);
////                        }
////                        else if (position == 0) {
////
////                            ImageSelector.getInstance()
////
////                                    .startSelect(StartpreActivity.this);
////
////                        }
////                        Toast.makeText(StartpreActivity.this, "Item " + (position + 1), Toast.LENGTH_SHORT).show();
////                    }
////                })
////                .build()
////                .show();
//    }
//
//    @Override
//    public void onClick(View v) {
//        Intent intent;
//        switch (v.getId()) {
//            case R.id.btn_dingdansaomiao:
//
//                showSimpleBottomSheetList();
//                break;
//
//            case R.id.rl_tv_more:
//                intent = new Intent(StartpreActivity.this, StartdetailActivity.class);
//                intent.putExtra("result", result);
//                startActivity(intent);
//                break;
//
//            case R.id.btn_start_beiliao:
//                StratBeiliao();
//
//                break;
//            case R.id.btn_zhuanbei_beiliao:
//                ZhunbeiBeiliao();
//
//                break;
//            default:
//
//                break;
//        }
//    }
//
//    private void ZhunbeiBeiliao() {
//
//        if (TextUtils.isEmpty(result))
//        {
//            return;
//        }
//
////        ApiWebService.Get_Production_orderStock_end(this, new ApiWebService.SuccessCall() {
////            @Override
////            public void SuccessBack(String result) {
////                T.showLong(StartpreActivity.this, String2Bean.EndBeiliaos[Integer.valueOf(result)]);
////            }
////
////            @Override
////            public void ErrorBack(String error) {
////                Log.i("dsds","dsds");
////
////            }
////        },result, App.MID, App.Property, App.IsSub);
//
//
//        ApiWebService.Get_Production_orderStock_del(this, new ApiWebService.SuccessCall() {
//            @Override
//            public void SuccessBack(String result) {
//
//                if (result==null||TextUtils.isEmpty(result)||result.equals("[]")) {
//                    T.showLong(StartpreActivity.this, String2Bean.IsNUll);
//                    return;
//                }
//
//                T.showLong(StartpreActivity.this, String2Bean.Cancelbeiliaos[Integer.valueOf(result)]);
//            }
//
//            @Override
//            public void ErrorBack(String error) {
//                Log.i("dsds","dsds");
//            }
//        },result, MID, Property, IsSub);
//
//    }
//
//    private void StratBeiliao() {
//
//        if (TextUtils.isEmpty(result))
//        {
//            return;
//        }
//
//        if (iscomplete!=null&&!TextUtils.isEmpty(iscomplete)&&iscomplete.equals("complete")) {
//
//
//            ApiWebService.Get_Production_orderStock_end(this, new ApiWebService.SuccessCall() {
//            @Override
//            public void SuccessBack(String result) {
//                if (result==null||TextUtils.isEmpty(result)||result.equals("[]")) {
//                    T.showLong(StartpreActivity.this, String2Bean.IsNUll);
//                    return;
//                }
//                    T.showLong(StartpreActivity.this, String2Bean.EndBeiliaos[Integer.valueOf(result)]);
//            }
//
//            @Override
//            public void ErrorBack(String error) {
//                Log.i("dsds","dsds");
//
//            }
//        },result, MID, Property, IsSub);
//
//
//        }else {
//            ApiWebService.Get_Production_orderStock_start(this, new ApiWebService.SuccessCall() {
//                @Override
//                public void SuccessBack(String result) {
//
//                    if (result==null||TextUtils.isEmpty(result)||result.equals("[]")) {
//                        T.showLong(StartpreActivity.this, String2Bean.IsNUll);
//                        return;
//                    }
//                        T.showLong(StartpreActivity.this, String2Bean.StartBeiliaos[Integer.valueOf(result)]);
//                }
//
//                @Override
//                public void ErrorBack(String error) {
//                    Log.i("dsds", "dsds");
//
//                }
//            }, result, MID, Property, IsSub);
//        }
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUST_CODE) {
//            if (resultCode == 1) { //putByteArray
//                Bundle bundle = data.getBundleExtra("bundle");
//                byte[] bitmaps = bundle.getByteArray("bitmap");
//                result = bundle.getString("result");
//
//                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmaps, 0, bitmaps.length);
//                initWebserviceData(result);
//            }
//        }
//        else if (requestCode == ImageSelector.REQUEST_SELECT_IMAGE && resultCode == RESULT_OK) {
//            ArrayList<String> imagesPath = data.getStringArrayListExtra(ImageSelector.SELECTED_RESULT);
//
//            if (imagesPath != null) {
//
//                Intent intent = new Intent(StartpreActivity.this, QDSnapHelperActivity.class);
//                intent.putStringArrayListExtra("listarrayExtra", imagesPath);
//                startActivity(intent);
//
//            }
//        }
//    }
//
////    private void StringURl2Bitmap(String imageurl)
////    {
////        Bitmap bm = null;
////        ContentResolver resolver = getContentResolver();
////
////        try {
////
////            bm = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(imageurl));
////            //显得到bitmap图片
////            int width = bm.getWidth();
////            int height = bm.getHeight();
////            int[] data = new int[width * height];
////            bm.getPixels(data, 0, width, 0, 0, width, height);
////            RGBLuminanceSource source = new BufferedImageLuminanceSource(width, height, data);
////            BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
////            QRCodeReader reader = new QRCodeReader();
////            Result re = null;
////
////            try {
////                re = reader.decode(bitmap1);
////            } catch (NotFoundException e) {
////                e.printStackTrace();
////            } catch (ChecksumException e) {
////                e.printStackTrace();
////            } catch (FormatException e) {
////                e.printStackTrace();
////            }
////            if (re == null) {
////
////            } else {
////
////            }
////
////        } catch (Exception e) {
////            Log.e("TAG-->Error", e.toString());
////        }
////    }
//
//
//    private class MyItemDecoration extends RecyclerView.ItemDecoration {
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            super.getItemOffsets(outRect, view, parent, state);
//            outRect.set(0, 0, 0, 3);
//        }
//
//        @Override
//        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//            super.onDrawOver(c, parent, state);
//        }
//
//        @Override
//        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//            super.onDraw(c, parent, state);
//        }
//    }
//
//    private void initToolbar() {
////        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                onBackPressed();
////            }
////        });
//
////        mTopBar.setTitle("开始备料");
//
//        setActionBarMidlleTitle("开始备料");
//    }
//
//    private void initview() {
//        btn_dingdansaomiao = (Button) findViewById(R.id.btn_dingdansaomiao);
//
//        usernumber_danCode = (EditText) findViewById(R.id.usernumber_danCode);
//        usernumber_planstartDate = (EditText) findViewById(R.id.usernumber_planstartDate);
//        usernumber_plancloseDate = (EditText) findViewById(R.id.usernumber_plancloseDate);
//        usernumber_danStatus = (EditText) findViewById(R.id.usernumber_danStatus);
//        usernumber_zhidanpeople = (EditText) findViewById(R.id.usernumber_zhidanpeople);
//        usernumber_shenhepeople = (EditText) findViewById(R.id.usernumber_shenhepeople);
//
//        rl_tv_more = (TextView) findViewById(R.id.rl_tv_more);
//
//        rl_tv_more.setOnClickListener(this);
//
//        btn_dingdansaomiao.setOnClickListener(this);
//
////        btnStartBeiliao .setOnClickListener(this);
////
////        btnZhuanbeiBeiliao.setOnClickListener(this);
////
////        if (iscomplete!=null&&!TextUtils.isEmpty(iscomplete)&&iscomplete.equals("complete")) {
////            btnStartBeiliao.setText("备料完成");
////        }
//    }
//}
