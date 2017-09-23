package de.hvv.hackathon.citytour.hvv_api;

public enum FahrzeugeTypen {
	UBAHN("UBAHN"),
	SBAHN("SBAHN"),
	AKN("AKN"),
	RBAHN("RBAHN"),
	FERNBAHN("FERNBAHN"),
	BUS("BUS"),
	STADTBUS("STADTBUS"),
	METROBUS("METROBUS"),
	SCHNELLBUS("SCHNELLBUS"),
	NACHTBUS("NACHTBUS"),
	EILBUS("EILBUS"),
	AST("AST"),
	FAEHRE("FAEHRE"),
	;

	String type;

	FahrzeugeTypen(String type) {
	        this.type = type;
	    }

	@Override
	public String toString() {
		return type;
	}
}
