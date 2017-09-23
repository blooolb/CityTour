package de.hvv.hackathon.citytour.hvv_api;

public enum IndividualProfileType {
	BICYCLE_NORMAL("BICYCLE_NORMAL"),
	BICYCLE_RACING("BICYCLE_RACING"),
	BICYCLE_QUIET_ROADS("BICYCLE_QUIET_ROADS"),
	BICYCLE_MAIN_ROADS("BICYCLE_MAIN_ROADS"),
	BICYCLE_BAD_WEATHER("BICYCLE_BAD_WEATHER"),
	FOOT_NORMAL("FOOT_NORMAL");

	String type;

	IndividualProfileType(String type) {
	        this.type = type;
	    }

	@Override
	public String toString() {
		return type;
	}
}
