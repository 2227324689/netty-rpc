package com.example.constants;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public enum ReqType {

    REQUEST((byte)1),
    RESPONSE((byte)2),
    HEARTBEAT((byte)3);

    private byte code;

    private ReqType(byte code) {
        this.code=code;
    }

    public byte code(){
        return this.code;
    }
    public static ReqType findByCode(int code) {
        for (ReqType msgType : ReqType.values()) {
            if (msgType.code() == code) {
                return msgType;
            }
        }
        return null;
    }
}
