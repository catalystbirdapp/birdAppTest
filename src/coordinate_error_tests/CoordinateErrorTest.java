package coordinate_error_tests;

import test_utilities.UtilityMethods;
import android.content.Context;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.MainActivity;
import com.catalyst.android.birdapp.R;
import com.catalyst.android.birdapp.ViewPastSightingsActivity;
import com.catalyst.android.birdapp.database.DatabaseHandler;
import com.catalyst.android.birdapp.entities.BirdSighting;
import com.jayway.android.robotium.solo.Solo;

public class CoordinateErrorTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
    private static final String MISC = "Misc",
    						    NEST = "Nest",
    						    FLYING = "Flying",
    						    HUNTING = "Hunting";
    					    	
	private Solo solo;
	
    private String commonName = "CommonName", 
    		       scientificName = "Scientific Name", 
    		       notes = "List of notes",
    		       badLatitude = "100",
    		       badLongitude = "200";
    
    private EditText commonNameEditText, 
                     scientificNameEditText, 
                     notesEditText,
                     latitudeEditText,
                     longitudeEditText;        

	public CoordinateErrorTest() {
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
	
	public void testCoordinateErrorAllFieldsEntered() {
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		
		//Populate Fields for sighting
		commonNameEditText = (EditText) solo.getView(R.id.common_name_edit_text);
		scientificNameEditText = (EditText) solo.getView(R.id.scientific_name_edit_text);
		notesEditText = (EditText) solo.getView(R.id.notes_edit_text);
		latitudeEditText = (EditText) solo.getView(R.id.latitude_edit_text);
		longitudeEditText = (EditText) solo.getView(R.id.longitude_edit_text);
		solo.enterText(commonNameEditText, commonName);
		solo.enterText(scientificNameEditText, scientificName);
		solo.enterText(notesEditText, notes);
		solo.clearEditText(latitudeEditText);
		solo.clearEditText(longitudeEditText);
		solo.enterText(latitudeEditText, badLatitude);
		solo.enterText(longitudeEditText, badLongitude);
		solo.clickOnText(FLYING);
		solo.clickOnText(HUNTING);
		solo.clickOnText(NEST);
		solo.clickOnText(MISC);
		solo.clickOnButton(solo.getString(R.string.submitButtonText));
		
		assertTrue(solo.waitForText(solo.getString(R.string.invalidLatitude)));
		assertTrue(solo.waitForText(solo.getString(R.string.invalidLongitude)));		
		
		solo.finishOpenedActivities();
	}
	
	public void testCoordinateErrorNoFieldsEntered() {
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		
		//Populate Fields for sighting
		latitudeEditText = (EditText) solo.getView(R.id.latitude_edit_text);
		longitudeEditText = (EditText) solo.getView(R.id.longitude_edit_text);
		solo.clearEditText(latitudeEditText);
		solo.clearEditText(longitudeEditText);
		solo.enterText(latitudeEditText, badLatitude);
		solo.enterText(longitudeEditText, badLongitude);
		solo.clickOnButton(solo.getString(R.string.submitButtonText));
		//Dialog box pops up.  Click yes
		solo.clickOnText(solo.getString(R.string.yes));
		
		assertTrue(solo.waitForText(solo.getString(R.string.invalidLatitude)));
		assertTrue(solo.waitForText(solo.getString(R.string.invalidLongitude)));		
		
		solo.finishOpenedActivities();
	}
	
}
	