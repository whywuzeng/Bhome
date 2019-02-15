package net.qiujuer.italker.common.callback;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.WeakHashMap;

/**
 * Created by Administrator on 2019-02-15.
 * <p>
 * by author wz
 * <p>
 * net.qiujuer.italker.common.callback
 */
public class CallBackManager {

    private CallBackManager(){}

    private final WeakHashMap<GlobalType,IGlobalCallBack> callBackWeakHashMap =new WeakHashMap<>();

    private final static class CallBackManagerHolder {
        private final static CallBackManager holder= new CallBackManager();
    }

    public static CallBackManager getIntanse(){
        return CallBackManagerHolder.holder;
    }

    public void setCallBackType(GlobalType type,@NonNull IGlobalCallBack callBack)
    {
        callBackWeakHashMap.put(type, callBack);
    }

    public @Nullable IGlobalCallBack getIGlobalCallBack(GlobalType type){
        return callBackWeakHashMap.get(type);
    }
}
