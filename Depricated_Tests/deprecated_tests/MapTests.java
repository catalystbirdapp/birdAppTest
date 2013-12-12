package deprecated_tests;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R.id;
//import com.catalyst.android.birdapp.R.string;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

/**
 * @author abarendt
 * To run these tests, the application needs to be open on the phone, so the splash screen can be avoided.
 */
public class MapTests extends
		ActivityInstrumentationTestCase2<BirdFormActivity> {

	private String BIRD_NAME = "Prigogine's Double-collared Sunbird";
	private String SCIENTIFIC_NAME = "Griseotyrannus aurantioatrocristatus aurantioatrocrista";
	private String NOTES = "These are the notes.";

	private EditText birdName;
	private EditText notes;
	private EditText scientificName;

	private int idSubmitButtonText;
	private String submitButtonText;

	private Solo solo;

	public MapTests() {
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
		submitButtonText = getActivity().getResources().getString(
				idSubmitButtonText);
	}
	
	public void testOpenMap(){
		solo.assertCurrentActivity("testOpenMap", BirdFormActivity.class);
		solo.clickOnActionBarItem(id.action_map);	
		solo.sleep(2000);
	}

	/**
	 * When I click on the submit a sighting
	 * I can open my map in the action bar
	 * And see the icon of the activity type 
	 * on the map.
	 * 
	 * Before this test is run, 
	 * manually delete the data in the application
	 * turn on the GPS
	 */
	public void testViewIconOnMap(){
		solo.assertCurrentActivity("testViewIconOnMap", BirdFormActivity.class);

		solo.enterText(birdName, BIRD_NAME);
		solo.enterText(scientificName, SCIENTIFIC_NAME);
		solo.enterText(notes, NOTES);

		solo.clickOnButton(submitButtonText);
		solo.sleep(2000);
		
		solo.clickOnActionBarItem(id.action_map);
		
		solo.sleep(20000);

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
