package de.hvv.hackathon.citytour.hvv_api;

public enum SDType {
	UNKNOWN("NO_FILTER"),
	STATION("STATION"),
	ADDRESS("ADDRESS"),
	POI("POI"),
	COORDINATE("COORDINATE");

	String type;

	SDType(String type) {
	        this.type = type;
	    }

	@Override
	public String toString() {
		return type;
	}
}
