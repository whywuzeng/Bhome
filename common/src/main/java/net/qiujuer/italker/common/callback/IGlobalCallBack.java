package net.qiujuer.italker.common.callback;

import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2019-02-15.
 * <p>
 * by author wz
 * <p>
 * net.qiujuer.italker.common.callback
 */
public interface IGlobalCallBack<T> {
    void globalCallBack(@NonNull T args);
}
