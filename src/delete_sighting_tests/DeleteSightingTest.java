package delete_sighting_tests;

import test_utilities.UtilityMethods;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.MainActivity;
import com.catalyst.android.birdapp.R;
import com.catalyst.android.birdapp.ViewPastSightingsActivity;
import com.jayway.android.robotium.solo.Solo;

public class DeleteSightingTest extends ActivityInstrumentationTestCase2<MainActivity> {
    					    	
	private Solo solo;
	
    private String commonName = "CommonName";
    		
    private EditText commonNameEditText, 
                     scientificNameEditText, 
                     notesEditText;
        

	public DeleteSightingTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		UtilityMethods.dealWithGPSPrompt(solo);
		//Go back to main activity
		solo.goBack();
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
	
	public void testDeleteSighting() {
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		
		UtilityMethods.submitNewBirdSighting(solo, commonNameEditText, scientificNameEditText, notesEditText);
		
		//Verify that we are on the splash screen
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Go to the Review Past Sightings Screen
		solo.clickOnButton(solo.getString(R.string.reviewRecords));
		solo.assertCurrentActivity("Review Past Sightings Page", ViewPastSightingsActivity.class);
		
		//Click on the saved sighting to go to the edit form
		solo.clickOnText(commonName);
		
		//Verify that we are on the bird form
		solo.assertCurrentActivity("Bird Form Page", BirdFormActivity.class);
		
		//Delete the sighting
		solo.clickOnText(solo.getString(R.string.deleteButtonText));
		
		//Go back and check to make sure it was deleted
		solo.goBack();
		assertFalse(solo.waitForText(commonName));
		
		solo.finishOpenedActivities();
	}
	
}
	