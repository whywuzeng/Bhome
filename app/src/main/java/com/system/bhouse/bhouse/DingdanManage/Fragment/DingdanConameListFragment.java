package com.system.bhouse.bhouse.DingdanManage.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.socks.library.KLog;
import com.system.bhouse.Common.DataLoadType;
import com.system.bhouse.Common.LoadOrder;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.ActivityFragmentInject;
import com.system.bhouse.base.App;
import com.system.bhouse.base.BaseFragment;
import com.system.bhouse.bean.Dingdan;
import com.system.bhouse.bean.DingdanDetail;
import com.system.bhouse.bean.PicUpLoad;
import com.system.bhouse.bean.SubmitRet;
import com.system.bhouse.bean.WholeConame;
import com.system.bhouse.bean.formlist;
import com.system.bhouse.bhouse.DingdanManage.DingdanConameListActivity;
import com.system.bhouse.bhouse.DingdanManage.Presenter.DingdanConameListPresenter;
import com.system.bhouse.bhouse.DingdanManage.Presenter.DingdanConameListPresenterImpl;
import com.system.bhouse.bhouse.DingdanManage.UiInterface.DingdanConameListView;
import com.system.bhouse.bhouse.ImageSelect.ImageSelectActivity;
import com.system.bhouse.bhouse.ImageSelect.PhotoViewActivity;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.ui.MListView;
import com.system.bhouse.ui.ThreePointLoadingView;
import com.system.bhouse.utils.BHEncodeUtils;
import com.system.bhouse.utils.MeasureUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-4-22.
 * ClassName: com.system.bhouse.bhouse.DingdanManage.Fragment
 * Author:Administrator
 * Fuction:  订单管理明细的  fragment实际界面
 * UpdateUser:
 * UpdateDate:
 */
@ActivityFragmentInject(contentViewId = R.layout.dingdanconamelistfragment)
public class DingdanConameListFragment extends BaseFragment<DingdanConameListPresenter> implements DingdanConameListView,DingdanConameListActivity.getClickListenerToActivity {

    @BindView(R.id.coname_list_content)
    MListView coname_list_content;
    @BindView(R.id.dingdan_list_content)
    MListView dingdan_list_content;
    @BindView(R.id.tpl_view)
    ThreePointLoadingView tpl_view;
//
//    @butterknife.BindView(R.id.btn_uploadpic)
//    //图片上传
//            Button btnUploadpic;
//    @butterknife.BindView(R.id.btn_switchstatus)
//    //订单状态转换
//            Button btnSwitchstatus;
    @BindView(R.id.iv_uploadpic)
    ImageView ivUploadpic;
//    @butterknife.BindView(R.id.ll_top)
//    LinearLayout ll_top;
    //图片上传的其他图片
    @BindView(R.id.iv_uploadpic1)
    ImageView iv_uploadpic1;
    @BindView(R.id.iv_uploadpic2)
    ImageView iv_uploadpic2;

    // coname 的数据
    private List<formlist> formlist;

    //要传送的数据  每个fragment 都不一样
    private String ccid;
    private String statusid;
    private String ccNumber;

    private adapter_coname_list_content MAdapter_coname_list_content;
    private MyAdapterdingdanslist myAdapterdingdanslist;
    private List<DingdanDetail> Dingdans;
    private String[] String_fanhui;

    private List<ImageView> imageViews;

    @Override
    protected void initview(View fragmentrootview) {

        initEvent();
        ccid = (getActivity().getIntent().getIntExtra("Ccid", 0)) + "";
        statusid = (getActivity().getIntent().getStringExtra("statusid"));
        ccNumber = (getActivity().getIntent().getStringExtra("ccNumber"));

        ((DingdanConameListActivity)getActivity()).SetClickListenerToActivity(this);

        mPresenter = new DingdanConameListPresenterImpl(this, ccid,BHEncodeUtils.BHEncode(ccid), BHEncodeUtils.BHEncode(statusid), BHEncodeUtils.BHEncode(App.MobileKey), App.USER_INFO);

        imageViews=new ArrayList<>();
        imageViews.add(ivUploadpic);
        imageViews.add(iv_uploadpic1);
        imageViews.add(iv_uploadpic2);


//        Glide.with(this.getActivity()).load("http://gis.bhome.com.cn:8050/upfile/20160606/2016060617231620160606172315_906Order-000011-2016-06-06.png").crossFade().placeholder(R.mipmap.ic_loading_small_bg).diskCacheStrategy(DiskCacheStrategy.ALL)
//                .error(R.mipmap.ic_fail).listener(new LoggingListener<String, GlideDrawable>()).into(iv_uploadpic3);

        getUploadPicData(LoadOrder.First);
    }

    private void getUploadPicData(LoadOrder type) {
//        mPresenter.RefreshDataPicUpload(BHEncodeUtils.BHEncode(ccid), BHEncodeUtils.BHEncode(statusid), BHEncodeUtils.BHEncode(App.MobileKey), App.USER_INFO,type);
    }

    private void initEvent() {

        String_fanhui = getContext().getResources().getStringArray(R.array.dingdanzhuangtaigenghuanfanhuizhi);

//        refreshLayout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                String label = DateUtils.formatDateTime(DingdanConameListFragment.this.getActivity(), System.currentTimeMillis(),
//                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//                // Update the LastUpdatedLabel
//                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//
//                if (!TextUtils.isEmpty(ccid))
//                    mPresenter.RefreshData(ccid,LoadOrder.Third);
//
////                getUploadPicData(LoadOrder.Third);
//            }
//        });

//        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
//        soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
//        soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);

//        //打开图片上传的状态
//        btnUploadpic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (Dingdans != null && Dingdans.size() > 0) {
//
//                }
//            }
//        });
        //更新状态
//        btnSwitchstatus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//                if (App.USER_INFO != null && App.MID != null) {
//                    String ccid_encode = BHEncodeUtils.BHEncode(ccid);
//                    String statusid_encode = BHEncodeUtils.BHEncode(statusid);
//                    String MobileKey_encode = BHEncodeUtils.BHEncode(App.MobileKey);
//                    ApiServiceUtils.getInstence().getDingdandetail(ccid_encode, ccNumber, statusid_encode, App.USER_INFO, App.MID + "", MobileKey_encode).subscribe(new Observer<SubmitRet[]>() {
//                        @Override
//                        public void onCompleted() {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onNext(SubmitRet[] submitRets) {
//                            Toast.makeText(DingdanConameListFragment.this.getActivity(), String_fanhui[submitRets[0].getDnum()], Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                else {
//                    Toast.makeText(getActivity(), "数据失效，重新登录", Toast.LENGTH_SHORT).show();
//                }

//            }
//        });
    }

    private void initdata(WholeConame wholeConame) {

        formlist = new ArrayList<>();
        String[] coname_key = getResources().getStringArray(R.array.Coname_context);

        String[] coname_values = new String[]{wholeConame.getCcid() + "", wholeConame.getCcNumber(), wholeConame.getCcType(), wholeConame.getCname(), wholeConame.getCper(), wholeConame.getCphoe(), wholeConame.getDisnum(), wholeConame.getRowfy() + "", wholeConame.getTransportfy() + "", wholeConame.getOtherfy() + "", wholeConame.getHjfy() + "", wholeConame.getInstallationarea(), wholeConame.getSpecificarea(), wholeConame.getArea(), wholeConame.getYjstartdate(), wholeConame.getYjenddate(), wholeConame.getYjdatenum() + "", wholeConame.getBeizhu(), wholeConame.getStatus(), wholeConame.getManCompany(), wholeConame.getAddPer(), wholeConame.getAddTime(), wholeConame.getShPer(), wholeConame.getShTime()};

        for (int i = 0; i < coname_key.length; i++) {
            formlist formlist1 = new formlist();
            formlist1.setName(coname_key[i]);
            formlist1.setInput(coname_values[i]);
            formlist.add(formlist1);
        }
        if (MAdapter_coname_list_content == null) {
            MAdapter_coname_list_content = new adapter_coname_list_content(formlist);
            coname_list_content.setAdapter(MAdapter_coname_list_content);
        }
        else {
            MAdapter_coname_list_content.setData(formlist);
            MAdapter_coname_list_content.notifyDataSetChanged();
        }

    }

    private void initDingdanDetail(DingdanDetail dingdanDetail) {

        formlist = new ArrayList<>();
        String[] coname_key = getResources().getStringArray(R.array.dingdanDetail);

        String[] coname_values = new String[]{dingdanDetail.ccNumber + "", dingdanDetail.ccType, dingdanDetail.cname, dingdanDetail.cper, dingdanDetail.cphoe, dingdanDetail.Installationarea, dingdanDetail.specificarea, dingdanDetail.area + "", dingdanDetail.yjstartdate + "", dingdanDetail.yjenddate + "", dingdanDetail.yjdatenum + "", dingdanDetail.beizhu, dingdanDetail.status, dingdanDetail.stuname, dingdanDetail.ManCompany};

        for (int i = 0; i < coname_key.length; i++) {
            formlist formlist1 = new formlist();
            formlist1.setName(coname_key[i]);
            formlist1.setInput(coname_values[i]);
            formlist.add(formlist1);
        }
        if (MAdapter_coname_list_content == null) {
            MAdapter_coname_list_content = new adapter_coname_list_content(formlist);
            coname_list_content.setAdapter(MAdapter_coname_list_content);
        }
        else {
            MAdapter_coname_list_content.setData(formlist);
            MAdapter_coname_list_content.notifyDataSetChanged();
        }

//        setpicListener();
//        Glide.with(this.getActivity()).load(R.mipmap.ic_fail).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_loading_small_bg)
//                .error(R.mipmap.ic_fail).centerCrop().override(300, 100).into(ivUploadpic);


//        ivUploadpic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DingdanConameListFragment.this.getActivity(), PhotoViewActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getUploadPicData(LoadOrder.Third);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public void setpicListener(int size, final List<PicUpLoad> pic_url) {
        for (int i = 0; i < size; i++) {

//            ImageView imageView = new ImageView(getContext());
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            imageViews.get(i).setVisibility(View.VISIBLE);
//            ll_top.addView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT));
            final String pics_url = pic_url.get(i).Url;
            if(pics_url.contains("png")||pics_url.contains("jpg")){

                Glide.with(this.getActivity()).load(pics_url).asBitmap().placeholder(R.mipmap.ic_loading_small_bg).diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .error(R.drawable.ic_fail) .centerCrop().override(MeasureUtil.dip2px(this.getActivity(), 652), MeasureUtil.dip2px(this.getActivity(),368)).into(imageViews.get(i));
            }else{
                Glide.with(this.getActivity()).load(R.mipmap.ic_fail).placeholder(R.mipmap.ic_loading_small_bg).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.mipmap.ic_fail).override(MeasureUtil.px2dip(this.getActivity(),652), MeasureUtil.px2dip(this.getActivity(),368)).centerCrop().into(imageViews.get(i));
            }

            //diskCacheStrategy(DiskCacheStrategy.ALL)
            imageViews.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(DingdanConameListFragment.this.getActivity(), PhotoViewActivity.class);
                    intent.putExtra("pic_url.get(i).Url",pics_url);
                    startActivity(intent);

                }
            });
        }
    }

    public class LoggingListener<T, R> implements RequestListener<T, R> {
        @Override public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
            android.util.Log.d("GLIDE", String.format(Locale.ROOT,
                    "onException(%s, %s, %s, %s)", e, model, target, isFirstResource), e);
            return false;
        }
        @Override public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
            android.util.Log.d("GLIDE", String.format(Locale.ROOT,
                    "onResourceReady(%s, %s, %s, %s, %s)", resource, model, target, isFromMemoryCache, isFirstResource));
            return false;
        }
    }

//    public void setUploadpic(int size){
//        PhotoView mPhotoView = new PhotoView(getActivity());
//
//        Glide.with(this.getActivity()).load(R.mipmap.ic_fail).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_loading_small_bg)
//                .error(R.mipmap.ic_fail).centerCrop().override(300, 100).into(mPhotoView);
//
//        ll_top.addView(mPhotoView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//    }

    //回调数据
    @Override
    public void initConameData(List<DingdanDetail> data, DataLoadType type) {

        switch (type) {
            case TYPE_REFRESH_SUCCESS:
//设置数据
//                initdata(data);
                Dingdans = new ArrayList<>();
                Dingdans = data;
                initDingdanDetail(data.get(0));
                initDingdan(data);

                break;
            case TYPE_REFRESH_FAIL:
                if(!TextUtils.isEmpty(ccid)&&data==null) {
                    mPresenter.RefreshData(ccid,LoadOrder.Second);
                }
                break;
        }
    }

    @Override
    public void initDingdanData(Dingdan data, DataLoadType type) {
        switch (type) {
            case TYPE_REFRESH_SUCCESS:
//                initDingdan(data);
                break;
            case TYPE_REFRESH_FAIL:
                break;
        }

    }

    //得到图片的url
    @Override
    public void getUploadpic(PicUpLoad[] s) {
        KLog.e(s);
        List<PicUpLoad> picUpLoads = new ArrayList<>();
        for (PicUpLoad p : s) {
            picUpLoads.add(p);
        }
        setpicListener(picUpLoads.size(), picUpLoads);
    }

    private void initDingdan(List<DingdanDetail> data) {
        List<DingdanDetail> Dingdans = new ArrayList<>();
        Dingdans.addAll(data);
        if (myAdapterdingdanslist == null) {
            myAdapterdingdanslist = new MyAdapterdingdanslist(Dingdans);
            dingdan_list_content.setAdapter(myAdapterdingdanslist);
        }
        else {
            myAdapterdingdanslist.setData(Dingdans);
            myAdapterdingdanslist.notifyDataSetChanged();
        }
    }


    @Override
    public void showProgress() {
        super.showProgress();
        if (!isshow) {
            tpl_view.play();
        }
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        if (isshow) {
            tpl_view.stop();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    /**
     * 合同的适配器
     */
    class adapter_coname_list_content extends BaseAdapter {
        private List<formlist> formlist;

        public adapter_coname_list_content(List<formlist> formlist) {
            this.formlist = formlist;
        }

        public void setData(List<formlist> formlist) {
            this.formlist = formlist;
        }

        @Override
        public int getCount() {
            return formlist.size();
        }

        @Override
        public Object getItem(int position) {
            return formlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(DingdanConameListFragment.this.getActivity(), R.layout.orderinputitem, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.et_orideritem.setText(formlist.get(position).getName());
            holder.orderitem_tv.setText(formlist.get(position).getInput());
            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.et_orideritem)
            TextView et_orideritem;
            @BindView(R.id.orderitem_tv)
            TextView orderitem_tv;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    public void ErrorRefresh(LoadOrder type) {
        if(type==LoadOrder.First) {
            getUploadPicData(LoadOrder.Second);
        }
    }

    /**
     * 订单的适配器
     */
    class MyAdapterdingdanslist extends BaseAdapter {

        private List<DingdanDetail> dingdanslist;

        public MyAdapterdingdanslist(List<DingdanDetail> dingdanslist) {
            this.dingdanslist = dingdanslist;
        }

        public void setData(List<DingdanDetail> dingdanslist) {
            this.dingdanslist = dingdanslist;
        }

        @Override
        public int getCount() {
            return dingdanslist == null ? 0 : dingdanslist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(DingdanConameListFragment.this.getActivity(), R.layout.dingdandetail_item_custom, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.dingdanxianshi_tv_custom.setText(dingdanslist.get(position).pname);
            holder.dingdan_bieshugenzhong_tv.setText(dingdanslist.get(position).proType);
            holder.dingdan_canpinleixing_tv.setText(dingdanslist.get(position).pnumber);
            holder.dingdan_bieshubianma_tv.setText(dingdanslist.get(position).pname);
            holder.dingdan_chanpinbieshumingchen_tv.setText(dingdanslist.get(position).pnum + "");
            holder.dingdan_chanpinguige_tv.setText(dingdanslist.get(position).anzhPer);
            holder.dingdan_dinggoushuliang_tv.setText(dingdanslist.get(position).anzhphone + "");
//            holder.dingdan_yunju_tv.setText(dingdanslist.get(position).getDisnum());
//            holder.dingdan_yunfei_tv.setText(dingdanslist.get(position).getDisfy() + "");
//            holder.dingdan_cailianfei_tv.setText(dingdanslist.get(position).getRowfy() + "");
//            holder.dingdan_yunshufei_tv.setText(dingdanslist.get(position).getTransportfy() + "");
//            holder.dingdan_qitafeiyong_tv.setText(dingdanslist.get(position).getOtherfy() + "");
//            holder.dingdan_heijijine_tv.setText(dingdanslist.get(position).getHtAmount() + "");
//            holder.dingdan_shengchangongchang_tv.setText(dingdanslist.get(position).getManCompany());
//            holder.dingdan_danjuzhuangtai_tv.setText(dingdanslist.get(position).getStatus());
//            holder.dingdan_lururen_tv.setText(dingdanslist.get(position).getAddPer());
//            holder.dingdan_lurushijian_tv.setText(dingdanslist.get(position).getAddTime());
//            holder.dingdan_shenghairen_tv.setText(dingdanslist.get(position).getShPer());
//            holder.dingdan_shenhaishijian_tv.setText(dingdanslist.get(position).getShTime());

//            holder.dingdan_yunju_tv.setVisibility(View.GONE);
//            holder.dingdan_yunfei_tv.setVisibility(View.GONE);
//            holder.dingdan_cailianfei_tv.setVisibility(View.GONE);
//            holder.dingdan_yunshufei_tv.setVisibility(View.GONE);
//            holder.dingdan_qitafeiyong_tv.setVisibility(View.GONE);
//            holder.dingdan_heijijine_tv.setVisibility(View.GONE);
//            holder.dingdan_shengchangongchang_tv.setVisibility(View.GONE);
//            holder.dingdan_danjuzhuangtai_tv.setVisibility(View.GONE);
//            holder.dingdan_lururen_tv.setVisibility(View.GONE);
//            holder.dingdan_lurushijian_tv.setVisibility(View.GONE);
//            holder.dingdan_shenghairen_tv.setVisibility(View.GONE);
//            holder.dingdan_shenhaishijian_tv.setVisibility(View.GONE);

            holder.dingdanxiugai_tv_custom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DingdanConameListFragment.this.getActivity(), "可有....", 1).show();
//                    dingdanslist

                }
            });
            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.dingdanxianshi_tv_custom)
            TextView dingdanxianshi_tv_custom;
            @BindView(R.id.dingdanxiugai_tv_custom)
            TextView dingdanxiugai_tv_custom;
            @BindView(R.id.dingdan_bieshugenzhong_tv)
            TextView dingdan_bieshugenzhong_tv;
            @BindView(R.id.dingdan_canpinleixing_tv)
            TextView dingdan_canpinleixing_tv;
            @BindView(R.id.dingdan_bieshubianma_tv)
            TextView dingdan_bieshubianma_tv;
            @BindView(R.id.dingdan_chanpinbieshumingchen_tv)
            TextView dingdan_chanpinbieshumingchen_tv;
            @BindView(R.id.dingdan_chanpinguige_tv)
            TextView dingdan_chanpinguige_tv;
            @BindView(R.id.dingdan_dinggoushuliang_tv)
            TextView dingdan_dinggoushuliang_tv;
//            @butterknife.BindView(R.id.dingdan_yunju_tv)
//            TextView dingdan_yunju_tv;
//            @butterknife.BindView(R.id.dingdan_yunfei_tv)
//            TextView dingdan_yunfei_tv;
//            @butterknife.BindView(R.id.dingdan_cailianfei_tv)
//            TextView dingdan_cailianfei_tv;
//            @butterknife.BindView(R.id.dingdan_yunshufei_tv)
//            TextView dingdan_yunshufei_tv;
//            @butterknife.BindView(R.id.dingdan_qitafeiyong_tv)
//            TextView dingdan_qitafeiyong_tv;
//            @butterknife.BindView(R.id.dingdan_heijijine_tv)
//            TextView dingdan_heijijine_tv;
//            @butterknife.BindView(R.id.dingdan_shengchangongchang_tv)
//            TextView dingdan_shengchangongchang_tv;
//            @butterknife.BindView(R.id.dingdan_danjuzhuangtai_tv)
//            TextView dingdan_danjuzhuangtai_tv;
//            @butterknife.BindView(R.id.dingdan_lururen_tv)
//            TextView dingdan_lururen_tv;
//            @butterknife.BindView(R.id.dingdan_lurushijian_tv)
//            TextView dingdan_lurushijian_tv;
//            @butterknife.BindView(R.id.dingdan_shenghairen_tv)
//            TextView dingdan_shenghairen_tv;
//            @butterknife.BindView(R.id.dingdan_shenhaishijian_tv)
//            TextView dingdan_shenhaishijian_tv;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }




    //这是  两个按钮的点击实现
    @Override
    public void getClickbtnUploadpic() {
        Bundle bundle = new Bundle();

        Intent intent = new Intent(DingdanConameListFragment.this.getActivity(), ImageSelectActivity.class);
        intent.putExtra("ccid", ccid);
        intent.putExtra("statusid", statusid);
        intent.putExtra("ccNumber", ccNumber);
        startActivity(intent);
    }

    @Override
    public void getClickbtnSwitchstatus() {
        if (App.USER_INFO != null && App.MID != null) {
            String ccid_encode = BHEncodeUtils.BHEncode(ccid);
            String statusid_encode = BHEncodeUtils.BHEncode(statusid);
            String MobileKey_encode = BHEncodeUtils.BHEncode(App.MobileKey);
//            ApiServiceUtils.getInstence().getDingdandetail(ccid_encode, ccNumber, statusid_encode, App.USER_INFO, App.MID + "", MobileKey_encode).subscribe(new Observer<SubmitRet[]>() {
//                @Override
//                public void onCompleted() {
//
//                }
//
//                @Override
//                public void onError(Throwable e) {
//
//                }
//
//                @Override
//                public void onNext(SubmitRet[] submitRets) {
//                    Toast.makeText(DingdanConameListFragment.this.getActivity(), String_fanhui[submitRets[0].getDnum()], Toast.LENGTH_SHORT).show();
//                }
//            });

            ApiWebService.getDingdanStatus(this.getActivity(), new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {

//                    T.showShort(DingdanConameListFragment.this.getActivity(),result);
                    Gson gson=new Gson();
                    SubmitRet[] submitRets = gson.fromJson(result, SubmitRet[].class);
                    Toast.makeText(DingdanConameListFragment.this.getActivity(), String_fanhui[submitRets[0].getDnum()], Toast.LENGTH_SHORT).show();
                }

                @Override
                public void ErrorBack(String error) {
                    Toast.makeText(DingdanConameListFragment.this.getActivity(),error,Toast.LENGTH_SHORT).show();
                }
            },ccid_encode,ccNumber,statusid_encode, App.USER_INFO, App.MID + "",MobileKey_encode);


        }
        else {
            Toast.makeText(DingdanConameListFragment.this.getActivity(), "数据失效，重新登录", Toast.LENGTH_SHORT).show();
        }
    }
}
