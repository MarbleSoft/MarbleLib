package com.marble.lib.async;

import java.util.Comparator;

public class PrioriityRunnableComparator<R extends Runnable> implements Comparator<R> {
    @Override
    public int compare(R o1, R o2) {
        int p1 = 0;
        Long createTime1 = Long.MAX_VALUE;
        int p2 = 0;
        Long createTime2 = Long.MAX_VALUE;
        if(o1 instanceof PriorityRunnable){
            PriorityRunnable e1 = (PriorityRunnable)o1;
            p1 = e1.runPriority.getValue();
            createTime1 = e1.createTime;
        }
        if(o2 instanceof PriorityRunnable){
            PriorityRunnable e2 = (PriorityRunnable)o2;
            p2 = e2.runPriority.getValue();
            createTime1 = e2.createTime;
        }
        int result = p1 -p2;
        if(result==0){
            //创建时间越大优先级越低
            return Long.compare(createTime2, createTime1);
        }
        return result;
    }
}
