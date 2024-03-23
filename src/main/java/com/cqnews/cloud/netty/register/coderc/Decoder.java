package com.cqnews.cloud.netty.register.coderc;

import com.cqnews.cloud.netty.register.protocol.Constant;
import com.cqnews.cloud.netty.register.protocol.RemotingCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.Arrays;
import java.util.List;

/**
 * 解码器
 */
public class Decoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
        // 2字节魔数
        byte[] magicByte = new byte[2];
        byteBuf.readBytes(magicByte);
        checkMagic(magicByte);

        // serializeType 序列化类型
        byte serializeType = byteBuf.readByte();

        // 状态事件
        int code = byteBuf.readInt();

        // 数据长度
        int dataLength = byteBuf.readInt();

        // 读取该长度的数据
        byte[] body = new byte[dataLength];
        byteBuf.readBytes(body);
        RemotingCommand remotingCommand = new RemotingCommand();
        // 构建传输对象
        remotingCommand.setBody(body);
        remotingCommand.setMagic(magicByte);
        remotingCommand.setCode(code);
        remotingCommand.setSerializeType(serializeType);
        remotingCommand.setDataLength(dataLength);
        out.add(remotingCommand);
    }

    private void checkMagic(byte[] transfer){
        if (!Arrays.equals(transfer, Constant.magic)){
            throw new ArithmeticException("magic dont not exist");
        }
    }

}
