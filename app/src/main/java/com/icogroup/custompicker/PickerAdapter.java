package com.icogroup.custompicker;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.icogroup.dallassuites.R;

import java.util.ArrayList;

public class PickerAdapter extends BaseAdapter {

	Activity activity;
	int mid;
	Holder holder = null; 
	ArrayList<String> data;
	
	public PickerAdapter(Activity activity, ArrayList<String> data) {
		this.activity = activity;
		this.data = data;
		mid = 1;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 //  View row = convertView;
		holder = null;  		    
	    if(convertView == null)
	    {
	        convertView = activity.getLayoutInflater().inflate(R.layout.item_picker, parent, false);	            
	        holder = new Holder();  
	        holder.texto = ((TextView)convertView.findViewById(R.id.textView1));  
	        convertView.setTag(holder); 	            
	    }
	    else
	    {
	        holder = (Holder)convertView.getTag();
	    }	        	

	    holder.texto.setText(data.get(position));
	        
	    if(position == 0 || position ==  (data.size()-1) )
	    {
			holder.texto.setTextColor(Color.TRANSPARENT);
	    }
	    else if(position == mid)
	    {
			holder.texto.setTextColor(Color.argb(255, 223, 188, 149));
			if(activity != null)
			{
				Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "brandon_light.otf");
				holder.texto.setTypeface(typeface);		
			}				
		}
		else 
		{
			holder.texto.setTextColor(Color.argb(127, 241, 226, 211));
			if(activity != null)
			{
				Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "brandon_light.otf");
				holder.texto.setTypeface(typeface);	
			}
									
		}		
		return convertView;
	}
	
	public void setMid(int position){
		mid = position;
		notifyDataSetChanged();
	}
	
	public String getMid(){
		return data.get(mid);
	}
	
	public int getMidPosition(){
		return mid;
	}
	
	public class Holder {
		TextView texto;
	}
	
	public void dispose(){
		activity = null;
	}
	
	public void setActivity(Activity activity){
		this.activity = activity;
	}

	public void actData(ArrayList<String> days) {
		int sizeBefore = data.size();
		this.data = days;
		int sizeAfter = data.size();
		
		if( (sizeAfter < sizeBefore) && (mid > sizeAfter - 2) )
			mid = sizeAfter - 2;

		notifyDataSetChanged();
	}
		
}
