package fansirsqi.xposed.sesame.util;

import java.util.function.Supplier;

/**
 * 统一异常处理工具类
 */
public class ExceptionHandler {
    
    /**
     * 安全执行方法，捕获异常并记录日志
     * 
     * @param supplier 要执行的方法
     * @param defaultValue 默认返回值
     * @param tag 日志标签
     * @param operation 操作描述
     * @return 执行结果或默认值
     */
    public static <T> T safeExecute(Supplier<T> supplier, T defaultValue, String tag, String operation) {
        try {
            return supplier.get();
        } catch (Exception e) {
            Log.error(tag, operation + " 执行失败: " + e.getMessage());
            Log.printStackTrace(tag, e);
            return defaultValue;
        }
    }
    
    /**
     * 安全执行无返回值的方法
     * 
     * @param runnable 要执行的方法
     * @param tag 日志标签
     * @param operation 操作描述
     */
    public static void safeExecute(Runnable runnable, String tag, String operation) {
        try {
            runnable.run();
        } catch (Exception e) {
            Log.error(tag, operation + " 执行失败: " + e.getMessage());
            Log.printStackTrace(tag, e);
        }
    }
    
    /**
     * 安全执行JSON解析
     * 
     * @param jsonString JSON字符串
     * @param tag 日志标签
     * @param operation 操作描述
     * @return JSONObject或null
     */
    public static org.json.JSONObject safeParseJson(String jsonString, String tag, String operation) {
        return safeExecute(() -> new org.json.JSONObject(jsonString), null, tag, operation);
    }
    
    /**
     * 安全执行JSON数组解析
     * 
     * @param jsonString JSON字符串
     * @param tag 日志标签
     * @param operation 操作描述
     * @return JSONArray或null
     */
    public static org.json.JSONArray safeParseJsonArray(String jsonString, String tag, String operation) {
        return safeExecute(() -> new org.json.JSONArray(jsonString), null, tag, operation);
    }
    
    /**
     * 安全获取JSON字段值
     * 
     * @param jsonObject JSON对象
     * @param fieldName 字段名
     * @param defaultValue 默认值
     * @return 字段值或默认值
     */
    public static String safeGetString(org.json.JSONObject jsonObject, String fieldName, String defaultValue) {
        return safeExecute(() -> jsonObject.optString(fieldName, defaultValue), defaultValue, "JSON", "获取字段 " + fieldName);
    }
    
    /**
     * 安全获取JSON字段整数值
     * 
     * @param jsonObject JSON对象
     * @param fieldName 字段名
     * @param defaultValue 默认值
     * @return 字段值或默认值
     */
    public static int safeGetInt(org.json.JSONObject jsonObject, String fieldName, int defaultValue) {
        return safeExecute(() -> jsonObject.optInt(fieldName, defaultValue), defaultValue, "JSON", "获取字段 " + fieldName);
    }
    
    /**
     * 安全获取JSON字段长整数值
     * 
     * @param jsonObject JSON对象
     * @param fieldName 字段名
     * @param defaultValue 默认值
     * @return 字段值或默认值
     */
    public static long safeGetLong(org.json.JSONObject jsonObject, String fieldName, long defaultValue) {
        return safeExecute(() -> jsonObject.optLong(fieldName, defaultValue), defaultValue, "JSON", "获取字段 " + fieldName);
    }
    
    /**
     * 安全获取JSON字段布尔值
     * 
     * @param jsonObject JSON对象
     * @param fieldName 字段名
     * @param defaultValue 默认值
     * @return 字段值或默认值
     */
    public static boolean safeGetBoolean(org.json.JSONObject jsonObject, String fieldName, boolean defaultValue) {
        return safeExecute(() -> jsonObject.optBoolean(fieldName, defaultValue), defaultValue, "JSON", "获取字段 " + fieldName);
    }
} 