package deprecated_tests;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.jayway.android.robotium.solo.Solo;

/**
 * @author abarendt
 * A series of tests for the GPS/mapping functions
 *
 */
public class GPSTests_Emulator_GPSOn extends
ActivityInstrumentationTestCase2<BirdFormActivity> {
	

    private Solo solo;

	public GPSTests_Emulator_GPSOn() {
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

	public void test_Zx_Autofill_GPSOff_LeaveOff() {
	//TODo make sure the emulator is set up wit the GPS turned off 

			solo.assertCurrentActivity("GPS is turned off, don't turn it on",
					BirdFormActivity.class);
			assertTrue(solo.waitForText("Bird Sighting Form"));

			solo.clickOnCheckBox(0);

			solo.sleep(2000);

			solo.clickOnText("OK");

			assertTrue(solo.waitForText("GPS is turned OFF"));
			solo.clickOnButton("NO");
			
			assertTrue(solo.waitForText("Coordinates not available"));
			
			solo.finishOpenedActivities();
		}

		public void test_Zy_AutoFill_GPSOff_TurnOn() {

			solo.assertCurrentActivity("GPS is turned off, turn it on",
					BirdFormActivity.class);
			assertTrue(solo.waitForText("Bird Sighting Form"));

			solo.clickOnCheckBox(0);

			solo.sleep(2000);

			solo.clickOnText("OK");

			assertTrue(solo.waitForText("GPS is turned OFF"));
			solo.clickOnButton("YES");

			// below GPS text and check box is on Anna's phone
			solo.sleep(1000);
			assertTrue(solo.searchText("Use GPS satellites"));
			// solo.clickOnCheckBox(1);
			// solo.goBack();
			// below code is poor coding, need variables...
			// assertTrue(solo.waitForText("45.51"));
			// assertTrue(solo.waitForText("-122.83"));
			solo.finishOpenedActivities();
		}}


	