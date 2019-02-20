package com.system.bhouse.bhouse.CommonTask.MaterialControl.PlateMaterial;

import com.system.bhouse.bhouse.CommonTask.MaterialControl.PlateMaterial.StationCarBean.MyStation;
import com.system.bhouse.bhouse.CommonTask.MaterialControl.PlateMaterial.StationCarBean.StationCarBean;
import net.qiujuer.italker.common.adapter.BaseSectionQuickAdapter;
import net.qiujuer.italker.common.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.R;

import java.util.List;

/**
 * Created by Administrator on 2018-07-03.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.MaterialControl.PlateMaterial
 */

public class plateMaterialAdapter extends BaseSectionQuickAdapter<MyStation, BaseViewHolder> {

    public plateMaterialAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MyStation item) {

    }

    @Override
    protected void convert(BaseViewHolder helper, MyStation item) {
        StationCarBean StationCarBean = (StationCarBean) item.t;
        helper.setText(R.id.tv_title, "生产订单:"+StationCarBean.getProducationOrderNumber() );
        helper.setText(R.id.tv_content,"构件二维码:"+StationCarBean.getComponentQrcode());
        helper.setText(R.id.tv_title_1,"模具名称:"+StationCarBean.getModuleName());
        helper.setText(R.id.tv_content_1,"台车名称:"+StationCarBean.getStationCarName());
        helper.setChecked(R.id.checkbox,StationCarBean.isSelect());

        helper.addOnClickListener(R.id.main_layout);
    }
}
