package com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Base.BaseBackFragment;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment.Bean.LoadingIntoPickBean;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment.Bean.LoadingIntoPickBeanSection;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment.adpter.LoadingIntoSectionAdapter;
import net.qiujuer.italker.common.adapter.BaseQuickAdapter;
import net.qiujuer.italker.common.adapter.BaseSectionQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.Widget.LRecyclerView;
import com.system.bhouse.bhouse.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018-08-24.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment
 */

public class LoadingIntoWareHousePickerFragment extends BaseBackFragment implements AdapterView.OnItemSelectedListener {

    public static final String BUNDLE_RESULT_AGES_CODING = "bundle_result_ages_coding";
    public static final String BUNDLE_RESULT_ARGS_PROJECTNAME="bundle_result_args_projectname";
    public static final String BUNDLE_RESULT_AGES_HID = "bundle_result_ages_hid";
    public static final String BUNDLE_RESULT_AGES_POSITION = "bundle_result_ages_position";
    public static final String BUNDLE_RESULT_AGES_BOMID = "bundle_result_ages_bomid";
    public static final String BUNDLE_RESULT_AGES_TITLE = "bundle_result_ages_title";
    public static final String BUNDLE_RESULT_AGES_CONTAINER="bundle_result_ages_container";
    @BindView(R.id.et_query)
    EditText etQuery;
    @BindView(R.id.tv_Empty)
    TextView tvEmpty;
    @BindView(R.id.spDwon)
    Spinner spDwon;

    private ArrayAdapter<String> stringArrayAdapter;
    private List<LoadingIntoPickBeanSection> mySections;
    private BaseSectionQuickAdapter commonSectionAdapter;

    @BindView(R.id.listView)
    LRecyclerView listView;
    @BindView(R.id.toolbar_com)
    android.support.v7.widget.Toolbar mToolbar;

    public final static String TITLE_EXTRA = "title";
    public final static String EXTRA_POSITION_EXTRA = "extraPosition";
    public final static String B_PRO_BO_MS_EXTRA = "bProBOMs";
    public final static String H_ID_EXTRA = "HId";
    public final static String POSITION_EXTRA = "Position";
    public final static String LAYOUT_ID_EXTRA = "LayoutID";
    public final static String IS_NEWS_ADAPTER_EXTRA = "isNewsAdapter";

    String title;

    String extraPosition;

    ArrayList<LoadingIntoPickBean> mLoadingIntoPickBean;

    String HId;

    Integer Position;

    Integer LayoutID;

    boolean isNewsAdapter;

    private int Count=50;

    public static LoadingIntoWareHousePickerFragment newInstance(String title, String extraPosition, ArrayList<LoadingIntoPickBean> bProBOMs, String HId, Integer position, Integer LayoutID, boolean isNewsAdapter) {

        Bundle args = new Bundle();
        args.putString(TITLE_EXTRA,title);
        args.putString(EXTRA_POSITION_EXTRA,extraPosition);
        args.putParcelableArrayList(B_PRO_BO_MS_EXTRA,  bProBOMs);
        args.putString(H_ID_EXTRA,HId);
        args.putInt(POSITION_EXTRA,position);
        args.putInt(LAYOUT_ID_EXTRA,LayoutID);
        args.putBoolean(IS_NEWS_ADAPTER_EXTRA,isNewsAdapter);
        LoadingIntoWareHousePickerFragment fragment = new LoadingIntoWareHousePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args!=null)
        {
            this.title = args.getString(TITLE_EXTRA, "");
            this.extraPosition = args.getString(EXTRA_POSITION_EXTRA, "");
            this.mLoadingIntoPickBean = args.getParcelableArrayList(B_PRO_BO_MS_EXTRA);
            this.HId = args.getString(H_ID_EXTRA, "");
            this.Position = args.getInt(POSITION_EXTRA, -1);
            this.LayoutID = args.getInt(LAYOUT_ID_EXTRA, -1);
            this.isNewsAdapter = args.getBoolean(IS_NEWS_ADAPTER_EXTRA);
        }
    }

    //初始化布局
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_complexnumpicker);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        setParentActionBarMidleTitle(title);
        initData();
            commonSectionAdapter = new LoadingIntoSectionAdapter(LayoutID, R.layout.layout_home_recommend_empty, mySections);
//        myAdapter = new MyAdapter();
        commonSectionAdapter.setFriends(mySections);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(commonSectionAdapter);
        queryEdit();
        initToolbarNav(mToolbar);
    }

    private  void initData() {
        mySections=new ArrayList<>();
        if (!mLoadingIntoPickBean.isEmpty())
        {
            for (LoadingIntoPickBean bom:mLoadingIntoPickBean)
            {
                mySections.add(new LoadingIntoPickBeanSection(bom));
            }
        }

    }

    private void queryEdit() {

        ArrayList<String> list = new ArrayList<>();
        list.add("50");
        list.add("100");
        list.add("500");
        list.add("1000");
        list.add("全部");
        stringArrayAdapter = new ArrayAdapter<String>(_mActivity, android.R.layout.simple_spinner_item, list);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spDwon.setAdapter(stringArrayAdapter);
        spDwon.setOnItemSelectedListener(this);

        //清除搜索内容
        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etQuery.getText().clear();
            }
        });

        etQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //过滤操作
                commonSectionAdapter.getFilter().filter(s);
                if (s.length() > 0) {
                    //清空按钮
                    tvEmpty.setEnabled(true);
                } else {
                    tvEmpty.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        commonSectionAdapter.setmRefreshListener(new BaseSectionQuickAdapter.FilterRefreshListener<LoadingIntoPickBeanSection>() {
            @Override
            public String getIsFilter(String prefixString, LoadingIntoPickBeanSection value) {
                String username = value.t.getOrderID();
                // First match against the whole ,non-splitted value，假如含有关键字的时候，添加
                username.contains(prefixString);
                return username;
            }

            @Override
            public void onAdapterRefresh() {
                commonSectionAdapter.notifyDataSetChanged();
            }
        });

        commonSectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(BUNDLE_RESULT_ARGS_PROJECTNAME, mLoadingIntoPickBean.get(position).getProjectName());
                bundle.putString(BUNDLE_RESULT_AGES_CODING, mLoadingIntoPickBean.get(position).getLoadingCarNumber());
                bundle.putString(BUNDLE_RESULT_AGES_HID, mLoadingIntoPickBean.get(position).getOrderID());
                bundle.putInt(BUNDLE_RESULT_AGES_POSITION, Position);
                bundle.putString(BUNDLE_RESULT_AGES_BOMID,mLoadingIntoPickBean.get(position).getLoadingCarID());
                bundle.putString(BUNDLE_RESULT_AGES_TITLE,title);
                bundle.putString(BUNDLE_RESULT_AGES_CONTAINER,mLoadingIntoPickBean.get(position).getContainerName());
                setFragmentResult(RESULT_OK, bundle);
                pop();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        String cityName=stringArrayAdapter.getItem(position);   //获取选中的那一项
        try {
            //减少源数据的个数 然后再刷新。 clone()之后防止,ConcurrentModificationException sublist 之后不能清除clear

            Count = Integer.valueOf(cityName);
            int min = Math.min(Count, mySections.size());
            List<LoadingIntoPickBeanSection> clone = (List<LoadingIntoPickBeanSection>) ((ArrayList<LoadingIntoPickBeanSection>)this.mySections).clone();
            List<LoadingIntoPickBeanSection> mySections = clone.subList(0,min);
            this.mySections.clear();
            this.mySections.addAll(mySections);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Count=9999;
        }
        commonSectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
