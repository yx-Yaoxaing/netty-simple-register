package com.cqnews.cloud.netty.register.model;

public class Service {

    private String ip;

    private String serviceName;

    public Service(String ip, String serviceName) {
        this.ip = ip;
        this.serviceName = serviceName;
    }

    public Service() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
