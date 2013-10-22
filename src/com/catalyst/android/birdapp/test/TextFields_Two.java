package com.catalyst.android.birdapp.test;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R.id;
import com.catalyst.android.birdapp.R.string;
import com.jayway.android.robotium.solo.Solo;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

public class TextFields_Two extends
		ActivityInstrumentationTestCase2<BirdFormActivity> {

	private Solo solo;

	private String BIRD_NAME = "Prigogine's Double-collared Sunbird";
	private EditText birdName;
	private int idBirdNameHint;
	private String birdNameHint;

	private int idBirdNameTextLabel;
	private String birdNameTextLabel;
	private String birdNameTextLabelRevised;

	private String NOTES = "These are the notes.";
	private EditText notes;

	private int idNoteLabel;
	private String noteLabel;
	private String noteLabelRevised;

	private String SCIENTIFIC_NAME = "Griseotyrannus aurantioatrocristatus aurantioatrocrista"; // "Griseotyrannus aurantioatrocristatus aurantioatrocristatus";
	private EditText scientificName;

	private int idScientificTextLabel;
	private String scientificTextLabel;
	private String scientificTextLabelRevised;

	private int idSubmitButton;
	private String submitButton;

	private int idAlert;
	private String alert;

	private int idToastOne;
	private String toastOne;

	private int idToastTwo;
	private String toastTwo;
	private Boolean toastShown;

	public TextFields_Two() {
		super(BirdFormActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		solo = new Solo(getInstrumentation(), getActivity());

		birdName = (EditText) solo.getView(id.common_name_edit_text);
		idBirdNameHint = string.birdNameHint;
		birdNameHint = getActivity().getResources().getString(idBirdNameHint);

		idBirdNameTextLabel = id.birdName;
		birdNameTextLabel = getActivity().getResources().getString(
				idBirdNameTextLabel);
		birdNameTextLabelRevised = birdNameTextLabel.substring(0,
				birdNameTextLabel.length() - 1);

		notes = (EditText) solo.getView(id.notes_edit_text);

		idNoteLabel = id.noteText;
		noteLabel = getActivity().getResources().getString(idNoteLabel);
		noteLabelRevised = noteLabel.substring(0, noteLabel.length() - 5);

		scientificName = (EditText) solo.getView(id.scientific_name_edit_text);

		idScientificTextLabel = id.scientificName;
		scientificTextLabel = getActivity().getResources().getString(
				idScientificTextLabel);
		scientificTextLabelRevised = scientificTextLabel.substring(0,
				scientificTextLabel.length() - 5);

		idSubmitButton = string.submitButtonText;
		submitButton = getActivity().getResources().getString(idSubmitButton);

		idAlert = string.alert;
		alert = getActivity().getResources().getString(idAlert);

		// idToastOne = string.added_bird_sighting_toast1;
		toastOne = getActivity().getResources().getString(idToastOne);
		// idToastTwo = string.added_bird_sighting_toast2;
		toastTwo = getActivity().getResources().getString(idToastTwo);

	}

	/*
	 * public void is_Equal(){ solo.assertCurrentActivity(
	 * "test scientificName string in equals scientificName string out",
	 * BirdFormActivity.class); solo.enterText(scientificName, SCIENTIFIC_NAME);
	 * String nameOut = scientificName.getText().toString(); int SciNameInLength
	 * = SCIENTIFIC_NAME.length(); int SciNameOutLength = nameOut.length();
	 * String outLength = Integer.toString(SciNameOutLength);
	 * //assertEquals("assert that the names are the same length"
	 * ,SciNameInLength,SciNameOutLength);
	 * 
	 * solo.enterText(birdName, Integer.toString(SciNameInLength)); }
	 */

	/**
	 * When I only have text in the BirdName field, I can click the Submit
	 * button Trigger the alert box click the "Yes" button And see the toast for
	 * a successful submission to the database
	 * 
	 * Will pass as a stand-alone test Will pass during the class test run
	 */
	public void testCommonNameTextBoxOnly_Yes() {
		solo.assertCurrentActivity("testCommonNameTextBoxOnly_Yes",
				BirdFormActivity.class);
		solo.enterText(birdName, BIRD_NAME);
		solo.scrollDown();
		solo.clickOnButton(submitButton);
		solo.waitForText(alert);
		solo.waitForText(noteLabelRevised);
		solo.waitForText(scientificTextLabelRevised);
		solo.clickOnButton("Yes");
		assertTrue(solo.waitForText(toastOne));
		solo.finishOpenedActivities();
	}

	/**
	 * When I only have text in the BirdName field, I can click the Submit
	 * button Trigger the alert box click the "No" button And be returned to the
	 * Bird Activity Form with the text still in its field
	 */

	public void testCommonNameTextBoxOnly_No() {
		solo.assertCurrentActivity("testCommonNameTextBoxOnly_No",
				BirdFormActivity.class);

		solo.enterText(birdName, BIRD_NAME);
		solo.scrollDown();
		solo.clickOnButton(submitButton);
		solo.waitForText(alert);
		solo.waitForText(noteLabelRevised);
		// solo.waitForText(scientificTextLabelRevised);
		solo.clickOnButton("No");
		assertTrue(birdName.getText().toString().equals(BIRD_NAME));
		solo.finishOpenedActivities();
	}

	/**
	 * When I only have text in the Notes field, I can click the Submit button
	 * Trigger the alert box click the "Yes" button And see the toast for a
	 * successful submission to the database
	 * 
	 * Will pass as a stand-alone test
	 */
	public void testNotesTextboxOnly_Yes() {
		solo.assertCurrentActivity("testNotesTextboxOnly_Yes",
				BirdFormActivity.class);
		solo.enterText(notes, NOTES);
		solo.scrollDown();
		solo.clickOnButton(submitButton);
		solo.waitForText(alert);
		solo.waitForText(birdNameTextLabelRevised);
		solo.waitForText(scientificTextLabelRevised);
		solo.clickOnButton("Yes");
		assertTrue(solo.waitForText("Bird Sighting Form"));
		assertTrue(birdName.getText().toString().equals(""));
		solo.finishOpenedActivities();

	}

	public void testNotesTextboxOnly_No() {
		solo.assertCurrentActivity("testNotesTextboxOnly_No",
				BirdFormActivity.class);

		solo.enterText(notes, NOTES);

		solo.scrollDown();
		solo.clickOnButton(submitButton);
		solo.waitForText(alert);
		solo.waitForText(birdNameTextLabelRevised);
		solo.waitForText(scientificTextLabelRevised);

		solo.clickOnButton("No");

		assertTrue(notes.getText().toString().equals(NOTES));

		solo.finishOpenedActivities();
	}

	/**
	 * When I only have text in the Scientific Name field, I can click the
	 * Submit button Trigger the alert box click the "Yes" button And see the
	 * toast for a successful submission to the database
	 */
	public void testScientificNameTextBoxOnly_Yes() {
		solo.assertCurrentActivity("testScientificNameTextBoxOnly_Yes",
				BirdFormActivity.class);

		solo.enterText(scientificName, SCIENTIFIC_NAME);

		solo.clickOnButton(submitButton);
		solo.waitForText(alert);
		solo.waitForText(birdNameTextLabelRevised);
		solo.waitForText(noteLabelRevised);

		solo.clickOnButton("Yes");
		assertTrue(solo.waitForText("Bird Sighting Form"));
		assertTrue(birdName.getText().toString().equals(""));
		solo.finishOpenedActivities();
		// TODO NOT passing at all--barfing on title
	}

	/**
	 * I want to be able to put text in all three fields: CommonName,
	 * ScientificName, and Notes. When I click the submit button I want to see
	 * the toast that says my information has been saved.
	 */
	public void testSubmitCommonNameSciNameNotes_Filled() {
		solo.assertCurrentActivity("testSubmitCommonNameSciNameNotes_Filled",
				BirdFormActivity.class);

		solo.enterText(birdName, BIRD_NAME);
		solo.enterText(scientificName, SCIENTIFIC_NAME);
		solo.enterText(notes, NOTES);
		solo.scrollDown();
		solo.clickOnButton(submitButton);
		solo.waitForText(toastOne);
		solo.scrollUp();

		solo.finishOpenedActivities();
	}

	/**
	 * I want to be able to submit with all three fields (CommonName,
	 * ScientificName, and Notes) empty . Upon submitting the information An
	 * alert will pop up telling me that all three fields are empty I will be
	 * allowed to choose to continue or exit the input. When I choose to
	 * continue the input I want to see the toast that says my information has
	 * been saved.
	 */
	public void testSubmitCommonNameSciNameNotes_Empty_Yes() {
		solo.assertCurrentActivity("testSubmitCommonNameSciNameNotes_Filled",
				BirdFormActivity.class);

		solo.scrollDown();
		solo.clickOnButton(submitButton);
		solo.waitForText(toastOne);
		solo.scrollUp();

		solo.finishOpenedActivities();

	}

	/**
	 * If there is text in the CommonName field And ScientificName field Upon
	 * submitting the information An alert will pop up telling me the Notes
	 * field is empty I will be allowed to choose to continue or exit the input.
	 * When I choose to continue the input I want to see the toast that says my
	 * information has been saved.
	 * 
	 */
	public void testSubmitCommonNameSciNameOnly_Yes() {
		solo.assertCurrentActivity("testSubmitWithoutNotes_Yes",
				BirdFormActivity.class);

		solo.enterText(birdName, BIRD_NAME);
		solo.enterText(scientificName, SCIENTIFIC_NAME);

		solo.scrollDown();

		solo.clickOnButton(submitButton);

		solo.waitForText(alert);
		solo.waitForText(noteLabelRevised);
		solo.clickOnButton("Yes");

		solo.scrollUp();
		solo.waitForText(toastOne);

		solo.finishOpenedActivities();
	}

	/**
	 * If there is text in the CommonName field And ScientificName field Upon
	 * submitting the information An alert will pop up telling me the Notes
	 * field is empty I will be allowed to choose to continue or exit the input.
	 * When I choose to exit the input I want to see that the alert box is gone
	 * and the original text is in my CommonName field
	 */
	public void testSubmitCommonNameSciNameOnly_No() {
		solo.assertCurrentActivity("testSubmitWithoutNotes_No",
				BirdFormActivity.class);

		solo.enterText(birdName, BIRD_NAME);
		solo.enterText(scientificName, SCIENTIFIC_NAME);
		solo.scrollDown();
		solo.clickOnButton(submitButton);
		solo.waitForText(alert);
		solo.waitForText(noteLabelRevised);
		solo.clickOnButton("No");

		assertFalse(solo.searchText(alert));
		assertTrue(birdName.getText().toString().equals(BIRD_NAME));

		solo.finishOpenedActivities();
		// TODO--Will pass test when run alone, not in queue
	}

	/**
	 * If there is text in the CommonName field And Notes field Upon submitting
	 * the information An alert will pop up telling me the ScientificName field
	 * is empty I will be allowed to choose to continue or exit the input. When
	 * I choose to continue the input I want to see the toast that says my
	 * information has been saved.
	 * 
	 */
	public void testSubmitCommonNameNotesOnly_Yes() {
		solo.assertCurrentActivity("testSubmitCommonNameNotesOnly_Yes",
				BirdFormActivity.class);
		solo.enterText(birdName, BIRD_NAME);
		solo.enterText(notes, NOTES);

		solo.clickOnButton(submitButton);

		solo.waitForText(alert);

		solo.waitForText(scientificTextLabelRevised);
		solo.waitForText(noteLabelRevised);
		solo.clickOnButton("Yes");

		solo.waitForText(toastOne);
		solo.finishOpenedActivities();
	}

	/**
	 * If there is text in the CommonName field And Notes field Upon submitting
	 * the information, an alert will pop up telling me the Notes field is empty
	 * I will be allowed to choose to continue or exit the input. When I choose
	 * to exit the input I want to see that the alert box is gone and the
	 * original text is in my CommonName field
	 */
	public void testSubmitCommonNameNotesOnly_No() {
		solo.assertCurrentActivity("testSubmitWithoutNotes_No",
				BirdFormActivity.class);

		solo.enterText(birdName, BIRD_NAME);
		solo.enterText(notes, NOTES);
		solo.scrollDown();
		solo.clickOnButton(submitButton);
		solo.waitForText(alert);
		solo.waitForText(scientificTextLabelRevised);
		solo.clickOnButton("No");

		assertFalse(solo.searchText(alert));
		assertTrue(birdName.getText().toString().equals(BIRD_NAME));
		assertTrue(notes.getText().toString().equals(NOTES));
		solo.finishOpenedActivities();
	}

	/**
	 * If there is text in the CommonName field And Notes field Upon submitting
	 * the information An alert will pop up telling me the ScientificName field
	 * is empty I will be allowed to choose to continue or exit the input. When
	 * I choose to continue the input I want to see the toast that says my
	 * information has been saved.
	 * 
	 */
	public void testSubmitScientificNameNotesOnly_Yes() {
		solo.assertCurrentActivity("testSubmitScientificNameNotesOnly_Yes",
				BirdFormActivity.class);
		solo.enterText(scientificName, SCIENTIFIC_NAME);
		solo.enterText(notes, NOTES);
		solo.scrollDown();
		solo.clickOnButton(submitButton);
		solo.waitForText(alert);
		solo.waitForText(birdNameTextLabelRevised);
		solo.clickOnButton("Yes");
		solo.scrollUp();
		solo.waitForText(toastOne);
		solo.finishOpenedActivities();
		// TODO Passes when not in queue
	}

	/**
	 * If there is text in the CommonName field And ScientificName field Upon
	 * submitting the information An alert will pop up telling me the Notes
	 * field is empty I will be allowed to choose to continue or exit the input.
	 * When I choose to exit the input I want to see that the alert box is gone
	 * and the original text is in my CommonName field
	 */
	public void testSubmitScientificNameNotesOnly_No() {
		solo.assertCurrentActivity("testSubmitScientificNameNotesOnly_No",
				BirdFormActivity.class);

		solo.enterText(notes, NOTES);
		solo.enterText(scientificName, SCIENTIFIC_NAME);
		solo.scrollDown();
		solo.clickOnButton(submitButton);
		solo.waitForText(alert);
		solo.waitForText(birdNameTextLabelRevised);
		solo.clickOnButton("No");

		assertFalse(solo.searchText(alert));
		assertTrue(notes.getText().toString().equals(NOTES));

		solo.finishOpenedActivities();
		// TODO Passes when not in queue
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
