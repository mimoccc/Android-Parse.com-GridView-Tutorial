package com.androidbegin.parsegridview;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends Activity {
	// Declare Variables
	GridView gridview;
	List<ParseObject> ob;
	ProgressDialog mProgressDialog;
	GridViewAdapter adapter;
	private List<PhoneList> phonearraylist = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from gridview_main.xml
		setContentView(R.layout.gridview_main);
		// Execute RemoteDataTask AsyncTask
		new RemoteDataTask().execute();
	}

	// RemoteDataTask AsyncTask
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(MainActivity.this);
			// Set progressdialog title
			mProgressDialog.setTitle("Parse.com GridView Tutorial");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Create the array
			phonearraylist = new ArrayList<PhoneList>();
			try {
				// Locate the class table named "SamsungPhones" in Parse.com
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
						"SamsungPhones");
				// Locate the column named "position" in Parse.com and order list
				// by ascending
				query.orderByAscending("position");
				ob = query.find();
				for (ParseObject country : ob) {
					ParseFile image = (ParseFile) country.get("phones");
					PhoneList map = new PhoneList();
					map.setPhone(image.getUrl());
					phonearraylist.add(map);
				}
			} catch (ParseException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// Locate the gridview in gridview_main.xml
			gridview = (GridView) findViewById(R.id.gridview);
			// Pass the results into ListViewAdapter.java
			adapter = new GridViewAdapter(MainActivity.this,
					phonearraylist);
			// Binds the Adapter to the ListView
			gridview.setAdapter(adapter);
			// Close the progressdialog
			mProgressDialog.dismiss();
		}
	}
}