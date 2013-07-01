package com.androidbegin.parsegridview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ImageLoader imageLoader;
	private List<PhoneList> phonearraylist = null;
	private ArrayList<PhoneList> arraylist;

	public GridViewAdapter(Context context,
			List<PhoneList> phonearraylist) {
		this.context = context;
		this.phonearraylist = phonearraylist;
		inflater = LayoutInflater.from(context);
		this.arraylist = new ArrayList<PhoneList>();
		this.arraylist.addAll(phonearraylist);
		imageLoader = new ImageLoader(context);
	}

	public class ViewHolder {
		ImageView phone;
	}

	@Override
	public int getCount() {
		return phonearraylist.size();
	}

	@Override
	public Object getItem(int position) {
		return phonearraylist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.gridview_item, null);
			// Locate the ImageView in gridview_item.xml
			holder.phone = (ImageView) view.findViewById(R.id.phone);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into ImageView
		imageLoader.DisplayImage(phonearraylist.get(position).getPhone(),
				holder.phone);
		// Listen for GridView Item Click
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Send single item click data to SingleItemView Class
				Intent intent = new Intent(context, SingleItemView.class);
				// Pass all data phone
				intent.putExtra("phone",
						(phonearraylist.get(position).getPhone()));
				// Start SingleItemView Class
				context.startActivity(intent);
			}
		});
		return view;
	}
}
