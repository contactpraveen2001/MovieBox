package com.backbase.assignment.UITest

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.backbase.assignment.R
import com.backbase.assignment.ui.movieList.MovieListActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.DateFormat
import java.text.SimpleDateFormat


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MovieBoxInstrumentedTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun happyPath() {
        val listActivityScenario = ActivityScenario.launch(MovieListActivity::class.java)
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val releaseDate = parser.parse("2021-07-07") ?: return
        val releaseDateString = DateFormat.getDateInstance().format(releaseDate)
        var itemCount = 0
        listActivityScenario.onActivity {
            val recyclerView = it.findViewById<RecyclerView>(R.id.recyclerView_popular)
            itemCount = recyclerView.adapter!!.itemCount
        }
        // check item count
        assert(itemCount > 3)
        // check for title in the list
        Espresso.onView(withText(Matchers.containsString("Black Widow")))
            .check(matches(isDisplayed()))
        // check for release date
        Espresso.onView(withText(Matchers.containsString(releaseDateString)))
            .check(matches(isDisplayed()))
        // open detail screen
        Espresso.onView(withText(Matchers.containsString("Black Widow"))).perform(click())
        // check for detail view
        Espresso.onView(withId(R.id.movieDetail)).check(matches(isDisplayed()))
        // check for title
        Espresso.onView(withId(R.id.movie_title)).check(matches(withText("Black Widow")))
        // check for duration text
        Espresso.onView(withId(R.id.release_date_duration)).check(matches(withSubstring("2h 14m")))
        // check for release date
        Espresso.onView(withId(R.id.release_date_duration))
            .check(matches(withSubstring(releaseDateString)))
        // check for over view
        Espresso.onView(withId(R.id.overview))
            .check(matches(withSubstring("also known as Black Widow,")))
        // check for genres
        Espresso.onView(withText(Matchers.containsString("Action"))).check(matches(isDisplayed()))
        Espresso.onView(withText(Matchers.containsString("Adventure")))
            .check(matches(isDisplayed()))
        Espresso.onView(withText(Matchers.containsString("Thriller"))).check(matches(isDisplayed()))
        Espresso.onView(withText(Matchers.containsString("Science Fiction")))
            .check(matches(isDisplayed()))
    }

}
