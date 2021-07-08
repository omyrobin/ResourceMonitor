package com.omyrobin.puglin.utils;

import java.io.File;

/**
 * @Author: omyrobin
 * @CreateDate: 2021/7/7 15:36 PM
 * @Description: 设置目录
 */
public class FileUtil {

    private static String rootDir;

    public static void setRootDir(String rootDir) {
        FileUtil.rootDir = rootDir;
    }

    public static String getRootDirPath() {
        return rootDir;
    }

    public static File getToolsDir() {
        return new File( "/cwebp/");
    }

    public static String getToolsDirPath() {
        return rootDir + "/cwebp/";
    }
}