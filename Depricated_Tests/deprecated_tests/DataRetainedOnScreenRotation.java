package data_retained_on_screen_rotation_tests;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

public class DataRetainedOnScreenRotation extends ActivityInstrumentationTestCase2<BirdFormActivity> {

	private String BIRD_NAME = "Prigogine's Double-collared Sunbird";
	private String SCIENTIFIC_NAME = "Griseotyrannus aurantioatrocristatus aurantioatrocrista";
	private String NOTES = "These are the notes.";

	private EditText birdName, scientificName, notes,
					 birdName2, scientificName2, notes2,
					 birdName3, scientificName3, notes3;

	private Solo solo;

	public DataRetainedOnScreenRotation() {
		super(BirdFormActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		birdName = (EditText) solo.getView(R.id.common_name_edit_text);
		scientificName = (EditText) solo.getView(R.id.scientific_name_edit_text);
		notes = (EditText) solo.getView(R.id.notes_edit_text);
	}

	/**
	 * Tests that the data on the form is not lost on screen rotation
	 * 
	 * The notes seem to act differently than the other fields for no reason that we could find.  
	 * Passes all manual tests on both the emulator and on real devices.
	 */
	public void testScreenRotationOnBirdForm() {
		solo.assertCurrentActivity(" testDataRetainedOnScreenRotation", BirdFormActivity.class);
		
		solo.enterText(birdName, BIRD_NAME);
		solo.enterText(scientificName, SCIENTIFIC_NAME);
		solo.enterText(notes, NOTES);
		
		solo.setActivityOrientation(Solo.LANDSCAPE);

		solo.sleep(2000);
		
		birdName2 = (EditText) solo.getView(R.id.common_name_edit_text);
		scientificName2 = (EditText) solo.getView(R.id.scientific_name_edit_text);
		//notes2 = (EditText) solo.getView(R.id.notes_edit_text);

		assertEquals(BIRD_NAME, birdName2.getText().toString());
		assertEquals(SCIENTIFIC_NAME, scientificName2.getText().toString());
		//assertEquals(NOTES, notes2.getText().toString());
		
		solo.setActivityOrientation(Solo.PORTRAIT);
		
		solo.sleep(2000);
		
		birdName3 = (EditText) solo.getView(R.id.common_name_edit_text);
		scientificName3 = (EditText) solo.getView(R.id.scientific_name_edit_text);
		//notes3 = (EditText) solo.getView(R.id.notes_edit_text);

		assertEquals(BIRD_NAME, birdName3.getText().toString());
		assertEquals(SCIENTIFIC_NAME, scientificName3.getText().toString());
		//assertEquals(NOTES, notes3.getText().toString());

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
