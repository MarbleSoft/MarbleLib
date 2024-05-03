package com.marble.lib.async;

public enum RunPriority {
    High(3),Normal(2),Low(1);

    private int value;
    private RunPriority(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
