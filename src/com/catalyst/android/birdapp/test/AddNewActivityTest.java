package com.catalyst.android.birdapp.test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.CheckBox;
import android.widget.EditText;

import com.catalyst.android.birdapp.AddNewActivity;
import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.MainActivity;
import com.catalyst.android.birdapp.R;
import com.catalyst.android.birdapp.database.DatabaseHandler;
import com.jayway.android.robotium.solo.Solo;

public class AddNewActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
    private Solo solo;

	public AddNewActivityTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() throws Exception {
		try {
			solo.finishOpenedActivities();
			solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish();
		super.tearDown();
	}
	
	public void testAAAAACloseGPSPrompt() {
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		
		//Deal with GPS prompt
		if (solo.waitForText("GPS is turned OFF")) {
			solo.clickOnButton("NO");
			assertTrue(solo.waitForText("Prompting for GPS has been disabled"));
		}
		
		solo.finishOpenedActivities();
	}
	
	public void testMenuAndErrors() {
		String errorMessageBlank = solo.getString(R.string.add_activity_empty).toString();
		String errorMessageInvalid = solo.getString(R.string.activity_alpha_error).toString();
		String testInvalidActivityName = "Test 12/10/2013";
		String errorMessageDuplicate = solo.getString(R.string.activity_already_exists_error).toString();
		String testDuplicateActivityName = "Flying";
		
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		assertTrue("on Bird Sighting Form", solo.waitForText(solo.getString(R.string.formTitle).toString()));
		
		//Navigate to Add Activity page
		solo.clickOnMenuItem(solo.getString(R.string.action_activity).toString());
		solo.assertCurrentActivity("Add New Activity page", AddNewActivity.class);
		assertTrue("on Add New Activity Form", solo.waitForText(solo.getString(R.string.addActivityTitleText).toString()));
		
		//Test Go Back button
		solo.goBack();
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		assertTrue("on Bird Sighting Form", solo.waitForText(solo.getString(R.string.formTitle).toString()));
	
		//Navigate to Add Activity page
		solo.clickOnMenuItem(solo.getString(R.string.action_activity).toString());
		solo.assertCurrentActivity("Add New Activity page", AddNewActivity.class);
		assertTrue("on Add New Activity Form", solo.waitForText(solo.getString(R.string.addActivityTitleText).toString()));
			
		//Test the settings menu button
		solo.clickOnMenuItem("Settings");
		assertTrue("Settings are displayed", solo.waitForText("Prompt to turn on GPS"));
		solo.goBack();
		
		//Test saving a blank activity name
		solo.clickOnButton(solo.getString(R.string.saveButton).toString());
		assertTrue("Error message for blank name", solo.waitForText(errorMessageBlank));
		
		//Test saving an activity name with invalid characters
		EditText activityName = (EditText) solo.getView(R.id.activityName);
		solo.clickOnText(solo.getString(R.string.addActivityNameHint).toString());
		solo.enterText(activityName, testInvalidActivityName);
		solo.clickOnButton(solo.getString(R.string.saveButton).toString());
		assertTrue("Error message for invalid name", solo.waitForText(errorMessageInvalid));
		
		//Test saving a duplicate activity name
		activityName = (EditText) solo.getView(R.id.activityName);
		solo.clearEditText(activityName);
		solo.enterText(activityName, testDuplicateActivityName);
		solo.clickOnButton(solo.getString(R.string.saveButton).toString());
		assertTrue("Error message for duplicate name", solo.waitForText(errorMessageDuplicate));
		
		solo.finishOpenedActivities();
	}
	
	public void testAddTooLongName() {
		String testTooLongActivityName = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXZY";
		String testSavedActivityName =   "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabc";
		Context context = solo.getCurrentActivity().getApplicationContext();
		
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		assertTrue("on Bird Sighting Form", solo.waitForText(solo.getString(R.string.formTitle).toString()));
		
		//Navigate to Add Activity page
		solo.clickOnMenuItem(solo.getString(R.string.action_activity).toString());
		solo.assertCurrentActivity("Add New Activity page", AddNewActivity.class);
		assertTrue("on Add New Activity Form", solo.waitForText(solo.getString(R.string.addActivityTitleText).toString()));
		
		//Try to save activity name with too many characters
		EditText activityName = (EditText) solo.getView(R.id.activityName);
		solo.clickOnText(solo.getString(R.string.addActivityNameHint).toString());
		solo.enterText(activityName, testTooLongActivityName);
		solo.clickOnButton(solo.getString(R.string.saveButton).toString());
		
		//Verify we have been automatically taken to bird form page
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		assertTrue("on Bird Sighting Form", solo.waitForText(solo.getString(R.string.formTitle).toString()));
		
		//Verify truncated activity name is available
		solo.clickOnText("Flying");
		assertFalse("Too Long Name saved", solo.waitForText(testTooLongActivityName));
		assertTrue("Too Long Name truncated", solo.waitForText(testSavedActivityName));
		
		//Clean up by deleting the test activity
		deleteActivity(testSavedActivityName, context);
		
		solo.finishOpenedActivities();
	}
	
	public void testAddGoodName() {
		String testActivityName = "Test Activity Name";
		Context context = solo.getCurrentActivity().getApplicationContext();
		
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		assertTrue("on Bird Sighting Form", solo.waitForText(solo.getString(R.string.formTitle).toString()));
		
		//Navigate to Add Activity page
		solo.clickOnMenuItem(solo.getString(R.string.action_activity).toString());
		solo.assertCurrentActivity("Add New Activity page", AddNewActivity.class);
		assertTrue("on Add New Activity Form", solo.waitForText(solo.getString(R.string.addActivityTitleText).toString()));
		
		//Try to save valid activity name
		EditText activityName = (EditText) solo.getView(R.id.activityName);
		solo.clickOnText(solo.getString(R.string.addActivityNameHint).toString());
		solo.enterText(activityName, testActivityName);
		solo.clickOnButton(solo.getString(R.string.saveButton).toString());
		
		//Verify we have been automatically taken to bird form page
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		assertTrue("on Bird Sighting Form", solo.waitForText(solo.getString(R.string.formTitle).toString()));
		
		//Verify valid activity name is available
		solo.clickOnText("Flying");
		assertTrue("Activity Name", solo.waitForText(testActivityName));
		
		//Clean up by deleting the test activity
		deleteActivity(testActivityName, context);
		
		solo.finishOpenedActivities();
	}
	
	public void testAddAnotherCheckbox() {
		String testFirstActivityName = "First Activity Name";
		String testSecondActivityName = "Second Activity Name";
		Context context = solo.getCurrentActivity().getApplicationContext();
		
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		assertTrue("on Bird Sighting Form", solo.waitForText(solo.getString(R.string.formTitle).toString()));
		
		//Navigate to Add Activity page
		solo.clickOnMenuItem(solo.getString(R.string.action_activity).toString());
		solo.assertCurrentActivity("Add New Activity page", AddNewActivity.class);
		assertTrue("on Add New Activity Form", solo.waitForText(solo.getString(R.string.addActivityTitleText).toString()));
		
		//Save first activity
		EditText activityName = (EditText) solo.getView(R.id.activityName);
		CheckBox addAnotherCheckbox = (CheckBox) solo.getView(R.id.add_another_button);
		solo.clickOnView(addAnotherCheckbox);
		solo.clickOnText(solo.getString(R.string.addActivityNameHint).toString());
		solo.enterText(activityName, testFirstActivityName);
		solo.clickOnButton(solo.getString(R.string.saveButton).toString());
		
		//Save second activity
		assertTrue("on Add New Activity Form", solo.waitForText(solo.getString(R.string.addActivityTitleText).toString()));
		solo.clickOnText(solo.getString(R.string.addActivityNameHint).toString());
		solo.enterText(activityName, testSecondActivityName);
		solo.clickOnButton(solo.getString(R.string.saveButton).toString());
		
		//Verify we have been automatically taken to bird form page
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		assertTrue("on Bird Sighting Form", solo.waitForText(solo.getString(R.string.formTitle).toString()));
		
		//Verify truncated activity name is available
		solo.clickOnText("Flying");
		assertTrue("First Activity Name", solo.waitForText(testFirstActivityName));
		assertTrue("Second Activity Name", solo.waitForText(testSecondActivityName));
		
		//Clean up by deleting the test activity
		deleteActivity(testFirstActivityName, context);
		deleteActivity(testSecondActivityName, context);
		
		solo.finishOpenedActivities();
	}
	
	private void deleteActivity(String activityName, Context context) {
		DatabaseHandler dbHandler = DatabaseHandler.getInstance(context);
		dbHandler.deleteActivity(activityName);
	}
	
}
	