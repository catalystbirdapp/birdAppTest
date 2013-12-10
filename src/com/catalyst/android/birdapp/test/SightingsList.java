package com.catalyst.android.birdapp.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.MainActivity;
import com.catalyst.android.birdapp.R;
import com.catalyst.android.birdapp.RecordsActivity;
import com.catalyst.android.birdapp.database.DatabaseHandler;
import com.catalyst.android.birdapp.entities.BirdSighting;
import com.jayway.android.robotium.solo.Solo;

public class SightingsList extends ActivityInstrumentationTestCase2<MainActivity> {
	
    private Solo solo;

	public SightingsList() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		solo = new Solo(getInstrumentation(), getActivity());
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
	
	/**
	 * Creates a sighting, validates that the sighting displays in the
	 * sightings list, then clicks on the sighting and validates that it
	 * displays correctly in the edit page.
	 * 
	 * Deletes the test sighting when test is complete.
	 */
	public void test_SightingsList_EndToEnd() {
		String testCommonName = "Test Common Name";
		String changedCommonName = "Changed Common Name";
		String testScientificName = "Test Scientific Name";
		String testNotes = "Test Notes";
		String category = "Misc";
		String activity = "Swimming";
		String testDate = "";
		String testTime = "";
		String testLatitude = "";
		String testLongitude = "";
		
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton("Submit A Sighting");
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		assertTrue("on Bird Sighting Form", solo.waitForText("Bird Sighting Form"));
		
		//Get handles to all the fields on the page
		EditText addCommonName = (EditText) solo.getView(R.id.common_name_edit_text);
		EditText addScientificName = (EditText) solo.getView(R.id.scientific_name_edit_text);
		EditText addNotes = (EditText) solo.getView(R.id.notes_edit_text);
		TextView addDate = (TextView) solo.getView(R.id.date_time_edit_text);
		TextView addTime = (TextView) solo.getView(R.id.hour_edit_text);
		TextView addLatitude = (TextView) solo.getView(R.id.latitude_edit_text);
		TextView addLongitude = (TextView) solo.getView(R.id.longitude_edit_text);
		testDate = addDate.getText().toString();
		testTime = addTime.getText().toString();
		testLatitude = addLatitude.getText().toString();
		testLatitude = testLatitude.equals("") ? "0.0" : testLatitude;
		testLongitude = addLongitude.getText().toString();
		testLongitude = testLongitude.equals("") ? "0.0" : testLongitude;
		
		//Submit info for new bird sighting
		solo.clickOnText("Enter Bird Name");
		solo.enterText(addCommonName, testCommonName);
		solo.clickOnText("Enter Scientific Name");
		solo.enterText(addScientificName, testScientificName);
		solo.clickOnText("Enter Notes");
		solo.enterText(addNotes, testNotes);
		solo.clickOnText("Nest");
		solo.clickOnText(category);
		solo.clickOnText("Flying");
		solo.clickOnText(activity);
		solo.clickOnButton("Submit");
		assertTrue("Save message displayed", solo.waitForText("Your Sighting Has Been Saved"));
		solo.sleep(1000);
		
		//Now go to the SIGHTINGS LIST
		solo.goBack();
		solo.clickOnButton("Review Past Sightings");
		solo.assertCurrentActivity("Past Bird Sightings List", RecordsActivity.class);
		assertTrue("on Bird Sightings List", solo.waitForText("Bird Sightings List"));
		
		//Verify test values are present in sightings list
		assertTrue("Common Name list", solo.waitForText(testCommonName));
		assertTrue("Category in list", solo.waitForText(category));
		Date date = new Date(testDate + " " + testTime);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy  HH:mm", Locale.US);
		String formattedDate = sdf.format(date); 
		assertTrue("Date in list " + formattedDate, solo.waitForText(formattedDate));
		
		//Click on the sighting in the sightings list
		solo.clickOnText(testCommonName);
		solo.assertCurrentActivity("Edit Bird Sighting Form", BirdFormActivity.class);
		assertTrue("on Edit Bird Form", solo.waitForText("Bird Sighting Form"));
		assertTrue("Save Changes button text", solo.waitForText("Save Changes"));
		
		//Get handles to all the fields on the EDIT BIRD FORM page
		EditText editCommonName = (EditText) solo.getView(R.id.common_name_edit_text);
		EditText editScientificName = (EditText) solo.getView(R.id.scientific_name_edit_text);
		EditText editNotes = (EditText) solo.getView(R.id.notes_edit_text);
		TextView editDate = (TextView) solo.getView(R.id.date_time_edit_text);
		TextView editTime = (TextView) solo.getView(R.id.hour_edit_text);
		TextView editLatitude = (TextView) solo.getView(R.id.latitude_edit_text);
		TextView editLongitude = (TextView) solo.getView(R.id.longitude_edit_text);
		Spinner editCategory = (Spinner) solo.getView(R.id.category_drop_down);
		Spinner editActivity = (Spinner) solo.getView(R.id.bird_acivity_dropdown);
		Bundle bundle = solo.getCurrentActivity().getIntent().getExtras();
		BirdSighting birdSighting = (BirdSighting) bundle.getSerializable(BirdSighting.BIRD_SIGHTING);
		Context context = solo.getCurrentActivity().getApplicationContext();
		
		//verify all the information on the edit page
		assertEquals("Common name", testCommonName, editCommonName.getText().toString());
		assertEquals("Scientific name", testScientificName, editScientificName.getText().toString());
		assertEquals("Notes", testNotes, editNotes.getText().toString());
		assertEquals("Date", testDate, editDate.getText().toString());
		assertEquals("Time", testTime, editTime.getText().toString());
		assertEquals("Latitude", testLatitude, editLatitude.getText().toString());
		assertEquals("Longitude", testLongitude, editLongitude.getText().toString());
		assertEquals("Category", category, editCategory.getSelectedItem().toString());
		assertEquals("Activity", activity, editActivity.getSelectedItem().toString());
		
		//Edit bird sighting info and save changes
		solo.clearEditText(editCommonName);
		solo.enterText(editCommonName, changedCommonName);
		solo.clickOnButton("Save Changes");
		assertTrue("Save message displayed", solo.waitForText("Your Sighting Has Been Saved"));
		
		//Verify test values are present in SIGHTINGS LIST
		assertTrue("Common Name changed to " + changedCommonName, solo.waitForText(changedCommonName));
		assertTrue("Category in list " + category, solo.waitForText(category));
		assertTrue("Date in list " + formattedDate, solo.waitForText(formattedDate));
		
		//Clean up by deleting the test sighting
		deleteBirdSighting(birdSighting, context);
		
		solo.finishOpenedActivities();
	}
	
	private void deleteBirdSighting(BirdSighting birdSighting, Context context) {
		DatabaseHandler dbHandler = DatabaseHandler.getInstance(context);
		dbHandler.deleteBirdSighting(birdSighting.getId());
	}
	
}
	