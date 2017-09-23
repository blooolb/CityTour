package de.hvv.hackathon.citytour.hvv_api;

import java.util.Locale;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hvv_api.BaseRequest;
import hvv_api.SDName;


public class getIndividualRouteRequest extends BaseRequest{
	
	private String ReqUri = "/getIndividualRoute";
	private SDName startpoint;
	private SDName destpoint;
	private SimpleServiceType ServiceType;
	private IndividualProfileType Profile;
	private int maxLength;
	private int maxResults;
	
	public getIndividualRouteRequest(SDName starts, SimpleServiceType ServiceType, IndividualProfileType Profile, SDName dests, int maxLength, int maxResults, String filterType, Locale locale, int ApiVersion)
	{
		super(filterType, locale, ApiVersion);
		this.startpoint = starts;
		this.destpoint = dests;
		this.Profile = Profile;
		this.ServiceType = ServiceType;
	}
	
    public String getRequestUri() {
		return ReqUri;
	}
	
    public JSONObject getBody() {
        try {
            JSONObject body = super.getBody();
            JSONArray starts = new JSONArray();
            starts.put(startpoint.getBody());
            body.put("starts", starts);
            JSONArray dests = new JSONArray();
            dests.put(destpoint.getBody());
            body.put("dests", dests);
            body.put("serviceType",ServiceType.toString());
            body.put("profile", Profile.toString());
            return body;
        } catch (JSONException e) {
            return null;
        }
    }

}
