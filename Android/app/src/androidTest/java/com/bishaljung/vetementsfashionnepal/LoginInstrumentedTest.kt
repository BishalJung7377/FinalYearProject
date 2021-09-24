package com.bishaljung.vetementsfashionnepal

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.bishaljung.vetementsfashionnepal.ui.DiscoverItemsActivity
import com.bishaljung.vetementsfashionnepal.ui.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class LoginInstrumentedTest {

    @get:Rule
    val testRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun testLoginUI(){
        Espresso.onView(withId(R.id.etEmail))
            .perform(ViewActions.typeText("bsal@gmail.com"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(withId(R.id.etPass))
            .perform(ViewActions.typeText("apple"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(withId(R.id.btnIdlogin))
            .perform(ViewActions.click())

        Thread.sleep(2000)

        Espresso.onView(withId(R.id.txtrecentproduct))
            .check(ViewAssertions.matches(ViewMatchers.withText("Recent Products")))
        Thread.sleep(2000)

//        Espresso.onView(withId(R.id.drawerLayout))
//            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//        Thread.sleep(1000)
////
    }
}