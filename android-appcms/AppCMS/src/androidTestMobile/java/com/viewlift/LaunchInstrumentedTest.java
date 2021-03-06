package com.viewlift;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.FailureHandler;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;

import com.viewlift.mobile.AppCMSLaunchActivity;
import com.viewlift.presenters.AppCMSPresenter;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import tools.fastlane.screengrab.Screengrab;
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy;
import tools.fastlane.screengrab.locale.LocaleTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * Created by viewlift on 5/9/17.
 */
@RunWith(AndroidJUnit4.class)
public class LaunchInstrumentedTest {


    private Context instrumentationCtx;

    @ClassRule
    public static final LocaleTestRule localeTestRule = new LocaleTestRule();

    @Rule
    public ActivityTestRule<AppCMSLaunchActivity> activityTestRule =
            new ActivityTestRule<>(AppCMSLaunchActivity.class, true, false);


    @Before
    public void setup() {
        instrumentationCtx = InstrumentationRegistry.getContext();
    }

    @Test
    public void test_launchActivity() throws Exception {

        Screengrab.setDefaultScreenshotStrategy(new UiAutomatorScreenshotStrategy());

        activityTestRule.launchActivity(new Intent(InstrumentationRegistry.getTargetContext(), AppCMSLaunchActivity.class));

        Thread.sleep(5000);

        Screengrab.screenshot("splash_screen");

        onView(withId(R.id.error_fragment)).check(doesNotExist());

        Thread.sleep(10000);

        Screengrab.screenshot("home_screen");

        onView(firstView(withId(R.id.recyclerview_tray))).withFailureHandler(new FailureHandler() {
            @Override
            public void handle(Throwable error, Matcher<View> viewMatcher) {
                Log.d("Piyush",error.toString());
                error.printStackTrace();
            }
        }).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Thread.sleep(5000);

        try {
            onView(withText("Shows")).perform(click());
            Thread.sleep(10000);
            Screengrab.screenshot("show_screen");
        } catch (NoMatchingViewException e) {
            // View is not in hierarchy
        }


        try {
            onView(withText("Movies")).perform(click());
            Thread.sleep(10000);
            Screengrab.screenshot("movies_screen");
        } catch (NoMatchingViewException e) {
            // View is not in hierarchy
        }

        try {
            onView(withText("Menu")).perform(click());
            Thread.sleep(10000);
            Screengrab.screenshot("menu_screen");
        } catch (NoMatchingViewException e) {
            // View is not in hierarchy
        }

        try {
            onView(withText("VIDEOS")).perform(click());
            Thread.sleep(10000);
            Screengrab.screenshot("menu_screen");
        } catch (NoMatchingViewException e) {
            // View is not in hierarchy
        }


        try {
            onView(withText("TEAMS")).perform(click());
            Thread.sleep(10000);
            Screengrab.screenshot("team_screen");
        } catch (NoMatchingViewException e) {
            // View is not in hierarchy
        }

        try {
            onView(withText("MORE")).perform(click());
            Thread.sleep(10000);
            Screengrab.screenshot("more_screen");
        } catch (NoMatchingViewException e) {
            // View is not in hierarchy
        }




        onView(withId(R.id.home_nested_scroll_view)).withFailureHandler(new FailureHandler() {
            @Override
            public void handle(Throwable error, Matcher<View> viewMatcher) {
                Log.d("Piyush",error.toString());
                error.printStackTrace();
            }
        }).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Thread.sleep(10000);

//        if(!isTablet(instrumentationCtx))
//            onView(withId(R.id.scrollview_home)).perform(click());

        //  if(isTablet(instrumentationCtx))


        Thread.sleep(3000);
        //Screengrab.screenshot("detail_screen");

        //onView(withId(android.R.id.content)).perform(ViewActions.swipeUp());

//        onView(withId(R.id.video_play_image)).perform(click());

        Activity activity = activityTestRule.getActivity();

        String package_name = Utils.getProperty("AppPackageName", activity);

        AppCMSPresenter appCMSPresenter = ((AppCMSApplication) activity.getApplication()).getAppCMSPresenterComponent().appCMSPresenter();
//
//        if (!appCMSPresenter.isUserLoggedIn() && package_name.equalsIgnoreCase("com.viewlift.hoichoi")) {
//
//            Thread.sleep(2000);
//            onView(withId(R.id.login_btn)).perform(click());
//
//            Thread.sleep(5000);
//            onView(withId(R.id.edittext_email)).perform(clearText(), typeText("hc10@gmail.com"));
//
//            Thread.sleep(2000);
//            onView(withId(R.id.edittext_password)).perform(clearText(), typeText("12345"));
//
//            Thread.sleep(2000);
//            onView(firstView(withId(R.id.submit_login_btn))).perform(click());
//        }
//
//        Thread.sleep(15000);
        //Screengrab.screenshot("player_screen");
    }


//    public class RecyclerViewItemCountAssertion implements ViewAssertion {
//        private final int expectedCount;
//
//        public RecyclerViewItemCountAssertion(int expectedCount) {
//            this.expectedCount = expectedCount;
//        }
//
//        @Override
//        public void check(View view, NoMatchingViewException noViewFoundException) {
//            if (noViewFoundException != null) {
//                throw noViewFoundException;
//            }
//
//            RecyclerView recyclerView = (RecyclerView) view;
//            RecyclerView.Adapter adapter = recyclerView.getAdapter();
//            assertThat(adapter.getItemCount(), is(expectedCount));
//        }
//    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private <T> Matcher<T> firstView(final Matcher<T> matcher) {
        return new BaseMatcher<T>() {
            boolean isFirst = true;

            @Override
            public boolean matches(final Object item) {
                if (isFirst && matcher.matches(item)) {
                    isFirst = false;
                    return true;
                }
                return false;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("should return first matching item");
            }
        };
    }

}
