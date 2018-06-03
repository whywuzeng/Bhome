package com.system.bhouse.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.system.bhouse.adpter.BaseAdapterHelper;
import com.system.bhouse.adpter.QuickAdapter;
import com.system.bhouse.base.App;
import com.system.bhouse.bean.Chanpin;
import com.system.bhouse.bean.Cnname;
import com.system.bhouse.bean.Coname;
import com.system.bhouse.bean.Kehu;
import com.system.bhouse.bean.Shengfen;
import com.system.bhouse.bean.UserManagement;
import com.system.bhouse.bean.WholeConame;
import com.system.bhouse.bean.YunJu;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.inteface.GetPopitemProject;
import com.system.bhouse.utils.inteface.getKVforpopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016-3-18.
 */
public class ItemPopupWindow<T> {

    public PopupWindow popupWindow;
    private QuickAdapter quickAdapter;
    private View layout;

    public void disswindow() {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    public String getEdittx() {
        return edittx;
    }

    public void setEdittx(String edittx) {
        this.edittx = edittx;
    }


    public ItemPopupWindow(){
        initdata();
    }

    private String edittx;

    private Toast mToast;
    private View image_view;

    public View getImage_view() {
        return image_view;
    }

    public void setImage_view(View image_view) {
        this.image_view = image_view;
    }

    private Context context;

    private getKVforpopup getKVforpopup;

    private GetPopitemProject getPopitemProject;

    private EditText eSearch;
    private  ListView viewById;
    private WindowManager.LayoutParams params;

    Handler myhandler = new Handler();

    public TranslateAnimation translateImpl(int from,int to) {

		  TranslateAnimation translateAnimation = new TranslateAnimation(0,
		  0, from, to); translateAnimation.setDuration(2000);
		 return translateAnimation;

    }

    /**
     * 关闭窗口
     */
    private void closePopupWindow()
    {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
            WindowManager.LayoutParams params= ( (Activity) context).getWindow().getAttributes();
            params.alpha=1f;
            ( (Activity) context).getWindow().setAttributes(params);
        }
    }

    public void showWindow(Context context, View view) {
        this.context=context;
        getKVforpopup=(getKVforpopup)context;
        getPopitemProject=(GetPopitemProject)context;

        layout.setFocusable(true);
        layout.setFocusableInTouchMode(true);

        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setContentView(layout);
        //蒙一沉灰色 不好使
        if (Build.VERSION.SDK_INT<23) {
            params = ((Activity) context).getWindow().getAttributes();
            params.alpha = 0.7f;

            ((Activity) context).getWindow().setAttributes(params);
        }

        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.AnimBottom);

        popupWindow.showAtLocation(((Activity) context).findViewById(R.id.content_pager),Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        layout.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closePopupWindow();
                    return true;
                }
                return false;
            }
        });

        EditText edit_listener = (EditText) layout.findViewById(R.id.popup_et);
        edit_listener.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closePopupWindow();
                    return true;
                }
                return false;
            }
        });

    }

    /**
     * 生明一个map来保存id 和 值
     */
    private Map<String,Integer> map_cid=new HashMap<>();

    /**
     * 这里是一个list数组  暂时用于adpter 存储的数组.这个存储多方面数组，现在用于usermanager
     */
    List<T> list_userman=new ArrayList<>();

    /**
     * 还要一个装数据的 判断数组
     */
    List<T> list_usermana_cun=new ArrayList<>();

    /**
     * 生明一个list数组来存对象，然后在传过去
     */
    private Set<T> tList=new HashSet<>();
    /**
     * 这个init  listview那个
     * @param context
     * @param view1
     * @param shengfens
     */

    public void initquickadapter(final Context context, View view1,List<T> shengfens) {
        this.context = context;
        setImage_view(view1);
        //这是主布局
        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.popup_dialog, null);

        //这里不会有重复的 这里有可能会多次
        if (list_usermana_cun.size()>0)
        {
            list_usermana_cun.clear();
        }
        list_usermana_cun.addAll(shengfens);

        if (list_usermana_cun.get(0) instanceof UserManagement)
        {
            for (T user :list_usermana_cun)
            {
                map_cid.put( ((UserManagement) user).Mancompany, ((UserManagement) user).mid);
            }
        }

         viewById = (ListView) layout.findViewById(R.id.popup_lv);
        final EditText viewById1 = (EditText) layout.findViewById(R.id.popup_et);
        setlistenerforbtn();
        quickAdapter = new QuickAdapter<T>(context, R.layout.popup_dialog_lv_item, shengfens) {
            @Override
            protected void convert(BaseAdapterHelper helper, T item) {


                if(item instanceof Shengfen) {
                    helper.setText(R.id.dialog_lv_tv, ((Shengfen) item).getArea());
                    map_cid.put(((Shengfen) item).getArea(), ((Shengfen) item).getAid());
                    tList.add(item);
                }else if (item instanceof Kehu){
                    helper.setText(R.id.dialog_lv_tv, ((Kehu) item).getCname());
                    map_cid.put(((Kehu) item).getCname(), ((Kehu) item).getCid());
                    tList.add(item);
                }else if (item instanceof Chanpin){
                    helper.setText(R.id.dialog_lv_tv, ((Chanpin) item).getPname());
                    map_cid.put(((Chanpin) item).getPname(), ((Chanpin) item).getPid());
                    tList.add(item);
                }else if (item instanceof YunJu){
                    helper.setText(R.id.dialog_lv_tv, ((YunJu) item).getDisnum());
                    map_cid.put(((YunJu) item).getDisnum(), ((YunJu) item).getDid());
                    tList.add(item);
                }else if (item instanceof Coname){
                    helper.setText(R.id.dialog_lv_tv, ((Coname) item).getConame());
                    map_cid.put(((Coname) item).getConame(), ((Coname) item).getCoid());
                    tList.add(item);
                }else if(item instanceof Cnname){
                    helper.setText(R.id.dialog_lv_tv, ((Cnname) item).getCnname());
                    map_cid.put( ((Cnname) item).getCnname(),((Cnname) item).getCnid());
                    tList.add(item);
                }else if (item instanceof WholeConame){
                    helper.setText(R.id.dialog_lv_tv, ((WholeConame) item).getCcNumber());
                    helper.setText(R.id.dialog_lv_tv1, ((WholeConame) item).getCname());
                    helper.getView(R.id.dialog_lv_tv1).setVisibility(View.VISIBLE);
                    helper.setText(R.id.dialog_lv_tv2, ((WholeConame) item).getCphoe());
                    helper.getView(R.id.dialog_lv_tv2).setVisibility(View.VISIBLE);
                    map_cid.put( ((WholeConame) item).getCcNumber(),((WholeConame) item).getCcid());
                    tList.add(item);
                }else if(item instanceof UserManagement){
                    helper.setText(R.id.dialog_lv_tv, ((UserManagement) item).Mancompany);
                    map_cid.put( ((UserManagement) item).Mancompany, ((UserManagement) item).mid);
                    //这里面会有重复的
                }

            }
        };
        viewById.setAdapter(quickAdapter);

        viewById.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView text = (TextView) view.findViewById(R.id.dialog_lv_tv);
                String text1 = text.getText().toString();
                if(list_usermana_cun.get(position) instanceof UserManagement){
                    Integer integer = map_cid.get(text1);
                    App.MID=integer;
                }
                if (map_cid != null) {
                    getKVforpopup.setlistKV(text.getText().toString(), getImage_view(), map_cid);
                }
                if (tList.size() > 0 && tList != null) {
                    List<T> ts = new ArrayList<T>();
                    String s = text.getText().toString();
                    for (Iterator<T> iter = tList.iterator(); iter.hasNext(); ) {
                        ts.add(iter.next());
                    }
                    getPopitemProject.getpopitemproject(ts.get(position), getImage_view());
                }
                closePopupWindow();

            }
        });
        set_eSearch_TextChanged();
    }


    Runnable eChanged = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            String data = eSearch.getText().toString();

       listtemp.clear();//先要清空，不然会叠加

           if (list_usermana_cun.get(0) instanceof  UserManagement) //这是管理单元
           {
               //这个全局变量 装 原来的数据

               list_userman.clear();

               getmDataSub(list_userman, data);//获取更新数据
               //list_userman重新赋值后 更新
               if (list_userman.size()>0&&null!=list_userman) {
                   quickAdapter = new QuickAdapter<T>(context, R.layout.popup_dialog_lv_item, list_userman) {
                       @Override
                       protected void convert(BaseAdapterHelper helper, T item) {
                           if (item instanceof UserManagement) {
                               helper.setText(R.id.dialog_lv_tv, ((UserManagement) item).Mancompany);
                           }
                       }
                   };
               }else
               {
                   return;
               }

               viewById.setAdapter(quickAdapter);

           }else
           {
               return;
           }

        }

    };
    //一个参数是空的
    private void getmDataSub(List<T> list, String data) {
        for(int i=0;i<this.list_usermana_cun.size();i++){
            if (list_usermana_cun.get(i) instanceof UserManagement) {
                if (((UserManagement)this.list_usermana_cun.get(i)).Mancompany.contains(data)){

                    list_userman.add(this.list_usermana_cun.get(i));
                }
            }else
            {
                return;
            }
        }
    }




    private void set_eSearch_TextChanged(){
         eSearch = (EditText) layout.findViewById(R.id.popup_et);
        eSearch.addTextChangedListener(new TextWatcher() {
             @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                //这个应该是在改变的时候会做的动作吧，具体还没用到过。
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
                //这是文本框改变之前会执行的动作
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                /**这是文本框改变之后 会执行的动作
                 * 因为我们要做的就是，在文本框改变的同时，我们的listview的数据也进行相应的变动，并且如一的显示在界面上。
                 * 所以这里我们就需要加上数据的修改的动作了。
                 */
//                if(s.length() == 0){
//                    ivDeleteText.setVisibility(View.GONE);//当文本框为空时，则叉叉消失
//                }
//                else {
//                    ivDeleteText.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉
//                }

                myhandler.post(eChanged);
            }
        });

    }

    private void setlistenerforbtn() {
        Button viewById = (Button) layout.findViewById(R.id.popup_dig_btnfrom);
        final EditText ed_viewById1 = (EditText) layout.findViewById(R.id.popup_et);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = ed_viewById1.getText().toString();
                if (checkLoginInfo(s)) {
                    setEdittx(s);
//                    viewById_tv.setText(s);
                    getKVforpopup.setlistKV(s,null,null);
                    closePopupWindow();
                }
            }
        });
    }


    private boolean checkLoginInfo(String userName) {

        if (TextUtils.isEmpty(userName)) {
            showToast(R.string.user_is_null);
            return false;
        }
        return true;
    }


    private void showToast(int resId) {

        if (mToast == null) {
            mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        }
        mToast.setText(resId);
        mToast.show();
    }

    private List<String> list = new ArrayList<>();
    private List<String> listtemp=new ArrayList<>();

    public void initdata() {
        for (int i = 0; i < 20; i++) {
            list.add("--信息：--" + i);
        }

        listtemp.addAll(list);
    }

}
