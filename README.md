# ResourceMonitor 资源监测

#### 目前主要实现了png、jpg等图片转换为webp文件功能 后续会添加不能转换的资源进行压缩处理


在需要进行转换的Moudle的build.gradle文件下加入下面代码，如果多个Moudle需要记得都加上这行代码

- 接入Plugin

```
apply plugin: 'com.omyrobin.resourcemonitor'
```

- Plugin 配置


```
Webp {
    quality = 75  //转webp的压缩质量
    appIconName = "ic_launcher.png" //appIcon
    appIconRoundName = "ic_launcher_round.png" //appRoundIcon
    whiteList = ["icon_xxxx.png"]  //白名单 记得加后缀名。
    maxWidth = 700
    maxHeight = 800
}
```

由于aapt2 flat文件问题 目前还不支持直接构建出最终apk，但是可以当做一个Task任务来执行，执行后所有的可以处理的图片资源都会转变成webp,此时再重新执行打包流程就可以构建出转换webp资源后的apk了，apk包的大小将会大大减少

mac os下

```
./gradlew assembleDebug
```

执行上面的代码来打包会由于aapt2问题失败，原因之前已经提过了，所以目前该插件只能当做一个单独的Task来执行

```
//一定要执行Relase的Task 因为依赖的library默认是按Relese打包
./gradlew mergeReleaseResources

```

执行完上面的命令就可以看到项目中所有的满足条件的图片全部转换为webp文件了，超过配置宽高的图片地址被输出到控制台

