package com.lyf.live.hilt

import com.lyf.export_data.di.Record
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//首先 SingletonComponent，它会存在整个App生命周期中，
// 随着App的销毁而销毁，也就意味着，在App的任何位置都可以使用这个Module
@InstallIn(SingletonComponent::class)
@Module
class RecordModule {

    @Singleton
    @Provides
    fun providerRecord(): Record {
        return Record()
    }
}

