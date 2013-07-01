package com.androidbegin.parsegridview;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class SingleItemView extends Activity {
	// Declare Variables
	String phone;
	ProgressDialog mProgressDialog;
	Bitmap bmImg = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.singleitemview);
		// Execute loadSingleView AsyncTask
		new loadSingleView().execute();
	}

	public class loadSingleView extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(SingleItemView.this);
			// Set progressdialog title
			mProgressDialog.setTitle("Parse.com GridView Tutorial");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			try {
				// Retrieve data from GridViewAdapter on click event
				Intent i = getIntent();
				// Get the result of phone
				phone = i.getStringExtra("phone");
				// Download the Image from the result URL given by phone
				URL url = new URL(phone);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setDoInput(true);
				conn.connect();
				InputStream is = conn.getInputStream();
				bmImg = BitmapFactory.decodeStream(is);
			} catch (IOException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String args) {
			// Locate the ImageView in singleitemview.xml
			ImageView phone = (ImageView) findViewById(R.id.phone);
			// Set results to the ImageView
			phone.setImageBitmap(bmImg);
			// Close the progressdialog
			mProgressDialog.dismiss();
		}
	}
}