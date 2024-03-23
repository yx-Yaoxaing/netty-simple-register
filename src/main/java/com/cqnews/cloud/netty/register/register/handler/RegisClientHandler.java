package com.cqnews.cloud.netty.register.register.handler;

import com.cqnews.cloud.netty.register.protocol.RemotingCommand;
import com.cqnews.cloud.netty.register.protocol.ResponseCode;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RegisClientHandler extends SimpleChannelInboundHandler<RemotingCommand> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RemotingCommand remotingCommand) throws Exception {
        if (remotingCommand.getCode() == ResponseCode.SUCCESS) {
            System.out.println(remotingCommand);
        }
    }
}
