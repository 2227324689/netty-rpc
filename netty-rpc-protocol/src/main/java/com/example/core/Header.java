package com.example.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@AllArgsConstructor
@Data
public class Header implements Serializable {
    /*
    +----------------------------------------------+
    | 魔数 2byte | 序列化算法 1byte | 请求类型 1byte  |
    +----------------------------------------------+
    | 消息 ID 8byte     |      数据长度 4byte       |
    +----------------------------------------------+
    */
    private short magic; //魔数-用来验证报文的身份（2个字节）
    private byte serialType; //序列化类型（1个字节）
    private byte reqType; //操作类型（1个字节）
    private long requestId; //请求id（8个字节）
    private int length; //数据长度（4个字节）

}
