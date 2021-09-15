package com.example;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public enum RegistryType {

    ZOOKEEPER((byte)0),
    EUREKA((byte)1);

    private byte code;

    RegistryType(byte code) {
        this.code=code;
    }

    public byte code(){
        return this.code;
    }

    public static RegistryType findByCode(byte code) {
        for (RegistryType rt : RegistryType.values()) {
            if (rt.code() == code) {
                return rt;
            }
        }
        return null;
    }
}
