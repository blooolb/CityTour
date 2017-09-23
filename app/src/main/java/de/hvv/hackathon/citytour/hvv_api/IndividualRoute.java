package de.hvv.hackathon.citytour.hvv_api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IndividualRoute{
	
	private SDName start;
	private SDName dest;
	private JSONArray path;
	private int length;
	private int time;
	private String serviceType;
		
	public IndividualRoute()
	{
		
	}
	
	public SDName getStart()
	{
		return this.start;
	}
	public SDName getDest()
	{
		return this.dest;
	}
	
	public int getLength()
	{
		return this.length;
	}
	
	public int getTime()
	{
		return this.time;
	}
	
	public String getServiceType()
	{
		return this.serviceType;
	}
	
	public JSONArray getPath()
	{
		return this.path;
	}
	
	public void ValuesFromJSON(JSONObject raw) throws JSONException
	{
		JSONArray array = raw.getJSONArray("routes");
		JSONObject route = array.getJSONObject(0);
		time = route.optInt("time");
		length = route.optInt("length");
		start = new SDName();
		start.newFromJSONObject(route.optJSONObject("start"));
		dest = new SDName();
		dest.newFromJSONObject(route.optJSONObject("dest"));
		serviceType = route.optString("serviceType");
		path = route.optJSONArray("paths");
	
	}

}
