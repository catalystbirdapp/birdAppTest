package com.catalyst.android.birdapp.test;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R.id;
import com.catalyst.android.birdapp.R.string;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

public class Submit extends ActivityInstrumentationTestCase2<BirdFormActivity> {

	private String BIRD_NAME = "Prigogine's Double-collared Sunbird";
	private String SCIENTIFIC_NAME = "Griseotyrannus aurantioatrocristatus aurantioatrocrista";
	private String NOTES = "These are the notes.";

	private EditText birdName;
	private EditText notes;
	private EditText scientificName;

	private int idSubmitButtonText;
	private int idToastText;
	
	private String submitButtonText;
	private String toastText;

	private Solo solo;

	public Submit() {
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
	 * When I click on the submit button
	 * the camera will vibrate
	 * and a toast will appear on the screen confirming my information has been saved.
	 * 
	 * The vibration has been confirmed manually, when triggered programmatically.
	 * The toast has been confirmed manually and programmatically when triggered programmatically.
	 */
	public void testSubmitVibrationAndToast() {
		solo.assertCurrentActivity(" testSubmit", BirdFormActivity.class);

		solo.enterText(birdName, BIRD_NAME);
		solo.enterText(scientificName, SCIENTIFIC_NAME);
		solo.enterText(notes, NOTES);

		solo.clickOnButton(submitButtonText);
		
		solo.waitForText(toastText);


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
