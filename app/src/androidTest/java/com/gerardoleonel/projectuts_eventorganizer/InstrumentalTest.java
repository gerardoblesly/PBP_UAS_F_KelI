package com.gerardoleonel.projectuts_eventorganizer;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InstrumentalTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void instrumentalTest() {

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnCreateEvent), withText("CREATE EVENT"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fl_container),
                                        0),
                                2)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnCreate), withText("CREATE"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction materialAutoCompleteTextView = onView(
                allOf(withId(R.id.txtEventType),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(replaceText("Birthday"), closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btnCreate), withText("CREATE"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1)));
        materialButton5.perform(scrollTo(), click());

        ViewInteraction materialAutoCompleteTextView2 = onView(
                allOf(withId(R.id.txtEventPlace),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        materialAutoCompleteTextView2.perform(replaceText("Italy"), closeSoftKeyboard());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.btnCreate), withText("CREATE"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1)));
        materialButton7.perform(scrollTo(), click());

        ViewInteraction materialAutoCompleteTextView3 = onView(
                allOf(withId(R.id.txtEventPackage),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        materialAutoCompleteTextView3.perform(replaceText("Flawless"), closeSoftKeyboard());


        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.btnCreate), withText("CREATE"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1)));
        materialButton8.perform(scrollTo(), click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.txtDescription),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("This is description that we are going to test right here over the instrumental test"), closeSoftKeyboard());

        ViewInteraction materialAutoCompleteTextView11 = onView(
                allOf(withId(R.id.txtEventType),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        materialAutoCompleteTextView11.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btnCreate), withText("CREATE"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1)));
        materialButton4.perform(scrollTo(), click());

        ViewInteraction materialAutoCompleteTextView112 = onView(
                allOf(withId(R.id.txtEventPlace),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        materialAutoCompleteTextView112.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction materialButton45 = onView(
                allOf(withId(R.id.btnCreate), withText("CREATE"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1)));
        materialButton45.perform(scrollTo(), click());

        ViewInteraction materialAutoCompleteTextView115 = onView(
                allOf(withId(R.id.txtEventType),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        materialAutoCompleteTextView115.perform(replaceText("Birthday"), closeSoftKeyboard());

        ViewInteraction materialAutoCompleteTextView111 = onView(
                allOf(withId(R.id.txtEventPlace),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                1),
                        isDisplayed()));
        materialAutoCompleteTextView111.perform(replaceText("Panama"), closeSoftKeyboard());

        ViewInteraction materialButton456 = onView(
                allOf(withId(R.id.btnCreate), withText("CREATE"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1)));
        materialButton456.perform(scrollTo(), click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
