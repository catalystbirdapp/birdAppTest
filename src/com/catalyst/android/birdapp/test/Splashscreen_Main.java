package com.catalyst.android.birdapp.test;

import java.io.File;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

import com.catalyst.android.birdapp.MainActivity;
import com.catalyst.android.birdapp.R.id;
import com.catalyst.android.birdapp.R.raw;
import com.catalyst.android.birdapp.R.string;

public class Splashscreen_Main extends
		ActivityInstrumentationTestCase2<MainActivity> {
	public Splashscreen_Main() {
		super(MainActivity.class);
	}

	private Solo solo;

	private String SUBMIT_SPLASH_TEXT = "Submit A Sighting";
	private String BIRD_FORM_TITLE = "Bird Sighting Form";

	private int idSplashSubmitButton;
	private int idSplashReviewButton;

	private String splashSubmitButtonText;
	private String splashReviewButtonText;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());

		idSplashSubmitButton = string.submitButton;
		idSplashReviewButton = string.reviewRecords;

		splashSubmitButtonText = getActivity().getResources().getString(
				idSplashSubmitButton);
		splashReviewButtonText = getActivity().getResources().getString(
				idSplashReviewButton);
	}

	/**
	 * Test that the splashscreen activates. .
	 */
	public void testSplashscreen() {
		solo.assertCurrentActivity("Tests the splashscreen is present",
				MainActivity.class);
		solo.waitForText(splashSubmitButtonText);
		solo.waitForText(splashReviewButtonText);
		solo.finishOpenedActivities();
	}

	public void testClickableSubmitButton(){
		solo.assertCurrentActivity("Tests the splashscreen is present",
				MainActivity.class);
		    assertTrue(solo.searchText(splashSubmitButtonText));
			assertTrue(solo.searchText(splashReviewButtonText));

			
			//solo.waitForText(BIRD_FORM_TITLE);
	
		
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
