package com.system.bhouse.bhouse.CommonTask.TechnologyExecution;

import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity.RelatedDetailBean;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity.TechnologyBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018-07-18.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution
 */

public interface ItemTouchListener {
    void sendRelatedDetail(String componentQr, String orderId, TechnologyBean bean);
}
