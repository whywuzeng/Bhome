package com.system.bhouse.bhouse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import net.qiujuer.italker.push.MessageReceiver;
import net.qiujuer.italker.push.activities.AccountActivity;

/**
 * Created by Administrator on 2019-02-14.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse
 */
public class BhomeMessageReceiver extends BroadcastReceiver {
    private static final String TAG = MessageReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent1) {

//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("强制下线广播");
//        builder.setMessage("你已被强制下线，请重新登陆。");
//        builder.setCancelable(false);
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                //可以结束所有的 activity 然后 newTask启动
////                ActivityCollector.finishAll();
//            }
//        });
//
//        AlertDialog alterDialog = builder.create();
////        alterDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        alterDialog.show();

        Intent intent = new Intent(context,AccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
