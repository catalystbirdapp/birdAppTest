package com.catalyst.android.birdapp.test;

import com.catalyst.android.birdapp.BirdFormActivity;

import com.catalyst.android.birdapp.R.string;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

import android.widget.Spinner;

/**
 * @author abarendt To run these tests, the application needs to be open on the
 *         phone, so the splash screen can be avoided.
 */
public class Spinner_Category extends
		ActivityInstrumentationTestCase2<BirdFormActivity> {
	private int idCategoryPrompt;
	private int idCategoryLabel;

	private int idNest;
	private int idSighting;
	private int idMisc;

	private String categoryPrompt;
	private String categoryLabel;

	private String nest;
	private String sighting;
	private String misc;

	private Spinner categorySpinner;

	private Solo solo;

	public Spinner_Category() {
		super(BirdFormActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		solo = new Solo(getInstrumentation(), getActivity());

		categorySpinner = solo.getView(Spinner.class, 1);
		idCategoryPrompt = string.categoryPrompt;
		idCategoryLabel = string.categoryText;

		idNest = string.nest;
		idSighting = string.sighting;
		idMisc = string.misc;

		categoryPrompt = getActivity().getResources().getString(
				idCategoryPrompt);
		categoryLabel = getActivity().getResources().getString(idCategoryLabel);

		nest = getActivity().getResources().getString(idNest);
		sighting = getActivity().getResources().getString(idSighting);
		misc = getActivity().getResources().getString(idMisc);
	}

	/**
	 * When I click on the text box for the Category spinner, A box will open
	 * with the choices for the Category spinner; Verify "Nest" is in the list;
	 * Click on the word "Nest"; Verify that the box goes away by looking for
	 * the word "Category". Verify that "Nest" was placed in the text box.
	 * Verify that the index of the word "Nest" will also trigger "Nest" to be
	 * chosen.
	 */
	public void testIndexOneZero_Nest() {
		solo.assertCurrentActivity("testActivityPrompt_Nest_index (1,0)",
				BirdFormActivity.class);

		// solo.enterText(birdName, activityLabel);
		solo.searchText(categoryPrompt);
		// solo.enterText(birdName, "");

		solo.clickOnView(categorySpinner); // opens up the category spinner

		solo.waitForText(categoryPrompt);
		solo.clickOnText(nest);

		solo.waitForText(categoryLabel);
		solo.searchText(nest);

		// able to click on the (1,0) index
		solo.pressSpinnerItem(1, 0);
		solo.waitForText(nest);
		solo.finishOpenedActivities();
	}

	/**
	 * When I click on the text box for the Category spinner, A box will open
	 * with the choices for the Category spinner; Verify "Sighting" is in the
	 * list; Click on the word "Sighting"; Verify that the box goes away by
	 * looking for the word "Category". Verify that "Sighting" was placed in the
	 * text box. Verify that the index of the word "Sighting" will also trigger
	 * "Sighting" to be chosen.
	 */
	public void testIndexOneOne_Sighting() {
		solo.assertCurrentActivity("testActivityPrompt_Sighting_index (1,1)",
				BirdFormActivity.class);

		solo.searchText(categoryPrompt);

		solo.clickOnView(categorySpinner);

		solo.waitForText(categoryPrompt);
		solo.clickOnText(sighting);

		solo.waitForText(categoryLabel);
		solo.searchText(sighting);

		// able to click on the (1,0) index
		solo.pressSpinnerItem(1, 0);
		solo.waitForText(sighting);
		solo.finishOpenedActivities();
	}

	/**
	 * When I click on the text box for the Category spinner, A box will open
	 * with the choices for the Category spinner; Verify "Misc" is in the list;
	 * Click on the word "Misc"; Verify that the box goes away by looking for
	 * the word "Misc". Verify that "Misc" was placed in the text box. Verify
	 * that the index of the word "Misc" will also trigger "Misc" to be chosen.
	 */
	public void testIndexOneTwo_Misc() {
		solo.assertCurrentActivity("testActivityPrompt_Misc_index (1,2)",
				BirdFormActivity.class);

		solo.searchText(categoryPrompt);
		solo.clickOnView(categorySpinner); 

		solo.waitForText(categoryPrompt);
		solo.clickOnText(misc);

		solo.waitForText(categoryLabel);
		solo.searchText(misc);

		// able to click on the (1,2) index
		solo.pressSpinnerItem(1, 2);
		solo.waitForText(misc);
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
