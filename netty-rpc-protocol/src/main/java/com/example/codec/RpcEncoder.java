package com.example.codec;

import com.example.core.Header;
import com.example.core.RpcProtocol;
import com.example.serial.ISerializer;
import com.example.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol<Object>> {

    /*
    +----------------------------------------------+
    | 魔数 2byte | 序列化算法 1byte | 请求类型 1byte  |
    +----------------------------------------------+
    | 消息 ID 8byte     |      数据长度 4byte       |
    +----------------------------------------------+
    */
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol<Object> msg, ByteBuf out) throws Exception {
        log.info("=============begin RpcEncoder============");
        Header header=msg.getHeader();
        out.writeShort(header.getMagic()); //写入魔数
        out.writeByte(header.getSerialType()); //写入序列化类型
        out.writeByte(header.getReqType());//写入请求类型
        out.writeLong(header.getRequestId()); //写入请求id
        ISerializer serializer= SerializerManager.getSerializer(header.getSerialType());
        byte[] data=serializer.serialize(msg.getContent()); //序列化
        header.setLength(data.length);
        out.writeInt(data.length); //写入消息长度
        out.writeBytes(data);
    }
}
