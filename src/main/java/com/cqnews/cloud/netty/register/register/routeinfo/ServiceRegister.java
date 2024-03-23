package com.cqnews.cloud.netty.register.register.routeinfo;

import com.cqnews.cloud.netty.register.model.Service;

public interface ServiceRegister {

    /**
     * 注册服务
     * @param service 服务
     * @return {@code true register success}
     */
    boolean registerService(Service service);

    /**
     * 删除服务(服务下线)
     * @param service 服务
     * @return
     */
    boolean removeService(Service service);

}
