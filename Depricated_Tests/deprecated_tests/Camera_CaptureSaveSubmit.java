package deprecated_tests;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R.id;
import com.catalyst.android.birdapp.R.string;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

public class Camera_CaptureSaveSubmit extends ActivityInstrumentationTestCase2<BirdFormActivity> {

	private String BIRD_NAME = "Prigogine's Double-collared Sunbird";
	private String SCIENTIFIC_NAME = "Griseotyrannus aurantioatrocristatus aurantioatrocrista";
	private String NOTES = "These are the notes.";

	private EditText beforeNotes;
	private EditText afterNotes;
	private EditText beforeBird;
	private EditText afterBird;
	private EditText beforeSci;
	private EditText afterSci;

	private EditText birdName;
	private EditText notes;
	private EditText scientificName;

	private int idSubmitButtonText;
	private int idToastText;

	private String submitButtonText;
	private String toastText;

	private Solo solo;

	public Camera_CaptureSaveSubmit() {
		super(BirdFormActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		solo = new Solo(getInstrumentation(), getActivity());
		notes = (EditText) solo.getView(id.notes_edit_text);
		scientificName = (EditText) solo.getView(id.scientific_name_edit_text);
		birdName = (EditText) solo.getView(id.common_name_edit_text);

		idSubmitButtonText = com.catalyst.android.birdapp.R.string.submitButtonText;
		idToastText = string.sightingAddedBlankName;

		submitButtonText = getActivity().getResources().getString(
				idSubmitButtonText);
		toastText = getActivity().getResources().getString(idToastText);

	}

	/**
	 * When I click on the submit button the camera will vibrate and a toast
	 * will appear on the screen confirming my information has been saved.
	 * 
	 * The vibration has been confirmed manually, when triggered
	 * programmatically. The toast has been confirmed manually and
	 * programmatically when triggered programmatically.
	 */
	public void testSubmitVibrationAndToast() {
		solo.assertCurrentActivity(" testSubmit", BirdFormActivity.class);

		// before any strings are entered into the form
		beforeNotes = notes;
		beforeBird = birdName;
		beforeSci = scientificName;

		// text is entered

		solo.enterText(birdName, BIRD_NAME);
		solo.enterText(scientificName, SCIENTIFIC_NAME);
		solo.enterText(notes, NOTES);

		solo.clickOnButton(submitButtonText);

		solo.waitForText(toastText);

		solo.sleep(2000);

		afterNotes = notes;
		afterBird = birdName;
		afterSci = scientificName;

		// verify fields are reset

		assertEquals(beforeNotes.getText().toString(), afterNotes.getText()
				.toString());
		assertEquals(beforeBird.getText().toString(), afterBird.getText()
				.toString());
		assertEquals(beforeSci.getText().toString(), afterSci.getText()
				.toString());


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
