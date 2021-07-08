package com.omyrobin.puglin;

import java.util.ArrayList;

/**
 * @Author: omyrobin
 * @CreateDate: 2021/7/6 10:33 AM
 * @Description:
 */
public class WebpExtension {

    ArrayList<String> whiteList = new ArrayList<>();

    String appIconName = "";

    String appIconRoundName = "";

    int quality;

    int[] bigImage;

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

    public int[] getBigImage() {
        return bigImage;
    }

    public void setBigImage(int[] bigImage) {
        this.bigImage = bigImage;
    }
}
