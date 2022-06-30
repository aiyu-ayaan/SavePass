package com.ab.core.modules

import android.content.Context
import android.content.SharedPreferences
import com.ab.core.constants.PREF_NAME
import com.ab.core.scope.SavePassScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
}