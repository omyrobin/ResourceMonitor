package com.omyrobin.puglin;

import java.util.ArrayList;

/**
 * @Author: omyrobin
 * @CreateDate: 2021/7/6 10:33 AM
 * @Description:
 */
public class WebpExtension {
    /**
     * 白名单 用来过滤不需要转换的图片
     */
    ArrayList<String> whiteList = new ArrayList<>();
    /**
     * appIcon
     */
    String appIconName = "";
    /**
     * appRoundIcon
     */
    String appIconRoundName = "";
    /**
     * webp压缩质量
     */
    int quality;
    /**
     * 最大宽度
     */
    int maxWidth;
    /**
     * 最大高度
     */
    int maxHeight;

    public ArrayList<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(ArrayList<String> whiteList) {
        this.whiteList = whiteList;
    }

    public String getAppIconName() {
        return appIconName;
    }

    public void setAppIconName(String appIconName) {
        this.appIconName = appIconName;
    }

    public String getAppIconRoundName() {
        return appIconRoundName;
    }

    public void setAppIconRoundName(String appIconRoundName) {
        this.appIconRoundName = appIconRoundName;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }
}
