package com.marble.lib.async;

import java.util.Comparator;

public class PrioriityRunnableComparator<R extends Runnable> implements Comparator<R> {
    @Override
    public int compare(R o1, R o2) {
        if((o1 instanceof PriorityRunnable) && (o2 instanceof PriorityRunnable)){
            PriorityRunnable e1 = (PriorityRunnable)o1;
            PriorityRunnable e2 = (PriorityRunnable)o2;
            int p1 = e1.runPriority.getValue();
            int p2 = e2.runPriority.getValue();

            return (p1 < p2) ? -1 : ((p1 == p2) ? Long.compare(e1.createTime, e2.createTime) : 1);
        }

        return 0;
    }
}
