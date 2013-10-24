package com.catalyst.android.birdapp.test;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R.id;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;


public class Camera extends ActivityInstrumentationTestCase2<BirdFormActivity> {

	private Solo solo;
	public Camera() {
		super(BirdFormActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		solo = new Solo(getInstrumentation(), getActivity());
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
