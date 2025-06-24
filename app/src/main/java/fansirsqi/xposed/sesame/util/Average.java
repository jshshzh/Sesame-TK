package fansirsqi.xposed.sesame.util;

import java.util.LinkedList;
import java.util.Queue;

/**平均值计算工具类*/
public class Average {
    /** 使用队列来存储固定数量的数值*/
    private final Queue<Integer> queue;
    /** 队列的最大容量*/
    private final int maxSize;
    /** 数值的总和，用于计算平均值*/
    private double sum;
    /** 当前的平均值*/
    private double average;

    /** 构造函数，初始化队列大小，初始总和和平均值*/
    public Average(int size) {
        this.queue = new LinkedList<>();
        this.maxSize = size;
        this.sum = 0.0;
        this.average = 0.0;
    }

    /**
     * 计算下一个数值加入后的新平均值
     *
     * @param value 新加入的数值
     * @return 当前的平均值
     */
    public double nextDouble(int value) {
        // 如果队列已满，移除最旧的元素
        if (queue.size() >= maxSize) {
            Integer removed = queue.poll();
            if (removed != null) {
                sum -= removed;
            }
        }
        
        // 添加新值
        queue.offer(value);
        sum += value;
        
        // 计算并返回新的平均值
        return average = sum / queue.size();
    }

    /**
     * 计算下一个数值加入后的新平均值（返回整数）
     *
     * @param value 新加入的数值
     * @return 当前的平均值（整数）
     */
    public int nextInteger(int value) {
        return (int) nextDouble(value);
    }

    /**
     * 获取当前的平均值（浮动型）
     *
     * @return 当前的平均值
     */
    public double averageDouble() {
        return average;
    }

    /**
     * 获取当前的平均值（整数型）
     *
     * @return 当前的平均值（整数）
     */
    public int getAverageInteger() {
        return (int) average;
    }

    /**
     * 清除队列和重置所有统计数据
     */
    public void clear() {
        queue.clear();
        sum = 0.0;
        average = 0.0;
    }
}
