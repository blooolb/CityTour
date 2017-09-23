package de.hvv.hackathon.citytour.hvv_api;

import java.util.ArrayList;

import org.json.JSONObject;

public class Departure{
	
	private int serviceID;
	private LineService line;
	private GTITime referenzZeit;
	private int timeOffset;
	private SDName childStation;
	private String platform;
	private int delay;
	private boolean extra;
	private boolean cancelled;
	private String realtimePlatform;
	private String attributes;
	
	private int length;
	
	
	Departure()
	{
		
	}
	
	public GTITime getReferenzTime()
	{
		return this.referenzZeit;
	}
	public int getTimeOffset()
	{
		return this.timeOffset;
	}
	
	public LineService getLine()
	{
		return this.line;
	}
	
	public int getServiceID()
	{
		return this.serviceID;
	}
	
	public String getPlatform()
	{
		return this.platform;
	}
	
	public String getRealtimePlatform()
	{
		return this.realtimePlatform;
	}
	
	public int getDelay()
	{
		return this.delay;
	}
	
	public boolean getExtra()
	{
		return this.extra;
	}
	
	public boolean getCancelled()
	{
		return this.cancelled;
	}
	
	public String getAttributes()
	{
		return this.attributes;
	}
	
	public SDName getChildStation()
	{
		return this.childStation;
	}
	
	public void ValuesFromJSON(JSONObject raw, GTITime Zeit)
	{
		referenzZeit = Zeit;
		line = new LineService();
		line.ValuesFromJSON(raw.getJSONObject("line"));
		timeOffset = raw.optInt("timeOffset");
		childStation = new SDName();
		childStation.newFromJSONObject(raw.optJSONObject("station"));
		serviceID = raw.optInt("serviceId");
		platform = raw.optString("platform");
		delay = raw.optInt("delay");
		extra = raw.optBoolean("extra", false);
		cancelled = raw.optBoolean("cancelled", false);
		realtimePlatform = raw.optString("realtimePlatform");
		attributes = raw.optString("attributes");
	}

}
