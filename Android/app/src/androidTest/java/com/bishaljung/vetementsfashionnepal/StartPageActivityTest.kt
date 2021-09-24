package com.bishaljung.vetementsfashionnepal

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.bishaljung.vetementsfashionnepal.ui.LoginActivity
import com.bishaljung.vetementsfashionnepal.ui.StartPageActivity
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@LargeTest
@RunWith(JUnit4::class)
class StartPageActivityTest {

    @Test
    fun StartPageTest() {
        val activityScenario = ActivityScenario.launch(StartPageActivity::class.java)
        Espresso.onView(withId(R.id.tvtitle))
            .check(ViewAssertions.matches(ViewMatchers.withText("Vetements FASHION")))

        Espresso.onView(withId(R.id.btnlogin))
            .perform(ViewActions.click())

        Thread.sleep(2000)
    }
}