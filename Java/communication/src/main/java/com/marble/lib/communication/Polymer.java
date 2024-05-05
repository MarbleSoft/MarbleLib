package com.marble.lib.communication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Polymer {
    protected static final Map<String,Map<String, List<Object>>> ScopePolymer =
            new HashMap<>(4);

    public static void createScope(Class scope){
        createScope(scope.getName());
    }

    public static void createScope(String scopeIdentifyName){
        synchronized (ScopePolymer){
            Map<String,List<Object>> polymer = new ConcurrentHashMap<>();
            ScopePolymer.put(scopeIdentifyName,polymer);
        }
    }


    public static void removeScope(Class scope){
        removeScope(scope.getName());
    }

    public static void removeScope(String scopeIdentifyName){
        synchronized (ScopePolymer){
            ScopePolymer.remove(scopeIdentifyName);
        }
    }

    static void register(Class scope,String action,Object ob){
        register(scope.getName(),action,ob);
    }

    static void register(String scopeIdentifyName,String action,Object ob){
        Map<String,List<Object>> polymer = ScopePolymer.get(scopeIdentifyName);
        if(polymer!=null){
            List<Object> objects = polymer.get(action);
            if(objects==null){
                objects = Collections.synchronizedList(new ArrayList<>(5));
                polymer.put(action,objects);
            }
            if(!objects.contains(ob))
                objects.add(ob);
            //else
               // LoggerFactory.getLogger("communication-polymer").warn("already register action"+action);
        }else{
            throw new RuntimeException("the scope didn't create,must first create a scope then register");
        }
    }

    static void unRegister(Class scope,String action){
        unRegister(scope.getName(),action);
    }

    static void unRegister(String scopeIdentifyName,String action){
        Map<String, List<Object>> polymerMap = ScopePolymer.get(scopeIdentifyName);
        if(polymerMap!=null)
            polymerMap.remove(action);
    }
}
