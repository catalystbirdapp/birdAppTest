package com.catalyst.android.birdapp.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R.id;
import com.catalyst.android.birdapp.R.string;
import com.jayway.android.robotium.solo.Solo;

/**
 * @author abarendt This class tests the Note field To avoid duplications, the
 *         ScientificName and Note combination can be found in
 *         TextFieldCombinations_ScientificName.java and the Bird Name and Note
 *         combination can be found in TextFieldCombinations_BirdName.java
 */
public class TextFieldCombinations_Notes extends
		ActivityInstrumentationTestCase2<BirdFormActivity> {
	private Solo solo;

	private String NOTES_LONG = "These are the notes, they are really really long and should be cropped and not the same, but I am adding more incase this isn't the case . . If I can continue putting in more stuff, then the storage type is different and will have to research that in the morning.";
	private String NOTES_SHORT =  "These are the notes.";
	
	private int idSciNameLabelText;
	private String sciNameLabelText;

	private int idBirdNameLabelText;
	private String birdNameLabelText;

	private EditText notes;
	private String extractedNotes = "";

	private int idNotesLabelText;
	private String notesLabelText;

	private int idNoText;
	private String noText;

	private int idYesText;
	private String yesText;

	private int idSubmitButtonText;
	private String submitButtonText;

	private int idAlertText;
	private String alertText;// "Alert!!!"

	private int idToastTextEmptyField;
	private String toastTextEmptyField;

	private int idToastTextAdded;
	private String toastTextAdded;

	public TextFieldCombinations_Notes() {
		super(BirdFormActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		solo = new Solo(getInstrumentation(), getActivity());

		notes = (EditText) solo.getView(id.notes_edit_text);

		idSubmitButtonText = string.submitButtonText;
		idAlertText = string.alert;
		idToastTextAdded = string.sightingAddedBlankName;
		idToastTextEmptyField = string.emptyFieldsWarning;
		idNoText = string.no;
		idYesText = string.yes;
		idBirdNameLabelText = string.birdName;
		idNotesLabelText = string.noteText;
		idSciNameLabelText = string.scientificName;

		submitButtonText = getActivity().getResources().getString(
				idSubmitButtonText);
		alertText = getActivity().getResources().getString(idAlertText);
		toastTextAdded = getActivity().getResources().getString(
				idToastTextAdded);
		toastTextEmptyField = getActivity().getResources().getString(
				idToastTextEmptyField);
		noText = getActivity().getResources().getString(idNoText);// "NO"
		yesText = getActivity().getResources().getString(idYesText);// "YES"

		noText = ((noText.substring(0, 1).toUpperCase()) + (noText.substring(1)
				.toLowerCase()));// will be deleted
		yesText = ((yesText.substring(0, 1).toUpperCase()) + (yesText
				.substring(1).toLowerCase())); // will be deleted

		birdNameLabelText = getActivity().getResources().getString(
				idBirdNameLabelText);
		birdNameLabelText = birdNameLabelText.substring(0,
				(birdNameLabelText.length() - 6));
		notesLabelText = getActivity().getResources().getString(
				idNotesLabelText);
		notesLabelText = notesLabelText.substring(0,
				notesLabelText.length() - 1);
		sciNameLabelText = getActivity().getResources().getString(
				idSciNameLabelText);
		sciNameLabelText = sciNameLabelText.substring(0,
				sciNameLabelText.length() - 6);

	}

	/**
	 * When I have information in the Note field only And I click the submit
	 * button, An alert box will pop up asking me if I want to submit the
	 * information with empty Scientific Name and Bird Name fields. If I click
	 * No, I will be returned to the main screen And the text I have already
	 * entered will still be present. If I click Yes, I will see a message that
	 * my information has been submitted.
	 */
	public void testNotesOnly() {
		solo.assertCurrentActivity("testNotesOnly", BirdFormActivity.class);
		
		solo.enterText(notes, NOTES_SHORT);
		solo.scrollDown();
		solo.clickOnButton(submitButtonText);
		solo.waitForText(alertText);
		solo.waitForText(toastTextEmptyField);
		// test for correct emptyFieldToast
		assertTrue(solo.searchText(birdNameLabelText));
		assertFalse(solo.searchText(notesLabelText));
		assertTrue(solo.searchText(sciNameLabelText));

		solo.clickOnButton(noText);
		assertTrue(notes.getText().toString().equals(NOTES_SHORT));
		// yes
		solo.clickOnButton(submitButtonText);
		solo.clickOnButton(yesText);
		assertTrue(solo.waitForText(toastTextAdded));

		solo.finishOpenedActivities();
	}

	/**
	 * Test that the actual information that is entered into the Note field is returned
	 * Note: the actual text will be cropped if it extends beyond 55 characters.
	 */
	public void testActualNoteReturned_Short() {
		solo.assertCurrentActivity("testActualNoteReturned",
				BirdFormActivity.class);
		solo.enterText(notes, NOTES_SHORT);
		extractedNotes = notes.getText().toString();
		assertTrue(NOTES_SHORT.equals(extractedNotes));
		solo.finishOpenedActivities();
	}
	
	/**
	 * Test that the actual information that is entered into the Note field is returned
	 * Note: the actual text will be cropped if it extends beyond 55 characters.
	 */
	public void testActualNoteReturned_Long() {
		solo.assertCurrentActivity("testActualNoteReturned",
				BirdFormActivity.class);
		solo.enterText(notes, NOTES_LONG);
		extractedNotes = notes.getText().toString();
		assertTrue(NOTES_LONG.equals(extractedNotes));
		solo.finishOpenedActivities();
	}
	/**
	 * This shows that if the note is very long, it will be cropped
	 */
	public void testIsEqualLength() {
		solo.assertCurrentActivity("testIsEqualLength", BirdFormActivity.class);
		solo.enterText(notes, NOTES_LONG);
		assertEquals(NOTES_LONG.length(),notes.getText().toString().length() );
		
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
