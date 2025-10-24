package com.example.androiduitesting;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAddCity() {
        // Click on Add City button
        onView(withId(R.id.button_add)).perform(click());

        // Type "Edmonton" in the editText
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));

        // Click on Confirm
        onView(withId(R.id.button_confirm)).perform(click());

        // Check if text "Edmonton" is matched with any of the text displayed on the screen
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }
    @Test
    public void testClearCity() {
        // Add first city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Add another city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Vancouver"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Clear the list
        onView(withId(R.id.button_clear)).perform(click());

        // Verify both cities are no longer displayed
        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Vancouver")).check(doesNotExist());
    }

    @Test
    public void testListView(){
    // Add a city
    onView(withId(R.id.button_add)).perform(click());
    onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
    onView(withId(R.id.button_confirm)).perform(click());

    // Check if in the Adapter view (given id of that adapter view), there is a data
    // (which is an instance of String) located at position zero.
    // If this data matches the text we provided then Voila! Our test should pass
    // You can also use anything() in place of is(instanceOf(String.class))
    onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.city_list)).atPosition(0).check(matches((withText("Edmonton"))));
    }

    // Test Case 1: Check whether the activity correctly switches from MainActivity to ShowActivity
    @Test
    public void testActivitySwitchOnCityClick() {
        // Initialize Intents to capture intent calls
        Intents.init();

        // Add a city to the list first
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Toronto"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click on the city item in the ListView
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Verify that an Intent to ShowActivity was sent
        Intents.intended(IntentMatchers.hasComponent(ShowActivity.class.getName()));

        // Clean up
        Intents.release();
    }

    // Test Case 2: Test whether the city name is consistent between MainActivity and ShowActivity
    @Test
    public void testCityNameConsistency() {
        String testCityName = "Vancouver";

        // Add a city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText(testCityName));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click on the city item to navigate to ShowActivity
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Verify that the city name is displayed correctly in ShowActivity
        onView(withId(R.id.textView_cityName)).check(matches(withText(testCityName)));
    }

    // Test Case 3: Test the "back" button functionality in ShowActivity
    @Test
    public void testBackButtonFunctionality() {
        // Add a city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Montreal"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Navigate to ShowActivity by clicking on the city
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Verify we're in ShowActivity by checking if the back button is displayed
        onView(withId(R.id.button_back)).check(matches(isDisplayed()));

        // Click the back button
        onView(withId(R.id.button_back)).perform(click());

        // Verify we're back in MainActivity by checking if the city list is displayed
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));

        // Also verify that our previously added city is still in the list
        onView(withText("Montreal")).check(matches(isDisplayed()));
    }


}
