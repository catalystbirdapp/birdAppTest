package deprecated_tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R.id;
import com.catalyst.android.birdapp.R.string;
import com.jayway.android.robotium.solo.Solo;

/**
 * @author abarendt This class tests the bird name field
 */
public class TextFieldCombinations_BirdName extends
		ActivityInstrumentationTestCase2<BirdFormActivity> {
	private Solo solo;

	private String BIRD_NAME = "Prigogine's Double-collared Sunbird";
	private String NOTES = "These are the notes.";

	private int idSciNameLabelText;
	private String sciNameLabelText;

	private EditText birdName;
	private String extractedName_Bird = "";

	private int idBirdNameLabelText;
	private String birdNameLabelText;

	private EditText notes;

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

	public TextFieldCombinations_BirdName() {
		super(BirdFormActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		solo = new Solo(getInstrumentation(), getActivity());

		birdName = (EditText) solo.getView(id.common_name_edit_text);
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
	 * When I have information in the Bird Name field and the Notes field And I
	 * click the submit button, An alert box will pop up asking me if I want to
	 * submit the information with an empty Scientific Name field. If I click
	 * No, I will be returned to the main screen And the text I have already
	 * entered will still be present. If I click Yes, I will see a message that
	 * my information has been submitted.
	 */
	public void testBirdNotesOnly() {
		solo.assertCurrentActivity("testBirdNotesOnly", BirdFormActivity.class);
		solo.enterText(birdName, BIRD_NAME);
		solo.enterText(notes, NOTES);
		solo.clickOnText(submitButtonText);
		solo.waitForText(alertText);
		solo.waitForText(toastTextEmptyField);
		// test for correct emptyfieldTest
		assertFalse(solo.searchText(notesLabelText));
		assertFalse(solo.searchText(birdNameLabelText));
		assertTrue(solo.searchText(sciNameLabelText));
		solo.clickOnButton(noText);
		// Text is still in text boxes

		assertTrue(birdName.getText().toString().equals(BIRD_NAME));
		assertTrue(notes.getText().toString().equals(NOTES));

		solo.clickOnText(submitButtonText);
		solo.waitForText(alertText);
		solo.waitForText(toastTextEmptyField);
		solo.clickOnButton(yesText);
		assertTrue(solo.waitForText(toastTextAdded));

		solo.finishOpenedActivities();
	}

	/**
	 * When I have information in the Scientific Name field only And I click the
	 * submit button, An alert box will pop up asking me if I want to submit the
	 * information with empty Note and Bird Name fields. If I click No, I will
	 * be returned to the main screen And the text I have already entered will
	 * still be present. If I click Yes, I will see a message that my
	 * information has been submitted.
	 */
	public void testBirdNameOnly() {
		solo.assertCurrentActivity("testBirdNameOnly", BirdFormActivity.class);
		solo.enterText(birdName, BIRD_NAME);
		solo.scrollDown();
		solo.clickOnButton(submitButtonText);
		solo.waitForText(alertText);
		solo.waitForText(toastTextEmptyField);
		// test for correct emptyFieldToast
		assertFalse(solo.searchText(birdNameLabelText));
		assertTrue(solo.searchText(notesLabelText));
		assertTrue(solo.searchText(sciNameLabelText));

		solo.clickOnButton(noText);
		assertTrue(birdName.getText().toString().equals(BIRD_NAME));
		// yes
		solo.clickOnButton(submitButtonText);
		solo.clickOnButton(yesText);
		assertTrue(solo.waitForText(toastTextAdded));

		solo.finishOpenedActivities();
	}

	/**
	 * Test that the actual information that is entered into the Bird Name field
	 */
	public void testActualBirdNameReturned() {
		solo.assertCurrentActivity("testActualBirdNameReturned",
				BirdFormActivity.class);
		solo.enterText(birdName, BIRD_NAME);
		extractedName_Bird = birdName.getText().toString();
		assertEquals(BIRD_NAME, extractedName_Bird);
		solo.finishOpenedActivities();
	}

	/**
	 * Tests that the text field is long enough for a very common name by
	 * verifying that the length of the string that was put in the field is the
	 * same length of the text coming out of the field.
	 */
	public void testIsEqualLength() {
		solo.assertCurrentActivity("testIsEqualLength", BirdFormActivity.class);
		solo.enterText(birdName, BIRD_NAME);
		assertEquals(BIRD_NAME.length(), birdName.getText().toString().length());
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
