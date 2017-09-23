package de.hvv.hackathon.citytour.hvv_api;

import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hvv_api.SDType;

public class SDName {
	
    private String combinedName;
    private String name;
	private String city;
	private String id;
	private String type;
	private JSONObject coordinate;
	private double coordinateX;
	private double coordinateY;
	private String serviceTypes;
	

	
	public SDName(String combinedName, SDType type)
	{
		this.combinedName = combinedName;
		this.type = type.toString();

	}
	public SDName()
	{
		
	}
	
	public void completWithCheckNameResponse (JSONObject ResponsBody, int index)
	{
		JSONObject StationObject = new JSONObject ();
		if (ResponsBody.getString("returnCode").equals("OK")) {
			StationObject = ResponsBody.getJSONArray("results").getJSONObject(index);
			this.newFromJSONObject(StationObject);
		}
	}
	
	public void newFromJSONObject (JSONObject raw)
	{
			this.name = raw.optString("name");
			this.city = raw.optString("city");
			this.combinedName = raw.optString("combinedName");
			this.id = raw.optString("id");
			this.type = raw.optString("type");
			this.coordinate = raw.optJSONObject("coordinate");
			if(this.type.equals("STATION"))
			{
				this.serviceTypes = raw.optJSONArray("serviceTypes").toString();
			}
			else
			{
				this.serviceTypes = "[Keine Station]";
			}
	}
	
	public String getCombinedName() {
		return this.combinedName;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getType() {
		return this.type;
	}
	
	public double getCoordinateX() {
		return this.coordinate.optDouble("x");
	}
	public double getCoordinateY() {
		return this.coordinate.optDouble("y");
	}
	
	public JSONObject getCoordinates() {
		return this.coordinate;
	}
	
	public String getServiceTypes() {
		return this.serviceTypes;
	}
	
    public JSONObject getCombinedNameAsBody() {
        try {
        	JSONObject body = new JSONObject();
            body.put("combinedName", this.combinedName);
            return body;
        } catch (JSONException e) {
            return null;
        }
    }
    
    public JSONObject getBody() {
        try {
        	JSONObject body = new JSONObject();
            body.put("combinedName", this.combinedName);
            body.put("name", this.name);
            body.put("city", this.city);
            body.put("id", this.id);
            body.put("type", this.type);
            body.put("coordinate", this.coordinate);
            body.put("serviceTypes", this.serviceTypes);
            
            return body;
        } catch (JSONException e) {
            return null;
        }
    }

}
