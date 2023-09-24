package com.org.planningapp.di

// Auth
import com.org.planningapp.data.repository.IAuthenticationRepository
import com.org.planningapp.data.repository.impl.SupabaseAuthRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepostiory(impl: SupabaseAuthRepository): IAuthenticationRepository
}