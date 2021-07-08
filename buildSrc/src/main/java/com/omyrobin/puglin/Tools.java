package com.omyrobin.puglin;

import com.omyrobin.puglin.utils.FileUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tools {

    public static void cmd(String cmd, String params) {
        String cmdStr;
        if (isCmdExist(cmd)) {
            cmdStr =  cmd + " "+ params;
        } else {
            if (isMac()) {
                cmdStr = com.omyrobin.puglin.utils.FileUtil.getToolsDirPath() + "mac/" + cmd + " "+ params;
            } else if (isLinux()) {
                cmdStr = com.omyrobin.puglin.utils.FileUtil.getToolsDirPath() + "linux/" + cmd + " "+ params;
            } else if (isWindows()) {
                cmdStr = com.omyrobin.puglin.utils.FileUtil.getToolsDirPath() + "windows/" + cmd + " "+ params;
            } else {
                cmdStr = "";
            }
        }
        if ("".equals(cmdStr)) {
            return;
        }
        System.out.println("cwebp命令:" + cmdStr);
        outputMessage(cmdStr);
    }

    public static boolean isLinux() {
        String system = System.getProperty("os.name");
        return system.startsWith("Linux");
    }

    public static boolean isMac() {
        String system = System.getProperty("os.name");
        return system.startsWith("Mac OS");
    }

    public static boolean isWindows() {
        String system = System.getProperty("os.name");
        return system.toLowerCase().contains("win");
    }

    public void chmod() {
        outputMessage("chmod 755 -R " + FileUtil.getRootDirPath());
    }

    private static void outputMessage(String cmd) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    private static boolean isCmdExist(String cmd) {
        String result;
        if (isMac() || isLinux()) {
            result = executeCmd("which " + cmd);
        } else {
            result = executeCmd("where " + cmd);
        }
        return result != null && !result.isEmpty();
    }

    private static String executeCmd(String cmd) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        try {
            return bufferReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}