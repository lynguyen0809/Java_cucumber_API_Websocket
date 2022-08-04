package com.eqonex.trading._support;

import java.util.HashMap;
import java.util.Map;

public class GlobalResource {
    private final Map<String, Object> resourcesMap;

    public GlobalResource(){
        resourcesMap = new HashMap<>();
    }

    public void save(Class clzz, Object obj){
        resourcesMap.put(clzz.getName(),obj);
    }

    public  <T> T  load(Class<T> clzz){
        return (T) resourcesMap.get(clzz.getName());
    }

    public void clear(){
        resourcesMap.clear();
    }
}
