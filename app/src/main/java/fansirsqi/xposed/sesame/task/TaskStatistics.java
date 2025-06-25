package fansirsqi.xposed.sesame.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 任务统计信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatistics {
    /**
     * 任务ID
     */
    private String taskId;
    
    /**
     * 是否正在运行
     */
    private boolean isRunning;
    
    /**
     * 开始时间
     */
    private long startTime;
    
    /**
     * 最后执行时间
     */
    private long lastExecuteTime;
    
    /**
     * 执行次数
     */
    private long executeCount;
    
    /**
     * 错误次数
     */
    private long errorCount;
    
    /**
     * 运行时长（毫秒）
     */
    private long runDuration;
    
    /**
     * 子任务数量
     */
    private int childTaskCount;
    
    /**
     * 获取成功率
     */
    public double getSuccessRate() {
        if (executeCount == 0) {
            return 0.0;
        }
        return (double) (executeCount - errorCount) / executeCount * 100;
    }
    
    /**
     * 获取平均执行时间
     */
    public long getAverageExecutionTime() {
        if (executeCount == 0) {
            return 0;
        }
        return runDuration / executeCount;
    }
    
    /**
     * 获取格式化后的运行时长
     */
    public String getFormattedRunDuration() {
        long duration = runDuration;
        long hours = duration / (1000 * 60 * 60);
        long minutes = (duration % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (duration % (1000 * 60)) / 1000;
        
        if (hours > 0) {
            return String.format("%d小时%d分钟%d秒", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%d分钟%d秒", minutes, seconds);
        } else {
            return String.format("%d秒", seconds);
        }
    }
    
    /**
     * 获取格式化后的统计信息
     */
    public String getFormattedStatistics() {
        return String.format(
            "任务: %s\n" +
            "状态: %s\n" +
            "执行次数: %d\n" +
            "错误次数: %d\n" +
            "成功率: %.2f%%\n" +
            "运行时长: %s\n" +
            "子任务数: %d",
            taskId,
            isRunning ? "运行中" : "已停止",
            executeCount,
            errorCount,
            getSuccessRate(),
            getFormattedRunDuration(),
            childTaskCount
        );
    }
} 