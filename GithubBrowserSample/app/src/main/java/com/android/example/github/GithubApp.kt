/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.github

import android.app.Activity
import android.app.Application
import com.android.example.github.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * 定制的Application，除了设置了Timber输出log，就是用Dagger2做依赖注入。
 * <p>
 * Application中的依赖注入工作有：
 * 1. 实现HasActivityInjector.activityInjector()方法
 * 2. 调用辅助类AppInjector对所有Activity、Fragment做依赖注入
 * 详情请见AppInjector类中的实现
 */
class GithubApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector.init(this)
    }

    /**
     * Dagger2依赖注入：
     * 将Activity的实例提供给注入器使用。
     * 每个Application有多个Acitvity，Activity中调用AndroidInjection.inject(Activity activity)时会用
     */
    override fun activityInjector() = dispatchingAndroidInjector
}
