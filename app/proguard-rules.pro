# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class fullName to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file fullName.
#-renamesourcefileattribute SourceFile

-keep class com.tencent.stat.** { *;}
-keep class com.tencent.mid.** { *;}
# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
 -ignorewarnings
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
-keep class com.ping.ticketver2.beans.beans.** {*;}


-keepattributes *Annotation*
-keepclassmembers class ** {
    protected void onPubEvent(...);
    public void onEventMainThread(...);
}
-keepclasseswithmembernames class * {
    native <methods>;
}
# BGA 反射混淆 避免滑动卡顿
-keep class cn.bingoogolapple.bgabanner.BGAViewPager {*;}

#融云的混淆代码
 -keepattributes Exceptions,InnerClasses

 -keepattributes Signature

 # RongCloud SDK
 -keep class io.rong.** {*;}
 -keep class * implements io.rong.imlib.model.MessageContent {*;}
 -dontwarn io.rong.push.**
 -dontnote com.xiaomi.**
 -dontnote com.google.android.gms.gcm.**
 -dontnote io.rong.**

 # VoIP
 -keep class io.agora.rtc.** {*;}

 # Location
 -keep class com.amap.api.**{*;}
 -keep class com.amap.api.services.**{*;}

 -dontwarn com.dinpay.plugin.**
 -keep class com.dinpay.plugin.** {*;}
 -dontwarn com.unionpay.**
 -keep class com.unionpay.** {*;}
 -dontwarn com.UCMobile.**
 -keep class com.UCMobile.** {*;}


 -keep class com.google.gson.stream.** { *; }
 -keep class com.xhh.ticketver2.beans.** { *; }
