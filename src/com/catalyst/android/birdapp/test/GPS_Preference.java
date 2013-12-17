package com.catalyst.android.birdapp.test;

import android.test.ActivityInstrumentationTestCase2;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.MainActivity;
import com.catalyst.android.birdapp.R;
import com.jayway.android.robotium.solo.Solo;

public class GPS_Preference extends ActivityInstrumentationTestCase2<MainActivity> {
	
    private Solo solo;

	public GPS_Preference() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		
		//if GPS prompt is turned off, turn it back on for the tests
		if (solo.waitForText("GPS is turned OFF")) {
			//prompt is on, close dialog box
			solo.goBack();
		} else {
			//turn on GPS prompt
			solo.clickOnMenuItem("Settings");
			solo.clickOnCheckBox(0);
			solo.goBack();
		}
		//go back to the main page
		solo.goBack();
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
	
	/**
	 * Leave the GPS unit turned off
	 */
	public void testGPSPreferenceYes() {
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		
		//verify GPS prompt is displayed
		assertTrue("GPS dialog box dispayed", solo.waitForText("GPS is turned OFF"));
		solo.goBack();
		
		//verify that GPS prompt is turned on in the settings
		assertTrue("On bird sighting page", solo.waitForText("Bird Sighting Form"));
		solo.clickOnMenuItem("Settings");
		assertTrue("GPS Prompt is selected", solo.isCheckBoxChecked(0));
		
		solo.finishOpenedActivities();
	}
	
	/**
	 * Leave the GPS unit turned off
	 */
	public void testGPSPreferenceNo() {
		//Go to MAIN PAGE
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		
		//turn GPS prompt off
		assertTrue("GPS Prompt dialog box", solo.waitForText("GPS is turned OFF"));
		solo.clickOnButton("NO");
		assertTrue("GPS disabled message", solo.waitForText("Prompting for GPS has been disabled"));
		assertTrue("on Bird Sighting Form", solo.waitForText(solo.getString(R.string.formTitle).toString()));
		
		//verify GPS prompt is off in the settings
		solo.clickOnMenuItem("Settings");
		assertFalse("GPS Prompt is selected", solo.isCheckBoxChecked(0));
		solo.goBack();
		
		//Go to MAIN PAGE
		solo.goBack();
		solo.assertCurrentActivity("Main Activity Page", MainActivity.class);
		
		//Navigate to new BIRD FORM page
		solo.clickOnButton(solo.getString(R.string.submitButton).toString());
		solo.assertCurrentActivity("Bird Sighting Page", BirdFormActivity.class);
		
		//verify GPS prompt was not displayed
		assertTrue("on Bird Sighting Form", solo.waitForText(solo.getString(R.string.formTitle).toString()));
		assertFalse("GPS dialog box", solo.waitForText("GPS is turned OFF"));
		
		//change back to the default preference
		solo.clickOnMenuItem("Settings");
		if (!solo.isCheckBoxChecked(0)){
			solo.clickOnCheckBox(0);
		}
		
		solo.finishOpenedActivities();
	}
		
}
	