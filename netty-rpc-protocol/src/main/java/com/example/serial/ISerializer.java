package com.example.serial;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public interface ISerializer {

    <T> byte[] serialize(T obj);

    <T> T deserialize(byte[] data,Class<T> clazz);

    byte getType();
}
