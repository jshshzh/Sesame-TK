package fansirsqi.xposed.sesame.util;

import java.util.UUID;

/**
 * RPC调用辅助工具类，用于减少重复代码
 */
public class RpcHelper {
    
    /**
     * 生成UUID字符串（去除横线）
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 生成短UUID字符串
     */
    public static String generateShortUuid() {
        StringBuilder sb = new StringBuilder();
        for (String str : UUID.randomUUID().toString().split("-")) {
            sb.append(str.substring(str.length() / 2));
        }
        return sb.toString();
    }
    
    /**
     * 构建标准的RPC请求参数
     * 
     * @param params 具体参数
     * @return JSON格式的参数字符串
     */
    public static String buildParams(String params) {
        if (params == null || params.trim().isEmpty()) {
            return "[{}]";
        }
        return "[" + params + "]";
    }
    
    /**
     * 构建带outBizNo的RPC请求参数
     * 
     * @param params 具体参数
     * @return JSON格式的参数字符串
     */
    public static String buildParamsWithBizNo(String params) {
        String bizNo = generateUuid();
        if (params == null || params.trim().isEmpty()) {
            return "[{\"outBizNo\":\"" + bizNo + "\"}]";
        }
        return "[{\"outBizNo\":\"" + bizNo + "\"," + params.substring(1);
    }
    
    /**
     * 构建带时间戳的RPC请求参数
     * 
     * @param params 具体参数
     * @return JSON格式的参数字符串
     */
    public static String buildParamsWithTimestamp(String params) {
        long timestamp = System.currentTimeMillis();
        if (params == null || params.trim().isEmpty()) {
            return "[{\"timestamp\":" + timestamp + "}]";
        }
        return "[{\"timestamp\":" + timestamp + "," + params.substring(1);
    }
    
    /**
     * 构建标准的支付宝版本参数
     * 
     * @param params 具体参数
     * @param version 版本号
     * @return JSON格式的参数字符串
     */
    public static String buildParamsWithVersion(String params, String version) {
        if (params == null || params.trim().isEmpty()) {
            return "[{\"version\":\"" + version + "\"}]";
        }
        return "[{\"version\":\"" + version + "\"," + params.substring(1);
    }
} 