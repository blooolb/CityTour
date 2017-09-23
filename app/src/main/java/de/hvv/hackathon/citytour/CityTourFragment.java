package de.hvv.hackathon.citytour;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import de.hvv.hackathon.citytour.Model.POI;
import de.hvv.hackathon.citytour.Model.Path;
import de.hvv.hackathon.citytour.hvv_api.Anfrage;
import de.hvv.hackathon.citytour.hvv_api.IndividualProfileType;
import de.hvv.hackathon.citytour.hvv_api.IndividualRoute;
import de.hvv.hackathon.citytour.hvv_api.PathSegment;
import de.hvv.hackathon.citytour.hvv_api.Paths;
import de.hvv.hackathon.citytour.hvv_api.SDName;
import de.hvv.hackathon.citytour.hvv_api.SDType;
import de.hvv.hackathon.citytour.hvv_api.Signatur;
import de.hvv.hackathon.citytour.hvv_api.SimpleServiceType;
import de.hvv.hackathon.citytour.hvv_api.checkNameRequest;
import de.hvv.hackathon.citytour.hvv_api.getIndividualRouteRequest;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityTourFragment extends Fragment implements AdapterView.OnItemSelectedListener, LocationListener, EventsAdapter.OnClickListerner {


    private View baseView;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private EventsAdapter eventsAdapter;
    private LocationManager locationManager;
    private Location currentLocation;
    private RequestQueue requestQueue;

    public String apiKey = "AIzaSyA_0vz7p9DYlnGEY_TxE1J9Qa9yfm0-XbA";


    private ArrayList<POI> pois = new ArrayList<>();
    private int radius = 500;

    public CityTourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        baseView = inflater.inflate(R.layout.fragment_city_tour, container, false);
        recyclerView = baseView.findViewById(R.id.recyclerViewPOI);
        spinner = baseView.findViewById(R.id.spinnerEntfernung);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.entfernung_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        eventsAdapter = new EventsAdapter(this);
        eventsAdapter.swapList(pois);
        recyclerView.setAdapter(eventsAdapter);


        spinner.setOnItemSelectedListener(this);

        spinner.setSelection(1);


        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(getContext(), "You need Permission granted", Toast.LENGTH_SHORT).show();
        } else
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 60, 10, this);

        checkLocationPermission();


//        locationManager.requestSingleUpdate();

        requestQueue = Volley.newRequestQueue(getContext());


        return baseView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String selectedItem = (String) adapterView.getSelectedItem();
        switch (selectedItem) {
            case "0 Kilometer":
                reloadWithValues(0);
                break;
            case "5 Kilometer":
                reloadWithValues(5000);
                break;
            case "10 Kilometer":
                reloadWithValues(10000);

                break;
            case "15 Kilometer":
                reloadWithValues(15000);

                break;
            case "25 Kilometer":
                reloadWithValues(25000);

                break;
            case "2 Kilometer":
                reloadWithValues(2000);

                break;
            case "1 Kilometer":
                reloadWithValues(1000);
                break;
            case "4 Kilometer":
                reloadWithValues(4000);
                break;
            default:
//                Toast.makeText(getContext(), "Soweit kann man doch nicht aus Hamburg raus wollen", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void reloadWithValues(int meter) {
        //TODO
        radius = meter;

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(getContext(), "You need Permission granted", Toast.LENGTH_SHORT).show();
        } else
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 60, 10, this);

        checkLocationPermission();
    }


    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onLocationChanged(Location location) {

        currentLocation = location;
        ArrayList<String> placesArray = getArrayOfPlaces();
        if (placesArray == null || placesArray.isEmpty())
            return;
//        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyA_0vz7p9DYlnGEY_TxE1J9Qa9yfm0-XbA&location="+ location.getLongitude()+"," +location.getLatitude() + "&radius=" +radius+ "&type=" + eventtype;

        for (String type : placesArray) {
            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyA_0vz7p9DYlnGEY_TxE1J9Qa9yfm0-XbA&location=" + location.getLatitude() + "," + location.getLongitude() + "&radius=" + radius + "&type=" + type;
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());


                            try {
                                JSONArray jsonArray = response.getJSONArray("results");

                                if (jsonArray.length() == 0) {
                                    Toast.makeText(getContext(), "Keine Einträge, Radius erhöhen", Toast.LENGTH_SHORT).show();
                                }

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);

                                    JSONObject location = obj.getJSONObject("geometry").getJSONObject("location");

                                    POI poiTemp = new POI();
                                    poiTemp.lat = location.getDouble("lat");
                                    poiTemp.lon = location.getDouble("lng");


                                    if (obj.has("photos")) {
                                        String photo = obj.getJSONArray("photos").getJSONObject(0).getString("photo_reference");
                                        poiTemp.imageUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photo + "&key=" + apiKey;
                                    }

                                    poiTemp.tag = "#" + obj.getJSONArray("types").getString(0);

                                    poiTemp.title = obj.getString("name");

                                    pois.add(poiTemp);

                                }


                                eventsAdapter.swapList(pois);

//                                new AsyncTaskGeofox().execute(pois);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.toString());

                        }
                    });

            requestQueue.add(jsObjRequest);

        }


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }


        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 60 * 1000, 10, this);

        return false;
    }


    public ArrayList<String> getArrayOfPlaces() {
        ArrayList<String> placesCategoriesArray = new ArrayList<>();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        boolean preferenceSport = preferences.getBoolean("sport_switch", false);
        boolean preferenceEssen = preferences.getBoolean("essen_switch", false);
        boolean preferenceNatur = preferences.getBoolean("natur_switch", false);
        boolean preferenceMuseen = preferences.getBoolean("museen_switch", false);
        boolean preferenceFun = preferences.getBoolean("fun_switch", false);
        boolean preferenceSauf = preferences.getBoolean("sauftour_switch", false);
        boolean preferenceRaub = preferences.getBoolean("raubzug_switch", false);
        boolean preferenceSehen = preferences.getBoolean("sehensw_switch", false);

        if (!preferenceSport && !preferenceEssen && !preferenceNatur
                && !preferenceMuseen
                && !preferenceFun
                && !preferenceSauf
                && !preferenceRaub
                && !preferenceSehen) {
            Toast.makeText(getContext(), "Du solltest was wählen", Toast.LENGTH_SHORT).show();
            return placesCategoriesArray;
        }


        if (preferenceSport) {
            placesCategoriesArray.add("stadium");
        }
        if (preferenceEssen) {
            placesCategoriesArray.add("bar");
            placesCategoriesArray.add("cafe");
            placesCategoriesArray.add("restaurant");

        }
        if (preferenceNatur) {
            placesCategoriesArray.add("park");
        }
        if (preferenceMuseen) {
            placesCategoriesArray.add("aquarium");
            placesCategoriesArray.add("art_gallery");
            placesCategoriesArray.add("museum");
            placesCategoriesArray.add("zoo");

        }
        if (preferenceFun) {
            placesCategoriesArray.add("amusement_park");
            placesCategoriesArray.add("casino");
            placesCategoriesArray.add("movie_theater");
            placesCategoriesArray.add("night_club");
        }
        if (preferenceSauf) {
            placesCategoriesArray.add("bar");
            placesCategoriesArray.add("night_club");
        }
        if (preferenceRaub) {
            placesCategoriesArray.add("casino");
            placesCategoriesArray.add("jewelry_store");
            placesCategoriesArray.add("liquor_store");
        }
        if (preferenceSehen) {
            placesCategoriesArray.add("cemetery");
            placesCategoriesArray.add("church");
            placesCategoriesArray.add("city_hall");
            placesCategoriesArray.add("hindu_temple");
            placesCategoriesArray.add("library");
            placesCategoriesArray.add("mosque");
            placesCategoriesArray.add("synagogue");
            placesCategoriesArray.add("university");
            placesCategoriesArray.add("zoo");

        }
        return placesCategoriesArray;
    }


    Signatur nutzer = new Signatur("mobi-hack", "H4m$urgH13okt");

    @Override
    public void click(POI path) {
        Intent intent = new Intent(getContext(),DetailActivity.class);
        double[] doubles = new double[4];
        doubles[0] = currentLocation.getLatitude();
        doubles[1] = currentLocation.getLongitude();

        doubles[2] = path.lat;
        doubles[3] = path.lon;

        intent.putExtra("Points",doubles);

        startActivity(intent);
    }

    class AsyncTaskGeofox extends AsyncTask<ArrayList<POI>, Integer, ArrayList<POI>> {


        @Override
        protected ArrayList<POI> doInBackground(ArrayList<POI>... arrayLists) {
            SDName startDestination = new SDName(currentLocation.getLatitude(), currentLocation.getLongitude(), SDType.UNKNOWN);
            checkNameRequest checkNameRequestStart = new checkNameRequest(startDestination, "HHV_LISTED", Locale.getDefault(), 30);
            Anfrage anfrage = new Anfrage(nutzer, checkNameRequestStart.getBody(), checkNameRequestStart.getRequestUri());

            try {
                anfrage.senden();
                startDestination.completWithCheckNameResponse(anfrage.getResponseBody(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayList<POI> poisList = arrayLists[0];
            for (POI poi : poisList) {
                SDName endDestination = new SDName(poi.lat, poi.lon, SDType.UNKNOWN);
                checkNameRequest checkNameRequestEnd = new checkNameRequest(endDestination, "HHV_LISTED", Locale.getDefault(), 30);
                Anfrage anfrageEnd = new Anfrage(nutzer, checkNameRequestEnd.getBody(), checkNameRequestEnd.getRequestUri());

                try {
                    anfrageEnd.senden();
                    endDestination.completWithCheckNameResponse(anfrageEnd.getResponseBody(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getIndividualRouteRequest getIndividualRouteRequest = new getIndividualRouteRequest(startDestination, SimpleServiceType.BICYCLE, IndividualProfileType.BICYCLE_NORMAL, endDestination, 500, 5, "HVV_LISTED", Locale.getDefault(), 30);

                Anfrage anfrageIndividualRoute = new Anfrage(nutzer, getIndividualRouteRequest.getBody(), getIndividualRouteRequest.getRequestUri());


                try {
                    anfrageIndividualRoute.senden();


                    IndividualRoute dieRoute = new IndividualRoute();
                    dieRoute.ValuesFromJSON(anfrageIndividualRoute.getResponseBody());
                    int time = dieRoute.getTime();
                    poi.timereq = time;

                    Paths pfad = new Paths();

                    pfad.ValuesFromJSON(dieRoute.getPath());


//                pfad.getSegmentByIndex(5).getAttribute() //wegbeschaffenheit

                    ArrayList<Path> pathArrayList = new ArrayList<>();
                    for(int i = 0; i < pfad.size(); i++){
                       PathSegment pathSegment=  pfad.getSegmentByIndex(i);

                        for(int pathIndex = 0; pathIndex < pathSegment.size(); pathIndex++){
                            Path path = new Path();
                            path.lat = pathSegment.getCoordinateByIndex(pathIndex).getCoordinateX();
                            path.lng = pathSegment.getCoordinateByIndex(pathIndex).getCoordinateY();
                            pathArrayList.add(path);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            return poisList;
        }

        @Override
        protected void onPostExecute(ArrayList<POI> pois) {
            eventsAdapter.swapList(pois);
        }
    }

}
