package com.marble.lib.communication;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ActionPolymer extends Polymer{

    public static void registerAction(Class scope,String action,Action doAction){
        registerAction(scope.getName(),action,doAction);
    }

    public static void registerAction(String scopeIdentifyName,String action,Action doAction){
        register(scopeIdentifyName,action,doAction);
    }

    public static void unRegisterAction(Class scope,String action){
        unRegisterAction(scope.getName(),action);
    }

    public static void unRegisterAction(String scopeIdentifyName,String action){
        unRegister(scopeIdentifyName, action);
    }

    public static void postAction(Class scope,String action,Object data){
        postAction(scope.getName(),action,data);
    }

    public static void postAction(Class scope,String action){
        postAction(scope.getName(),action,null);
    }

    public static void postActionAllScope(String action,Object data){
        Set<String> keys = ScopePolymer.keySet();
        for(String key: keys){
            postAction(key,action,data);
        }
    }

    public static void postAction(String identifyName,String action,Object data){
        Map<String, List<Object>> polymer = ScopePolymer.get(identifyName);
        if(polymer!=null){
            List<Object> objects = polymer.get(action);
            if(objects==null || objects.size()<1){
                return;
            }
            for(Object o:objects) {
                if (o instanceof Action) {
                    ((Action) o).doAction(action,data);
                }
            }
        }
    }

    public static interface Action{
        void doAction(String action,Object obj);
    }
}
