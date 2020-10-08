package com.namanh.coccocnews.application;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.namanh.coccocnews.utils.LogUtil;
import com.namanh.coccocnews.utils.PriorityThreadFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AppExecutors {

    private static final String TAG = "AppExecutors";

    private static AppExecutors sInstance;

    private static final int CORE_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 5;
    private static final int KEEP_ALIVE_TIME = 100;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<>();

    private final Executor mMainThread;
    private final ThreadPoolExecutor mNetworkIO;
    private final Executor mDiskIO;

    private AppExecutors() {
        ThreadFactory backgroundPriorityThreadFactory = new PriorityThreadFactory(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        this.mMainThread = new MainThreadExecutor();
        this.mNetworkIO = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, TIME_UNIT, WORK_QUEUE, backgroundPriorityThreadFactory);
        this.mDiskIO = Executors.newSingleThreadExecutor();
    }

    public Executor mainThread() {
        return mMainThread;
    }

    public ThreadPoolExecutor networkIO() {
        return mNetworkIO;
    }

    public Executor diskIO() {
        return mDiskIO;
    }

    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (AppExecutors.class) {
                if (sInstance == null) {
                    LogUtil.i(TAG, "init multi threads");
                    sInstance = new AppExecutors();
                }
            }
        }
        return sInstance;
    }

    public void executeThreadPool(Runnable runnable) {
        mNetworkIO.submit(runnable);
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }

}
