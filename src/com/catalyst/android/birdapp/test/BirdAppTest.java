package com.catalyst.android.birdapp.test;

import java.util.ArrayList;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class BirdAppTest extends
		ActivityInstrumentationTestCase2<BirdFormActivity> {
	
	private TextView text;
	private BirdFormActivity bfActivity;
	private String resourceString;
	private CheckBox checkBox;
	private Spinner categorySpinner;
	private SpinnerAdapter sAdapter;
	public static final int ADAPTER_COUNT = 4;
	public static final int INITIAL_POSITION = 0;
	public static final int TEST_POSITION = 3;
	private String mSelection;
	private int mPos;
	
	
	  
	//Robotium
	private Solo solo;
	private static final String WRONG_ACTIVITY_MESSAGE = "Was not BirdFormActivity";
	private static final String DATE_REGEX = "^\\d{2}/\\d{2}/\\d{4}$";  //catches 00/00/0000 to 99/99/9999
	private static final String TIME_REGEX = "^\\d{2}:\\d{2}.*";  //catches 00:00... to 99:99...
	
	public BirdAppTest() {
			super("com.catalyst.android.birdapp", BirdFormActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		
		
		bfActivity = getActivity();
		text = (TextView)bfActivity.findViewById(com.catalyst.android.birdapp.R.id.formTitle);
		resourceString = bfActivity.getString(com.catalyst.android.birdapp.R.string.formTitle);
		checkBox = (CheckBox) bfActivity.findViewById(com.catalyst.android.birdapp.R.id.add_another_button);
setActivityInitialTouchMode(false);
		
	
		
		categorySpinner = (Spinner)bfActivity.findViewById(com.catalyst.android.birdapp.R.id.category_drop_down);
		sAdapter = categorySpinner.getAdapter();
	}


//	 public void testSpinnerUI() {
//
//		 bfActivity.runOnUiThread(
//		      new Runnable() {
//		        public void run() {
//		        	categorySpinner.requestFocus();
//		        	categorySpinner.setSelection(INITIAL_POSITION);
//		        } 
//		      } 
//		    ); 
//	
//		 this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
//		    for (int i = 1; i <= TEST_POSITION; i++) {
//		      this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
//		    } // end of for loop
//
//		    this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
//	 
//		    mPos = categorySpinner.getSelectedItemPosition();
//		    mSelection = (String)categorySpinner.getItemAtPosition(mPos);
//
//		    assertEquals("Rare", mSelection);
//
//		  } 
//	
//	public void testText(){
//		assertEquals(resourceString, (String)text.getText());
//	}
//	@UiThreadTest
//	public void testCheckBox(){
//		
//		bfActivity.runOnUiThread(
//				new Runnable(){
//					public void run(){
//						
//					}
//				}
//				);
////		checkBox.performClick();
////		assertTrue(checkBox.isChecked());
//	}
	
	// Robotium tests
	
	/**
	 * Tests that the First EditText element has focus when app opens 
	 * 
	 */
	public void testInitialFocus() {
		solo.assertCurrentActivity(WRONG_ACTIVITY_MESSAGE, BirdFormActivity.class);
		EditText firstEditText = solo.getEditText(0);
		EditText secondEditText = solo.getEditText(1);
		assertTrue(firstEditText.isFocused());
		assertFalse(secondEditText.isFocused());
	}
	
	
	/**
	 * Tests that a toast returns on submission, with the proper submit message.
	 * Checks BIRD-54
	 */
	public void testSubmitToast() {
		solo.assertCurrentActivity(WRONG_ACTIVITY_MESSAGE, BirdFormActivity.class);
		solo.clickOnButton(solo.getString(com.catalyst.android.birdapp.R.string.submitButtonText));
		assertTrue(solo.waitForText(solo.getString(com.catalyst.android.birdapp.R.string.added_bird_sighting_toast)));
		assertFalse(solo.waitForText("This text Should not exist"));
	}
	
	/**
	 * Enters text into all EditText elements and tests that the fields are cleared on submission.
	 * Checks BIRD-53,54
	 */
	public void testFormClearsOnSubmission() {
		solo.assertCurrentActivity(WRONG_ACTIVITY_MESSAGE, BirdFormActivity.class);
		enterAllTheText();
		assertTrue(checkForEnteredText());
		solo.clickOnButton(solo.getString(com.catalyst.android.birdapp.R.string.submitButtonText));
		assertTrue(solo.waitForText(solo.getString(com.catalyst.android.birdapp.R.string.added_bird_sighting_toast)));
		//Make sure form reloads before checking for form entries
		assertTrue(solo.waitForText(solo.getString(com.catalyst.android.birdapp.R.string.birdNameHint)));
		assertFalse(checkForEnteredText());
	}
	
	
	/**
	 * Checks time. Goes to AddActivity screen for a minute. Returns to form.
	 * Compares new time to old time. 
	 * Checks BIRD-52
	 */
	public void testTimeRefresh() {
		solo.assertCurrentActivity(WRONG_ACTIVITY_MESSAGE, BirdFormActivity.class);
		View timeEdit = solo.getView(com.catalyst.android.birdapp.R.id.hour_edit_text);
		assertNotNull(timeEdit);
		assertTrue(timeEdit instanceof EditText);
		String time1 = ((EditText) timeEdit).getText().toString();
		solo.clickOnMenuItem(solo.getString(com.catalyst.android.birdapp.R.string.action_activity));
		solo.sleep(60000);
		solo.goBack();
		String time2 = ((EditText) timeEdit).getText().toString();
		assertFalse(time1.equals(time2));
	}
	
	/**
	 * Loops through all the EditTexts and enters "Text"
	 */
	private void enterAllTheText() {
		ArrayList<View> views= solo.getViews();
		for (View view : views) {
			if (view instanceof EditText) {
				solo.typeText((EditText) view, "Text"); 
			}		
		}
	}
	
	
	/**
	 * Loops through EditTextElements looking for entered text. 
	 * Returns boolean of whether any text is found. 
	 * @return foundText
	 */
	private boolean checkForEnteredText() {
		boolean foundText = false;
		ArrayList<View> views= solo.getViews();
		for (View view : views) {
			if (view instanceof EditText) {
				String string = ((EditText) view).getText().toString();
				
				//Ignores text if blank or date/time
				if (string != null && !string.equals("")
						&& !(string.matches(DATE_REGEX) || string.matches(TIME_REGEX))) {
					foundText = true;
				}
			}		
		}
		return foundText;
	}
	
	
	
	
	
	
	

}
