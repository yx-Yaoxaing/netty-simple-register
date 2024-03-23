package com.cqnews.cloud.netty.register.register.routeinfo;

import com.cqnews.cloud.netty.register.model.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryStore {

    private final ConcurrentHashMap<String, Set<Service>> storeCache;

    public MemoryStore() {
        storeCache = new ConcurrentHashMap<String, Set<Service>>();
    }

    public void set(Service service){
        String serviceName = service.getServiceName();
        // 获取服务列表
        Set<Service> serviceList = storeCache.computeIfAbsent(serviceName, k -> new HashSet<>());
        serviceList.add(service);
    }


}
