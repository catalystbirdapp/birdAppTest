/**
 * 
 */
package com.catalyst.android.birdapp.test;

import java.util.Locale;

import com.catalyst.android.birdapp.CameraActivity;
import com.jayway.android.robotium.solo.Solo;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
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
	private Camera camera;
	private Parameters params;
	
	
	public CameraActivityTest() {
		super(CameraActivity.class);
		
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		cActivity = getActivity();
		camera = cActivity.getmCamera();
		params = cActivity.getParameters();
		setActivityInitialTouchMode(false);
		
	}
	/**
	 * Tests that the zoom spinner saves the specified zoom size
	 */
public void testZoomSettingPreference(){
	int positionNumber = 2;
	solo.clickOnImageButton(1);
	defaultButton = solo.getView(com.catalyst.android.birdapp.R.id.restore_defults_button);
	saveButton = solo.getView(com.catalyst.android.birdapp.R.id.save_button);
	zoomSpinner = (Spinner)cActivity.findViewById(com.catalyst.android.birdapp.R.id.zoom_spinner);
	solo.pressSpinnerItem(0, positionNumber);
	String spinnerTest = zoomSpinner.getItemAtPosition(positionNumber).toString();
	solo.clickOnView(saveButton);
	solo.clickOnImageButton(1);
	String test = zoomSpinner.getSelectedItem().toString();
	assertEquals(spinnerTest, test);
	solo.clickOnView(defaultButton);
	solo.finishOpenedActivities();
	
}
/**
 * Tests that the resolution spinner saves the specified resolution size
 */
public void testzResolutionSettingPreference(){
	int positionNumber = 2;
	solo.clickOnImageButton(1);
	defaultButton = solo.getView(com.catalyst.android.birdapp.R.id.restore_defults_button);
	saveButton = solo.getView(com.catalyst.android.birdapp.R.id.save_button);
	resolutionSpinner = (Spinner)cActivity.findViewById(com.catalyst.android.birdapp.R.id.resolution_spinner);
	solo.pressSpinnerItem(1, positionNumber);
	String spinnerTest = resolutionSpinner.getItemAtPosition(positionNumber).toString();
	solo.clickOnView(saveButton);
	solo.clickOnImageButton(1);
	String test = resolutionSpinner.getSelectedItem().toString();
	assertEquals(spinnerTest, test);
	solo.clickOnView(defaultButton);
	solo.finishOpenedActivities();
}
/**
 * Tests that the preview spinner saves the specified preview size
 */
public void testzPreviewSettingPreference(){
	int positionNumber = 2;
	solo.clickOnImageButton(1);
	defaultButton = solo.getView(com.catalyst.android.birdapp.R.id.restore_defults_button);
	saveButton = solo.getView(com.catalyst.android.birdapp.R.id.save_button);
	previewSizeSpinner = (Spinner)cActivity.findViewById(com.catalyst.android.birdapp.R.id.picture_size_spinner);
	solo.pressSpinnerItem(2, positionNumber);
	String spinnerTest = previewSizeSpinner.getItemAtPosition(positionNumber).toString();
	solo.clickOnView(saveButton);
	solo.clickOnImageButton(1);
	String test = previewSizeSpinner.getSelectedItem().toString();
	assertEquals(spinnerTest, test);
	solo.clickOnView(defaultButton);
	solo.finishOpenedActivities();
}

/**
 * Tests that the white balance spinner saves the specified white balance setting
 */

public void testWhiteBalanceSettingPreference(){
	int positionNumber = 3;
	solo.clickOnImageButton(1);
	defaultButton = solo.getView(com.catalyst.android.birdapp.R.id.restore_defults_button);
	saveButton = solo.getView(com.catalyst.android.birdapp.R.id.save_button);
	whiteBalanceSpinner = (Spinner)cActivity.findViewById(com.catalyst.android.birdapp.R.id.white_balance_spinner);
	solo.pressSpinnerItem(3, positionNumber);
	String spinnerTest = whiteBalanceSpinner.getItemAtPosition(positionNumber).toString();
	solo.clickOnView(saveButton);
	solo.clickOnImageButton(1);
	String test = whiteBalanceSpinner.getSelectedItem().toString();
	assertEquals(spinnerTest, test);
	solo.clickOnView(defaultButton);
	solo.finishOpenedActivities();
}

/**
 * Tests the restore defaults button
 */

public void testRestoreDefaultsButton(){
	int positionNumber = 3;
	solo.clickOnImageButton(1);
	defaultButton = solo.getView(com.catalyst.android.birdapp.R.id.restore_defults_button);
	saveButton = solo.getView(com.catalyst.android.birdapp.R.id.save_button);
	whiteBalanceSpinner = (Spinner)cActivity.findViewById(com.catalyst.android.birdapp.R.id.white_balance_spinner);
	solo.pressSpinnerItem(3, positionNumber);
	solo.clickOnView(saveButton);
	solo.clickOnImageButton(1);
	solo.clickOnView(defaultButton);
	assertEquals(whiteBalanceSpinner.getSelectedItem().toString(), whiteBalanceSpinner.getItemAtPosition(0));
	solo.finishOpenedActivities();
}
/**
 * tests to make sure that the white balance parameters are being set on click on the save button
 */
public void testParametersSetWhiteBalance(){
	int positionNumber = 5;
	solo.clickOnImageButton(1);
	defaultButton = solo.getView(com.catalyst.android.birdapp.R.id.restore_defults_button);
	saveButton = solo.getView(com.catalyst.android.birdapp.R.id.save_button);
	whiteBalanceSpinner = (Spinner)cActivity.findViewById(com.catalyst.android.birdapp.R.id.white_balance_spinner);
	solo.pressSpinnerItem(3, positionNumber);
	String test = whiteBalanceSpinner.getSelectedItem().toString().toLowerCase(Locale.US);
	solo.clickOnView(saveButton);
	solo.clickOnImageButton(1);
	params = camera.getParameters();
	assertEquals(test, params.getWhiteBalance());
	solo.clickOnView(defaultButton);
	solo.finishOpenedActivities();
}
/**
 * tests to make sure that the preview size parameters are being set on click on the save button
 */
public void testParametersSetPreviewSize(){
	int positionNumber = 2;
	solo.clickOnImageButton(1);
	defaultButton = solo.getView(com.catalyst.android.birdapp.R.id.restore_defults_button);
	saveButton = solo.getView(com.catalyst.android.birdapp.R.id.save_button);
	previewSizeSpinner = (Spinner)cActivity.findViewById(com.catalyst.android.birdapp.R.id.picture_size_spinner);
	solo.pressSpinnerItem(2, positionNumber);
	String spinnerTest = previewSizeSpinner.getItemAtPosition(positionNumber).toString();
	solo.clickOnView(saveButton);
	solo.clickOnImageButton(1);
	params = camera.getParameters();
	Size size = params.getPreviewSize();
	String preview = size.height + " X " + size.width;
	assertEquals(spinnerTest, preview);
	solo.clickOnView(defaultButton);
	solo.finishOpenedActivities();
}

/**
 * tests to make sure that the resolution parameters are being set on click on the save button
 */
public void testParametersSetResolution(){
	int positionNumber = 2;
	solo.clickOnImageButton(1);
	defaultButton = solo.getView(com.catalyst.android.birdapp.R.id.restore_defults_button);
	saveButton = solo.getView(com.catalyst.android.birdapp.R.id.save_button);
	resolutionSpinner = (Spinner)cActivity.findViewById(com.catalyst.android.birdapp.R.id.resolution_spinner);
	solo.pressSpinnerItem(1, positionNumber);
	String spinnerTest = resolutionSpinner.getItemAtPosition(positionNumber).toString();
	solo.clickOnView(saveButton);
	solo.clickOnImageButton(1);
	params = camera.getParameters();
	Size size = params.getPictureSize();
	String resolution = size.height + " X " + size.width;
	assertEquals(spinnerTest, resolution);
	solo.clickOnView(defaultButton);
	solo.finishOpenedActivities();
}

/**
 * tests to make sure that the zoom parameters are being set on click on the save button
 */

public void testParametersSetZoom(){
	int positionNumber = 2;
	String firstNumber;
	String secondNumber;
	solo.clickOnImageButton(1);
	defaultButton = solo.getView(com.catalyst.android.birdapp.R.id.restore_defults_button);
	saveButton = solo.getView(com.catalyst.android.birdapp.R.id.save_button);
	zoomSpinner = (Spinner)cActivity.findViewById(com.catalyst.android.birdapp.R.id.zoom_spinner);
	String spinnerTest = zoomSpinner.getItemAtPosition(positionNumber).toString();
	solo.pressSpinnerItem(0, positionNumber);
	solo.clickOnView(saveButton);
	solo.clickOnImageButton(1);
	params = camera.getParameters();
	int spinnerNumber = zoomSpinner.getCount();	
	if(positionNumber == spinnerNumber - 1){
		assertEquals(params.getZoom(), (spinnerNumber *10));
	}else {
	firstNumber = String.valueOf(params.getZoom()).substring(0,1);
	secondNumber = String.valueOf(params.getZoom()).substring(1,2);
	assertEquals(spinnerTest, firstNumber+"."+secondNumber+"x");
	}
	solo.clickOnView(defaultButton);
	solo.finishOpenedActivities();
}
}
