package date_autofill_tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class DateAutoFill extends ActivityInstrumentationTestCase2<BirdFormActivity> {

	private TextView formDate;
	private TextView formTime;

	private Solo solo;

	public DateAutoFill() {
		super(BirdFormActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		formDate = (TextView) solo.getView(R.id.date_time_edit_text);
		formTime = (TextView) solo.getView(R.id.hour_edit_text);
	}

	/**
	 * Tests that the date that is automatically put on the form is the correct date.
	 */
	public void testDateAndTimeAutoFill() {
		solo.assertCurrentActivity(" testDateAndTimeAutoFill", BirdFormActivity.class);
		
		String dateString = formDate.getText().toString();
		String timeString = formTime.getText().toString();
		String dateAndTimeString = dateString + "," + timeString;
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy,hh:mm aaa");
		Date date = null;
		
		try {
			date = formatter.parse(dateAndTimeString);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		
		long formMilliseconds = date.getTime();
		long currentMilliseconds = new Date().getTime();
		
		boolean isCorrectDate = false;
		if(currentMilliseconds - formMilliseconds < 60000){
			isCorrectDate = true;
		}
		
		assertTrue(isCorrectDate);
		
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
