package com.cqnews.cloud.netty.register.register;

import com.cqnews.cloud.common.IpUtils;
import com.cqnews.cloud.netty.register.coderc.Decoder;
import com.cqnews.cloud.netty.register.coderc.Encoder;
import com.cqnews.cloud.netty.register.model.Service;
import com.cqnews.cloud.netty.register.protocol.RemotingCommand;
import com.cqnews.cloud.netty.register.protocol.RequestCode;
import com.cqnews.cloud.netty.register.protocol.ResponseCode;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Promise;

public class RegisterClient {
    static final String HOST = System.getProperty("host","127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port","8007"));
    static final int Size = Integer.parseInt(System.getProperty("size","256"));


    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new Encoder()); // 添加编码器
                            p.addLast(new Decoder()); // 添加解码器
                           // p.addLast(new RegisClientHandler()); // 添加客户端处理器
                        }
                    });

            // 启动客户端连接
            ChannelFuture f = b.connect(HOST, PORT).sync();
            Service service = new Service( IpUtils.getIp(),"user-login-service");
            RemotingCommand remotingCommand = RemotingCommand.createRequest(RequestCode.REGISTER_SERVICE, service);
            //ChannelFuture channelFuture = f.channel().writeAndFlush(remotingCommand).sync();
            RemotingCommand response = sendRequestAndWaitForResponse(f.channel(), remotingCommand);
            System.out.println("同步返回的结果"+response);
            if (response.getCode() == ResponseCode.SUCCESS) {
                System.out.println("服务注册成功");
            }
            // 等待连接关闭
            f.channel().closeFuture().sync();
        } finally {

        }
    }


    public static RemotingCommand sendRequestAndWaitForResponse(Channel channel, RemotingCommand request) throws InterruptedException {
        // 创建一个Promise来等待服务器响应
        Promise<RemotingCommand> responsePromise = channel.eventLoop().newPromise();

        // 发送请求
        channel.writeAndFlush(request).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                // 请求发送成功
            } else {
                // 请求发送失败
                Throwable cause = future.cause();
                responsePromise.setFailure(cause);
            }
        });

        // 在客户端Handler中处理服务器的响应
        channel.pipeline().addLast("responseHandler", new ClientHandler(responsePromise));

        // 等待服务器响应
        return responsePromise.sync().getNow();
    }

    // 客户端Handler
    private static class ClientHandler extends SimpleChannelInboundHandler<RemotingCommand> {
        private final Promise<RemotingCommand> responsePromise;

        public ClientHandler(Promise<RemotingCommand> responsePromise) {
            this.responsePromise = responsePromise;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, RemotingCommand msg) throws Exception {
            // 将服务器的响应设置到Promise中
            responsePromise.setSuccess(msg);
            ctx.pipeline().remove(this); // 处理完响应后移除Handler
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            // 出现异常时设置Promise的失败状态
            responsePromise.setFailure(cause);
            cause.printStackTrace();
            ctx.close();
        }
    }
}
