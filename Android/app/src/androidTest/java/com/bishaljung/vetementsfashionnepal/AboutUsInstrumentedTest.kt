package com.bishaljung.vetementsfashionnepal

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.bishaljung.vetementsfashionnepal.ui.AboutPageActivity
import com.bishaljung.vetementsfashionnepal.ui.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@LargeTest
@RunWith(JUnit4::class)

class AboutUsInstrumentedTest {
    @get:Rule
    val testRule = ActivityScenarioRule(AboutPageActivity::class.java)
    @Test
    fun testAboutUs(){
        Thread.sleep(1000)
        onView(withId(R.id.webviewSoftwarica)).check(matches(isDisplayed()))
        Thread.sleep(4000)
    }

}