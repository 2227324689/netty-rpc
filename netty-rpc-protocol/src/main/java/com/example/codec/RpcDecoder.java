package com.example.codec;

import com.example.constants.ReqType;
import com.example.constants.RpcConstant;
import com.example.core.Header;
import com.example.core.RpcProtocol;
import com.example.core.RpcRequest;
import com.example.core.RpcResponse;
import com.example.serial.ISerializer;
import com.example.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {


    /*
    +----------------------------------------------+
    | 魔数 2byte | 序列化算法 1byte | 请求类型 1byte  |
    +----------------------------------------------+
    | 消息 ID 8byte     |      数据长度 4byte       |
    +----------------------------------------------+
    */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("==========begin RpcDecoder ==============");
        if(in.readableBytes()< RpcConstant.HEAD_TOTAL_LEN){
            //消息长度不够，不需要解析
            return;
        }
        in.markReaderIndex();//标记一个读取数据的索引，后续用来重置。
        short magic=in.readShort(); //读取magic
        if(magic!=RpcConstant.MAGIC){
            throw new IllegalArgumentException("Illegal request parameter 'magic',"+magic);
        }
        byte serialType=in.readByte(); //读取序列化算法类型
        byte reqType=in.readByte(); //请求类型
        long requestId=in.readLong(); //请求消息id
        int dataLength=in.readInt(); //请求数据长度
        //可读区域的字节数小于实际数据长度
        if(in.readableBytes()<dataLength){
            in.resetReaderIndex();
            return;
        }
        //读取消息内容
        byte[] content=new byte[dataLength];
        in.readBytes(content);

        //构建header头信息
        Header header=new Header(magic,serialType,reqType,requestId,dataLength);
        ISerializer serializer=SerializerManager.getSerializer(serialType);
        ReqType rt=ReqType.findByCode(reqType);
        switch(rt){
            case REQUEST:
                RpcRequest request=serializer.deserialize(content, RpcRequest.class);
                RpcProtocol<RpcRequest> reqProtocol=new RpcProtocol<>();
                reqProtocol.setHeader(header);
                reqProtocol.setContent(request);
                out.add(reqProtocol);
                break;
            case RESPONSE:
                RpcResponse response=serializer.deserialize(content,RpcResponse.class);
                RpcProtocol<RpcResponse> resProtocol=new RpcProtocol<>();
                resProtocol.setHeader(header);
                resProtocol.setContent(response);
                out.add(resProtocol);
                break;
            case HEARTBEAT:
                break;
            default:
                break;
        }

    }
}
