package deprecated_tests;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R.id;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;


public class Camera_Open extends ActivityInstrumentationTestCase2<BirdFormActivity> {

	private Solo solo;
	public Camera_Open() {
		super(BirdFormActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		solo = new Solo(getInstrumentation(), getActivity());
	}

	/**
	 * When I open PutABirdOnIt and the GPS is turned on, I can click on the camera button, and the camera will open.
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
