package com.catalyst.android.birdapp.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.jayway.android.robotium.solo.Solo;

/**
 * @author abarendt
 * A series of tests for the GPS/mapping functions
 * 
 * Start this series with the GPS on the phone turned off
 *
 */
/**
 * @author abarendt
 *
 */
/**
 * @author abarendt
 *
 */
public class GPSTests_Phone extends
ActivityInstrumentationTestCase2<BirdFormActivity> {
	

    private Solo solo;

	public GPSTests_Phone() {
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
	 * Tests that the checkbox can be checked and unchecked
	 */
	//Note:  this test case tests obsolete functionality
//	public void test_CheckBox_Checked_Unchecked() {
//		// TODo make sure the emulator is set up wit the GPS turned off
//
//		solo.assertCurrentActivity("Toggle checkbox off and on",
//				BirdFormActivity.class);
//		assertTrue(solo.waitForText("Bird Sighting Form"));
//		solo.clickOnCheckBox(0);
//		solo.sleep(1000);
//		solo.waitForText("unavailable");
//		solo.clickOnText("OK");
//		assertTrue(solo.waitForText("GPS is turned OFF"));
//		solo.clickOnButton("NO");
//		solo.isCheckBoxChecked(0);
//		solo.clickOnCheckBox(0);
//		assertFalse(solo.isCheckBoxChecked(0));
//		solo.finishOpenedActivities();
//
//	}
	
	/**
	 * Leave the GPS unit turned off
	 */
	public void test_GPSOff_LeaveOff() {
	//TODo make sure the emulator is set up wit the GPS turned off 

			solo.assertCurrentActivity("GPS is turned off, don't turn it on",
					BirdFormActivity.class);
//			assertTrue(solo.waitForText("Bird Sighting Form"));
//
//			solo.clickOnCheckBox(0);
//
//			solo.sleep(2000);
//
//			solo.clickOnText("OK");

			assertTrue(solo.waitForText("GPS is turned OFF"));
			solo.clickOnButton("NO");
			
			assertTrue(solo.waitForText("Bird Sighting Form"));
			assertTrue(solo.waitForText("Coordinates not available"));
			
			//change back to the default preference
			solo.clickOnMenuItem("Settings");
			solo.clickOnCheckBox(0);
			
			solo.finishOpenedActivities();
		}

		/**
		 * will test up to the point that the device switches into the phone's 
		 * GPS system--unable to test in the phone's GPS system
		 */
		public void test_GPSOff_TurnOn() {

			solo.assertCurrentActivity("GPS is turned off, turn it on",
					BirdFormActivity.class);
//			assertTrue(solo.waitForText("Bird Sighting Form"));
//
//			solo.clickOnCheckBox(0);
//
//			solo.sleep(2000);
//
//			solo.clickOnText("OK");

			assertTrue(solo.waitForText("GPS is turned OFF"));
			solo.clickOnButton("YES");

			solo.finishOpenedActivities();
		}
		
}
