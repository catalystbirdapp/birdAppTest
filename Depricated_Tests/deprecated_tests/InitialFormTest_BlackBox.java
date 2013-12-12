package deprecated_tests;

import junit.framework.Assert;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.jayway.android.robotium.solo.Solo;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

/**
 * @author abarendt Robotium test cases for com.catalyst.android.birdapp
 *         BirdFormActivity. Will be used for regression testing
 */

public class InitialFormTest_BlackBox extends
		ActivityInstrumentationTestCase2<BirdFormActivity> {

	private String COMMON_NAME = "CommonBird";
	private String NOT_COMMON_NAME = "RareBird";
	private String SCIENTIFIC_NAME = "ScientificName";
	private String NOT_SCIENTIFIC_NAME = "NotScientificName";
	private String NOTES = "These are the notes.";
	private String NOT_NOTES = "These are not the notes.";
	private String NEW_ACTIVITY = "NewActivity";
	private String FIRST_NEW_ACTIVITY = "FirstNewActivity";
	private String SECOND_NEW_ACTIVITY = "SecondNewActivity";

	// private String SYSTEM_TIME= (String) System.currentTimeMillis();

	private Solo solo;

	public InitialFormTest_BlackBox() {
		super(BirdFormActivity.class);

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
	 * Black box test to verify that the soft keyboard appears when the common
	 * bird text field is tapped. This may not be the best test method when
	 * other languages are incorporated.
	 */
	public void test_KeyboardAppear_BirdName_English() {
		solo.assertCurrentActivity("Soft Keyboard_BirdName",
				BirdFormActivity.class);
		solo.clickOnEditText(0);
		solo.waitForText("q");
		solo.searchText("z");
		solo.goBack();
		solo.finishOpenedActivities();
		
	}

	/**
	 * Black box test to verify that the soft keyboard appears when the
	 * scientific name text field is tapped. This may not be the best test
	 * method when other languages are incorporated.
	 */
	public void test_KeyboardAppear_ScientificName_English() {
		solo.assertCurrentActivity("Soft Keyboard_ScientificName",
				BirdFormActivity.class);
		solo.clickOnEditText(1);
		solo.waitForText("q");
		solo.searchText("z");
		solo.goBack();
		solo.finishOpenedActivities();
	}

	/**
	 * Black box test to verify that the soft keyboard appears when the notes
	 * text field is tapped. This may not be the best test method when other
	 * languages are incorporated.
	 */
	public void test_KeyboardAppear_Notes_English() {
		solo.assertCurrentActivity("Soft Keyboard_Notes",
				BirdFormActivity.class);
		solo.clickOnEditText(2);
		solo.waitForText("q");
		solo.searchText("z");
		solo.goBack();
		solo.finishOpenedActivities();
	}

	/**
	 * Black box test. Tests that the toast (a small pop-up box) is visible with
	 * the correct text.
	 */
	public void test_Toast() {
		solo.assertCurrentActivity("Testing the 'toast'.",
				BirdFormActivity.class);
		solo.enterText(0, COMMON_NAME);
		Assert.assertTrue(solo.getEditText(0).getText().toString()
				.equals(COMMON_NAME));
		solo.scrollDown();
		solo.clickOnButton("Submit");
		assertTrue(solo.searchText("Added Bird Sighting :" + COMMON_NAME));
		solo.finishOpenedActivities();
	}

	/**
	 * Tests that text entered into the notes field returns the entered notes
	 * True test
	 */
	public void test_NotesTextField() {

		solo.assertCurrentActivity("Check first activity",
				BirdFormActivity.class);
		solo.enterText(2, NOTES);
		Assert.assertTrue(solo.getEditText(2).getText().toString()
				.equals(NOTES));
		Assert.assertFalse(solo.getEditText(2).getText().toString()
				.equals(NOT_NOTES));
		solo.finishOpenedActivities();
	}

	/**
	 * After the submit button has been triggered, any user-entered text will be
	 * cleared from the Notes field.
	 */
	public void test_AfterSubmit_NotesTextFieldReset() {

		solo.assertCurrentActivity("Check Notes Submit", BirdFormActivity.class);
		solo.enterText(2, NOTES);

		Assert.assertTrue(solo.getEditText(2).getText().toString()
				.equals(NOTES));

		solo.clickOnButton("Submit");

		solo.waitForText(solo
				.getString(com.catalyst.android.birdapp.R.string.notesHint));
		Assert.assertTrue(solo.getEditText(2).getText().toString().equals(""));
		solo.finishOpenedActivities();
	}

	/**
	 * Tests that name entered into common name field returns the entered common
	 * name True test
	 */
	public void test_CommonNameTextField() {

		solo.assertCurrentActivity("Check first activity",
				BirdFormActivity.class);
		solo.enterText(0, COMMON_NAME);
		Assert.assertTrue(solo.getEditText(0).getText().toString()
				.equals(COMMON_NAME));
		Assert.assertFalse(solo.getEditText(0).getText().toString()
				.equals(NOT_COMMON_NAME));
	};

	/**
	 * After the submit button has been triggered, any user-entered text will be
	 * cleared from the Common Name field.
	 */
	public void test_AfterSubmit_CommonNameFieldReset() {
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
		solo.finishOpenedActivities();
	}

	/**
	 * Tests that name(s) entered into scientific name field returns the entered
	 * scientific name True test
	 */
	public void test_ScientificNameTextField() {
		solo = new Solo(getInstrumentation(), getActivity());

		solo.assertCurrentActivity("Check first activity",
				BirdFormActivity.class);
		solo.enterText(1, SCIENTIFIC_NAME);
		Assert.assertTrue(solo.getEditText(1).getText().toString()
				.equals(SCIENTIFIC_NAME));
		Assert.assertFalse(solo.getEditText(1).getText().toString()
				.equals(NOT_SCIENTIFIC_NAME));
		solo.finishOpenedActivities();
	}

	/**
	 * After the submit button has been triggered, any user-entered text will be
	 * cleared from the Scientific Name field.
	 */
	public void test_AfterSubmit_ScientificNameTextFieldReset() {

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
		solo.finishOpenedActivities();
	}

	/**
	 * Testing the activity spinner Use Android 2.2 Activity SpinnerView has at
	 * least 3
	 * 
	 */
	// will this test work on the very first view as well as after adding a new
	// activity?
	public void test_ActivitySpinner() {

		solo = new Solo(getInstrumentation(), getActivity());

		solo.scrollDown();
		assertTrue(solo.searchText("Activity:"));
		assertTrue(solo.searchText("Flying"));
		solo.pressSpinnerItem(0, 0);
		assertTrue(solo.searchText("Flying"));
		solo.pressSpinnerItem(0, 1);
		assertTrue(solo.searchText("Hunting"));
		solo.pressSpinnerItem(0, 2);
		assertTrue(solo.waitForText("Mating"));
		solo.pressSpinnerItem(0, -1);
		assertTrue(solo.waitForText("Swimming"));

		solo.finishOpenedActivities();
	}

	/**
	 * Testing the Category spinner Use Android 2.2 Category SpinnerView has at
	 * least
	 * 
	 * Call Solo.pressSpinneritem(1,2) Expect "Misc" to be on text line Call
	 * Solo.pressSpinneritem(1,-1) Expect "Sighting" to be on text line
	 */
	public void test_CategorySpinner() {
		solo = new Solo(getInstrumentation(), getActivity());

		solo.scrollDown();
		assertTrue(solo.searchText("Category:"));
		assertTrue(solo.waitForText("Nest"));
		solo.pressSpinnerItem(1, 2);
		assertTrue(solo.waitForText("Misc"));
		solo.pressSpinnerItem(1, -1);
		assertTrue(solo.waitForText("Sighting"));

		solo.finishOpenedActivities();
	}

	/**
	 * Testing the activity spinner Use Android 2.2 Adding activity to the
	 * activity spinner
	 * 
	 * On further research: This spans two applications
	 */
	public void test_AddActivity() {
		solo = new Solo(getInstrumentation(), getActivity());
		// to access the phone's menu and click on "
		solo.sendKey(Solo.MENU);

		solo.clickOnText("Add Activity");

		// to write to the "Add an Activity" form
		assertTrue(solo.waitForText("Add an Activity"));
		solo.enterText(0, NEW_ACTIVITY);

		// save the text
		solo.clickOnButton("Save");
		solo.waitForText("Bird Sighting Form");
		solo.scrollDown();
		assertTrue(solo.searchText("Activity:"));
		solo.pressSpinnerItem(0, 1);
		solo.searchText(NEW_ACTIVITY);
		solo.finishOpenedActivities();
	}

	public void test_AddAnotherActivity() {
		solo = new Solo(getInstrumentation(), getActivity());
		solo.sendKey(Solo.MENU);
		solo.clickOnText("Add Activity");

		// to write to the "Add an Activity" form
		assertTrue(solo.waitForText("Add an Activity"));
		solo.enterText(0, FIRST_NEW_ACTIVITY);
		// remove keypad
		// solo.goBack();
		// does each screen start re-indexing or would this really be
		// checkbox(1)??

		solo.clickOnCheckBox(0);
		solo.clickOnButton("Save");
		solo.enterText(0, SECOND_NEW_ACTIVITY);
		// solo.goBack();

		solo.clickOnButton("Save");
		solo.waitForText("Bird Sighting Form");
		solo.scrollDown();
		assertTrue(solo.searchText("Activity:"));
		//
		solo.pressSpinnerItem(0, 1);
		solo.scrollDown();// scroll to bottom of spinner

		solo.searchText(FIRST_NEW_ACTIVITY);
		solo.searchText(SECOND_NEW_ACTIVITY);
		solo.finishOpenedActivities();

	}
}
