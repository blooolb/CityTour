package de.hvv.hackathon.citytour.hvv_api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class PathSegment extends ArrayList<Coordinate>{
	
	private int length;
	private String attributte;
	
	public PathSegment()
	{
		super();
	}
	
	public int getSize()
	{
		return this.size();
	}
	
	public Coordinate getCoordinateByIndex(int id)
	{
		return (Coordinate) this.get(id);
	}
	
	public String getAttribute()
	{
		return this.attributte;
	}
	
	public void ValuesFromJSON(JSONObject raw)
	{
		
		attributte = raw.getJSONArray("attributes").getString(0);
			
		JSONArray array = raw.getJSONArray("track");
		length = array.length();
		
		for(int i=0;i<length;i++)
		{
			Coordinate longlat = new Coordinate(array.getJSONObject(i));
			this.add(longlat);
		}
	}

}
