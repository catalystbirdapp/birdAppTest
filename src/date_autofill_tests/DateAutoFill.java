package date_autofill_tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.TextView;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R;
import com.jayway.android.robotium.solo.Solo;

public class DateAutoFill extends ActivityInstrumentationTestCase2<BirdFormActivity> {

	private TextView formDate;
	private TextView formTime;

	private Solo solo;
	
	private long currentMilliseconds;

	public DateAutoFill() {
		super(BirdFormActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		currentMilliseconds = new Date().getTime();
		formDate = (TextView) solo.getView(R.id.date_time_edit_text);
		formTime = (TextView) solo.getView(R.id.hour_edit_text);
	}

	/**
	 * Tests that the date that is automatically put on the form is the correct date.
	 */
	public void testDateAndTimeAutoFill() {
		solo.assertCurrentActivity(" testDateAndTimeAutoFill", BirdFormActivity.class);
		
		//Deal with GPS prompt
		if (solo.waitForText("GPS is turned OFF")) {
			solo.clickOnButton("NO");
			assertTrue("Prompt was disabled", solo.waitForText("Prompting for GPS has been disabled"));
		}
		solo.scrollUp();
		assertTrue("on Bird Sighting Form", solo.waitForText(solo.getString(R.string.formTitle).toString()));
		
		String dateString = formDate.getText().toString();
		String timeString = formTime.getText().toString();
		String dateAndTimeString = dateString + "," + timeString;
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy,hh:mm aa");
		Date date = null;
		
		try {
			date = formatter.parse(dateAndTimeString);
		} catch (ParseException e) {
			Log.e("DEBUG", "Exception message - " + e.getMessage());
			e.printStackTrace();
		}		
		
		long formMilliseconds = date.getTime();
		
		boolean isCorrectDate = false;
		if(currentMilliseconds - formMilliseconds < 90000){
			isCorrectDate = true;
		}
		
		assertTrue("Date is correct - expected: " + currentMilliseconds + "  - actual: " + formMilliseconds, isCorrectDate);
		
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
