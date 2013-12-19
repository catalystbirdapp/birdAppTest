package com.catalyst.android.birdapp.test;

import android.content.Context;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.Spinner;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.MainActivity;
import com.catalyst.android.birdapp.R;
import com.catalyst.android.birdapp.ViewPastSightingsActivity;
import com.catalyst.android.birdapp.database.DatabaseHandler;
import com.catalyst.android.birdapp.entities.BirdSighting;
import com.jayway.android.robotium.solo.Solo;

public class NewBirdSightingTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
    private Solo solo;

	public NewBirdSightingTest() {
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
	
	public void testCommonNameField() {
		String warningMessageBlank = "Bird Name";
		String errorMessageInvalid = solo.getString(R.string.bird_name_alpha_error).toString();
		String testInvalidName = "Test 12/10/2013";
		String testTooLongName = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXZY";
		String testSavedName =   "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabc";
		
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		assertTrue("on Bird Sighting Form", solo.waitForText(solo.getString(R.string.formTitle).toString()));
		
		//Test saving a blank common name
		solo.clickOnButton(solo.getString(R.string.submitButtonText).toString());
		assertTrue("Warning alert for blank common name", solo.waitForText(warningMessageBlank));
		solo.clickOnButton("NO");
		solo.scrollUp();
		
		//Test saving a common name with invalid characters
		EditText commonName = (EditText) solo.getView(R.id.common_name_edit_text);
		solo.clickOnText(solo.getString(R.string.birdNameHint).toString());
		solo.enterText(commonName, testInvalidName);
		solo.clickOnButton(solo.getString(R.string.submitButtonText).toString());
		solo.clickOnButton("YES");
		solo.scrollUp();
		assertTrue("Error message for invalid common name", solo.waitForText(errorMessageInvalid));
		
		//Test saving a common name that is too long
		solo.clearEditText(commonName);
		solo.clickOnText(solo.getString(R.string.birdNameHint).toString());
		solo.enterText(commonName, testTooLongName);
		solo.clickOnButton(solo.getString(R.string.submitButtonText).toString());
		solo.clickOnButton("YES");
		solo.scrollUp();
		assertTrue("Save message displayed", solo.waitForText(solo.getString(R.string.sightingAddedBlankName).toString()));
		
		//Now go to the SIGHTINGS LIST and verify truncated common name saved
		solo.goBack();
		solo.clickOnButton(solo.getString(R.string.reviewRecords).toString());
		solo.assertCurrentActivity("Past Bird Sightings List", ViewPastSightingsActivity.class);
		assertTrue("Common Name", solo.waitForText(testSavedName));
		
		//Go to edit bird form and verify truncated common name is displayed
		solo.clickOnText(testSavedName);
		solo.assertCurrentActivity("Edit Bird Sighting Form", BirdFormActivity.class);
		assertTrue("on Edit Bird Form", solo.waitForText("Bird Sighting Form"));
		EditText editCommonName = (EditText) solo.getView(R.id.common_name_edit_text);
		assertEquals("Edit Common name", testSavedName, editCommonName.getText().toString());
		
		//Clean up by deleting the test sighting
		Bundle bundle = solo.getCurrentActivity().getIntent().getExtras();
		BirdSighting birdSighting = (BirdSighting) bundle.getSerializable(BirdSighting.BIRD_SIGHTING);
		deleteBirdSighting(birdSighting, solo.getCurrentActivity().getApplicationContext());
		
		solo.finishOpenedActivities();
	}
	
	public void testScientificNameField() {
		String warningMessageBlank = "Scientific Name";
		String errorMessageInvalid = solo.getString(R.string.scientific_name_alpha_error).toString();
		String testInvalidName = "Test 12/10/2013";
		String testTooLongName = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXZY";
		String testSavedName =   "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabc";
		String testCommonName = "Test for Scientific Name";
		
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		assertTrue("on Bird Sighting Form", solo.waitForText(solo.getString(R.string.formTitle).toString()));
		
		//Test saving a blank scientific name
		solo.clickOnButton(solo.getString(R.string.submitButtonText).toString());
		assertTrue("Warning alert for blank scientific name", solo.waitForText(warningMessageBlank));
		solo.clickOnButton("NO");
		solo.scrollUp();
		
		//Test saving a scientific name with invalid characters
		EditText scientificName = (EditText) solo.getView(R.id.scientific_name_edit_text);
		EditText commonName = (EditText) solo.getView(R.id.common_name_edit_text);
		solo.clickOnText(solo.getString(R.string.scientificNameHint).toString());
		solo.enterText(scientificName, testInvalidName);
		solo.clickOnButton(solo.getString(R.string.submitButtonText).toString());
		solo.clickOnButton("YES");
		solo.scrollUp();
		assertTrue("Error message for invalid scientific name", solo.waitForText(errorMessageInvalid));
		
		//Test saving a scientific name that is too long
		solo.clearEditText(scientificName);
		solo.clickOnText(solo.getString(R.string.scientificNameHint).toString());
		solo.enterText(scientificName, testTooLongName);
		solo.enterText(commonName, testCommonName);
		solo.clickOnButton(solo.getString(R.string.submitButtonText).toString());
		solo.clickOnButton("YES");
		solo.scrollUp();
		assertTrue("Save message displayed", solo.waitForText(solo.getString(R.string.sightingAddedBlankName).toString()));
		
		//Now go to the SIGHTINGS LIST
		solo.goBack();
		solo.clickOnButton(solo.getString(R.string.reviewRecords).toString());
		solo.assertCurrentActivity("Past Bird Sightings List", ViewPastSightingsActivity.class);
		
		//Go to edit bird form and verify truncated scientific name is displayed
		solo.clickOnText(testCommonName);
		solo.assertCurrentActivity("Edit Bird Sighting Form", BirdFormActivity.class);
		assertTrue("on Edit Bird Form", solo.waitForText("Bird Sighting Form"));
		EditText editScientificName = (EditText) solo.getView(R.id.scientific_name_edit_text);
		assertEquals("Edit Scientific name", testSavedName, editScientificName.getText().toString());
		
		//Clean up by deleting the test sighting
		Bundle bundle = solo.getCurrentActivity().getIntent().getExtras();
		BirdSighting birdSighting = (BirdSighting) bundle.getSerializable(BirdSighting.BIRD_SIGHTING);
		deleteBirdSighting(birdSighting, solo.getCurrentActivity().getApplicationContext());
		
		solo.finishOpenedActivities();
	}
	
	public void testNotesField() {
		String warningMessageBlank = "Notes";
		String testTooLongName = "Four score and seven years ago our fathers brought forth on this continent, a new nation, conceived in Liberty, and dedicated to the proposition that all men are created equal.  Now we are engaged in a great civil war, testing whether that nation, or anynation so conceived ";
		String testSavedName =   "Four score and seven years ago our fathers brought forth on this continent, a new nation, conceived in Liberty, and dedicated to the proposition that all men are created equal.  Now we are engaged in a great civil war, testing whether that nation, or anyn";
		String testCommonName = "Test for Notes";
		
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		assertTrue("on Bird Sighting Form", solo.waitForText(solo.getString(R.string.formTitle).toString()));
		
		//Test saving a blank notes
		solo.clickOnButton(solo.getString(R.string.submitButtonText).toString());
		assertTrue("Warning alert for blank notes", solo.waitForText(warningMessageBlank));
		solo.clickOnButton("NO");
		solo.scrollUp();
		
		//Test saving a note that is too long
		EditText notes = (EditText) solo.getView(R.id.notes_edit_text);
		EditText commonName = (EditText) solo.getView(R.id.common_name_edit_text);
		solo.clickOnText(solo.getString(R.string.notesHint).toString());
		solo.enterText(notes, testTooLongName);
		solo.enterText(commonName, testCommonName);
		solo.clickOnButton(solo.getString(R.string.submitButtonText).toString());
		solo.clickOnButton("YES");
		solo.scrollUp();
		assertTrue("Save message displayed", solo.waitForText(solo.getString(R.string.sightingAddedBlankName).toString()));
		
		//Now go to the SIGHTINGS LIST
		solo.goBack();
		solo.clickOnButton(solo.getString(R.string.reviewRecords).toString());
		solo.assertCurrentActivity("Past Bird Sightings List", ViewPastSightingsActivity.class);
		
		//Go to edit bird form and verify truncated notes are displayed
		solo.clickOnText(testCommonName);
		solo.assertCurrentActivity("Edit Bird Sighting Form", BirdFormActivity.class);
		assertTrue("on Edit Bird Form", solo.waitForText("Bird Sighting Form"));
		EditText editNotes = (EditText) solo.getView(R.id.notes_edit_text);
		assertEquals("Edit notes", testSavedName, editNotes.getText().toString());
		
		//Clean up by deleting the test sighting
		Bundle bundle = solo.getCurrentActivity().getIntent().getExtras();
		BirdSighting birdSighting = (BirdSighting) bundle.getSerializable(BirdSighting.BIRD_SIGHTING);
		deleteBirdSighting(birdSighting, solo.getCurrentActivity().getApplicationContext());
		
		solo.finishOpenedActivities();
	}
	
	public void testCategoryAndActivity() {
		String initialCategory = "Nest";
		String newCategory = "Sighting";
		String initialActivity = "Flying";
		String newActivity = "Swimming";
		String testCommonName = "Test for Category and Activity";
		
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		assertTrue("on Bird Sighting Form", solo.waitForText(solo.getString(R.string.formTitle).toString()));
		
		//Test clicking on category
		EditText commonName = (EditText) solo.getView(R.id.common_name_edit_text);
		Spinner category = (Spinner) solo.getView(R.id.category_drop_down);
		assertEquals("Initial Category", initialCategory, category.getSelectedItem().toString());
		solo.clickOnView(category);
		assertTrue("Category list opened", solo.waitForText(solo.getString(R.string.categoryPrompt).toString()));
		solo.clickOnText(newCategory);
		assertEquals("New Category", newCategory, category.getSelectedItem().toString());
		assertTrue("Category spinner has focus", category.hasFocus());
		
		//Test clicking on activity
		Spinner activity = (Spinner) solo.getView(R.id.bird_acivity_dropdown);
		assertEquals("Initial Activity", initialActivity, activity.getSelectedItem().toString());
		solo.clickOnView(activity);
		assertTrue("Activity list opened", solo.waitForText(solo.getString(R.string.activityPrompt).toString()));
		solo.clickOnText(newActivity);
		assertEquals("New Activity", newActivity, activity.getSelectedItem().toString());
		assertTrue("Activity spinner has focus", activity.hasFocus());
		
		//Test saving a sighting
		solo.enterText(commonName, testCommonName);
		solo.clickOnButton(solo.getString(R.string.submitButtonText).toString());
		solo.clickOnButton("YES");
		solo.scrollUp();
		assertTrue("Save message displayed", solo.waitForText(solo.getString(R.string.sightingAddedBlankName).toString()));
		
		//Now go to the SIGHTINGS LIST
		solo.goBack();
		solo.clickOnButton(solo.getString(R.string.reviewRecords).toString());
		solo.assertCurrentActivity("Past Bird Sightings List", ViewPastSightingsActivity.class);
		
		//Go to edit bird form and verify category and activity are displayed
		solo.clickOnText(testCommonName);
		solo.assertCurrentActivity("Edit Bird Sighting Form", BirdFormActivity.class);
		assertTrue("on Edit Bird Form", solo.waitForText("Bird Sighting Form"));
		Spinner editCategory = (Spinner) solo.getView(R.id.category_drop_down);
		Spinner editActivity = (Spinner) solo.getView(R.id.bird_acivity_dropdown);
		assertEquals("Edit category", newCategory, editCategory.getSelectedItem().toString());
		assertEquals("Edit activity", newActivity, editActivity.getSelectedItem().toString());
		
		//Clean up by deleting the test sighting
		Bundle bundle = solo.getCurrentActivity().getIntent().getExtras();
		BirdSighting birdSighting = (BirdSighting) bundle.getSerializable(BirdSighting.BIRD_SIGHTING);
		deleteBirdSighting(birdSighting, solo.getCurrentActivity().getApplicationContext());
		
		solo.finishOpenedActivities();
	}
	
	private void deleteBirdSighting(BirdSighting birdSighting, Context context) {
		DatabaseHandler dbHandler = DatabaseHandler.getInstance(context);
		dbHandler.deleteBirdSighting(birdSighting.getId());
	}
	
}
	