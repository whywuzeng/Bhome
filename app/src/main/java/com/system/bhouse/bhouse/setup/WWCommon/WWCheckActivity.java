package com.system.bhouse.bhouse.setup.WWCommon;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.util.Log;

import com.system.bhouse.utils.blankutils.ToastUtils;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Administrator on 2018-10-25.
 * <p>
 *     检查6.0的权限操作
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.setup.WWCommon
 */
@RuntimePermissions
public class WWCheckActivity extends WWBaseActivity {

    private static final String TAG = "WWCheckActivity";
    
//    @NeedsPermission({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE})
//    public void startCamera(){
//
//    }

    @NeedsPermission({Manifest.permission.READ_PHONE_STATE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE})
    public void checkPhoneState(){
        Log.e(TAG, "checkPhoneState: ");
    }

    public void checkPhoneReadState(){
        WWCheckActivityPermissionsDispatcher.checkPhoneStateWithPermissionCheck(this);
    }

    @SuppressLint("NoCorrespondingNeedsPermission")
    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    void onCheckPhoneRationale(final PermissionRequest request){
        showRationaleDialog(request);
    }

    protected  void showRationaleDialog(final PermissionRequest request){
        new AlertDialog.Builder(this).setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.proceed();
            }
        }).setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();
            }
        }).setCancelable(false)
                .setMessage("权限管理")
                .show();
    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    void onCheckPhoneDenied(){
        ToastUtils.showShort("不允许获取手机状态");
    }

    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    void onCheckPhoneNever(){
        ToastUtils.showShort("永久拒绝获取手机状态");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WWCheckActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }
}
