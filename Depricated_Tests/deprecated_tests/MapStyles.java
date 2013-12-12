package deprecated_tests;

import android.test.ActivityInstrumentationTestCase2;

import com.catalyst.android.birdapp.BirdFormActivity;
import com.catalyst.android.birdapp.R.id;
import com.catalyst.android.birdapp.R.string;

import com.jayway.android.robotium.solo.Solo;

/**
 * @author abarendt
 * 
 */
public class MapStyles extends
		ActivityInstrumentationTestCase2<BirdFormActivity> {
	private Solo solo;

	private int idNormal;
	private int idSatellite;
	private int idTerrain;
	private int idHybrid;

	private String normalText;
	private String satelliteText;
	private String terrainText;
	private String hybridText;
	private String saveButtonText;
	private String mapTypeText;
	
	private Boolean currentFragmentId;
	private Boolean savedFragmentId;

	public MapStyles() {
		super(BirdFormActivity.class);

	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		
		idNormal = id.normal;
		idSatellite = id.satellite;
		idTerrain = id.terrain;
		idHybrid = id.hybrid;
     
		mapTypeText = getActivity().getResources().getString(string.map_type);// "Map Type:"
		normalText = getActivity().getResources().getString(string.normal);
		satelliteText = getActivity().getResources()
				.getString(string.satellite);
		terrainText = getActivity().getResources().getString(string.terrain);
		hybridText = getActivity().getResources().getString(string.hybrid);
		saveButtonText = getActivity().getResources().getString(
				string.save_button);
	}

	/**
	 * When the map is open I can click on the iconButton with a gear on it.
	 * A selection spinner will open and allow me to choose a map type for my base map.
	 * When I select the satellite style, the satellite style will load on my device.
	 * When I click save, the map style will be saved.
	 * When I reopen the map, I will still see the satellite view.
	 */
	public void testSatelliteStyle() {
		solo.assertCurrentActivity("testSatelliteStyle", BirdFormActivity.class);
		solo.clickOnActionBarItem(id.action_map);
		solo.clickOnImageButton(0);
		solo.waitForText(mapTypeText);
		solo.clickOnView(solo.getCurrentActivity().findViewById(
				id.map_type_spinner));
		solo.clickOnText(satelliteText);
		solo.waitForText(satelliteText);
		solo.clickOnButton(saveButtonText);
		
		// asserts that the value I am calling on is the value I was targeting. 
		// assertEquals(idSatellite, 0x7f060002);
		 
		savedFragmentId = solo.waitForFragmentById(idSatellite);
		solo.goBack();
		solo.sleep(2000);
		solo.clickOnActionBarItem(id.action_map);
		currentFragmentId = solo.waitForFragmentById(idSatellite);
		assertTrue(savedFragmentId.equals(currentFragmentId));
		solo.goBack();
		solo.finishOpenedActivities();
	}

	/**
	 * When the map is open I can click on the iconButton with a gear on it.
	 * A selection spinner will open and allow me to choose a map type for my base map.
	 * When I select the terrain style, the terrain style will load on my device.
	 * When I click save, the map style will be saved.
	 * When I reopen the map, I will still see the terrain view.
	 */
	public void testTerrainStyle() {
		solo.assertCurrentActivity("testTerrainStyle", BirdFormActivity.class);
		solo.clickOnActionBarItem(id.action_map);
		solo.clickOnImageButton(0);
		solo.waitForText(mapTypeText);
		solo.clickOnView(solo.getCurrentActivity().findViewById(
				id.map_type_spinner));
		solo.clickOnText(terrainText);
		solo.waitForText(terrainText);
		solo.clickOnButton(saveButtonText);
		
		 //asserts that the value I am calling on is the value I was targeting. 
		// assertEquals(idTerrain, 0x7f060003);
		 
		savedFragmentId = solo.waitForFragmentById(idTerrain);
		solo.goBack();
		solo.sleep(2000);
		solo.clickOnActionBarItem(id.action_map);
		currentFragmentId = solo.waitForFragmentById(idTerrain);
		assertTrue(savedFragmentId.equals(currentFragmentId));
		solo.goBack();
		solo.finishOpenedActivities();
	}

	/**
	 * When the map is open I can click on the iconButton with a gear on it.
	 * A selection spinner will open and allow me to choose a map type for my base map.
	 * When I select the hybrid style, the hybrid style will load on my device.
	 * When I click save, the map style will be saved.
	 * When I reopen the map, I will still see the hybrid view.
	 */
	public void testHybridStyle() {
		solo.assertCurrentActivity("testHybridStyle", BirdFormActivity.class);
		solo.clickOnActionBarItem(id.action_map);
		solo.clickOnImageButton(0);
		solo.waitForText(mapTypeText);
		
		solo.clickOnView(solo.getCurrentActivity().findViewById(
				id.map_type_spinner));
		solo.clickOnText(hybridText);
		solo.waitForText(hybridText);
		solo.clickOnButton(saveButtonText);
		
		 //asserts that the value I am calling on is the value I was targeting. 
		// assertEquals(idHybrid, 0x7f060004);
	
		savedFragmentId = solo.waitForFragmentById(idHybrid);
		solo.goBack();
		solo.sleep(2000);
		solo.clickOnActionBarItem(id.action_map);
		currentFragmentId = solo.waitForFragmentById(idHybrid);
		assertTrue(savedFragmentId.equals(currentFragmentId));
		solo.goBack();
		solo.finishOpenedActivities();
	}

	/**
	 * When the map is open I can click on the iconButton with a gear on it.
	 * A selection spinner will open and allow me to choose a map type for my base map.
	 * When I select the normal style, the normal style will load on my device.
	 * When I click save, the map style will be saved.
	 * When I reopen the map, I will still see the normal view.
	 */
	public void testNormalStyle() {
		solo.assertCurrentActivity("testNormalStyles", BirdFormActivity.class);
		solo.clickOnActionBarItem(id.action_map);
		solo.clickOnImageButton(0);
		solo.waitForText(mapTypeText);
		solo.clickOnView(solo.getCurrentActivity().findViewById(
				id.map_type_spinner));
		solo.clickOnText(normalText);
		solo.waitForText(normalText);
		solo.clickOnButton(saveButtonText);
		
		  //asserts that the value I am calling on is the value I was targeting. 
		// assertEquals(idNormal, 0x7f060001);
		 
		savedFragmentId = solo.waitForFragmentById(idNormal);// boolean return
		solo.goBack();
		solo.sleep(2000);
		solo.clickOnActionBarItem(id.action_map);

		currentFragmentId = solo.waitForFragmentById(idNormal);// boolean return
		assertTrue(savedFragmentId.equals(currentFragmentId));
		solo.goBack();

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
