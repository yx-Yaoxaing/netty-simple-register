package com.cqnews.cloud.netty.register.coderc;

import com.cqnews.cloud.netty.register.protocol.RemotingCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 */
public class Encoder extends MessageToByteEncoder<RemotingCommand> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RemotingCommand remotingCommand, ByteBuf byteBuf) throws Exception {
        byte[] magic = remotingCommand.getMagic();
        byte[] body = remotingCommand.getBody();
        int code = remotingCommand.getCode();
        byte serializeType = remotingCommand.getSerializeType();
        int dataLength = remotingCommand.getDataLength();
        // 2字节魔数
        byteBuf.writeBytes(magic);
        // serializeType 序列化类型
        byteBuf.writeByte(serializeType);
        // 四字节状态事件
        byteBuf.writeInt(code);
        // 四字节数据长度
        byteBuf.writeInt(dataLength);
        // 数据
        byteBuf.writeBytes(body);

    }
}
