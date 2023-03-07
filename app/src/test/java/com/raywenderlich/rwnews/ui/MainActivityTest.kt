package com.raywenderlich.rwnews.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.google.common.truth.Truth.assertThat
import com.raywenderlich.rwnews.di.ActivityModule
import com.raywenderlich.rwnews.ui.list.NewsListFragment
import com.raywenderlich.rwnews.ui.navigation.NavigationHelper
import dagger.hilt.android.testing.*
import fakes.FakeNavigationHelper
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@HiltAndroidTest // 1
@Config(application = HiltTestApplication::class) // 2
@RunWith(RobolectricTestRunner::class) // 3
@LooperMode(LooperMode.Mode.PAUSED)  // 4
@UninstallModules(ActivityModule::class) // 8
class MainActivityTest {
    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this) // 5

    @get:Rule(order = 1) // 2
    var activityScenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java) // 1

    @BindValue // to add the binding for FakeNavigationHelper to the dependency graph for the test.
    @JvmField // to ask Kotlin to generate a navigator as a field without a getter and setter.
    val navigator: NavigationHelper = FakeNavigationHelper()  // 3

    @Before
    fun setUp() {
        hiltAndroidRule.inject() // 6
    }

    @Test
    fun whenMainActivityLaunchedNavigationHelperIsInvokedForFragment() { // 7
        activityScenarioRule.scenario // 1
        val fakeHelper = navigator as FakeNavigationHelper // 2
        with(fakeHelper.replaceRequests[0]) { // 3
//            assertThat(anchorId).isEqualTo(R.id.anchor)
            assertThat(fragment).isInstanceOf(NewsListFragment::class.java)
            assertThat(backStack).isNull()
        }
    }
}