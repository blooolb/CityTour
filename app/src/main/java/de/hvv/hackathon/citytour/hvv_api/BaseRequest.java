package de.hvv.hackathon.citytour.hvv_api;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;


public class BaseRequest {
	
    private Locale locale = Locale.getDefault();
    private String filterType; //= FilterType.getDefault();
    private int ApiVersion;
	
	public BaseRequest(String filterType, Locale locale, int ApiVersion)
	{
        this.filterType = filterType;
        this.locale = locale;
        this.ApiVersion = ApiVersion;
	}
	
    public JSONObject getBody() {
        try {
            JSONObject body = new JSONObject();
            body.put("version", ApiVersion);
            body.put("language", locale.getLanguage());
            body.put("filterType", filterType);
            return body;
        } catch (JSONException e) {
            return null;
        }
    }

}
