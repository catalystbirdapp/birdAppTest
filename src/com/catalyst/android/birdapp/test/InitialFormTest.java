package com.catalyst.android.birdapp.test;

import junit.framework.Assert;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.google.android.gms.internal.by;
import com.jayway.android.robotium.solo.Solo;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

/**
 * @author abarendt Robotium test cases for com.catalyst.android.birdapp
 *         BirdFormActivity Will be used for regression testing
 */

public class InitialFormTest extends
		ActivityInstrumentationTestCase2<BirdFormActivity> {

	private String COMMON_NAME = "CommonBird";
	private String NOT_COMMON_NAME = "RareBird";
	private String SCIENTIFIC_NAME = "ScientificName";
	private String NOT_SCIENTIFIC_NAME = "NotScientificName";
	private String NOTES = "These are the notes.";
	private String NOT_NOTES = "These are not the notes.";
	// private String SYSTEM_TIME= (String) System.currentTimeMillis();

	private Solo solo;

	public InitialFormTest() {
		/*
		 * super("com.catalyst.android.birdapp", BirdFormActivity.class); cannot
		 * find what this depricated code has been replaced by, have found this
		 * in several tuts even 2013 tuts.
		 */

		super(BirdFormActivity.class);

	}

	protected void setUp() throws Exception {
		super.setUp();
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		solo = new Solo(getInstrumentation(), getActivity());
	}
  @Override
  public void tearDown() throws Exception{
	  try{
		  solo.finalize();
	  }catch (Throwable e){
		  e.printStackTrace();
	  }
	  getActivity().finish();
	  super.tearDown();
  }
	/*
	 * public void testDate(){
	 * solo.assertCurrentActivity("BirdFormActivity testDate",
	 * BirdFormActivity.class);
	 * Assert.assertTrue(solo.getEditText(3).getText().toString
	 * ().equals(SYSTEM_TIME)) }
	 * 
	 * public void testTime(){
	 * solo.assertCurrentActivity("BirdFormActivity testTime",
	 * BirdFormActivity.class);
	 * 
	 * }
	 */

	public void testAutofill_GPSOff_LeaveOff() {
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		solo = new Solo(getInstrumentation(), getActivity());
		
		solo.assertCurrentActivity("GPS is turned off, don't turn it on",
				BirdFormActivity.class);
		assertTrue(solo.waitForText("Bird Sighting Form"));

		solo.clickOnCheckBox(0);

		solo.sleep(2000);

		solo.clickOnText("OK");

		assertTrue(solo.waitForText("GPS is turned OFF"));
		solo.clickOnButton("NO");
		assertTrue(solo.waitForText("Coordinates not available"));
	}

	public void testAutoFill_GPSOff_TurnOn(){
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		solo = new Solo(getInstrumentation(), getActivity());
		
		solo.assertCurrentActivity("GPS is turned off, don't turn it on",
				BirdFormActivity.class);
		assertTrue(solo.waitForText("Bird Sighting Form"));

		solo.clickOnCheckBox(0);

		solo.sleep(2000);

		solo.clickOnText("OK");

		assertTrue(solo.waitForText("GPS is turned OFF"));
		solo.clickOnButton("YES");

	//below GPS text and check box is on Anna's phone
		solo.sleep(1000);
	assertTrue(solo.searchText("Use GPS satellites"));
	//solo.clickOnCheckBox(1);
	//solo.goBack();
	//below code is poor coding, need variables...
	//assertTrue(solo.waitForText("45.51"));
	//assertTrue(solo.waitForText("-122.83"));
}

	/**
	 * Tests that name entered into common name field returns the entered common
	 * name True test
	 */
	public void testActivity_commonNameTextField() {
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		solo = new Solo(getInstrumentation(), getActivity());
		
		solo.assertCurrentActivity("Check first activity",
				BirdFormActivity.class);
		solo.enterText(0, COMMON_NAME);
		Assert.assertTrue(solo.getEditText(0).getText().toString()
				.equals(COMMON_NAME));
		Assert.assertFalse(solo.getEditText(0).getText().toString()
				.equals(NOT_COMMON_NAME));
	};

	/**
	 * Tests that name(s) entered into scientific name field returns the entered
	 * scientific name True test
	 */
	public void testActivity_ScientificName() {
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		solo = new Solo(getInstrumentation(), getActivity());
		
		solo.assertCurrentActivity("Check first activity",
				BirdFormActivity.class);
		solo.enterText(1, SCIENTIFIC_NAME);
		Assert.assertTrue(solo.getEditText(1).getText().toString()
				.equals(SCIENTIFIC_NAME));
		Assert.assertFalse(solo.getEditText(1).getText().toString()
				.equals(NOT_SCIENTIFIC_NAME));
	}

	/**
	 * Tests that text entered into the notes field returns the entered notes
	 * True test
	 */
	public void testActivity_Notes() {
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		solo = new Solo(getInstrumentation(), getActivity());
		
		solo.assertCurrentActivity("Check first activity",
				BirdFormActivity.class);
		solo.enterText(2, NOTES);
		Assert.assertTrue(solo.getEditText(2).getText().toString()
				.equals(NOTES));
		Assert.assertFalse(solo.getEditText(2).getText().toString()
				.equals(NOT_NOTES));
	}

	/**
	 * After the submit button has been triggered, any user-entered text will be
	 * cleared from the Common Name field.
	 */
	public void testAfterSubmit_CommonNameFieldReset_True() {
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		solo = new Solo(getInstrumentation(), getActivity());
		
		solo.assertCurrentActivity("Check first activity",
				BirdFormActivity.class);
		solo.enterText(0, COMMON_NAME);
		Assert.assertTrue(solo.getEditText(0).getText().toString()
				.equals(COMMON_NAME));

		solo.clickOnButton("Submit");
		solo.waitForText(solo
				.getString(com.catalyst.android.birdapp.R.string.birdNameHint));

		Assert.assertTrue(solo.getEditText(0).getText().toString().equals(""));
	}

	/**
	 * After the submit button has been triggered, any user-entered text will be
	 * cleared from the Scientific Name field.
	 */
	public void testAfterSubmit_ScientificFieldReset_True() {
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		solo = new Solo(getInstrumentation(), getActivity());
		
		solo.assertCurrentActivity("Check Scientific Name Submit",
				BirdFormActivity.class);
		solo.enterText(1, SCIENTIFIC_NAME);

		Assert.assertTrue(solo.getEditText(1).getText().toString()
				.equals(SCIENTIFIC_NAME));

		solo.clickOnButton("Submit");

		solo.waitForText(solo
				.getString(com.catalyst.android.birdapp.R.string.scientificNameHint));
		Assert.assertTrue(solo.getEditText(1).getText().toString().equals(""));
	}

	/**
	 * After the submit button has been triggered, any user-entered text will be
	 * cleared from the Notes field.
	 */
	public void testAfterSubmit_NotesReset_True() {
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		solo = new Solo(getInstrumentation(), getActivity());
		
		solo.assertCurrentActivity("Check Notes Submit", BirdFormActivity.class);
		solo.enterText(2, NOTES);

		Assert.assertTrue(solo.getEditText(2).getText().toString()
				.equals(NOTES));

		solo.clickOnButton("Submit");

		solo.waitForText(solo
				.getString(com.catalyst.android.birdapp.R.string.notesHint));
		Assert.assertTrue(solo.getEditText(2).getText().toString().equals(""));

	}

	/**
	 * This test combines all of the "happy path" functions of the above tests.
	 */
	// There was a problem with Eclipse "hanging up" while running the mini
	// tests.
	// This seems to avoid the problem. Don't know if it is based on the app
	// submitting
	// so many times in a row or ???
	
	
	public void testBigTest() {
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		solo = new Solo(getInstrumentation(), getActivity()); 
		
		solo.assertCurrentActivity("Check first activity",
				BirdFormActivity.class);
		// common name field
		solo.enterText(0, COMMON_NAME);
		Assert.assertTrue(solo.getEditText(0).getText().toString()
				.equals(COMMON_NAME));
		Assert.assertFalse(solo.getEditText(1).getText().toString()
				.equals(NOT_SCIENTIFIC_NAME));

		solo.enterText(1, SCIENTIFIC_NAME);
		Assert.assertTrue(solo.getEditText(1).getText().toString()
				.equals(SCIENTIFIC_NAME));
		Assert.assertFalse(solo.getEditText(1).getText().toString()
				.equals(NOT_SCIENTIFIC_NAME));

		solo.enterText(2, NOTES);
		Assert.assertTrue(solo.getEditText(2).getText().toString()
				.equals(NOTES));
		Assert.assertFalse(solo.getEditText(2).getText().toString()
				.equals(NOT_NOTES));

		solo.clickOnButton("Submit");

		solo.waitForText(solo
				.getString(com.catalyst.android.birdapp.R.string.birdNameHint));

		Assert.assertTrue(solo.getEditText(0).getText().toString().equals(""));

		// Scientific name field

		solo.waitForText(solo
				.getString(com.catalyst.android.birdapp.R.string.scientificNameHint));

		Assert.assertTrue(solo.getEditText(1).getText().toString().equals(""));

		// notes field

		solo.waitForText(solo
				.getString(com.catalyst.android.birdapp.R.string.notesHint));
		Assert.assertTrue(solo.getEditText(2).getText().toString().equals(""));

	}

}
