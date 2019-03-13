 [ ![Download](https://api.bintray.com/packages/gybin02/maven/router/images/download.svg) ](https://bintray.com/gybin02/maven/router/_latestVersion)
 [![qq群](https://img.shields.io/badge/QQ%E7%BE%A4-547612870-ff69b4.svg)](http://shang.qq.com/wpa/qunwpa?idkey=f474c19f6b6b7d67e91685511207bcd326a38f50818d8e4569e52a167df85009)
 [![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
# RouterKit
[中文wiki](/Readme.md).
![原理图](http://upload-images.jianshu.io/upload_images/1194012-012b64699f6d1222.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## Getting started


//edit module build.gradle file:
```groove
//内部版本：0.0.1-SNAPSHOT
compile "com.jet.framework:router:1.0.0"
```

## Simple usage
`RouterKit` uses annotation to specify the mapping relationship.
	
- eg1: Page Router

```java
@JUri("/home")
public class IntentActivity extends AppCompatActivity {
    // Uri 的参数通过 Intent传递进来, 推荐使用Jet自动读取；
    .....
    //
}
```
- or  eg2: service Router
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
- Method Call

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

### Open From outSide Web Brower Or other APP
    
just  register RouterCenterActivity in AndroidManifest.xml，
//即可变成经典的Uri打开，可以支持外部浏览器、其它APP打开内部的Activity。
```xml
<activity android:name="com.jet.router.RouterCenterActivity">
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
### Support Interceptor 

通过前置拦截器可以对URL进行拦截，可以通过拦截器对URL进行修改，也可以拦截URL，不让路由器打开。
```java
Router.addInterceptor(new UriInterceptor() {
    public String beforeExecute(InterceptorData data) {
    	//return url.replace("test://www.XXX.com/","test://");
        return data;
    }
});
```
### Support set Scheme
```java
Router.addScheme("meiyou");
```

### 原理图
![原理图](http://upload-images.jianshu.io/upload_images/53953-ce3ffb119e0d6534.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![URI定义](http://upload-images.jianshu.io/upload_images/53953-054d5e9096445d84.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## Contact
QQ群：547612870 
 
### License

Copyright 2017 zhengxiaobin; 

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

