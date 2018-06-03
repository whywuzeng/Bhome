package com.system.bhouse.bhouse.Workflowlist.Model;

import com.system.bhouse.base.RequestCallBack;

import rx.Subscription;

/**
 * Created by Administrator on 2017-03-16.
 * ClassName: com.system.bhouse.bhouse.Workflowlist.Model
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public interface WorkflowlistInteractor {
    Subscription getWorkflowlist(RequestCallBack<Object> callBack,String status, int rownum, String username, String FormNumber);
}
