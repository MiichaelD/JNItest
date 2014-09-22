LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := JNItest
LOCAL_ARM_MODE 	:= arm
#TARGET_OUT  	:= $(LOCAL_PATH)/

### Add all source file names to be included in lib separated by a whitespace
LOCAL_SRC_FILES := JNItest.cpp

#preprocess defines & flags
LOCAL_CFLAGS := -DOS_ANDROID\
				-fno-strict-aliasing\
				-fsigned-char \
				-Wno-write-strings

LOCAL_LDLIBS := -ldl -llog -lstdc++ -O3

#include folders
LOCAL_C_INCLUDES := $(LOCAL_PATH)/
	
include $(BUILD_SHARED_LIBRARY)