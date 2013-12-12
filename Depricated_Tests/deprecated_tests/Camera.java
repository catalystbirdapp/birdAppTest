package deprecated_tests;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R.id;
import com.catalyst.android.birdapp.R.string;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

public class Camera extends ActivityInstrumentationTestCase2<BirdFormActivity> {

	private Solo solo;

	private int idSettingTitleText;
	private int idZoomTitleText;
	private int idResolutionTitleText;
	private int idPictureTitleText;
	private int idWhiteBalanceTitleText;
	private int idSaveButtonText;
	private int idRestoreDefaultButtonText;

	private String settingTitleText;
	private String zoomTitleText;
	private String resolutionTitleText;
	private String pictureTitleText;
	private String whiteBalanceTitleText;
	private String saveButtonText;
	private String restoreDefaultButtonText;

	public Camera() {
		super(BirdFormActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		solo = new Solo(getInstrumentation(), getActivity());

		idSettingTitleText = string.settings_title;
		idZoomTitleText = string.zoom_title;
		idResolutionTitleText = string.resolution_title;
		idPictureTitleText = string.picture_size_title;
		idWhiteBalanceTitleText = string.white_balance_title;
		idSaveButtonText = string.save_button;
		idRestoreDefaultButtonText = string.restore_defaults;

		settingTitleText = getActivity().getResources().getString(
				idSettingTitleText);
		zoomTitleText = getActivity().getResources().getString(idZoomTitleText);
		resolutionTitleText = getActivity().getResources().getString(
				idResolutionTitleText);
		pictureTitleText = getActivity().getResources().getString(
				idPictureTitleText);
		pictureTitleText = pictureTitleText.substring(0, 4);
		whiteBalanceTitleText = getActivity().getResources().getString(
				idWhiteBalanceTitleText);
		whiteBalanceTitleText = whiteBalanceTitleText.substring(0, 4);
		saveButtonText = getActivity().getResources().getString(idSaveButtonText);
		restoreDefaultButtonText = getActivity().getResources().getString(idRestoreDefaultButtonText);
		restoreDefaultButtonText = restoreDefaultButtonText.substring(0,4);
	}

	/**
	 * When I click on the camera button, the camera will open.
	 */
	public void testOpenCamera() {
		solo.assertCurrentActivity("testOpenCamera", BirdFormActivity.class);
		solo.clickOnActionBarItem(id.action_camera);
		solo.sleep(2000);
		solo.finishOpenedActivities();
	}

	/**
	 * When I click on the camera button, the camera will open. When I click on
	 * the image button I want to see the CameraSettingOverlay I want to verify
	 * that the text is present for the overlay title.
	 */
	public void testClickOnImageButton() {
		solo.assertCurrentActivity("testClickOnImageButton",
				BirdFormActivity.class);

		solo.clickOnActionBarItem(id.action_camera);
		solo.clickOnImageButton(0);
		solo.waitForText(settingTitleText);
		assertTrue(solo.searchText(settingTitleText));
		assertTrue(solo.searchText(zoomTitleText));
		assertTrue(solo.searchText(resolutionTitleText));
		assertTrue(solo.searchText(pictureTitleText));
		assertTrue(solo.searchText(whiteBalanceTitleText));
		assertTrue(solo.searchText(saveButtonText));
		assertTrue(solo.searchText(restoreDefaultButtonText));

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
