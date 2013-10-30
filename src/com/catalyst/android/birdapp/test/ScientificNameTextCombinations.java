package com.catalyst.android.birdapp.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R.id;
import com.catalyst.android.birdapp.R.string;
import com.jayway.android.robotium.solo.Solo;

public class ScientificNameTextCombinations extends ActivityInstrumentationTestCase2<BirdFormActivity> {
	private Solo solo;


	private EditText birdName;

	private int idBirdNameTextLabel;
	private String birdNameTextLabel;
	private String birdNameTextLabelRevised;


	private int idNoteLabel;
	private String noteLabel;
	private String noteLabelRevised;

	private String SCIENTIFIC_NAME = "Griseotyrannus aurantioatrocristatus aurantioatrocristatus"; // "Griseotyrannus aurantioatrocristatus aurantioatrocristatus";
	private EditText scientificName;
	private int scientificNameLength;

	private int idSubmitButtonText;
	private String submitButtonText;

	private int idAlertText;
	private String alertText;//"Alert!!!"
	
	private int idToastText;
	private String toastText;
	
	public ScientificNameTextCombinations() {
		super(BirdFormActivity.class);
	}
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		solo = new Solo(getInstrumentation(), getActivity());
		
		scientificName = (EditText) solo.getView(id.scientific_name_edit_text);
		birdName = (EditText) solo.getView(id.common_name_edit_text);
		
		idSubmitButtonText = string.submitButtonText;
		idNoteLabel=string.notesHint;
		idBirdNameTextLabel=string.birdNameHint;
		idToastText = string.sightingAddedBlankName;
		idAlertText=string.alert;
		
		submitButtonText = getActivity().getResources().getString(
				idSubmitButtonText);
		noteLabel=getActivity().getResources().getString(idNoteLabel);
		birdNameTextLabel=getActivity().getResources().getString(idBirdNameTextLabel);
		noteLabelRevised=noteLabel.substring(0, (noteLabel.length()-5));
		birdNameTextLabelRevised=birdNameTextLabel.substring(0, (birdNameTextLabel.length()-6));
		alertText=getActivity().getResources().getString(idAlertText);
		toastText = getActivity().getResources().getString(idToastText);	
		
		
	}
	
	public void testActualScientificNameReturned(){
		solo.assertCurrentActivity("testScientificName",
				BirdFormActivity.class);
		solo.enterText(scientificName, SCIENTIFIC_NAME);
		String extractedName = scientificName.getText().toString();
		assertEquals(SCIENTIFIC_NAME, extractedName);
		solo.finishOpenedActivities();
	}
	public void testIsEqualLength(){
		solo.assertCurrentActivity("testScientificName",
				BirdFormActivity.class);
		solo.enterText(scientificName, SCIENTIFIC_NAME);
		String extractedName = scientificName.getText().toString();
		int extractedNameLength = extractedName.length();
		scientificNameLength=SCIENTIFIC_NAME.length();
		assertEquals(scientificNameLength,extractedNameLength);
		
		solo.finishOpenedActivities();
	}
	public void testScientificNameOnly_Yes(){
	
	}

	public void testScientificNameOnly_No(){
	solo.assertCurrentActivity("testScientificNameOnly_No",
			BirdFormActivity.class);

	solo.enterText(birdName,birdNameTextLabelRevised);
	solo.enterText(scientificName, SCIENTIFIC_NAME);
	solo.scrollDown();
	solo.clickOnButton(submitButtonText);
	solo.waitForText(alertText);
	//solo.waitForText(birdNameTextLabelRevised);
	//solo.waitForText(noteLabelRevised);
	solo.clickOnButton("No");//NEED TO LOCALIZE

	assertFalse(solo.searchText(alertText));
	assertTrue(scientificName.getText().toString().equals(SCIENTIFIC_NAME));

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
