package com.system.bhouse.bhouse.Service.listener;

import java.io.File;

/**
 * Created by Administrator on 2018-10-11.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Service.listener
 */

public interface DownServiceStartListener {
    void onServiceStart();
    void onServiceEnd(File mFile);
    void onServiceError();
}
