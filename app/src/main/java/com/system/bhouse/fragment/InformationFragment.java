package com.system.bhouse.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.bhouse.bhouse.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/7.
 */
public class InformationFragment extends Fragment {
//    @Bind(R.id.text_title)
//    TextView textTitle;

//    protected List<Node> mDatas = new ArrayList<Node>();
//
//    private TreeListViewAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.informationfragment, null);
//        ButterKnife.bind(this, inflate);
//
//        textTitle.setText(R.string.housekeeper);
//        ListView mTree = (ListView) inflate.findViewById(R.id.lv_tree);
        initDatas();

        View root = inflater.inflate(R.layout.fragment_emptyview_weinew, container, false);
        ButterKnife.bind(this, root);

//        mAdapter = new SimpleTreeAdapter(mTree, this.getActivity(),
//                mDatas, 1,R.mipmap.tree_ex,R.mipmap.tree_ec);
//        mTree.setAdapter(mAdapter);

        initTopBar();
        return root;
    }

    private void initTopBar() {
//        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                popBackStack();
//            }
//        });

//        mEmptyView.show("", "敬请期待。");

        // 切换其他情况的按钮
//        mTopBar.addRightImageButton(R.mipmap.icon_topbar_overflow, R.id.topbar_right_change_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                showBottomSheetList();
//            }
//        });

//        mTopBar.setTitle(R.string.housekeeper);
    }


    private void initDatas()
    {
        // id , pid , label , 其他属性
//        mDatas.add(new Node("1", "-1", "文件管理系统"));
//
//        mDatas.add(new Node(2+"", 1+"", "游戏"));
//        mDatas.add(new Node(3+"", 1+"", "文档"));
//        mDatas.add(new Node(4+"", 1+"", "程序"));
//        mDatas.add(new Node(5+"", 2+"", "war3"));
//        mDatas.add(new Node(6+"", 2+"", "刀塔传奇"));
//
//        mDatas.add(new Node(7+"", 4+"", "面向对象"));
//        mDatas.add(new Node(8+"", 4+"", "非面向对象"));
//
//        mDatas.add(new Node(9+"", 7+"", "C++"));
//        mDatas.add(new Node(10+"", 7+"", "JAVA"));
//        mDatas.add(new Node(11+"", 7+"", "Javascript"));
//        mDatas.add(new Node(12+"", 8+"", "C"));
//        mDatas.add(new Node(13+"", 12+"", "C"));
//        mDatas.add(new Node(14+"", 13+"", "C"));
//        mDatas.add(new Node(15+"", 14+"", "C"));
//        mDatas.add(new Node(16+"", 15+"", "C"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
