package com.example.loadbalance;

import java.util.List;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public interface ILoadBalance<T> {

    T select(List<T> servers);
}
