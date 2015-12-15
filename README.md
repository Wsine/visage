# Visage 

对代码稍微进行了一下整理，在这里贴出一些关于代码的说明。方便大家贡献代码。

## 用到的库

### Android Support Library

Android 官方的兼容库，目的是在 Lolipop 之前的 Android 上支持 Lolipop 之后的特性：如 `TabLayout`, `RecyclerView` 等。

```
compile 'com.android.support:appcompat-v7:23.1.1'
compile 'com.android.support:design:23.1.1'
compile 'com.android.support:support-v4:23.1.1'
compile 'com.android.support:recyclerview-v7:23.1.1'
```

### Square 的开源库

除了 Google 自己之外，[Square](http://square.github.io/) 公司也为 Android 贡献了不少好用的开源第三方库。他们说：

> As a company built on open source, here are some of the internally-developed libraries we have contributed back to the community.

```
compile 'com.squareup.picasso:picasso:2.5.2'
compile 'com.squareup.okhttp:okhttp:2.6.0'
compile 'com.jakewharton:butterknife:7.0.1'
```


其中，`Picasso` 用于加载图片，支持本地图片和网络图片。它帮你解决了网络访问、缓存以及多线程的问题。`OkHttp`，一个简单易用的 HTTP Client，同样解决了网络访问和缓存的问题。`ButterKnife`，简单的依赖注入库。它可以让你这样获取一个 `View` 实例：

```
@Bind(R.id.text) TextView textView;
```

### CircleImageView
[Github](https://github.com/hdodenhof/CircleImageView) 上找的一个开源第三方库，用于显示 NavigationDrawer 上的圆形头像（图片上的人物是 Spock，如果你们没有看过《星际迷航》的话）。
```
compile 'de.hdodenhof:circleimageview:2.0.0'
```
