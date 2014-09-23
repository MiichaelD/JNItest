
#include <errno.h>

#include <string.h>
#include <android/log.h>
#include "com_android_JNItest_JNItestActivity.h"

#define TAG "JNItest-CPP"

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, __VA_ARGS__))
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, __VA_ARGS__))




 JNIEXPORT jstring JNICALL Java_com_android_JNItest_JNItestActivity_stringFromJNICPP(JNIEnv * env, jobject obj)
 {
     return env->NewStringUTF("Hello World to this awesome calculator from CPP :)");
 }

 JNIEXPORT jdouble JNICALL Java_com_android_JNItest_JNItestActivity_operacion
   (JNIEnv *env , jobject obj, jdouble a, jdouble b, jint op)
 {

	 //__android_log_print(ANDROID_LOG_INFO,TAG,"A= %f, B=%f, Op=%d",a,b,op);
	 LOGI(TAG,"Received: A= %f, B=%f, Op=%d",a,b,op);
	 switch(op){
	 case 1: return a*b;
	 case 2: return a+b;
	 case 3: return a/b;
	 case 4: return a-b;
	 default:
		 return 0;
	 }
 }
