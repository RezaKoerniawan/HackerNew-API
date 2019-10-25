package com.reza.base.presentation.mainpage

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.reza.base.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainPageActivityTest {
    @get:Rule
    var activityTestRule = ActivityTestRule(MainPageActivity::class.java)

    @Test
    fun launch_app_test() {
        delay()

//        Espresso.onView(ViewMatchers.withId(R.menu.menu_favorite)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.rv_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        delay()
    }

    private fun delay() {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}