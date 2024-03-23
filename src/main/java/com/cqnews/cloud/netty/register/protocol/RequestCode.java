package com.cqnews.cloud.netty.register.protocol;

public class RequestCode {

    /**
     * 客户端进行注册
     */
    public static final int REGISTER_SERVICE = 103;

    /**
     * 客户端通过topic 获取路由信息
     */
    public static final int GET_ROUTEINFO_BY_SERVICE =105;

    /**
     * 心跳检测
     */
    public static final int HEARTBEAT_CHECK = 107;

}
