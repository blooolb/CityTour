package de.hvv.hackathon.citytour.hvv_api;

import org.json.JSONObject;

public class LineService {
	
	private String name;
	private String direction;
	private String simpleType;
	private String shortInfo;
	private String longInfo;

	LineService()
	{
		
	}
	
	public String getName()
	{
		return this.name;
	}
	public String getDirection()
	{
		return this.direction;
	}
	public String getTypeSimple()
	{
		return this.simpleType;
	}
	public String getTypeInfoShort()
	{
		return this.shortInfo;
	}
	public String getTypeInfoLong()
	{
		return this.longInfo;
	}
	
	public void ValuesFromJSON(JSONObject raw)
	{
		name = raw.optString("name");
		direction = raw.optString("direction");
		simpleType = raw.getJSONObject("type").optString("simpleType");
		shortInfo = raw.getJSONObject("type").optString("shortInfo");
		longInfo = raw.getJSONObject("type").optString("longInfo");


	}

}
