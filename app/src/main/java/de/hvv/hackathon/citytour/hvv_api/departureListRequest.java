package de.hvv.hackathon.citytour.hvv_api;

import java.util.Locale;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import hvv_api.BaseRequest;
import hvv_api.SDName;


public class departureListRequest extends BaseRequest{
	
	private String ReqUri = "/departureList";
	private SDName station;
	private GTITime time;
	  
	
	public departureListRequest(SDName station, GTITime time, String filterType, Locale locale, int ApiVersion)
	{
		super(filterType, locale, ApiVersion);
		this.station = station;
		this.time = time;
	}
	
    public String getRequestUri() {
		return ReqUri;
	}
	
    public JSONObject getBody() {
        try {
            JSONObject body = super.getBody();
            body.put("station", this.station.getBody());
            body.put("time", this.time.getBody());
            body.put("maxList", 10);
            body.put("maxTimeOffset", 30);
            body.put("allStationsInChangingNode", true);
            body.put("returnFilters", false);
            //body.put("filter", "");
            //body.put("serviceTypes", "");
            body.put("useRealtime", true);
            return body;
        } catch (JSONException e) {
            return null;
        }
    }

}
