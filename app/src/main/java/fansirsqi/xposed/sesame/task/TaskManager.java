package fansirsqi.xposed.sesame.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import fansirsqi.xposed.sesame.util.Log;
import lombok.Getter;

/**
 * 任务管理器
 * 统一管理所有任务的启动、停止和状态监控
 */
public class TaskManager {
    private static final String TAG = "TaskManager";
    
    @Getter
    private static final TaskManager instance = new TaskManager();
    
    private final Map<String, ModelTask> taskRegistry = new ConcurrentHashMap<>();
    private final AtomicBoolean isInitialized = new AtomicBoolean(false);
    
    private TaskManager() {}
    
    /**
     * 注册任务
     * @param task 要注册的任务
     */
    public void registerTask(ModelTask task) {
        if (task != null) {
            taskRegistry.put(task.getName(), task);
            Log.runtime(TAG, "注册任务: " + task.getName());
        }
    }
    
    /**
     * 获取任务
     * @param taskName 任务名称
     * @return 任务实例
     */
    public ModelTask getTask(String taskName) {
        return taskRegistry.get(taskName);
    }
    
    /**
     * 启动指定任务
     * @param taskName 任务名称
     * @param force 是否强制启动
     * @return 是否启动成功
     */
    public boolean startTask(String taskName, boolean force) {
        ModelTask task = taskRegistry.get(taskName);
        if (task != null) {
            try {
                task.startTask(force);
                Log.runtime(TAG, "启动任务: " + taskName);
                return true;
            } catch (Exception e) {
                Log.printStackTrace(TAG, e);
            }
        } else {
            Log.runtime(TAG, "任务不存在: " + taskName);
        }
        return false;
    }
    
    /**
     * 停止指定任务
     * @param taskName 任务名称
     */
    public void stopTask(String taskName) {
        ModelTask task = taskRegistry.get(taskName);
        if (task != null) {
            task.stopTask();
            Log.runtime(TAG, "停止任务: " + taskName);
        }
    }
    
    /**
     * 启动所有任务
     * @param force 是否强制启动
     */
    public void startAllTasks(boolean force) {
        Log.runtime(TAG, "开始启动所有任务，强制模式: " + force);
        taskRegistry.values().forEach(task -> {
            try {
                task.startTask(force);
            } catch (Exception e) {
                Log.printStackTrace(TAG, e);
            }
        });
    }
    
    /**
     * 停止所有任务
     */
    public void stopAllTasks() {
        Log.runtime(TAG, "开始停止所有任务");
        taskRegistry.values().forEach(ModelTask::stopTask);
    }
    
    /**
     * 获取任务状态
     * @param taskName 任务名称
     * @return 任务状态信息
     */
    public TaskStatus getTaskStatus(String taskName) {
        ModelTask task = taskRegistry.get(taskName);
        if (task != null) {
            return new TaskStatus(taskName, task.check(), task.countChildTask());
        }
        return new TaskStatus(taskName, false, 0);
    }
    
    /**
     * 获取所有任务状态
     * @return 任务状态映射
     */
    public Map<String, TaskStatus> getAllTaskStatus() {
        Map<String, TaskStatus> statusMap = new ConcurrentHashMap<>();
        taskRegistry.forEach((name, task) -> 
            statusMap.put(name, new TaskStatus(name, task.check(), task.countChildTask())));
        return statusMap;
    }
    
    /**
     * 初始化任务管理器
     */
    public void initialize() {
        if (isInitialized.compareAndSet(false, true)) {
            Log.runtime(TAG, "任务管理器初始化完成");
        }
    }
    
    /**
     * 清理资源
     */
    public void cleanup() {
        stopAllTasks();
        taskRegistry.clear();
        isInitialized.set(false);
        Log.runtime(TAG, "任务管理器清理完成");
    }
} 