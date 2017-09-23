package de.hvv.hackathon.citytour.hvv_api;

public enum SimpleServiceType {
	BUS("BUS"),
	TRAIN("TRAIN"),
	SHIP("SHIP"),
	FOOTPATH("FOOTPATH"),
	BICYCLE("BICYCLE"),
	AIRPLAN("AIRPLAN"),
	CHANGE("CHANGE");

	String type;

	SimpleServiceType(String type) {
	        this.type = type;
	    }

	@Override
	public String toString() {
		return type;
	}
}
