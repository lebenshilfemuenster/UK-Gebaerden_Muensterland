package de.lebenshilfe_muenster.uk_gebaerden_muensterland.sign_trainer;

import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.lebenshilfe_muenster.uk_gebaerden_muensterland.R;
import de.lebenshilfe_muenster.uk_gebaerden_muensterland.activities.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static de.lebenshilfe_muenster.uk_gebaerden_muensterland.util.OrientationChangeAction.orientationLandscape;
import static de.lebenshilfe_muenster.uk_gebaerden_muensterland.util.OrientationChangeAction.orientationPortrait;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anyOf;

/**
 * Created by mtonhaeuser on 26.03.2016.
 */
@RunWith(AndroidJUnit4.class)
public class SignTrainerTest {

    public static final String TAG = SignTrainerTest.class.getSimpleName();

    @Rule
    public final ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void navigateToSignTrainer() {
        onView(isRoot()).perform(orientationPortrait());
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
        final String navigationButtonText = mainActivityActivityTestRule.getActivity().getResources().getString(R.string.train_signs);
        final String toolbarTitle = mainActivityActivityTestRule.getActivity().getResources().getString(R.string.sign_trainer);
        onView(withText(navigationButtonText)).perform(click());
        onView(allOf(withText(toolbarTitle), withParent((withId(R.id.toolbar))))).check(matches(isDisplayed()));
    }

    @Test
    public void checkNavigationDrawerButtonIsPresent() {
        onView(withContentDescription(R.string.navigation_drawer_open)).check(matches(isDisplayed()));
    }

    @Test
    public void checkNavigationDrawerIsClosed() {
        onView(withId(R.id.nav_view)).check(matches(not(isDisplayed())));
    }

    @Test
    public void checkToolbarIsPresent() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void checkQuestionTextIsPresent() {
        onView(withId((R.id.signTrainerQuestionText))).check(matches(isDisplayed()));
    }

    @Test
    public void checkAnswerButtonsAreNotPresent() {
        onView(withText(getStringResource(R.string.questionWasEasy))).check(matches(not(isDisplayed())));
        onView(withText(getStringResource(R.string.questionWasFair))).check(matches(not(isDisplayed())));
        onView(withText(getStringResource(R.string.questionWasHard))).check(matches(not(isDisplayed())));
    }

    @Test
    public void checkSignTrainerIsWorkingCorrectly() {
        onView((withContentDescription(anyOf(containsString(getStringResource(R.string.videoIsLoading)),
                Matchers.containsString(getStringResource(R.string.videoIsPlaying)))))).check(matches(isDisplayed()));
        onView(withText(getStringResource(R.string.solveQuestion))).check(matches(isDisplayed()));
        // trigger configuration change and check state afterwards
        onView(isRoot()).perform(orientationLandscape());
        onView((withContentDescription(anyOf(containsString(getStringResource(R.string.videoIsLoading)),
                Matchers.containsString(getStringResource(R.string.videoIsPlaying)))))).check(matches(isDisplayed()));
        // click solve button
        onView(withText(getStringResource(R.string.solveQuestion))).check(matches(isDisplayed())).perform(click());
        checkStateAfterSolveButtonClicked();
        // trigger configuration change and check state afterwards
        onView(isRoot()).perform(orientationPortrait());
        checkStateAfterSolveButtonClicked();
        // click on answer button
        onView(withText(getStringResource(R.string.questionWasFair))).perform(click());
        onView((withContentDescription(anyOf(containsString(getStringResource(R.string.videoIsLoading)),
                Matchers.containsString(getStringResource(R.string.videoIsPlaying)))))).check(matches(isDisplayed()));
        checkStateAfterAnswerButtonClicked();
    }

    @Test
    public void checkTogglingLearningModeWorks() {
        onView(withId(R.id.action_toggle_learning_mode)).check(matches(isDisplayed())).perform(click());
        onView(withText(R.string.howDoesThisSignLookLike)).check(matches(isDisplayed()));
    }


    private void checkStateAfterSolveButtonClicked() {
        onView(withText(getStringResource(R.string.solveQuestion))).check(matches((not(isDisplayed()))));
        onView(withText(getStringResource(R.string.signQuestion))).check(matches((not(isDisplayed()))));
        onView(withId(R.id.signTrainerVideoView)).check(matches((not(isDisplayed()))));
        onView(withText(getStringResource(R.string.questionWasEasy))).check(matches(isCompletelyDisplayed()));
        onView(withText(getStringResource(R.string.questionWasFair))).check(matches(isCompletelyDisplayed()));
        onView(withText(getStringResource(R.string.questionWasHard))).check(matches(isCompletelyDisplayed()));
        onView(withContentDescription(getStringResource(R.string.answer))).check(matches(isCompletelyDisplayed()));
        onView(withContentDescription(getStringResource(R.string.trainerMnemonic))).check(matches(isCompletelyDisplayed()));
        onView(withContentDescription(getStringResource(R.string.learningProgress))).check(matches(isCompletelyDisplayed()));
        onView(withContentDescription(getStringResource(R.string.howHardWasTheQuestion))).check(matches(isCompletelyDisplayed()));
        onView(withContentDescription(getStringResource(R.string.signTrainerExplanation))).check(matches(isCompletelyDisplayed()));
    }

    private void checkStateAfterAnswerButtonClicked() {
        onView(withId(R.id.signTrainerVideoView)).check(matches((isDisplayed())));
        onView(withText(getStringResource(R.string.solveQuestion))).check(matches(isEnabled()));
        onView(withText(getStringResource(R.string.questionWasEasy))).check(matches(not(isDisplayed())));
        onView(withText(getStringResource(R.string.questionWasFair))).check(matches(not(isDisplayed())));
        onView(withText(getStringResource(R.string.questionWasHard))).check(matches(not(isDisplayed())));
        onView(withContentDescription(getStringResource(R.string.answer))).check(matches(not(isDisplayed())));
        onView(withContentDescription(getStringResource(R.string.trainerMnemonic))).check(matches(not(isDisplayed())));
        onView(withContentDescription(getStringResource(R.string.learningProgress))).check(matches(not(isDisplayed())));
        onView(withContentDescription(getStringResource(R.string.howHardWasTheQuestion))).check(matches(not(isDisplayed())));
        onView(withContentDescription(getStringResource(R.string.signTrainerExplanation))).check(matches(not(isDisplayed())));
    }


    @NonNull
    private String getStringResource(int stringResourceId) {
        return mainActivityActivityTestRule.getActivity().getResources().getString(stringResourceId);
    }

}
