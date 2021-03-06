package de.hvv.hackathon.citytour.hvv_api;

import org.json.JSONException;
import org.json.JSONObject;

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
	private int mode;
	

	
	public SDName(String combinedName, SDType type)
	{
		this.combinedName = combinedName;
		this.type = type.toString();
		this.mode = 1;
	}

	public SDName(double coordinateX, double coordinateY, SDType type)
	{
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
		this.type = type.toString();
		this.mode = 2;
	}
	public SDName()
	{
		
	}
	
	public void completWithCheckNameResponse (JSONObject ResponsBody, int index) throws JSONException
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

	public int getMode()
	{
		return this.mode;

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
	
//	public double getCoordinateX() {
//		return this.coordinate.optDouble("x");
//	}
//	public double getCoordinateY() {
//		return this.coordinate.optDouble("y");
//	}


    public double getCoordinateX() {
        return coordinateX;
    }
    public double getCoordinateY() {
        return coordinateY;
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
