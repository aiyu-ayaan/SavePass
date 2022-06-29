package com.ab.core.modules

import com.ab.core.scope.SavePassScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @SavePassScope
    @Provides
    @Singleton
    fun provideSavePassScope() = CoroutineScope(SupervisorJob())


}