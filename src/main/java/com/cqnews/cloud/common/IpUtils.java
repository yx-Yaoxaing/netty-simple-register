package com.cqnews.cloud.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtils {
    public static String getIp(){
        InetAddress localhost = null;
        try {
            localhost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        // 获取本机的 IP 地址
        String ipAddress = localhost.getHostAddress();
        return ipAddress;
    }
}
