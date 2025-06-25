package fansirsqi.xposed.sesame.task;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import fansirsqi.xposed.sesame.util.GlobalThreadPools;
import fansirsqi.xposed.sesame.util.Log;
import lombok.Getter;

/**
 * 任务执行器
 * 提供优化的线程管理和任务执行功能
 */
public class TaskExecutor {
    private static final String TAG = "TaskExecutor";
    
    @Getter
    private final ExecutorService executor;
    private final AtomicInteger threadCounter = new AtomicInteger(0);
    
    public TaskExecutor() {
        this.executor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("TaskExecutor-" + threadCounter.incrementAndGet());
                thread.setDaemon(true);
                return thread;
            }
        });
    }
    
    public TaskExecutor(int corePoolSize, int maximumPoolSize) {
        this.executor = Executors.newThreadPoolExecutor(
            corePoolSize, 
            maximumPoolSize, 
            60L, 
            TimeUnit.SECONDS,
            GlobalThreadPools.getWorkQueue(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("TaskExecutor-" + threadCounter.incrementAndGet());
                    thread.setDaemon(true);
                    return thread;
                }
            }
        );
    }
    
    /**
     * 异步执行任务
     * @param task 任务
     * @return Future对象
     */
    public Future<?> submit(Runnable task) {
        try {
            return executor.submit(() -> {
                try {
                    task.run();
                } catch (Exception e) {
                    Log.printStackTrace(TAG, e);
                }
            });
        } catch (Exception e) {
            Log.printStackTrace(TAG, e);
            return CompletableFuture.failedFuture(e);
        }
    }
    
    /**
     * 异步执行任务并返回结果
     * @param task 任务
     * @return CompletableFuture对象
     */
    public <T> CompletableFuture<T> submitAsync(java.util.concurrent.Callable<T> task) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return task.call();
            } catch (Exception e) {
                Log.printStackTrace(TAG, e);
                throw new RuntimeException(e);
            }
        }, executor);
    }
    
    /**
     * 延迟执行任务
     * @param task 任务
     * @param delay 延迟时间
     * @param unit 时间单位
     * @return Future对象
     */
    public Future<?> schedule(Runnable task, long delay, TimeUnit unit) {
        return submit(() -> {
            try {
                Thread.sleep(unit.toMillis(delay));
                task.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Log.runtime(TAG, "任务被中断: " + e.getMessage());
            } catch (Exception e) {
                Log.printStackTrace(TAG, e);
            }
        });
    }
    
    /**
     * 周期性执行任务
     * @param task 任务
     * @param initialDelay 初始延迟
     * @param period 执行周期
     * @param unit 时间单位
     * @return 可取消的任务
     */
    public CancellableTask scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        CancellableTask cancellableTask = new CancellableTask();
        
        submit(() -> {
            try {
                Thread.sleep(unit.toMillis(initialDelay));
                while (!cancellableTask.isCancelled()) {
                    task.run();
                    Thread.sleep(unit.toMillis(period));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Log.runtime(TAG, "周期性任务被中断");
            } catch (Exception e) {
                Log.printStackTrace(TAG, e);
            }
        });
        
        return cancellableTask;
    }
    
    /**
     * 关闭执行器
     */
    public void shutdown() {
        try {
            executor.shutdown();
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 强制关闭执行器
     */
    public void shutdownNow() {
        executor.shutdownNow();
    }
    
    /**
     * 可取消的任务包装器
     */
    public static class CancellableTask {
        private volatile boolean cancelled = false;
        
        public void cancel() {
            cancelled = true;
        }
        
        public boolean isCancelled() {
            return cancelled;
        }
    }
} 