// Заглушка для проверки сборки: возвращает строку с возможностями CPU из llama.cpp.
// Если она показалась на экране, значит llama.cpp собран и слинкован.
#include <jni.h>
#include "llama.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_gguflauncher_NativeBridge_systemInfo(JNIEnv *env, jobject /*thiz*/) {
    return env->NewStringUTF(llama_print_system_info());
}
