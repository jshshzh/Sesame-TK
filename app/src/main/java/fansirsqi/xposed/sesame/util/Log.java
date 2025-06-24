package fansirsqi.xposed.sesame.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fansirsqi.xposed.sesame.BuildConfig;
import fansirsqi.xposed.sesame.model.BaseModel;

/**
 * 日志工具类，负责初始化和管理各种类型的日志记录器，并提供日志输出方法。
 */
public class Log {
    private static final String TAG = "";
    
    /**
     * 日志类型枚举
     */
    public enum LogType {
        SYSTEM(LoggerFactory.getLogger("system")),
        RUNTIME(LoggerFactory.getLogger("runtime")),
        RECORD(LoggerFactory.getLogger("record")),
        DEBUG(LoggerFactory.getLogger("debug")),
        FOREST(LoggerFactory.getLogger("forest")),
        FARM(LoggerFactory.getLogger("farm")),
        OTHER(LoggerFactory.getLogger("other")),
        ERROR(LoggerFactory.getLogger("error")),
        CAPTURE(LoggerFactory.getLogger("capture"));
        
        private final Logger logger;
        
        LogType(Logger logger) {
            this.logger = logger;
        }
        
        public Logger getLogger() {
            return logger;
        }
    }
    
    static {
        Logback.configureLogbackDirectly();
    }

    /**
     * 通用日志记录方法
     */
    private static void log(LogType logType, String message, boolean isError) {
        if (logType == LogType.RUNTIME || logType == LogType.RECORD) {
            LogType.SYSTEM.getLogger().info(TAG + "{}", message);
        }
        
        if (logType == LogType.RECORD && !BaseModel.getRecordLog().getValue()) {
            return;
        }
        
        if (isError) {
            logType.getLogger().error(TAG + "{}", message);
        } else {
            logType.getLogger().info(TAG + "{}", message);
        }
    }
    
    /**
     * 记录日志
     */
    private static void log(LogType logType, String message) {
        log(logType, message, false);
    }
    
    /**
     * 记录带标签的日志
     */
    private static void log(LogType logType, String tag, String message) {
        log(logType, "[" + tag + "]: " + message);
    }

    // 系统日志
    public static void system(String msg) {
        log(LogType.SYSTEM, msg);
    }

    public static void system(String TAG, String msg) {
        log(LogType.SYSTEM, TAG, msg);
    }

    // 运行时日志
    public static void runtime(String msg) {
        log(LogType.RUNTIME, msg);
    }

    public static void runtime(String TAG, String msg) {
        log(LogType.RUNTIME, TAG, msg);
    }

    // 记录日志
    public static void record(String msg) {
        log(LogType.RECORD, msg);
    }

    public static void record(String TAG, String msg) {
        log(LogType.RECORD, TAG, msg);
    }

    // 森林日志
    public static void forest(String msg) {
        log(LogType.FOREST, msg);
    }

    public static void forest(String TAG, String msg) {
        log(LogType.FOREST, TAG, msg);
    }

    // 农场日志
    public static void farm(String msg) {
        log(LogType.FARM, msg);
    }

    public static void farm(String TAG, String msg) {
        log(LogType.FARM, TAG, msg);
    }

    // 其他日志
    public static void other(String msg) {
        log(LogType.OTHER, msg);
    }

    public static void other(String TAG, String msg) {
        log(LogType.OTHER, TAG, msg);
    }

    // 调试日志
    public static void debug(String msg) {
        log(LogType.DEBUG, msg);
    }

    public static void debug(String TAG, String msg) {
        log(LogType.DEBUG, TAG, msg);
    }

    // 错误日志
    public static void error(String msg) {
        log(LogType.ERROR, msg, true);
    }

    public static void error(String TAG, String msg) {
        log(LogType.ERROR, TAG, msg);
    }

    // 捕获日志
    public static void capture(String msg) {
        log(LogType.CAPTURE, msg);
    }

    public static void capture(String TAG, String msg) {
        log(LogType.CAPTURE, TAG, msg);
    }

    // 异常堆栈跟踪
    public static void printStackTrace(Throwable th) {
        String stackTrace = "error: " + android.util.Log.getStackTraceString(th);
        error(stackTrace);
    }

    public static void printStackTrace(String msg, Throwable th) {
        String stackTrace = "Throwable error: " + android.util.Log.getStackTraceString(th);
        error(msg, stackTrace);
    }

    public static void printStackTrace(String TAG, String msg, Throwable th) {
        String stackTrace = "[" + TAG + "] Throwable error: " + android.util.Log.getStackTraceString(th);
        error(msg, stackTrace);
    }

    public static void printStackTrace(Exception e) {
        String stackTrace = "Exception error: " + android.util.Log.getStackTraceString(e);
        error(stackTrace);
    }

    public static void printStackTrace(String msg, Exception e) {
        String stackTrace = "Throwable error: " + android.util.Log.getStackTraceString(e);
        error(msg, stackTrace);
    }

    public static void printStackTrace(String TAG, String msg, Exception e) {
        String stackTrace = "[" + TAG + "] Throwable error: " + android.util.Log.getStackTraceString(e);
        error(msg, stackTrace);
    }
}
