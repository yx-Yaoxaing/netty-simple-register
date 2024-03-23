package com.cqnews.cloud.netty.register.register.handler;

import com.cqnews.cloud.netty.register.model.Service;
import com.cqnews.cloud.netty.register.protocol.RemotingCommand;
import com.cqnews.cloud.netty.register.protocol.RequestCode;
import com.cqnews.cloud.netty.register.protocol.ResponseCode;
import com.cqnews.cloud.netty.register.register.routeinfo.ServiceRegister;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RegistryServerHandler extends SimpleChannelInboundHandler<RemotingCommand> {

    private final ServiceRegister serviceRegister;

    public RegistryServerHandler(ServiceRegister serviceRegister){
        this.serviceRegister = serviceRegister;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext cx, RemotingCommand remotingCommand) throws Exception {

        // 进行服务注册
        if (remotingCommand.getCode() == RequestCode.REGISTER_SERVICE) {
            Service service = remotingCommand.decode(Service.class);
            serviceRegister.registerService(service);
        }
        // 进行心跳检测
        if (remotingCommand.getCode() == RequestCode.HEARTBEAT_CHECK) {

        }
        // 根据服务名称拉取最新的路由信息
        if (remotingCommand.getCode() == RequestCode.GET_ROUTEINFO_BY_SERVICE) {

        }
        RemotingCommand response = RemotingCommand.createResponse(ResponseCode.SUCCESS);
        cx.writeAndFlush(response);
    }
}
