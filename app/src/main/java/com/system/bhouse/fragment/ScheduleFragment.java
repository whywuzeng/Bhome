package com.system.bhouse.fragment;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.custom.vg.list.CustomListView;
import com.custom.vg.list.OnItemClickListener;
import com.system.bhouse.base.ActivityFragmentInject;
import com.system.bhouse.base.BaseFragment;
import com.system.bhouse.bean.SearchHistroyBean;
import com.system.bhouse.bean.SiteSearchTwoWayBean;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.Workflowlist.Presenter.WorkflowlistPresenterImp;
import com.system.bhouse.bhouse.Workflowlist.UIInterface.WorkflowlistView;
import com.system.bhouse.bhouse.Workflowlist.adapter.SeacherHistoryAdapter;
import com.system.bhouse.bhouse.Workflowlist.adapter.SearchListAdapter;
import com.system.bhouse.bhouse.Workflowlist.adapter.SiteSearchTwoWayAdapter;
import com.system.bhouse.bhouse.Workflowlist.adapter.section.WorkflowSection;
import com.system.bhouse.db.SearchRecordDao;
import com.system.bhouse.ui.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/7.
 */
@ActivityFragmentInject(contentViewId = R.layout.schedulefragment)
public class ScheduleFragment extends BaseFragment<WorkflowlistPresenterImp> implements WorkflowlistView,View.OnClickListener {


    @BindView(R.id.site_search_back)
    ImageView siteSearchBack;
    @BindView(R.id.tv_query)
    TextView tvQuery;
    @BindView(R.id.et_search_content)
    EditText etSearchContent;
    @BindView(R.id.middle_search)
    LinearLayout middleSearch;
    @BindView(R.id.tv_history_top)
    TextView tvHistoryTop;
    @BindView(R.id.tv_delAll)
    TextView tvDelAll;
    @BindView(R.id.clv_search_stroy)
    CustomListView clvSearchStroy;
    @BindView(R.id.ll_history_layout)
    LinearLayout llHistoryLayout;
    @BindView(R.id.layout_maylike)
    LinearLayout layoutMaylike;
    @BindView(R.id.rv_listview)
    RecyclerView rv_listview;
    private SearchRecordDao searchRecordDao; //数据库
    private String key_word;
    private PopupWindow popupWindow;
    private ListView popList;
    private ArrayList<SearchHistroyBean> searchHistroyBeans;

    private SectionedRecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void initview(View fragmentrootview) {
        searchRecordDao = new SearchRecordDao();
        initSearch();
        initView();
    }

    private void initView() {
        siteSearchBack.setOnClickListener(this);
        tvQuery.setOnClickListener(this);

        etSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!s.toString().isEmpty()) {
                    if (!(s.toString().equals(key_word))) {
                        // 与选择的关键字不一样
                        showHistroyList(s.toString());
                    }
                    else {
                        //关闭popWindow
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void showHistroyList(String s) {

        //这里可以从数据库里 取得数据
        //然后创建 popwindow()

        createPopWindow();
    }

    private void createPopWindow() {
        SearchListAdapter searchListAdapter = new SearchListAdapter(getActivity());
        if (popupWindow==null)
        {
            LayoutInflater from = LayoutInflater.from(getActivity());
            View view = from.inflate(R.layout.pop_searchlist, null, false);
            popList= (ListView)view.findViewById(R.id.luck_pop_listview);

            popList.setAdapter(searchListAdapter);

            int height=(int)(getResources().getDimension(R.dimen.search_item_height_1))*6;

            popupWindow= new PopupWindow(view,middleSearch.getWidth(),height);//初始化popwindow

            popupWindow.setTouchable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(getDrawable());

            popupWindow.showAsDropDown(middleSearch,0,0);
        }

        if (!popupWindow.isShowing())
        {
            popupWindow.showAsDropDown(middleSearch,0,0);
        }

        searchListAdapter.setOnItemMyClick(new SearchListAdapter.OnItemMyClick() {
            @Override
            public void onItem(String value) {
                key_word = value;
                startSearch();
            }
        });

        popList.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        break;
                }
                return true;
            }
        });

    }

    private Drawable getDrawable()
    {
        ShapeDrawable bgdrawable =new ShapeDrawable(new OvalShape());
        bgdrawable.getPaint().setColor(getActivity().getResources().getColor(android.R.color.transparent));
        return   bgdrawable;
    }

    private void startSearch() {
        SearchHistroyBean bean = new SearchHistroyBean();
        bean.setSh_name(key_word);
        int code=0;
        for (int i=0;i<searchHistroyBeans.size();i++)
        {
            SearchHistroyBean bean1 = searchHistroyBeans.get(i);
            if (bean1.getSh_name().equals(key_word))
            {
                code=1;
            }
        }

        if (1!=code)
        {
            searchRecordDao.add(bean);
            searchHistroyBeans.add(bean);//把代码加进去
        }


        if (searchHistroyBeans.size()>10)
        {
            searchRecordDao.delete(searchHistroyBeans.get(10));
        }

        //进入搜索商品的种类的界面 这个界面也可以写的啦
    }

    private void initSearch() {

        mPresenter = new WorkflowlistPresenterImp(this, "未审批", 50, "dujun", null);
        SiteSearchTwoWayAdapter siteSearchTwoWayAdapter = new SiteSearchTwoWayAdapter(getActivity());

//        gridview.setNumColumns(3);
//        gridview.setScrollDirectionPortrait(TwoWayAbsListView.SCROLL_VERTICAL);
//        gridview.setScrollDirectionLandscape(TwoWayAbsListView.SCROLL_HORIZONTAL);
//        gridview.setAdapter(siteSearchTwoWayAdapter);

//        rv_listview

        mRecyclerViewAdapter=new SectionedRecyclerViewAdapter();
        GridLayoutManager manager=new GridLayoutManager(getActivity(),1);
        rv_listview.setLayoutManager(manager);
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
        layoutMaylike.setVisibility(View.VISIBLE);

        rv_listview.setAdapter(mRecyclerViewAdapter);

        List<SiteSearchTwoWayBean> beanList=new ArrayList<>();

        SiteSearchTwoWayBean searchTwoWayBean=null;
        for (int i=1;i<20;i++)
        {
            searchTwoWayBean=new SiteSearchTwoWayBean();
            searchTwoWayBean.SPH="131"+i;
            searchTwoWayBean.numberType="销售报价"+i;
            searchTwoWayBean.DocumentNumber="天津-201703-001"+"--"+i;
            searchTwoWayBean.ManagementOrganization="江苏工厂"+i;
            beanList.add(searchTwoWayBean);
        }

        WorkflowSection workflowSection = new WorkflowSection(beanList);

        workflowSection.setOnItemClickListener(new WorkflowSection.OnItemClickListener() {
            @Override
            public void onItemClick(View view, TextView textView, int position) {
                showToastmsg("你点击了"+position);

                mRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerViewAdapter.addSection(workflowSection);

        mRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResume() {
        searchHistroyBeans = searchRecordDao.fetcheAll();
        if (searchHistroyBeans.size()==0)
        {
            tvHistoryTop.setVisibility(View.VISIBLE);
            llHistoryLayout.setVisibility(View.GONE);
        }else {
            tvHistoryTop.setVisibility(View.GONE);
            llHistoryLayout.setVisibility(View.VISIBLE);
        }
        SeacherHistoryAdapter seacherHistoryAdapter = new SeacherHistoryAdapter(getActivity(),searchHistroyBeans);
        clvSearchStroy.setAdapter(seacherHistoryAdapter);
        clvSearchStroy.setDividerHeight(16);
        clvSearchStroy.setDividerWidth(12);

        clvSearchStroy.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SearchHistroyBean bean = searchHistroyBeans.get(i);
                key_word = bean.getSh_name();
                startSearch();
            }
        });

        tvDelAll.setOnClickListener(this);//点击事件

        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void RefreshView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_delAll:
                searchRecordDao.deleteAll();
                searchHistroyBeans= searchRecordDao.fetcheAll();
                SeacherHistoryAdapter seacherHistoryAdapter = new SeacherHistoryAdapter(getActivity(),searchHistroyBeans);
                clvSearchStroy.setAdapter(seacherHistoryAdapter);
                tvHistoryTop.setVisibility(View.VISIBLE);
                llHistoryLayout.setVisibility(View.GONE);
                break;
            case R.id.site_search_back:

                break;
            case R.id.tv_query:
                key_word = etSearchContent.getText().toString().trim();
                if (key_word.equals(""))
                {
                    Toast.makeText(getActivity(),"搜索内容不能为空!",Toast.LENGTH_SHORT).show();
                }else {
                    startSearch();
                }
                break;

        }
    }
}
