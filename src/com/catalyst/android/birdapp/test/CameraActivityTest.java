/**
 * 
 */
package com.catalyst.android.birdapp.test;

import com.catalyst.android.birdapp.CameraActivity;
import com.jayway.android.robotium.solo.By;
import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Spinner;




/**
 * Tests for CameraActivity spinners and saved preferences. I tried to make these as flexible as possible.
 * The buttons have to be set in each method because the settings screen overlays on the camera surfaceview
 * so the buttons and spinners are not created/populated until the settings button is clicked.
 * @author hyoung
 *
 */
public class CameraActivityTest extends ActivityInstrumentationTestCase2<CameraActivity>{

	private Solo solo;  
	private CameraActivity cActivity;
	private Spinner zoomSpinner;
	private Spinner resolutionSpinner;
	private Spinner whiteBalanceSpinner;
	private Spinner previewSizeSpinner;
	private View defaultButton;
	private View saveButton;
	
	
	public CameraActivityTest() {
		super(CameraActivity.class);
		
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		cActivity = getActivity();
		
		
		setActivityInitialTouchMode(false);
		
	}
	/**
	 * Tests that the zoom spinner saves the specified zoom size
	 */
public void testZoomSettingPreference(){
	int positionNumber = 1;
	solo.clickOnImageButton(0);
	defaultButton = solo.getView(com.catalyst.android.birdapp.R.id.restore_defults_button);
	saveButton = solo.getView(com.catalyst.android.birdapp.R.id.save_button);
	zoomSpinner = (Spinner)cActivity.findViewById(com.catalyst.android.birdapp.R.id.zoom_spinner);
	solo.pressSpinnerItem(0, positionNumber);
	String spinnerTest = zoomSpinner.getItemAtPosition(positionNumber).toString();
	solo.clickOnView(saveButton);
	solo.clickOnImageButton(0);
	String test = zoomSpinner.getSelectedItem().toString();
	assertEquals(spinnerTest, test);
	solo.clickOnView(defaultButton);
	
}
/**
 * Tests that the resolution spinner saves the specified resolution size
 */
public void testResolutionSettingPreference(){
	int positionNumber = 5;
	solo.clickOnImageButton(0);
	defaultButton = solo.getView(com.catalyst.android.birdapp.R.id.restore_defults_button);
	saveButton = solo.getView(com.catalyst.android.birdapp.R.id.save_button);
	resolutionSpinner = (Spinner)cActivity.findViewById(com.catalyst.android.birdapp.R.id.resolution_spinner);
	solo.pressSpinnerItem(1, positionNumber);
	String spinnerTest = resolutionSpinner.getItemAtPosition(positionNumber).toString();
	solo.clickOnView(saveButton);
	solo.clickOnImageButton(0);
	String test = resolutionSpinner.getSelectedItem().toString();
	assertEquals(spinnerTest, test);
	solo.clickOnView(defaultButton);
}
/**
 * Tests that the preview spinner saves the specified preview size
 */
public void testPreviewSettingPreference(){
	int positionNumber = 5;
	solo.clickOnImageButton(0);
	defaultButton = solo.getView(com.catalyst.android.birdapp.R.id.restore_defults_button);
	saveButton = solo.getView(com.catalyst.android.birdapp.R.id.save_button);
	previewSizeSpinner = (Spinner)cActivity.findViewById(com.catalyst.android.birdapp.R.id.picture_size_spinner);
	solo.pressSpinnerItem(2, positionNumber);
	String spinnerTest = previewSizeSpinner.getItemAtPosition(positionNumber).toString();
	solo.clickOnView(saveButton);
	solo.clickOnImageButton(0);
	String test = previewSizeSpinner.getSelectedItem().toString();
	assertEquals(spinnerTest, test);
	solo.clickOnView(defaultButton);
}

/**
 * Tests that the white balance spinner saves the specified white balance setting
 */

public void testWhiteBalanceSettingPreference(){
	int positionNumber = 3;
	solo.clickOnImageButton(0);
	defaultButton = solo.getView(com.catalyst.android.birdapp.R.id.restore_defults_button);
	saveButton = solo.getView(com.catalyst.android.birdapp.R.id.save_button);
	whiteBalanceSpinner = (Spinner)cActivity.findViewById(com.catalyst.android.birdapp.R.id.white_balance_spinner);
	solo.pressSpinnerItem(3, positionNumber);
	String spinnerTest = whiteBalanceSpinner.getItemAtPosition(positionNumber).toString();
	solo.clickOnView(saveButton);
	solo.clickOnImageButton(0);
	String test = whiteBalanceSpinner.getSelectedItem().toString();
	assertEquals(spinnerTest, test);
	solo.clickOnView(defaultButton);
}
}
