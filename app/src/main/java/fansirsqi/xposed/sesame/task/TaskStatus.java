package fansirsqi.xposed.sesame.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 任务状态信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatus {
    /**
     * 任务名称
     */
    private String taskName;
    
    /**
     * 是否可执行
     */
    private boolean executable;
    
    /**
     * 子任务数量
     */
    private int childTaskCount;
    
    /**
     * 任务运行状态
     */
    @Builder.Default
    private TaskState state = TaskState.UNKNOWN;
    
    /**
     * 最后执行时间
     */
    private long lastExecuteTime;
    
    /**
     * 执行次数
     */
    @Builder.Default
    private long executeCount = 0;
    
    /**
     * 错误次数
     */
    @Builder.Default
    private long errorCount = 0;
    
    /**
     * 任务状态枚举
     */
    public enum TaskState {
        UNKNOWN("未知"),
        IDLE("空闲"),
        RUNNING("运行中"),
        STOPPED("已停止"),
        ERROR("错误"),
        DISABLED("已禁用");
        
        private final String description;
        
        TaskState(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * 创建默认状态
     */
    public static TaskStatus createDefault(String taskName) {
        return TaskStatus.builder()
                .taskName(taskName)
                .executable(false)
                .childTaskCount(0)
                .state(TaskState.UNKNOWN)
                .build();
    }
    
    /**
     * 更新执行信息
     */
    public void updateExecution() {
        this.lastExecuteTime = System.currentTimeMillis();
        this.executeCount++;
    }
    
    /**
     * 更新错误信息
     */
    public void updateError() {
        this.errorCount++;
        this.state = TaskState.ERROR;
    }
    
    /**
     * 重置状态
     */
    public void reset() {
        this.state = TaskState.IDLE;
        this.executeCount = 0;
        this.errorCount = 0;
    }
}
