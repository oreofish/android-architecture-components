package com.android.example.github.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.android.example.github.ui.repo.RepoViewModel;
import com.android.example.github.ui.search.SearchViewModel;
import com.android.example.github.ui.user.UserViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * 生成一个以ViewModel类为key，ViewModel实例为value的Map。用到对应类的时候就通过这里创建。
 * 这个Map是给ViewModelProvider.Factory用的。具体用法见{@link com.android.example.github.ui.search.SearchFragment}。
 */
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RepoViewModel.class)
    abstract ViewModel bindRepoViewModel(RepoViewModel repoViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(GithubViewModelFactory factory);
}
