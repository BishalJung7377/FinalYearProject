package com.bishaljung.vetementsfashionnepal

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.bishaljung.vetementsfashionnepal.ui.AboutPageActivity
import com.bishaljung.vetementsfashionnepal.ui.GoogleMapActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class MapPageInstrumentedTest {

    @get:Rule
    val testRule = ActivityScenarioRule(GoogleMapActivity::class.java)
    @Test
    fun testMap(){
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.map))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(3000)
    }

}