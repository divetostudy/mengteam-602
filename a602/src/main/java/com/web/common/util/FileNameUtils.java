package com.web.common.util;
import java.util.UUID;

/**
 * 处理图片名称的工具类
 * @author wyp
 */
public class FileNameUtils {
    /**
     * 根据文件全名获取后缀名
     * @param fileName 文件名
     * @return 后缀名字符串
     */
    public static String getSuffix(String fileName){
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex == -1) {
            return ""; // 文件名中没有后缀
        }
        return fileName.substring(lastIndex);
    }


    /**
     * 更改文件名(UUID防止上传文件重名)
     * @param fileName 原文件名
     * @return uuid处理后的新文件名
     */
    public static String getFileName(String fileName){
        String uuid = UUID.randomUUID().toString().replace("-","");
        return uuid + getSuffix(fileName);
    }
}
