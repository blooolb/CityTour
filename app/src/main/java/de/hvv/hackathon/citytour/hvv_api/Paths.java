package de.hvv.hackathon.citytour.hvv_api;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Paths extends ArrayList<PathSegment>{
	
	private int length;
	
	public Paths()
	{
		super();
	}
	
	public int getSize()
	{
		return this.size();
	}
	
	public PathSegment getSegmentByIndex(int id)
	{
		return (PathSegment) this.get(id);
	}
	
	public void ValuesFromJSON(JSONArray array) throws JSONException
	{
		length = array.length();
		
		for(int i=0;i<length;i++)
		{
			PathSegment Seg = new PathSegment();
			Seg.ValuesFromJSON(array.optJSONObject(i));
			this.add(Seg);
		}
	}

}
