package com.system.bhouse.bhouse.Workflowlist.Presenter;

import com.system.bhouse.base.BasePresenterImpl;
import com.system.bhouse.bhouse.Workflowlist.Model.WorkflowlistInteractorImp;
import com.system.bhouse.bhouse.Workflowlist.UIInterface.WorkflowlistView;

/**
 * Created by Administrator on 2017-03-16.
 * ClassName: com.system.bhouse.bhouse.Workflowlist.Presenter
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class WorkflowlistPresenterImp extends BasePresenterImpl<WorkflowlistView,Object> implements WorkflowlistPresenter {

    private  WorkflowlistInteractorImp workflowlistInteractorImp;
    public WorkflowlistPresenterImp(WorkflowlistView view,String status, int rownum, String username, String FormNumber) {
        super(view);
         workflowlistInteractorImp = new WorkflowlistInteractorImp();
        mCompositeSubscription.add(workflowlistInteractorImp.getWorkflowlist(this, status, rownum, username, FormNumber));

    }

    @Override
    public void RequstData(String status, int rownum, String username, String FormNumber) {

    }

    @Override
    public void beforeRequest() {
        super.beforeRequest();
    }

    @Override
    public void requestComplete() {
        super.requestComplete();
    }

    @Override
    public void requestSuccess(Object data) {
        super.requestSuccess(data);
        mView.basetoast(data.toString());
    }

    @Override
    public void requestError(String msg) {
        super.requestError(msg);
    }
}
