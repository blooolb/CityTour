package de.hvv.hackathon.citytour.hvv_api;

import org.json.JSONObject;

public class Coordinate{
	
	private double x;
	private double y;
	JSONObject coordinate;
	
	Coordinate(JSONObject raw)
	{
		this.coordinate = raw;
		this.x = this.coordinate.optDouble("x");
		this.y = this.coordinate.optDouble("y");
	}
	
	public double getCoordinateX() {
		return this.x;
	}
	public double getCoordinateY() {
		return this.y;
	}
}
