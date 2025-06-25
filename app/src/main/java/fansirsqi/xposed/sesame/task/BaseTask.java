package fansirsqi.xposed.sesame.task;

import android.os.Build;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import fansirsqi.xposed.sesame.util.Log;

import lombok.Getter;

/**
 * 基础任务抽象类
 * 提供任务的基本功能和生命周期管理
 */
public abstract class BaseTask {
    private static final String TAG = "BaseTask";

    @Getter
    private volatile Thread thread;
    
    @Getter
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    
    @Getter
    private final AtomicLong startTime = new AtomicLong(0);
    
    @Getter
    private final AtomicLong lastExecuteTime = new AtomicLong(0);
    
    @Getter
    private final AtomicLong executeCount = new AtomicLong(0);
    
    @Getter
    private final AtomicLong errorCount = new AtomicLong(0);

    private final Map<String, BaseTask> childTaskMap = new ConcurrentHashMap<>();

    public BaseTask() {
        this.thread = null;
    }

    /**
     * 获取任务ID
     */
    public String getId() {
        return toString();
    }

    /**
     * 检查任务是否可执行
     */
    public abstract Boolean check();

    /**
     * 执行任务
     */
    public abstract void run();

    /**
     * 任务执行前的准备工作
     */
    protected void beforeRun() {
        // 子类可以重写此方法
    }

    /**
     * 任务执行后的清理工作
     */
    protected void afterRun() {
        // 子类可以重写此方法
    }

    /**
     * 检查是否包含指定的子任务
     */
    public synchronized Boolean hasChildTask(String childId) {
        return childTaskMap.containsKey(childId);
    }

    /**
     * 获取指定的子任务
     */
    public synchronized BaseTask getChildTask(String childId) {
        return childTaskMap.get(childId);
    }

    /**
     * 添加子任务
     */
    public synchronized void addChildTask(BaseTask childTask) {
        String childId = childTask.getId();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            childTaskMap.compute(childId, (key, value) -> {
                if (value != null) {
                    value.stopTask();
                }
                childTask.startTask();
                return childTask;
            });
        } else {
            BaseTask oldTask = childTaskMap.get(childId);
            if (oldTask != null) {
                oldTask.stopTask();
            }
            childTask.startTask();
            childTaskMap.put(childId, childTask);
        }
    }

    /**
     * 移除子任务
     */
    public synchronized void removeChildTask(String childId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            childTaskMap.compute(childId, (key, value) -> {
                if (value != null) {
                    shutdownAndWait(value.getThread(), -1, TimeUnit.SECONDS);
                }
                return null;
            });
        } else {
            BaseTask oldTask = childTaskMap.get(childId);
            if (oldTask != null) {
                shutdownAndWait(oldTask.getThread(), -1, TimeUnit.SECONDS);
            }
            childTaskMap.remove(childId);
        }
    }

    /**
     * 获取子任务数量
     */
    public synchronized Integer countChildTask() {
        return childTaskMap.size();
    }

    /**
     * 启动任务
     */
    public void startTask() {
        startTask(false);
    }

    /**
     * 启动任务
     */
    public synchronized void startTask(Boolean force) {
        if (thread != null && thread.isAlive()) {
            if (!force) {
                Log.runtime(TAG, "任务已在运行中: " + getId());
                return;
            }
            stopTask();
        }
        
        if (!check()) {
            Log.runtime(TAG, "任务检查失败，无法启动: " + getId());
            return;
        }
        
        thread = new Thread(() -> {
            try {
                isRunning.set(true);
                startTime.set(System.currentTimeMillis());
                lastExecuteTime.set(System.currentTimeMillis());
                executeCount.incrementAndGet();
                
                Log.runtime(TAG, "开始执行任务: " + getId());
                beforeRun();
                run();
                afterRun();
                Log.runtime(TAG, "任务执行完成: " + getId());
                
            } catch (Exception e) {
                errorCount.incrementAndGet();
                Log.printStackTrace(TAG, e);
                Log.runtime(TAG, "任务执行异常: " + getId() + ", 错误: " + e.getMessage());
            } finally {
                isRunning.set(false);
                Log.runtime(TAG, "任务结束: " + getId());
            }
        });
        
        thread.setName("BaseTask-" + getId());
        thread.setDaemon(true);
        
        try {
            thread.start();
            Log.runtime(TAG, "任务启动成功: " + getId());
            
            // 启动子任务
            for (BaseTask childTask : childTaskMap.values()) {
                if (childTask != null) {
                    childTask.startTask();
                }
            }
        } catch (Exception e) {
            Log.printStackTrace(TAG, e);
            isRunning.set(false);
        }
    }

    /**
     * 安全关闭线程
     */
    public static void shutdownAndWait(Thread thread, long timeout, TimeUnit unit) {
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
            if (timeout > -1L) {
                try {
                    thread.join(unit.toMillis(timeout));
                } catch (InterruptedException e) {
                    Log.runtime(TAG, "线程等待中断: " + e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * 停止任务
     */
    public synchronized void stopTask() {
        if (thread != null && thread.isAlive()) {
            Log.runtime(TAG, "正在停止任务: " + getId());
            shutdownAndWait(thread, 5, TimeUnit.SECONDS);
        }
        
        // 停止所有子任务
        for (BaseTask childTask : childTaskMap.values()) {
            if (childTask != null) {
                shutdownAndWait(childTask.getThread(), -1, TimeUnit.SECONDS);
            }
        }
        
        thread = null;
        isRunning.set(false);
        childTaskMap.clear();
        Log.runtime(TAG, "任务已停止: " + getId());
    }

    /**
     * 获取任务运行时长（毫秒）
     */
    public long getRunDuration() {
        long start = startTime.get();
        if (start > 0) {
            return System.currentTimeMillis() - start;
        }
        return 0;
    }

    /**
     * 获取任务统计信息
     */
    public TaskStatistics getStatistics() {
        return TaskStatistics.builder()
                .taskId(getId())
                .isRunning(isRunning.get())
                .startTime(startTime.get())
                .lastExecuteTime(lastExecuteTime.get())
                .executeCount(executeCount.get())
                .errorCount(errorCount.get())
                .runDuration(getRunDuration())
                .childTaskCount(countChildTask())
                .build();
    }

    /**
     * 创建空任务实例
     */
    public static BaseTask newInstance() {
        return new BaseTask() {
            @Override
            public void run() {
            }

            @Override
            public Boolean check() {
                return true;
            }
        };
    }

    /**
     * 创建指定ID的空任务实例
     */
    public static BaseTask newInstance(String id) {
        return new BaseTask() {
            @Override
            public String getId() {
                return id;
            }

            @Override
            public void run() {
            }

            @Override
            public Boolean check() {
                return true;
            }
        };
    }

    /**
     * 创建指定ID和运行逻辑的任务实例
     */
    public static BaseTask newInstance(String id, Runnable runnable) {
        return new BaseTask() {
            @Override
            public String getId() {
                return id;
            }

            @Override
            public void run() {
                runnable.run();
            }

            @Override
            public Boolean check() {
                return true;
            }
        };
    }
}
