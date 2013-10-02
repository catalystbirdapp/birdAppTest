package com.catalyst.android.birdapp.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import com.catalyst.android.birdapp.AddNewActivity;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.app.Activity;

public class AddActivity2 extends ActivityInstrumentationTestCase2<AddNewActivity> {
	private AddNewActivity addNewActivity;
	private CheckBox checkBox;
	private Button saveButton;
	public AddActivity2() {
		super(AddNewActivity.class);
	}


	protected void setUp() throws Exception {
		super.setUp();
		addNewActivity = getActivity();
		checkBox = (CheckBox)addNewActivity.findViewById(com.catalyst.android.birdapp.R.id.add_another_button);
		saveButton = (Button)addNewActivity.findViewById(com.catalyst.android.birdapp.R.id.saveButton);
	}

	

	public void testCheckBoxAddActivityChecked(){
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(AddNewActivity.class.getName(), null, false);
		
		
		addNewActivity.runOnUiThread(
			      new Runnable() {
			        public void run() {
			        	checkBox.performClick();
			        	saveButton.performClick();
			        	
			        } 
			      } 
			    ); 
		
		Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
		
		assertNotNull(nextActivity);
		  nextActivity.finish();
	}
	
	public void testCheckBoxAddActivityUnChecked(){
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(AddNewActivity.class.getName(), null, false);
		
		
		addNewActivity.runOnUiThread(
			      new Runnable() {
			        public void run() {
			        	saveButton.performClick();
			        	
			        } 
			      } 
			    ); 
		
		Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
		nextActivity.getTitle();
		//assertNotNull(nextActivity);
		assertEquals(nextActivity.getTitle(), "test");
		  nextActivity.finish();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
//	@Test
//
//		 public void testSpinnerUI() {
//
//			 addActivity.runOnUiThread(
//			      new Runnable() {
//			        public void run() {
//			        	checkBox.performClick();
//			        	assertTrue(checkBox.isChecked());
//			        } 
//			      } 
//			    ); 
//	}

}
