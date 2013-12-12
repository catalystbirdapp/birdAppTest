package deprecated_tests;

import junit.framework.Assert;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R;
import com.catalyst.android.birdapp.GPS_Utility.GPSUtility;
import com.jayway.android.robotium.solo.Solo;

/**
 * @author abarendt A series of tests for the GPS/mapping functions
 * 
 *         Start this series with: 
 *         App loaded on phone; 
 *         Phone turned on;
 *         GPS turned on;
 */

public class GPSTests_Phone_GPSOn extends
		ActivityInstrumentationTestCase2<BirdFormActivity> {

	private Solo solo;
	private BirdFormActivity bfActivity;
	private EditText editText;
	private GPSUtility gpsUtility;
	private Location location;

	public GPSTests_Phone_GPSOn() {
		super(BirdFormActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		solo = new Solo(getInstrumentation(), getActivity());
		
		//location = gpsUtility.getCurrentLocation();
	
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
	 * Tests autofill of latitude textbox.
	 * Requirements: Phone on; GPS on.
	 * 
	 * --tests that something gets entered into the textbox
	 * --a better test would be to use the phone's gps coordinates 
	 * to validate that the coordinate in the txt box is correct
	 */
	public void test_GPSon_AutoFill_Latitude() {
		// TODo make sure the emulator is set up wit the GPS turned off
		

		solo.assertCurrentActivity("Phone is on, select autofill; latitude",
				BirdFormActivity.class);
		
		assertTrue(solo.waitForText("Bird Sighting Form"));

		solo.clickOnCheckBox(0); 
		solo.sleep(2000);
		String latitudeText = solo.getEditText(5).getText().toString(); 
		assertFalse(latitudeText.equals(""));
		
		
		double latitudeLocation = location.getLatitude();
		String latitudeLocationConverted = Double.toString(latitudeLocation);
		assertEquals(latitudeLocationConverted, latitudeLocation);
		solo.finishOpenedActivities();
	}
	/**
	 * Tests autofill of longitude textbox.
	 * Requirements: Phone on; GPS on.
	 */
	public void test_GPSon_AutoFill_Longitude() {
		// TODo make sure the emulator is set up wit the GPS turned off
		
		solo.assertCurrentActivity("Phone is on, select autofill",
				BirdFormActivity.class);
		assertTrue(solo.waitForText("Bird Sighting Form"));

		solo.clickOnCheckBox(0);

		solo.sleep(2000);
		String longitudeText = solo.getEditText(6).getText().toString();
		String emptyString = "";
				
		//assertTrue(longitudeText.equals(Double.toString(longitude)));

		solo.finishOpenedActivities();
	}
	

}
