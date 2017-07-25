# Jet RouterKit
美柚路由库；Android平台对页面、服务的路由框架。自动化且易用。

- 基于**APT**技术（注解-编译时生成代码，不反射，无性能损耗），通过注解方式来实现**URL**打开Activity功能。
- 并支持在WebView和外部浏览器使用，支持多级Activity跳转，
- 使用[**Jet**](http://git.meiyou.im/Android/jet)技术支持Bundle、Intent，Uri参数自动注入页面并转换参数类型。


### 方案对比
|实现功能|**RouterKit**|Airbnb 的**DeepLinkDispatch**|阿里     **ARouter**|天猫 统跳协议|**ActivityRouter**Github上Star最多 |
|:------:|:--------:| :----------------------:| :-------------:| :----------:|-------------:|
|路由注册	|注解式APT自动注册	|每个module都要手动注册	|每个module的路由表都要APT类查找	|AndroidManiFest配置	|每个module都要手动注册|
|路由查找	|路由表|	路由表	|路由表	|系统Intent|	路由表
|路由分发	|Activity转发|	Activity转发|	Activity转发|	Activity转发	|Activity转发|
|动态替换	|主线程|	不支持	|线程等待	|不支持|	不支持|
|动态拦截	|主线程|	不支持|	线程等待	|不支持	|主线程|
|安全拦截	|主线程|	不支持	|线程等待	|不支持	|主线程|
|方法调用	|手动拼装	|手动拼装	|手动拼装	|手动拼装	|手动拼装|
|参数获取	|JET 依赖自动注入，支持所有类型|	参数定义在path，不利于多人协作|	Apt依赖注入，但是要手动调用get方法|	手动调用	|手动调用|
|结果返回	|onActivityResult|	onActivityResult	|onActivityResult	|onActivityResult	|onActivityResult|
|支持多Module	|支持	|不支持	|支持	|不支持|	支持|


整体类似 阿里开源的[**ARoute**](https://github.com/alibaba/ARouter) 功能；移除分组概念，强化多Module编译和自动注册路由表，会更通用。

### 特色：
1. 支持注解方式，APT编译器自动注册Activity 和**Action**（类似Struts里面的Action）
2. 支持自动注入Intent,Bundle、Uri里的参数到页面使用[**Jet**](http://git.meiyou.im/Android/jet)
3. 支持外部浏览器打开。
4. 支持HTTP协议。
5. 支持多个Module。
6. 支持Uri 跳转和 Action 执行；
7. 路由表自动初始化，也可以手动再维护；
8. 支持服务端下发路由配置，简单支持页面降级功能；

### 功能：
- Apt实现自动路由注册，支持多Module
- 路由表维护
- Activity转发 和 Action转发（支持URI页面跳转和方法调用）

### 原理图
![原理图](http://upload-images.jianshu.io/upload_images/53953-ce3ffb119e0d6534.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![URI定义](http://upload-images.jianshu.io/upload_images/53953-054d5e9096445d84.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 典型应用
1. 从外部URL映射到内部页面，以及参数传递与解析
2. 跨模块页面跳转，模块间解耦
3. 拦截跳转过程，处理登陆、埋点等逻辑
4. 跨模块API调用，通过控制反转来做组件解耦

### 使用范例 
- 声明1: 页面跳转

```java
@JUri("/home")
public class IntentActivity extends AppCompatActivity {
    // Uri 的参数通过 Intent传递进来, 推荐使用Jet自动读取；
    .....
    //
}
```
- or 声明2： 服务功能调用：
```java
//支持，多个地址 @JUri(array={"/home","/action"})
@JUri("/action")
public class TestAction extends Action {
    Context context = MyApplication.getContext();

    @Override
    public void run(Map queryMap) {
        super.run(queryMap);
        //Uri 里面的参数通过Map传递进来
        String result = (String) queryMap.get("param");
        Toast.makeText(context, "Test Action: " + result, Toast.LENGTH_SHORT).show();
    }
}
    
```
- 方法调用

```java
    // 尽可能早，推荐在Application中初始化,初始化路由表
    Router.getInstance().init(mApplication);


    // 方式一
    String uri = "meiyou:///home";
    Router.getInstance().run(uri);

    // 方式二
    Router.getInstance().run(context, Uri.parse("meiyou:///second?uid=233"));
    
    // 方式三
    // 如果AndroidManifest.xml注册了RouterCenterActivity，也可以通过下面的方式打开，如果是APP内部使用，不建议使用。
    // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("meiyou:///home?uid=233")));
```

### 从外部浏览器、其它APP打开
    
只要在AndroidManifest.xml注册了RouterCenterActivity，即可变成经典的Uri打开，可以支持外部浏览器、其它APP打开内部的Activity。
```xml
<activity android:name="com.meiyou.router.RouterCenterActivity">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="test" />
    </intent-filter>
</activity>
```
```java
// Java代码调用
startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("meiyou:///second?uid=233&name=Wiki")));

// HTML方式，系统浏览器（不支持微信，微信内部开网页会禁止所有的Scheme）
<a href="test:///second?uid=233&name=Wiki">打开JoyrunApp的SecondActivity</a>

```

### 支持拦截器，典型应用就是：某些URI需要授权才能访问

通过前置拦截器可以对URL进行拦截，可以通过拦截器对URL进行修改，也可以拦截URL，不让路由器打开。
```java
Router.addInterceptor(new UriInterceptor() {
    public String beforeExecute(InterceptorData data) {
    	//return url.replace("test://www.XXX.com/","test://");
        return data;
    }
});
```
### 支持 设置Scheme ，只有允许的Scheme才有效；才允许路由分发
```java
Router.addScheme("meiyou");
```

### 集成
在gradle文件配置：
```groove
//内部版本：0.0.1-SNAPSHOT
compile "com.meiyou.framework:router:0.0.1-SNAPSHOT"
```

### 混淆
### 常见问题
* [Intent参数自动注入IOC - Jet](git.meiyou.im/Android/jet)
* 参考[Android 组件化 —— 路由设计最佳实践](http://www.jianshu.com/p/8a3eeeaf01e8)
* [开源最佳实践：Android平台页面路由框架ARouter](https://yq.aliyun.com/articles/71687?spm=5176.100240.searchblog.7.8os9Go)
* [iOS 组件化 —— 路由设计思路分析](https://halfrost.com/ios_route)
* [LiteRouter模仿retrofit，各个业务分根据需求约定好接口，就像一份接口文档一样](http://www.jianshu.com/p/79e9a54e85b2)
* [routable-android 模式匹配方式的路由](https://github.com/clayallsopp/routable-android)

### TODO
- JUri 支持数组数据（fixed）
- Module传递依赖解决 (fixed)
- 自定义 注解实现，可以再自定义额外的路由表，实现自定义的注解的路由，Door接口的优化
-  Gradle Plugin实现，APT 主工程 需要配置编译过程问题
- 拦截器排序,优先级 priority
- 路由匹配规则Matcher功能升级, Pattern 模式匹配
- 调用方式接口化，like: retrofit;
- 路由结果回调？ isNeed？
- Kotlin版本实现

### License

Copyright 2017 zhengxiaobin@xiaoyouzi.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

