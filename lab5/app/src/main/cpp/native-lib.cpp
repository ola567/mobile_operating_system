#include <jni.h>
#include <string>
#include <stdio.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_lab5_MainActivity_stringFromJNI(JNIEnv* env,jobject /* this */) {
    std::string hello = "Hello Ola!";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jintArray JNICALL
Java_com_example_lab5_MainActivity_arrayFromJNI(JNIEnv* env,jobject,jintArray inputArray) {
    jint inputArrayLength = env->GetArrayLength(inputArray);
    jintArray outputArray = env->NewIntArray(inputArrayLength);

    // Copy original input array and sort
    jint *input = env->GetIntArrayElements(inputArray, NULL);
    std::sort(input, input + inputArrayLength);

    // Iterate through input array and compare each two elements
    jint *output = new jint[inputArrayLength];
    jint outputIndex = 0;
    for (jint i = 0; i < inputArrayLength; i++) {
        if (i == 0 || input[i] != input[i - 1]) {
            output[outputIndex] = input[i];
            outputIndex++;
        }
    }

    // Create new array and copy data from buffer
    env->SetIntArrayRegion(outputArray, 0, outputIndex, output);

    // Release memory
    env->ReleaseIntArrayElements(inputArray, input, 0);
    delete[] output;

    return outputArray;
}

