package com.cqnews.cloud.netty.register.protocol;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.cqnews.cloud.netty.register.model.Service;

import java.util.Arrays;

public class RemotingCommand {

    /**
     * 2字节的魔数
     */
    private byte[] magic;

    /**
     * 数据序列化类型 目前只有json
     */
    private byte serializeType;

    /**
     * 代表状态
     */
    private int code;

    /**
     * 数据内容的长度
     */
    private int dataLength;

    /**
     * 数据内容
     */
    private transient byte[] body;

    public byte[] getMagic() {
        return magic;
    }

    public void setMagic(byte[] magic) {
        this.magic = magic;
    }

    public byte getSerializeType() {
        return serializeType;
    }

    public void setSerializeType(byte serializeType) {
        this.serializeType = serializeType;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }


    public <T> T decode(Class<T> classaz){
        SerializeType serializeHandler = SerializeType.valueOf(serializeType);
        switch (serializeHandler) {
            case JSON -> {
                Object parse = JSON.parse(body);
               return JSONObject.parseObject(JSON.toJSONString(parse),classaz);
            }
        }
        return null;
    }

    public static RemotingCommand createResponse(int code){
        RemotingCommand remotingCommand = new RemotingCommand();
        // 写入魔数
        remotingCommand.setMagic(Constant.magic);
        remotingCommand.setSerializeType((byte) 0);
        remotingCommand.setCode(code);
        remotingCommand.setDataLength(1);
        remotingCommand.setBody(new byte[1]);
        return  remotingCommand;
    }


    public static RemotingCommand createRequest(int code, Service service){
        RemotingCommand remotingCommand = new RemotingCommand();
        // 写入魔数
        remotingCommand.setMagic(Constant.magic);
        remotingCommand.setSerializeType((byte) 0);
        remotingCommand.setCode(code);
        byte[] data = JSON.toJSONBytes(service);
        remotingCommand.setDataLength(data.length);
        remotingCommand.setBody(data);
        return  remotingCommand;
    }

    @Override
    public String toString() {
        return "RemotingCommand{" +
                "magic=" + Arrays.toString(magic) +
                ", serializeType=" + serializeType +
                ", code=" + code +
                ", dataLength=" + dataLength +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
