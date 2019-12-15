package com.retrofit.demo.help.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by XiaoJianjun on 2017/5/11.
 * 将EventBus封装一层.
 */
public class EventBusHelp {

    public static void register(Object subscriber) {
        if (!isRegister(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    public static void unregister(Object subscriber) {
        if(isRegister(subscriber)){
            EventBus.getDefault().unregister(subscriber);
        }
    }

    public static void post(Event event) {
        EventBus.getDefault().post(event);
    }

    public static void postSticky(Event event) {
        EventBus.getDefault().postSticky(event);
    }

    private static boolean isRegister(Object subscriber) {
        return EventBus.getDefault().isRegistered(subscriber);
    }

}
