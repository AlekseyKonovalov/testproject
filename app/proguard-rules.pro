# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
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
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

 #retrofit
 -dontwarn retrofit2.Platform$Java8
 -dontwarn retrofit2.**
 -keep class retrofit2.** { *; }
 -keepattributes Signature
 -keepattributes Exceptions
 -keepclasseswithmembers class * {
     @retrofit2.* <methods>;
 }

  #okhttp3
  -keepattributes Signature
  -keepattributes *Annotation*
  -keep class okhttp3.** { *; }
  -keep interface okhttp3.** { *; }
  -dontwarn okhttp3.**
  -dontnote okhttp3.**

 ## Parcelable
 -keepclassmembers class * implements android.os.Parcelable {
   public static final android.os.Parcelable$Creator CREATOR;
 }
 -keep class androidx.core.app.CoreComponentFactory { *; }

  #PdfViewer
  -keep class com.shockwave.**
  -keepclassmembers enum * { *; }

   # Gson specific classes
   -dontwarn sun.misc.**
   -keep class com.google.gson.stream.** { *; }

   # keeping databinding
   -dontwarn androidx.databinding.**
   -keep class androidx.databinding.** { *; }
   -keep class * extends androidx.databinding.DataBinderMapper

   -keep enum com.alekseykon.testproject.**
   -keep class com.alekseykon.testproject.data.services.api.models.** { *; }
