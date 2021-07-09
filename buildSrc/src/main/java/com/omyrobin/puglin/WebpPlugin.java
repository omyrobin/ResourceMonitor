package com.omyrobin.puglin;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.LibraryExtension;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.api.BaseVariant;
import com.android.build.gradle.api.LibraryVariant;
import com.android.build.gradle.tasks.MergeResources;
import com.omyrobin.puglin.utils.FileUtil;
import com.omyrobin.puglin.utils.ImageUtil;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskInputs;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

/**
 * @Author: omyrobin
 * @CreateDate: 2021/7/4 4:53 PM
 * @Description:
 */
public class WebpPlugin implements Plugin<Project> {

    private Project project;
    /**
     * project下android的配置信息
     */
    private AppExtension appExtension;
    /**
     * project下library的配置信息
     */
    private LibraryExtension libraryExtension;
    /**
     * project下Webp的配置信息
     */
    private WebpExtension webPExtension;
    /**
     * 缓存列表 用于优化遍历
     */
    private ArrayList<String> cacheList = new ArrayList<>();
    /**
     *
     */
    private ArrayList<String> largeList = new ArrayList<>();
    //以下字段用来接收用户配置数据
    private ArrayList<String> whiteList;

    private int quality;

    private String appIcon;

    private String appIconRound;

    private int maxWidth;

    private int maxHeight;

    @Override
    public void apply(Project project) {
        this.project = project;
        //设置根目录，用来查找cwep工具位置
        FileUtil.setRootDir(project.getRootDir().getAbsolutePath());
        //创建"Webp"的配置函数
        webPExtension = project.getExtensions().create("Webp", WebpExtension.class);
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                //获取用户配置信息
                initConfig();
                //如果project是android application
                if (project.getPlugins().hasPlugin("com.android.application")) {
                    //获取android配置
                    appExtension = project.getExtensions().getByType(AppExtension.class);
                    //获取所有变体、可以理解为各种渠道包
                    appExtension.getApplicationVariants().all(new Action<ApplicationVariant>() {
                        @Override
                        public void execute(ApplicationVariant variant) {
                            getResource(variant);
                        }
                    });
                }

                //如果project是android library
                if (project.getPlugins().hasPlugin("com.android.library")) {
                    //获取library配置
                    libraryExtension = project.getExtensions().getByType(LibraryExtension.class);

                    libraryExtension.getLibraryVariants().all(new Action<LibraryVariant>() {
                        @Override
                        public void execute(LibraryVariant variant) {
                            getResource(variant);
                        }
                    });
                }
            }
        });
    }

    /**
     * 获取项目中所有的resources资源 还有其他方式获取 可以自行钻研
     *
     * @param variant
     */
    private void getResource(BaseVariant variant) {
        //Gradle脚本下App构建流程，资源是Task --- MergeXXXXResources的输入,获取MergeXXXXResources Task
        MergeResources mergeResources = variant.getMergeResourcesProvider().get();
        //在MergeResources Task执行前先执行
        mergeResources.doFirst(new Action<Task>() {
            @Override
            public void execute(Task task) {
                //获取Task的输入
                TaskInputs inputs = task.getInputs();
                //获取输入文件集合
                Set<File> files = inputs.getFiles().getFiles();
                //遍历文件集合
                for (File file : files) {
                    recursionFile(file);
                }
                //输出大图列表
                printLargeImage();
            }
        });
    }

    /**
     * 通过遍历找到符合规则的资源，添加到处理集合中
     *
     * @param file
     */
    private void recursionFile(File file) {
        //如果遍历到的文件是文件夹
        if (file.isDirectory() && file.listFiles() != null) {
            for (File f : file.listFiles()) {
                //递归调用
                recursionFile(f);
            }
        } else {
            //如果符合要求的图片
            if (filterRule(file)) {
                //如果超出尺寸限制
                if(ImageUtil.isLargeImage(file, maxWidth, maxHeight)){
                    largeList.add(file.getAbsolutePath());
                    return;
                }

                if (!cacheList.contains(file.getAbsolutePath())) {
                    cacheList.add(file.getAbsolutePath());

                    String inPut = file.getAbsolutePath();
                    //此处踩坑了 String的split函数用"."作为正则的分割符需要转义
                    String outPut = file.getParent() +"/"+ file.getName().split("\\.")[0] + ".webp";
                    System.out.println("<<<<<<<: "+ outPut);
                    //执行命令
                    Tools.cmd("cwebp", "-q " + quality + " " + inPut + " -o " + outPut);

                    //判断生成的webp文件和原文件大小
                    File webpFile = new File(outPut);
                    if (webpFile.length() > file.length()) {
                        if (webpFile.exists()) {
                            webpFile.delete();
                        }
                    } else {
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取build.gradle文件中的用户配置
     */
    private void initConfig() {
        //获取用户配置
        webPExtension = project.getExtensions().getByType(WebpExtension.class);
        //webp质量
        quality = webPExtension.getQuality();
        //图片白名单
        whiteList = webPExtension.getWhiteList();
        //app icon
        appIcon = webPExtension.getAppIconName();
        //app icon round
        appIconRound = webPExtension.getAppIconRoundName();
        //最大宽度
        maxWidth = webPExtension.getMaxWidth();
        //最大高度
        maxHeight = webPExtension.getMaxHeight();
    }

    /**
     * 资源过滤规则
     *
     * @param file
     * @return
     */
    private boolean filterRule(File file) {
        //如果是白名单中的文件，过滤掉
        if (whiteList.contains(file.getName())) {
            return false;
        }
        //如果是应用启动图片或启动的圆角图标（Google Play 要求appIcon 必须使用png文件）
        if (appIcon.equals(file.getName()) || appIconRound.equals(file.getName())) {
            return false;
        }
        //符合要求的图片
        if (ImageUtil.isImage(file)) {
            return true;
        }
        return false;
    }

    private void printLargeImage() {
        if(largeList.size()>0) {
            StringBuilder builder = new StringBuilder("Waring Large list : \n");

            for (String path: largeList) {
                builder.append(path);
                builder.append("\n");
            }

            System.err.println(builder.toString());
        }
    }
}



