package com.raywenderlich.rwnews

import android.provider.SyncStateContract.Helpers.insert
import com.raywenderlich.rwnews.di.AppModule
import com.raywenderlich.rwnews.logger.RwNewsLogger
import com.raywenderlich.rwnews.repository.NewsRepository
import com.raywenderlich.rwnews.repository.entity.News
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
@UninstallModules(AppModule::class) // HERE
class NewsListFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Module
    @InstallIn(SingletonComponent::class) // 1
    object TestAppModule {

        @Provides
        fun provideNewsRepository(): NewsRepository { // 2
            return FakeNewsRepository().apply {
                insert(News(1, "First Title", "First Body"))
                insert(News(2, "Second Title", "Second Body"))
                insert(News(3, "Third Title", "Third Body"))
            }
        }

        @Provides
        fun provideNewsLogger(): RwNewsLogger = FakeNewsLogger() // 2
    }
}
