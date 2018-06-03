package com.system.bhouse.bhouse.phone.activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerButton;
import com.system.bhouse.Custom.anim.MyJumpingBeans;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.bean.EventBean.EventOrganization;
import com.system.bhouse.bean.UserMidPerm;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.TreeList.adapter.Node;
import com.system.bhouse.bhouse.TreeList.adapter.SimpleTreeAdapter;
import com.system.bhouse.utils.TenUtils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.functions.Func2;


/**
 * Created by Administrator on 2017-8-28.
 */

public class InformationActivity extends AppCompatActivity implements SimpleTreeAdapter.OnCbClick, View.OnClickListener {

    private static final String tag="InformationActivity";

    @Bind(R.id.text_title)
    TextView textTitle;

    protected List<Node> mDatas = new ArrayList<Node>();

    private SimpleTreeAdapter mAdapter;
    private ListView mTree;

    private TextView et_organize;

    private Button bt_switch;

    private String stringname;
    private ShimmerButton shimmer_bt;
    private Shimmer mShimmer;

    private ArrayList<UserMidPerm> userMidPerms;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_layout);
        ButterKnife.bind(this);

        textTitle.setText(R.string.housekeeper);
        mTree = (ListView) findViewById(R.id.lv_tree);
//        initDatas();
        et_organize=(TextView) findViewById(R.id.et_organize);
        bt_switch=(Button)findViewById(R.id.bt_switch);
        shimmer_bt=(ShimmerButton)findViewById(R.id.shimmer_bt);

        initWebservice();
        bt_switch.setOnClickListener(this);
        shimmer_bt.setOnClickListener(this);
        initShimmerAnim();
    }

    private void initShimmerAnim() {
        mShimmer = new Shimmer();

        mShimmer.setRepeatCount(1000)
                .setDuration(5000)
                .setStartDelay(300)
                .setDirection(Shimmer.ANIMATION_DIRECTION_RTL)
                .setAnimatorListener(new Animator.AnimatorListener(){
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

        mShimmer.start(shimmer_bt);


    }

    private void initDataWithView() {

        for (int i = 0; i <mDatas.size(); i++) {
            if (mDatas.get(i).isChecked())
            {
                et_organize.setText(mDatas.get(i).getName());
            }
        }
    }



    private void initWebservice() {

        //查询所有单元
        Observable getmidfulllistdsJson = ApiWebService.Getmidfulllistds_Json(App.USER_INFO);
        //4.查询用户有权限的所有管理单元范围
        Observable getusermidpermJson = ApiWebService.Getusermidperm_Json(App.USER_INFO);

        Observable.zip(getmidfulllistdsJson, getusermidpermJson, new Func2<Object, Object, Object>() {
            @Override
            public Object call(Object o, Object o2) {

                ArrayList<UserMidPerm> shengfens = App.getAppGson().fromJson(o.toString(), new TypeToken<List<UserMidPerm>>() {
                }.getType());


                ArrayList<UserMidPerm> shengfens1 = App.getAppGson().fromJson(o2.toString(), new TypeToken<List<UserMidPerm>>() {
                }.getType());

                if (shengfens.isEmpty()&&shengfens.size()<=0)
                {
                    return null;
                }
                //过滤数据
                for (int i=0;i<shengfens.size();i++)
                {
                    for (int j=0;j<shengfens1.size();j++)
                    {

                        if (shengfens.get(i).getMid()==shengfens1.get(j).getMid())
                        {
                            break;
                        }else if (j==shengfens1.size()-1){
                            shengfens.remove(i);
                        }
                    }

                }

                return shengfens;
            }
        }).subscribe(ApiWebService.getMutiCallback(this, new ApiWebService.ObjectSuccessCall() {
            @Override
            public void SuccessBack(Object result) {

                initDatas((ArrayList<UserMidPerm>)result);
            }

            @Override
            public void ErrorBack(Object error) {
                L.v(tag,"",(Throwable) error);
            }
        }));

    }

    private void initDatas( ArrayList<UserMidPerm> userMidPerms)
    {
        if (userMidPerms.isEmpty()&&userMidPerms.size()<=0)
            return;
        if (this.userMidPerms == null) {
            this.userMidPerms = new ArrayList<>();
        }
        else {
            if (this.userMidPerms.size() > 0 || !(this.userMidPerms.isEmpty())) {
                this.userMidPerms.clear();
            }
        }
        this.userMidPerms.addAll(userMidPerms);

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


        for (int i = 0; i < userMidPerms.size(); i++) {
            if (userMidPerms.get(i).getFatherid()==0)
            {
                mDatas.add(new Node(userMidPerms.get(i).getMid(),-1,userMidPerms.get(i).getManCompany()));
            }else {
                mDatas.add(new Node(userMidPerms.get(i).getMid(),userMidPerms.get(i).getFatherid(),userMidPerms.get(i).getManCompany()));
            }
        }

        mAdapter = new SimpleTreeAdapter(mTree, this,
                mDatas, 1,R.mipmap.tree_ex,R.mipmap.tree_ec);

        mTree.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        mAdapter.setmOnCbClick(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mShimmer.cancel();
    }


    @Override
    public void CbClick(Node node) {
        if (node.isChecked()){
            et_organize.setText(node.getName()+"");

        }
        final TextView textView2 = (TextView) findViewById(R.id.et_organize);
        if (textView2.getText().length()>0&&!TextUtils.isEmpty(textView2.getText())) {
            MyJumpingBeans jumpingBeans2 = MyJumpingBeans.with(textView2)
                    .makeTextJump(0, textView2.getText().toString().length())
                    .setIsWave(true)
                    .setLoopDuration(1000)  // ms
                    .build();
        }

        mDatas.add(node);

        List<Node> allNodes = mAdapter.getAllNodes();

        for (Node node1:allNodes)
        {
            if (!node1.equals(node))
            {
                node1.setChecked(false);
            }

        }

        mAdapter.setChecked(node,node.isChecked());

        mTree.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.shimmer_bt)
        {
            //保存为全局变量
            stringname = et_organize.getText() + "";
            if (!TextUtils.isEmpty(stringname)) {
            for (UserMidPerm userMidPerm : userMidPerms) {
                if (userMidPerm.getManCompany().equals(stringname))
                {
                    App.MID=userMidPerm.getMid();
                    App.Fatherid=userMidPerm.getFatherid();
                    App.Mancompany=userMidPerm.getManCompany();
                    App.Property=userMidPerm.getProperty();
                    App.IsSub=userMidPerm.isIsSub();
                    App.GSMID=userMidPerm.getGsmid();

                }
            }
        }
            EventOrganization eventOrganization = new EventOrganization(et_organize.getText()+"");
            sureDataRefresh(eventOrganization);
            //设置回调吧
            Intent mIntent = new Intent();
            mIntent.putExtra("change",et_organize.getText()+"");
            this.setResult(Activity.RESULT_OK,mIntent);
            this.finish();
        }
    }

    protected void sureDataRefresh(EventOrganization type) {
        EventBus.getDefault().post(type);
    }
}
