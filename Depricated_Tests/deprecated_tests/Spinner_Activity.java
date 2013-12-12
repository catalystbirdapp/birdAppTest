package deprecated_tests;

import java.util.ArrayList;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R.string;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Spinner;

/**
 * @author abarendt
 * To run these tests, the application needs to be open on the phone, so the splash screen can be avoided.
 */
public class Spinner_Activity extends
		ActivityInstrumentationTestCase2<BirdFormActivity> {
	private int idActivityPrompt;
	private int idActivityLabel;

	private int idFlying;
	private int idHunting;
	private int idSwimming;
	private int idMating;
	
	private String activityPrompt;
	private String activityLabel;

	private String flying;
	private String hunting;
	private String swimming;
	private String mating;

	private Spinner activity;

	private Solo solo;

	public Spinner_Activity() {
		super(BirdFormActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		solo = new Solo(getInstrumentation(), getActivity());

		activity = solo.getView(Spinner.class, 0);
		idActivityPrompt = string.activityPrompt;
		idActivityLabel = string.activityText;

		idFlying = string.flying;
		idHunting = string.hunting;
		idSwimming = string.swimming;
		idMating = string.mating;

		activityPrompt = getActivity().getResources().getString(
				idActivityPrompt);
		activityLabel = getActivity().getResources().getString(idActivityLabel);

		flying = getActivity().getResources().getString(idFlying);
		hunting = getActivity().getResources().getString(idHunting);
		swimming = getActivity().getResources().getString(idSwimming);
		mating = getActivity().getResources().getString(idMating);
	}

	/**
	 * When I click on the text box for the Activity spinner, A box will open
	 * with the choices for the Activity spinner Verify; "Flying" is in the list;
	 * Click on the word "Flying"; Verify that the box goes away by looking for
	 * the word "Activity".  Verify that "Flying" was placed in the text box.
	 * Verify that the index of the word "Flying" will also trigger "Flying" to be chosen.
	 * 
	 * Running independently, test passes
	 */
	public void testIndexZeroZero_Flying() {
		solo.assertCurrentActivity("testActivityPrompt_flying_index (0,0)",
				BirdFormActivity.class);

		
		solo.searchText(activityPrompt);
	

		solo.clickOnView(activity); // opens up the activity spinner

		solo.waitForText(activityPrompt);
		solo.scrollToBottom();
		solo.waitForText(flying);
		solo.clickOnText(flying);

		solo.waitForText(activityLabel);
		solo.searchText(flying);

		// able to click on the (0,0) index
		solo.pressSpinnerItem(0, 0);
		solo.waitForText(flying);
		solo.finishOpenedActivities();
	}

	/**
	 *  * When I click on the text box for the Activity spinner, A box will open
	 * with the choices for the Activity spinner; Verify "Flying" is in the list;
	 * Click on the word "Hunting"; Verify that the box goes away by looking for
	 * the word "Activity".  Verify that "Hunting" was placed in the text box.
	 * Verify that the index of the word "Hunting" will
	 * also trigger "Hunting" to be chosen.
	 */
	public void testIndexZeroOne_Hunting() {
		solo.assertCurrentActivity("testIndexZeroOne_Hunting(0,1)",
				BirdFormActivity.class);
		solo.searchText(activityPrompt);
		solo.clickOnView(activity); // opens up the category spinner
		solo.waitForText(activityPrompt);
		solo.scrollToBottom();
		solo.waitForText(hunting);
		solo.clickOnText(hunting);

		solo.waitForText(activityLabel);
		solo.searchText(hunting);

		// able to click on the (0,1) index
		solo.pressSpinnerItem(0, 1);
		solo.waitForText(hunting);
		solo.finishOpenedActivities();
	}
	
	/**
	 *  When I click on the text box for the Activity spinner, A box will open
	 * with the choices for the Activity spinner; Verify "Swimming" is in the list;
	 * Click on the word "Swimming"; Verify that the box goes away by looking for
	 * the word "Activity".  Verify that "Swimming" was placed in the text box.
	 * Verify that the index of the word "Swimming" will also trigger "Swimming" to be chosen.
	 */
	public void testIndexZeroTwo_Swimming() {
		solo.assertCurrentActivity("testIndexZeroTwo_Swimming(0,2)",
				BirdFormActivity.class);
		solo.searchText(activityPrompt);
		solo.clickOnView(activity); // opens up the category spinner
		solo.waitForText(activityPrompt);
		solo.scrollToBottom();
		solo.waitForText(swimming);
		solo.clickOnText(swimming);

		solo.waitForText(activityLabel);
		solo.searchText(swimming);

		// able to click on the (0,2) index
		solo.pressSpinnerItem(0, 2);
		solo.waitForText(swimming);
		solo.finishOpenedActivities();
	}
	
	/**
	 * When I click on the text box for the Activity spinner, A box will open
	 * with the choices for the Activity spinner; Verify "Mating" is in the list;
	 * Click on the word "Mating"; Verify that the box goes away by looking for
	 * the word "Activity".  Verify that "Mating" was placed in the text box.
	 * Verify that the index of the word "Mating" will also trigger "Mating" to be chosen.
	 */
	public void testIndexZeroThree_Mating() {
		solo.assertCurrentActivity("testIndexZeroThree_Mating(0,3)",
				BirdFormActivity.class);
		solo.searchText(activityPrompt);
		solo.clickOnView(activity); // opens up the category spinner
		solo.waitForText(activityPrompt);
		solo.scrollToBottom();
		solo.waitForText(mating);
		solo.clickOnText(mating);

		solo.waitForText(activityLabel);
		solo.searchText(mating);

		// able to click on the (0,3) index
		solo.pressSpinnerItem(0, 3);
		solo.waitForText(mating);
		solo.finishOpenedActivities();
	}

	@Override
	public void tearDown() throws Exception {
		try {
			solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish();
		super.tearDown();
	}
}
