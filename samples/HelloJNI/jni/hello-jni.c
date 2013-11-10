// A simple example of JNI code to interface with HelloJNI
#include <string.h>
#include <jni.h>

jstring
Java_aad_app_hello_jni_HelloJNIActivity_getHelloWorldJNI(JNIEnv* env, jobject thiz)
{
    return (*env)->NewStringUTF(env, "HelloWorld");
}

jint
Java_aad_app_hello_jni_HelloJNIActivity_incrementJNI(JNIEnv* env, jobject thiz, jint value)
{
	return value + 1;
}
