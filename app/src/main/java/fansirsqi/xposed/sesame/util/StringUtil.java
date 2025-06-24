package fansirsqi.xposed.sesame.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * 字符串工具类
 */
public class StringUtil {
    
    /**
     * 检查字符串是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
    
    /**
     * 检查字符串是否不为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    /**
     * 检查字符串是否为空白
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * 检查字符串是否不为空白
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
    
    /**
     * 安全获取字符串，如果为null则返回空字符串
     */
    public static String safeString(String str) {
        return str != null ? str : "";
    }
    
    /**
     * 安全获取字符串，如果为null或空则返回默认值
     */
    public static String safeString(String str, String defaultValue) {
        return isNotEmpty(str) ? str : defaultValue;
    }
    
    /**
     * 将集合元素用分隔符连接成字符串
     */
    public static String join(CharSequence conjunction, Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        StringBuilder b = new StringBuilder();
        Iterator<?> iterator = collection.iterator();
        b.append(toStringOrEmpty(iterator.next()));
        while (iterator.hasNext()) {
            b.append(conjunction).append(toStringOrEmpty(iterator.next()));
        }
        return b.toString();
    }
    
    /**
     * 将数组元素用分隔符连接成字符串
     */
    public static String join(CharSequence conjunction, Object... array) {
        if (array == null || array.length == 0) {
            return "";
        }
        StringBuilder b = new StringBuilder();
        b.append(toStringOrEmpty(array[0]));
        for (int i = 1; i < array.length; i++) {
            b.append(conjunction).append(toStringOrEmpty(array[i]));
        }
        return b.toString();
    }
    
    /**
     * 将数组元素用逗号连接成字符串
     */
    public static String join(Object... array) {
        return join(",", array);
    }
    
    /**
     * 安全转换为字符串
     */
    private static String toStringOrEmpty(Object obj) {
        return Objects.toString(obj, "");
    }
    
    /**
     * 左填充字符串
     */
    public static String padLeft(String str, int totalWidth, char padChar) {
        if (str == null) {
            str = "";
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() < totalWidth) {
            sb.insert(0, padChar);
        }
        return sb.toString();
    }
    
    /**
     * 右填充字符串
     */
    public static String padRight(String str, int totalWidth, char padChar) {
        if (str == null) {
            str = "";
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() < totalWidth) {
            sb.append(padChar);
        }
        return sb.toString();
    }
    
    /**
     * 整数左填充
     */
    public static String padLeft(int value, int totalWidth, char padChar) {
        return padLeft(String.valueOf(value), totalWidth, padChar);
    }
    
    /**
     * 整数右填充
     */
    public static String padRight(int value, int totalWidth, char padChar) {
        return padRight(String.valueOf(value), totalWidth, padChar);
    }
    
    /**
     * 提取两个字符串之间的内容
     */
    public static String substringBetween(String text, String left, String right) {
        if (text == null || left == null || right == null) {
            return "";
        }
        
        int leftIndex = left.isEmpty() ? 0 : text.indexOf(left);
        if (leftIndex == -1) {
            return "";
        }
        
        int startIndex = leftIndex + left.length();
        int rightIndex = right.isEmpty() ? text.length() : text.indexOf(right, startIndex);
        if (rightIndex == -1) {
            rightIndex = text.length();
        }
        
        return text.substring(startIndex, rightIndex);
    }
    
    /**
     * 检查字符串是否包含指定字符
     */
    public static boolean contains(String str, String searchStr) {
        return str != null && searchStr != null && str.contains(searchStr);
    }
    
    /**
     * 检查字符串是否以指定前缀开头
     */
    public static boolean startsWith(String str, String prefix) {
        return str != null && prefix != null && str.startsWith(prefix);
    }
    
    /**
     * 检查字符串是否以指定后缀结尾
     */
    public static boolean endsWith(String str, String suffix) {
        return str != null && suffix != null && str.endsWith(suffix);
    }
    
    /**
     * 移除字符串开头和结尾的空白字符
     */
    public static String trim(String str) {
        return str != null ? str.trim() : "";
    }
    
    /**
     * 将字符串转换为大写
     */
    public static String toUpperCase(String str) {
        return str != null ? str.toUpperCase() : "";
    }
    
    /**
     * 将字符串转换为小写
     */
    public static String toLowerCase(String str) {
        return str != null ? str.toLowerCase() : "";
    }
    
    /**
     * 替换字符串中的指定字符
     */
    public static String replace(String str, String searchStr, String replacement) {
        if (str == null || searchStr == null || replacement == null) {
            return str;
        }
        return str.replace(searchStr, replacement);
    }
    
    /**
     * 分割字符串
     */
    public static String[] split(String str, String delimiter) {
        if (str == null || delimiter == null) {
            return new String[0];
        }
        return str.split(delimiter);
    }
    
    /**
     * 检查字符串是否为数字
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * 安全转换为整数
     */
    public static int toInt(String str, int defaultValue) {
        if (isNumeric(str)) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
    
    /**
     * 安全转换为长整数
     */
    public static long toLong(String str, long defaultValue) {
        if (isNumeric(str)) {
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
    
    /**
     * 安全转换为双精度浮点数
     */
    public static double toDouble(String str, double defaultValue) {
        if (isNumeric(str)) {
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
}
