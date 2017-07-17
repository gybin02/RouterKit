# Jet Route
美柚路由库；Android平台页面路由框架

基于apt技术，通过注解方式来实现URL打开Activity功能，并支持在WebView和外部浏览器使用，支持多级Activity跳转，支持Bundle、Uri参数注入并转换参数类型。

类似 ARoute 功能；

### 特色：
1. 支持注解方式，APT编译器自动注册Activity 和**Action**（类似Struts里面的Action）
2. 支持注入Bundle、Uri的参数并转换格式。使用**Jet**
3. 支持外部浏览器打开。
4. 支持HTTP协议。
5. 支持多个Module。TODO

### 功能：
- Apt实现自动路由注册，支持多Module
- 路由表维护
- Activity转发 和 Action转发（支持URI页面跳转和方法调用）

![原理图](http://upload-images.jianshu.io/upload_images/53953-ce3ffb119e0d6534.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![URI定义](http://upload-images.jianshu.io/upload_images/53953-054d5e9096445d84.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 使用范例 
- 声明1: 页面跳转；
```java
@JUri("/home")
public class IntentActivity extends AppCompatActivity {
    // Uri 的参数通过 Intent传递进来, 推荐使用Jet自动读取；
    .....
    //
}
```
- or 声明2： 功能调用：
```java
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
- 调用
```java
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

// HTML方式，系统浏览器（不支持微信?）
<a href="test:///second?uid=233&name=Wiki">打开JoyrunApp的SecondActivity</a>

```

### 支持前置拦截器

通过前置拦截器可以对URL进行过滤，可以通过过滤器对URL进行修改，也可以拦截URL，不让路由器打开。
```java
Router.setFilter(new Filter() {
    public String doFilter(String url) {
    	//return url.replace("test://www.thejoyrun.com/","test://");
        return url;
    }
});
```

### 混淆
### 常见问题
* 参考[Android 组件化 —— 路由设计最佳实践]（http://www.jianshu.com/p/8a3eeeaf01e8）
* [开源最佳实践：Android平台页面路由框架ARouter](https://yq.aliyun.com/articles/71687?spm=5176.100240.searchblog.7.8os9Go)

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

