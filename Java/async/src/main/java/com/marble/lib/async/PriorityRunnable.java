package com.marble.lib.async;

public abstract class PriorityRunnable implements Runnable{
    public RunPriority runPriority;
    public long createTime;

    public PriorityRunnable(){
        this(RunPriority.Low);
    }

    public PriorityRunnable(RunPriority runPriority){
        this.runPriority = runPriority;
        createTime = System.nanoTime();
    }
}
