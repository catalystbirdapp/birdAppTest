package com.catalyst.android.birdapp.test;




import com.catalyst.android.birdapp.AddNewActivity;
import com.catalyst.android.birdapp.BirdFormActivity;
import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class AddActivity2 extends ActivityInstrumentationTestCase2<AddNewActivity> {
	private AddNewActivity addNewActivity;
	private BirdFormActivity bfActivity;
	private CheckBox checkBox;
	private Button saveButton;
	private EditText editText;
	private static final String testString = "junitTest";
	private Solo solo;
	
	public AddActivity2() {
		super(AddNewActivity.class);
		
	}

	protected void setUp() throws Exception {
		super.setUp();
		addNewActivity = getActivity();
		solo = new Solo(getInstrumentation(), getActivity());
		checkBox = (CheckBox)addNewActivity.findViewById(com.catalyst.android.birdapp.R.id.add_another_button);
		saveButton = (Button)addNewActivity.findViewById(com.catalyst.android.birdapp.R.id.saveButton);
		editText = (EditText)addNewActivity.findViewById(com.catalyst.android.birdapp.R.id.activityName);
	}

	public void testCheckBoxAddActivityUnChecked(){
		
	
			        	
			        	solo.enterText(editText, "junitTest");
			        	solo.clickOnCheckBox(0);
			        	solo.clickOnButton("Save");
			        	solo.assertCurrentActivity("on AddNewActivity", AddNewActivity.class);
			        	solo.enterText(editText, "junitTest2");
			        	solo.clickOnButton("Save");
			        	solo.finishOpenedActivities();
			        	solo.assertCurrentActivity("on BirdActivityForm", BirdFormActivity.class);
			        	
	}



	
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}



}
