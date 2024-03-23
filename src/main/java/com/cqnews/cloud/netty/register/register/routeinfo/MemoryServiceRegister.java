package com.cqnews.cloud.netty.register.register.routeinfo;

import com.cqnews.cloud.netty.register.model.Service;

public class MemoryServiceRegister implements ServiceRegister{

    private MemoryStore memoryStore;

    public MemoryServiceRegister() {
        this.memoryStore = new MemoryStore();
    }

    @Override
    public boolean registerService(Service service) {
        memoryStore.set(service);
        return true;
    }

    @Override
    public boolean removeService(Service service) {
        return false;
    }
}
