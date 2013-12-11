package com.catalyst.android.birdapp.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.jayway.android.robotium.solo.Solo;

public class GPS_Preference extends ActivityInstrumentationTestCase2<BirdFormActivity> {
	
    private Solo solo;

	public GPS_Preference() {
		super(BirdFormActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		solo = new Solo(getInstrumentation(), getActivity());
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
	public void test_GPS_Preference_Yes() {

		solo.assertCurrentActivity("Test GPS Prompt Preference is Yes",
				BirdFormActivity.class);
		
		//verify GPS prompt is displayed
		assertTrue(solo.waitForText("GPS is turned OFF"));
		solo.goBack();
		
		//verify that GPS prompt is turned on in the settings
		assertTrue(solo.waitForText("Bird Sighting Form"));
		solo.clickOnMenuItem("Settings");
		assertTrue("GPS Prompt is selected", solo.isCheckBoxChecked(0));
		
		solo.finishOpenedActivities();
	}
	
	/**
	 * Leave the GPS unit turned off
	 */
	public void test_GPS_Preference_No() {
		solo.assertCurrentActivity("Test GPS Prompt Preference is Yes and then turn it off",
				BirdFormActivity.class);

		//turn GPS prompt off
		assertTrue(solo.waitForText("GPS is turned OFF"));
		solo.clickOnButton("NO");
		assertTrue(solo.waitForText("Prompting for GPS has been disabled"));
		assertTrue(solo.waitForText("Bird Sighting Form"));
		
		//verify GPS prompt is off in the settings
		solo.clickOnMenuItem("Settings");
		assertFalse("GPS Prompt is selected", solo.isCheckBoxChecked(0));
		solo.goBack();
		
		//open a new bird sighting form
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		solo = new Solo(getInstrumentation(), getActivity());
		
		//verify GPS prompt was not displayed
		assertTrue(solo.waitForText("Bird Sighting Form"));
		assertFalse(solo.waitForText("GPS is turned OFF"));
		
		//change back to the default preference
		solo.clickOnMenuItem("Settings");
		if (!solo.isCheckBoxChecked(0)){
			solo.clickOnCheckBox(0);
		}
		
		solo.finishOpenedActivities();
	}
		
}
	