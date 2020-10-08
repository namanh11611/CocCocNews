package com.namanh.coccocnews.utils;

import java.util.concurrent.ThreadFactory;

public class PriorityThreadFactory implements ThreadFactory {

    private final int mThreadPriority;

    public PriorityThreadFactory(int threadPriority) {
        mThreadPriority = threadPriority;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Runnable wrapperRunnable = () -> {
            try {
                android.os.Process.setThreadPriority(mThreadPriority);
            } catch (Throwable ignored) {
            }
            runnable.run();
        };
        return new Thread(wrapperRunnable);
    }
}
