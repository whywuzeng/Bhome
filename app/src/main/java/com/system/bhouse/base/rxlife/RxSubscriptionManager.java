package com.system.bhouse.base.rxlife;

import java.util.ArrayList;

import rx.Subscription;

/**
 * Created by Administrator on 2018-09-07.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.base.rxlife
 */

public class RxSubscriptionManager {

    private final ArrayList<Subscription> subscriptions=new ArrayList<>();

    public ArrayList<Subscription> getSubscriptions()
    {
        return subscriptions;
    }

    private static final class RxSubscriptionManagerHolder{
        private static final RxSubscriptionManager RX_SUBSCRIPTION_MANAGER=new RxSubscriptionManager();
    }

    public static RxSubscriptionManager getInstanse(){
        return RxSubscriptionManagerHolder.RX_SUBSCRIPTION_MANAGER;
    }

    public void addRxSubscription(ArrayList<Subscription> subscriptions){
        subscriptions.addAll(subscriptions);
    }

    public void addRxSubscription(Subscription subscription)
    {
        subscriptions.add(subscription);
    }

    public void stopAllSubscription(){
        for (Subscription subscription:subscriptions)
        {
            if (!subscription.isUnsubscribed())
            {
                subscription.unsubscribe();
            }
        }
    }
}
