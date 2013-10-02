package com.catalyst.android.birdapp.test;

import com.catalyst.android.birdapp.BirdFormActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.KeyEvent;
import android.widget.CheckBox;
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
	
	
	public BirdAppTest() {
		super(BirdFormActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		bfActivity = getActivity();
		text = (TextView)bfActivity.findViewById(com.catalyst.android.birdapp.R.id.formTitle);
		resourceString = bfActivity.getString(com.catalyst.android.birdapp.R.string.formTitle);
		checkBox = (CheckBox) bfActivity.findViewById(com.catalyst.android.birdapp.R.id.add_another_button);
setActivityInitialTouchMode(false);
		
	
		
		categorySpinner = (Spinner)bfActivity.findViewById(com.catalyst.android.birdapp.R.id.category_drop_down);
		sAdapter = categorySpinner.getAdapter();
	}

	 public void testSpinnerUI() {

		 bfActivity.runOnUiThread(
		      new Runnable() {
		        public void run() {
		        	categorySpinner.requestFocus();
		        	categorySpinner.setSelection(INITIAL_POSITION);
		        } 
		      } 
		    ); 
	
		 this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		    for (int i = 1; i <= TEST_POSITION; i++) {
		      this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
		    } // end of for loop

		    this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
	 
		    mPos = categorySpinner.getSelectedItemPosition();
		    mSelection = (String)categorySpinner.getItemAtPosition(mPos);

		    assertEquals("Rare", mSelection);

		  } 
	
	public void testText(){
		assertEquals(resourceString, (String)text.getText());
	}
	@UiThreadTest
	public void testCheckBox(){
		
		bfActivity.runOnUiThread(
				new Runnable(){
					public void run(){
						
					}
				}
				);
//		checkBox.performClick();
//		assertTrue(checkBox.isChecked());
	}
	
}
