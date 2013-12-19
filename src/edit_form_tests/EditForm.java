package edit_form_tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.MainActivity;
import com.catalyst.android.birdapp.R;
import com.catalyst.android.birdapp.ViewPastSightingsActivity;
import com.catalyst.android.birdapp.database.DatabaseHandler;
import com.catalyst.android.birdapp.RecordsActivity;
import com.jayway.android.robotium.solo.Solo;

public class EditForm extends ActivityInstrumentationTestCase2<MainActivity> {
	
    private static final String MISC = "Misc",
    						    MATING = "Mating",
    						    NEST = "Nest",
    						    FLYING = "Flying",
    						    SIGHTING = "Sighting",
    					    	HUNTING = "Hunting";;
    					    	
	private Solo solo;
	
    private String commonName = "CommonName", 
    		       scientificName = "Scientific Name", 
    		       notes = "List of notes",
    		       newNotes = "New List of Notes",
    			   numbers = "12345",
    			   invalidCharacter = "punct$uation",
                   tooLongBirdName= "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXZY",
                   tooLongNameShortened = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabc",
                   validCommonName = "Valid Common Name",
                   validScientificName = "Valid Scientific Name";
    
    private EditText commonNameEditText, 
                     scientificNameEditText, 
                     notesEditText;
        

	public EditForm() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
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
	
	public void testDealWithGPSPrompt() {
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		
		//Deal with GPS prompt
		if (solo.waitForText("GPS is turned OFF")) {
			solo.clickOnButton("NO");
			assertTrue(solo.waitForText("Prompting for GPS has been disabled"));
		}
		
		solo.finishOpenedActivities();
	}
	
	public void testSubmitSightingViewEditForm() {
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		submitNewBirdSighting();
		
		//Verify that we are on the splash screen
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Go to the Review Past Sightings Screen
		solo.clickOnButton(solo.getString(R.string.reviewRecords));
		solo.assertCurrentActivity("Review Past Sightings Page", ViewPastSightingsActivity.class);
		
		//Click on the saved sighting to go to the edit form
		solo.clickOnText(commonName);
		
		//Verify that we are on the bird form
		solo.assertCurrentActivity("Bird Form Page", BirdFormActivity.class);
		
		//Verify that the fields are correct
		assertTrue(solo.waitForText(commonName));
		assertTrue(solo.waitForText(scientificName));
		assertTrue(solo.waitForText(notes));
		assertTrue(solo.waitForText(HUNTING));
		assertTrue(solo.waitForText(MISC));
		
		enterNumbersIntoCommonNameTest();
		enterNumbersIntoScientificNameTest();
		
		//Also enters a name that is too long for both
		enterPunctuationIntoCommonNameTest();
		enterPunctuationIntoScientificNameTest();
		
		//Checks that the names that are too long are shortened correctly
		assertEquals(tooLongNameShortened, commonNameEditText.getText().toString());
		assertEquals(tooLongNameShortened, scientificNameEditText.getText().toString());
		
		changeActivityCategoryAndNotes();
		
		//Submit the changes and verify that we are back at the view past sightings page
		solo.clickOnButton(solo.getString(R.string.save_changes));
		solo.assertCurrentActivity("Review Past Sightings Page", ViewPastSightingsActivity.class);		
		
		//Click on the saved sighting to go to the edit form
		solo.clickOnText(tooLongNameShortened);
		
		//Verify that the fields are correct
		assertEquals(tooLongNameShortened, commonNameEditText.getText().toString());
		assertEquals(tooLongNameShortened, scientificNameEditText.getText().toString());
		assertTrue(solo.waitForText(newNotes));
		assertTrue(solo.waitForText(MATING));
		
		solo.finishOpenedActivities();
	}

	private void changeActivityCategoryAndNotes() {
		//Change activity and category
		solo.clickOnText(HUNTING);
		solo.clickOnText(MATING);
		solo.clickOnText(MISC);
		solo.clickOnText(SIGHTING);
		
		//Change Notes
		notesEditText = (EditText) solo.getView(R.id.notes_edit_text);
		solo.clearEditText(notesEditText);
		solo.enterText(notesEditText, newNotes);
	}

	private void enterPunctuationIntoScientificNameTest() {
		//Attempt to save punctuation in the scientific name
		scientificNameEditText = (EditText) solo.getView(R.id.scientific_name_edit_text);
		solo.enterText(scientificNameEditText, invalidCharacter);
		
		//Attempt to submit, then check for error messages
		solo.clickOnButton(solo.getString(R.string.save_changes));
		assertTrue(solo.waitForText(solo.getString(R.string.scientific_name_alpha_error)));
		
		//Clear scientific name and put a name that is too long into it
		solo.clearEditText(scientificNameEditText);
		solo.enterText(scientificNameEditText, tooLongBirdName);
	}

	private void enterPunctuationIntoCommonNameTest() {
		//Attempt to put punctuation in the common name
		commonNameEditText = (EditText) solo.getView(R.id.common_name_edit_text);
		solo.enterText(commonNameEditText, invalidCharacter);
		
		//Attempt to submit, then check for error messages
		solo.clickOnButton(solo.getString(R.string.save_changes));
		assertTrue(solo.waitForText(solo.getString(R.string.bird_name_alpha_error)));
		
		//Clear the Common name and put in a name that is too long
		solo.clearEditText(commonNameEditText);
		solo.enterText(commonNameEditText, tooLongBirdName);
	}

	private void enterNumbersIntoScientificNameTest() {
		//Attempt to save numbers in the scientific name
		scientificNameEditText = (EditText) solo.getView(R.id.scientific_name_edit_text);
		solo.enterText(scientificNameEditText, numbers);
		
		//Attempt to submit, then check for error messages
		solo.clickOnButton(solo.getString(R.string.save_changes));
		assertTrue(solo.waitForText(solo.getString(R.string.scientific_name_alpha_error)));
		
		//Clear scientific name and put valid name in
		solo.clearEditText(scientificNameEditText);
		solo.enterText(scientificNameEditText, validScientificName);
	}

	private void enterNumbersIntoCommonNameTest() {
		//Attempt to enter numbers in common name and save
		commonNameEditText = (EditText) solo.getView(R.id.common_name_edit_text);
		solo.enterText(commonNameEditText, numbers);
		
		//Attempt to submit, then check for error messages
		solo.clickOnButton(solo.getString(R.string.save_changes));
		assertTrue(solo.waitForText(solo.getString(R.string.bird_name_alpha_error)));
		
		//Clear the Common name and put in a valid entry
		solo.clearEditText(commonNameEditText);
		solo.enterText(commonNameEditText, validCommonName);
	}

	private void submitNewBirdSighting() {
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		assertTrue("on Bird Sighting Form", solo.waitForText(solo.getString(R.string.formTitle).toString()));
		
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
	
}
	