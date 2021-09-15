package com.example.constants;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public enum SerialType {

    JSON_SERIAL((byte)0),
    JAVA_SERIAL((byte)1);

    private byte code;

    SerialType(byte code) {
        this.code=code;
    }

    public byte code(){
        return this.code;
    }
}
