package com.cqnews.cloud.netty.register.register;

import com.cqnews.cloud.netty.register.coderc.Decoder;
import com.cqnews.cloud.netty.register.coderc.Encoder;
import com.cqnews.cloud.netty.register.register.handler.RegistryServerHandler;
import com.cqnews.cloud.netty.register.register.routeinfo.MemoryServiceRegister;
import com.cqnews.cloud.netty.register.register.routeinfo.ServiceRegister;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class RegistryServer {
    static final int PORT = Integer.parseInt(System.getProperty("port","8007"));

    public static void main(String[] args) throws Exception {
        main0(args);
        startRegisterServer();
    }

    private static void startRegisterServer() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServiceRegister serviceRegister =  new MemoryServiceRegister();
        RegistryServerHandler registryServerHandler = new RegistryServerHandler(serviceRegister);


        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new Encoder()); // 添加编码器
                            p.addLast(new Decoder()); // 添加解码器
                            p.addLast(registryServerHandler);
                        }
                    });
            // start server
            ChannelFuture f = b.bind(PORT).sync();
            System.out.println("Server started on port " + PORT);
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private static void main0(String[] args) {

    }

}
