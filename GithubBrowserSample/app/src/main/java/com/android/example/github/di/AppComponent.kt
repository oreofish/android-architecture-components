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

package com.android.example.github.di

import android.app.Application
import com.android.example.github.GithubApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Dagger2依赖注入的代码基本都在di目录中，除了App、Activity、Fragment中implement的一些辅助类。
 *
 * 可以将整个di目录理解为一个独立的代工厂，DI代工厂包揽了所有创建@Inject新对象的任务。
 * 其他类中需要新对象时，只要用@Inject标记出来就可以直接使用。至于这些对象如何创建、之间的关联、是否单例等，
 * 都由DI代工厂处理，使用者不用操心。
 * 当然，使用者也要按照Dagger2的要求做一些注册工作：使用者将自身提供给DI代工厂，DI代工厂自动发现其中的@Inject，
 * 并创建对象。这个过程就是依赖注入。
 *
 * 本文件是di目录的首页。可以理解为DI代工厂的总厂房，一切依赖注入工作都是从这里开始的。
 *
 * @Singleton @Component定义了注入器，并且是单例。三个module用途见注释。
 * 对于自己定义的类，如{@link com.android.example.github.ui.common.NavigationController}，不需要定义module。
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class, // Dagger 2.10的一个内部类，对Activity、Fragment做注入的
        AppModule::class,              // app全局对象在这里初始化，如retrofit、db、SharedPrefs等。
        MainActivityModule::class]     // MainActivity的Module，AndroidInjectionModule从这里获得MainActivity的实例
)
/**
 * builder主要是向注入器提供application的实例。因为AppModule中有个地方需要用到application
 */
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(githubApp: GithubApp)
}
