package com.example.serial;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class SerializerManager {

    private final static ConcurrentHashMap<Byte, ISerializer> serializers=new ConcurrentHashMap<Byte, ISerializer>();

    static {
        ISerializer jsonSerializer=new JsonSerializer();
        ISerializer javaSerializer=new JavaSerializer();
        serializers.put(jsonSerializer.getType(),jsonSerializer);
        serializers.put(javaSerializer.getType(),javaSerializer);
    }

    public static ISerializer getSerializer(byte key){
        ISerializer serializer=serializers.get(key);
        if(serializer==null){
            return new JavaSerializer();
        }
        return serializer;
    }
}
