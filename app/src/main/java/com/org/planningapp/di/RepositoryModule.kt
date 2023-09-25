package com.org.planningapp.di

import com.org.planningapp.data.repository.AuthenticationRepository
import com.org.planningapp.data.repository.CategoryRepository
import com.org.planningapp.data.repository.DailyGoalRepository
import com.org.planningapp.data.repository.TimesheetRepository
import com.org.planningapp.data.repository.impl.AuthRepositoryImpl
import com.org.planningapp.data.repository.impl.CategoryRepositoryImpl
import com.org.planningapp.data.repository.impl.DailyGoalRepositoryImpl
import com.org.planningapp.data.repository.impl.TimesheetRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepostiory(impl: AuthRepositoryImpl): AuthenticationRepository
    @Binds
    abstract fun bindDailyGoalRepository(impl: DailyGoalRepositoryImpl): DailyGoalRepository
    @Binds
    abstract fun bindTimesheetRepository(impl: TimesheetRepositoryImpl): TimesheetRepository
    @Binds
    abstract fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository
}