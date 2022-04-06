package com.tikaa.mygithub.theme

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.tikaa.mygithub.R

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class Setting_ThemeTest {

    @Before
    fun setup() {
        ActivityScenario.launch(Setting_Theme::class.java)
    }

    @Test
    fun assertMenuButton() {
        Espresso.onView(ViewMatchers.withId(R.id.switch_theme))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}