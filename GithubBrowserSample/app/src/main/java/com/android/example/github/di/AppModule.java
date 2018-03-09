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

package com.android.example.github.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.android.example.github.api.GithubService;
import com.android.example.github.db.GithubDb;
import com.android.example.github.db.RepoDao;
import com.android.example.github.db.UserDao;
import com.android.example.github.util.LiveDataCallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * app全局对象在这里初始化，如retrofit、db、SharedPrefs等。这里包装了所有创建的细节，如参数和依赖关系等。
 * 每个@Provides能够提供对应类的实例，它们需要的参数也由注入器提供，如provideDb(app)。
 * @Singleton 对应的类，全app共用一个实例。
 * <p>
 * 这个Module继承自ViewModelModule，包含了ViewModelModule的所有内容。
 */
@Module(includes = ViewModelModule.class)
class AppModule {
    @Singleton @Provides
    GithubService provideGithubService() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(GithubService.class);
    }

    @Singleton @Provides
    GithubDb provideDb(Application app) {
        return Room.databaseBuilder(app, GithubDb.class,"github.db").build();
    }

    @Singleton @Provides
    UserDao provideUserDao(GithubDb db) {
        return db.userDao();
    }

    @Singleton @Provides
    RepoDao provideRepoDao(GithubDb db) {
        return db.repoDao();
    }
}
