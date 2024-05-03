package com.marble.lib.async;

import java.util.concurrent.ExecutorService;

public class Async {
    private static final Object lock = new Object();
    private static Async async;
    public static Async getInstance(){
        if(async==null){
            synchronized (lock){
                if(async==null){
                    async = new Async();
                }
            }
        }
        return async;
    }

    private ExecutorService executor;
    private Async(){
        int cores = Runtime.getRuntime().availableProcessors();
        executor = new PriorityThreadPoolExecutor(cores,cores*2);
    }

    public void exec(PriorityRunnable r){
        executor.execute(r);
    }

    public void exec(Runnable r){
        executor.execute(r);
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}
