package de.hvv.hackathon.citytour.hvv_api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

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
	
	public void ValuesFromJSON(JSONArray array)
	{
		length = array.length();
		
		for(int i=0;i<length;i++)
		{
			PathSegment Seg = new PathSegment();
			Seg.ValuesFromJSON(array.getJSONObject(i));
			this.add(Seg);
		}
	}

}
