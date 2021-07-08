package com.omyrobin.puglin.utils;

import java.io.File;

/**
 * @Author: omyrobin
 * @CreateDate: 2021/7/8 3:36 PM
 * @Description: 判断文件是否是图片资源
 */
public class ImageUtil {

    private static final String JPG = ".jpg";
    private static final String JPEG = ".jpeg";
    private static final String PNG = ".png";
    private static final String D_9PNG = ".9.png";

    public static boolean isImage(File file) {
        if (file.getName().endsWith(JPG) || file.getName().endsWith(JPEG) || file.getName().endsWith(PNG) && !file.getName().endsWith(D_9PNG)) {
            return true;
        }
        return false;
    }
}
