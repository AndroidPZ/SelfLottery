# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


# ----------------------------------------------------------------------------
# 混淆的压缩比例，0-7
-optimizationpasses 5    # 指定代码的压缩级别(默认)
# 指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers
# 指定混淆是采用的算法
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*

# 指定外部模糊字典 proguard-chinese.txt 改为混淆文件名，下同
-obfuscationdictionary proguard-dict.txt
# 指定class模糊字典
-classobfuscationdictionary proguard-dict.txt
# 指定package模糊字典
-packageobfuscationdictionary proguard-dict.txt


#-optimizationpasses 5
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify          # 混淆时是否做预校验
-ignorewarning                          # 忽略警告，避免打包时某些警告出现
-verbose                # 混淆时是否记录日志
-renamesourcefileattribute SourceFile    # 重命名抛出异常时的文件名称
-keepattributes SourceFile,LineNumberTable   # 抛出异常时保留代码行号


#APK自定义view
# 自定义控件不参与混淆
-keep class zhcw.lottery.znzd.com.selflottery.widgets.** { *; }
-dontwarn zhcw.lottery.znzd.com.selflottery.widgets.**
-dontwarn zhcw.lottery.znzd.com.selflottery.base.**
-dontwarn zhcw.lottery.znzd.com.selflottery.wxapi.**


# 实体类不被混淆
-keep class zhcw.lottery.znzd.com.selflottery.ui.bonus.entity.** { *; } 
-keep class zhcw.lottery.znzd.com.selflottery.ui.lottery.entity.** { *; } 
-keep class zhcw.lottery.znzd.com.selflottery.ui.my.entity.** { *; } 
-keep class zhcw.lottery.znzd.com.selflottery.ready_entity.** { *; }  


-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆


-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}

####################support.v4#####################
# 保留support下的所有类及其内部类
#-keep class android.support.** {*;}

# 保留继承的
#-keep public class * extends android.support.v4.**
#-keep public class * extends android.support.v7.**
#-keep public class * extends android.support.annotation.**



# 保留R下面的资源
-keep class **.R$* {*;}
# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

###### ---------------------------------- #####
#                  webView处理 相关
###### ---------------------------------- #####
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}

###### ---------------------------------- #####
#      ButterKnife 相关
###### ---------------------------------- #####
-keep class butterknife.** { *;}
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *;}
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

###### ---------------------------------- ######
#           avi:library 相关
###### ---------------------------------- ######
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }

###### ---------------------------------- ######
#           yanzhenjie:permission 相关
###### ---------------------------------- ######
-keepclassmembers class ** {
    @com.yanzhenjie.permission.PermissionYes <methods>;
}
-keepclassmembers class ** {
    @com.yanzhenjie.permission.PermissionNo <methods>;
}
###### ---------------------------------- ######
#      BaseRecyclerViewAdapterHelper 相关
###### ---------------------------------- ######
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}

###### ---------------------------------- ######
#               Glide 相关
###### ---------------------------------- ######
-keep class com.bumptech.glide.Glide { *; }
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

###### ---------------------------------- ######
#               youth.banner 相关
###### ---------------------------------- ######
-keep class com.youth.banner.** {
    *;
 }
-dontwarn com.youth.banner.**

###### ---------------------------------- ######
#               vlayout 相关
###### ---------------------------------- ######
-keepattributes InnerClasses
-keep class com.alibaba.android.vlayout.ExposeLinearLayoutManagerEx { *; }
-keep class android.support.v7.widget.RecyclerView$LayoutParams { *; }
-keep class android.support.v7.widget.RecyclerView$ViewHolder { *; }
-keep class android.support.v7.widget.ChildHelper { *; }
-keep class android.support.v7.widget.ChildHelper$Bucket { *; }
-keep class android.support.v7.widget.RecyclerView$LayoutManager { *; }

###### ---------------------------------- ######
#               baidu百度 相关
###### ---------------------------------- ######
#-libraryjars libs/baidumapapi_v3_2_0.jar
#-libraryjars libs/locSDK_5.0.jar
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}
-dontwarn com.baidu.**
################baidu ocr###############
-dontwarn com.baidu.ocr.**
################baidu amap###############
-dontwarn com.amap.api.**
-keep class com.amap.api.** { *; }

###### ---------------------------------- ######
#               takephoto 相关
###### ---------------------------------- ######
-dontwarn android.support.**
-keep class android.support.** { *; }
-keepattributes InnerClasses
-dontoptimize
#-keep class com.jph.takephoto.** { *; }
#-dontwarn com.jph.takephoto.**
#-keep class com.darsh.multipleimageselect.** { *; }
#-dontwarn com.darsh.multipleimageselect.**
#-keep class com.soundcloud.android.crop.** { *; }
#-dontwarn com.soundcloud.android.crop.**


###### ---------------------------------- ######
#                facebook 相关
###### ---------------------------------- ######
-keep class com.facebook.** { *; }
-dontwarn com.facebook.**


###### ---------------------------------- ######
#                alipay支付宝 相关
###### ---------------------------------- ######
#-libraryjars libs/alipaysecsdk.jar
#-libraryjars libs/alipayutdid.jar
#-libraryjars libs/alipaysdk.jar
#-keep class com.alipay.android.app.IAliPay{*;}
#-keep class com.alipay.android.app.IAlixPay{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
#-keep class com.alipay.android.app.lib.ResourceMap{*;}

###### ---------------------------------- ######
#                 gson 相关
###### ---------------------------------- ######
-keep class com.google.gson.** {*;}
-dontwarn com.google.gson.**
#-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.google.** {
    <fields>;
    <methods>;
}

###### ---------------------------------- #####
#      保留Serializable序列化的类不被混淆
###### ---------------------------------- #####
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

###### ---------------------------------- ######
#               zxing 相关
###### ---------------------------------- ######
-keep class com.zxing.** {*;}
-dontwarn com.zxing.**
-dontwarn com.google.zxing.**
-keep class com.google.zxing.** {*;}


###### ---------------------------------- ######
#               tencent 相关
###### ---------------------------------- ######
-keep class com.tencent.** { *; }
-dontwarn com.tencent.**


###### ---------------------------------- ######
#               Eventbus3 相关
###### ---------------------------------- ######
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

###### ---------------------------------- ######
#                    UIL 相关
###### ---------------------------------- ######
-keep class com.nostra13.universalimageloader.** { *; }
-keepclassmembers class com.nostra13.universalimageloader.** {*;}
-dontwarn com.nostra13.universalimageloader.**


####### ---------------------------------- ######
#              DataBinding 相关
####### ---------------------------------- ######
-keepclasseswithmembers class * extends android.databinding.ViewDataBinding{
    <methods>;
}

####### ---------------------------------- ######
##      BGAPhotoPicker 相关
####### ---------------------------------- ######
-keep class **.*BGA*.** {*;}
#-dontwarn cn.bingoogolapple.photopicker.imageloader.**

#-keep class cn.bingoogolapple.baseadapter.***{ *; }
#-dontwarn cn.bingoogolapple.baseadapter.***
-keep class cn.bingoogolapple.***{ *; }
-dontwarn cn.bingoogolapple.***
-keep class uk.co.senab.photoviewnew.***{ *; }
-dontwarn uk.co.senab.photoviewnew.***

####### ---------------------------------- ######
#               imagepicker 相关
####### ---------------------------------- ######
-keep class com.lzy.imagepicker.** { *; }
-dontwarn om.lzy.imagepicker.**


#其他第三方

#华为
-keep class com.huawei.** { *; }
-dontwarn com.huawei.**
#极光
-keep class cn.jpush.android.** { *; }
-dontwarn cn.jpush.android.**
#OKgo
-keep class okio.Okio** { *; }
-dontwarn okio.Okio**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *;}
-keepattributes Signature
-keepattributes Exceptions
#rx
-keep class rx.internal.** { *; }
-dontwarn rx.internal.**

-keep class com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

-keep class csun.security.ssl.** { *; }
-dontwarn sun.security.ssl.**

-keep class com.lvfq.pickerview.** { *; }
-dontwarn com.lvfq.pickerview.**



###### ---------------------------------- ######
#               umeng 相关
###### ---------------------------------- ######
#  -libraryjars libs/umeng-analytics-v5.2.4.jar
#  -keep class com.umeng.analytics.** {*;}
#  -dontwarn com.umeng.analytics.**

  #-keep public class * extends com.umeng.**
  #-keep public class * extends com.umeng.analytics.**
  #-keep public class * extends com.umeng.common.**
  #-keep public class * extends com.umeng.newxp.**
#  -keep class com.umeng.** { *; }
#  -keep class com.umeng.analytics.** { *; }
#  -keep class com.umeng.common.** { *; }
#  -keep class com.umeng.newxp.** { *; }
#
#  -keepclassmembers class * {
#     public <init>(org.json.JSONObject);
#  }
#  -keep class com.umeng.**
#
#  -keep public class com.idea.fifaalarmclock.app.R$*{
#      public static final int *;
#  }
#
#  -keep public class com.umeng.fb.ui.ThreadView {
#  }
#
#  -dontwarn com.umeng.**
#
#  -dontwarn org.apache.commons.**
#
#  -keep public class * extends com.umeng.**
#
#  -keep class com.umeng.** {*; }