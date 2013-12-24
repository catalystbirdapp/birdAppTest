package test_utilities;

import android.content.Context;
import android.widget.EditText;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.MainActivity;
import com.catalyst.android.birdapp.R;
import com.catalyst.android.birdapp.database.DatabaseHandler;
import com.catalyst.android.birdapp.entities.BirdSighting;
import com.jayway.android.robotium.solo.Solo;

public class UtilityMethods {
	
	   private static final String MISC = "Misc",
			   					   NEST = "Nest",
			   					   FLYING = "Flying",
			   					   HUNTING = "Hunting";
	   private static String commonName = "CommonName",
			   				 scientificName = "Scientific Name",
			   				 notes = "List of notes";
	
	public static void dealWithGPSPrompt(Solo solo) {
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		
		//Deal with GPS prompt
		if (solo.waitForText("GPS is turned OFF")) {
			solo.clickOnButton("NO");
		}
	}
	
	public static void submitNewBirdSighting(Solo solo, EditText commonNameEditText, EditText scientificNameEditText, EditText notesEditText) {
		
		//Populate Fields for sighting
		commonNameEditText = (EditText) solo.getView(R.id.common_name_edit_text);
		scientificNameEditText = (EditText) solo.getView(R.id.scientific_name_edit_text);
		notesEditText = (EditText) solo.getView(R.id.notes_edit_text);
		solo.enterText(commonNameEditText, commonName);
		solo.enterText(scientificNameEditText, scientificName);
		solo.enterText(notesEditText, notes);
		solo.clickOnText(FLYING);
		solo.clickOnText(HUNTING);
		solo.clickOnText(NEST);
		solo.clickOnText(MISC);
		
		//SubmitBirdForm
		solo.clickOnButton(solo.getString(R.string.submitButtonText));
		solo.goBack();
	}
	
	public static void deleteBirdSighting(BirdSighting birdSighting, Context context) {
		DatabaseHandler dbHandler = DatabaseHandler.getInstance(context);
		dbHandler.deleteBirdSighting(birdSighting.getId());
	}
	
}
