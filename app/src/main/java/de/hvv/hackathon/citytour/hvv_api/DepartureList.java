package de.hvv.hackathon.citytour.hvv_api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DepartureList extends ArrayList{
	
	private int length;
	private GTITime Uhrzeit;
	
	
	public DepartureList()
	{
		super();
	}
	
	public int getSize()
	{
		return this.size();
	}
	
	public Departure getDepartureByIndex(int id)
	{
		return (Departure) this.get(id);
	}
	
	public void ValuesFromJSON(JSONObject raw) throws Exception
	{
		Uhrzeit = new GTITime(
				raw.getJSONObject("time").optString("date"),
				raw.getJSONObject("time").optString("time"));
		
		JSONArray array = raw.getJSONArray("departures");
		length = array.length();
		
		for(int i=0;i<length;i++)
		{
			Departure Halt = new Departure();
			Halt.ValuesFromJSON(array.getJSONObject(i),Uhrzeit);
			this.add(Halt);
		}
	}

}
