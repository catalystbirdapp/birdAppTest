package com.catalyst.android.birdapp.test;

import android.test.ActivityInstrumentationTestCase2;
import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R.string;
import com.jayway.android.robotium.solo.Solo;

/**
 * @author abarendt To run these tests, the application needs to be open on the
 *         phone, so the splash screen can be avoided.
 */
public class Spinner_AddActivity extends
		ActivityInstrumentationTestCase2<BirdFormActivity> {

	private String ACTIVITY_ONE = "ActivityOne";
	private String ACTIVITY_TWO = "ActivityTwo";

	private Solo solo;

	private int idActivityLabelText;
	private int idAddActivityAlertTitle;
	private int idAddActivityMenu;
	private int idBirdSightingFormText;
	private int idSaveButtonText;

	private String activityLabelText;
	private String addActivityAlertTitle;
	private String addActivityMenu;
	private String birdSightingFormText;
	private String saveButtonText;

	public Spinner_AddActivity() {
		super(BirdFormActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		solo = new Solo(getInstrumentation(), getActivity());

		idActivityLabelText = string.activityText;
		idAddActivityAlertTitle = string.addActivityPageTitle;
		idAddActivityMenu = string.action_activity;
		idBirdSightingFormText = string.formTitle;
		idSaveButtonText = string.saveButton;

		activityLabelText = getActivity().getResources().getString(
				idActivityLabelText);// "Activity: "
		addActivityAlertTitle = getActivity().getResources().getString(
				idAddActivityAlertTitle);// "Add an Activity"
		addActivityMenu = getActivity().getResources().getString(
				idAddActivityMenu);// "Add Activity"
		birdSightingFormText = getActivity().getResources().getString(
				idBirdSightingFormText);// "Bird Sighting Form"
		saveButtonText = getActivity().getResources().getString(
				idSaveButtonText);
	}

	/**
	 * When I click on the phone menu; the menu will pop up. I can click on
	 * "Add Activity"; and an alert box to add an activity will open up. After I
	 * type in an activity name and click the save button, I will be returned to
	 * the Submit a Sighting form. When I click on the activity spinner, I will
	 * be able to see that the new activity is saved to the activity spinner.
	 */
	public void testAddOneActivity() {
		solo.assertCurrentActivity("addOneActivity", BirdFormActivity.class);

		solo.sendKey(Solo.MENU);
		solo.clickOnText(addActivityMenu);

		assertTrue(solo.waitForText(addActivityAlertTitle));

		solo.enterText(0, ACTIVITY_ONE);
		solo.clickOnButton(saveButtonText);

		solo.waitForText(birdSightingFormText);
		solo.scrollDown();
		assertTrue(solo.searchText(activityLabelText));

		solo.pressSpinnerItem(0, 1);
		solo.searchText(ACTIVITY_ONE);

		solo.finishOpenedActivities();
	}

	/**
	 * * When I click on the phone menu; the menu will pop up. I can click on
	 * "Add Activity"; and an alert box to add an activity will open up. After I
	 * type in an activity name, I can checkmark the checkbox to add more and
	 * click the save button. After clicking the save button, I will remain on
	 * the activity alert box to enter another activity. After I am finished
	 * entering the second activity, I can click the save button, and be
	 * returned to the Submit Sighting form, where I can open the activity
	 * spinner and see that both activities are saved to the Activity List.
	 */
	public void testAddTwoActivities() {
		solo.assertCurrentActivity("addTwoActivities", BirdFormActivity.class);

		solo.sendKey(Solo.MENU);
		solo.clickOnText(addActivityMenu);

		assertTrue(solo.waitForText(addActivityAlertTitle));

		solo.enterText(0, ACTIVITY_ONE);
		if (solo.isCheckBoxChecked(0)) {
			solo.clickOnCheckBox(0);
		} else {
			solo.clickOnCheckBox(0);
		}
		solo.clickOnButton(saveButtonText);
		solo.enterText(0, ACTIVITY_TWO);
		solo.clickOnButton(saveButtonText);
		solo.waitForText(birdSightingFormText);
		solo.scrollDown();
		assertTrue(solo.searchText(activityLabelText));

		solo.pressSpinnerItem(0, 1);
		solo.waitForText(ACTIVITY_ONE);
		solo.waitForText(ACTIVITY_TWO);

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
