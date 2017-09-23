package de.hvv.hackathon.citytour;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

public class DetailActivity extends AppCompatActivity {

    private double[] doubleArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            doubleArray = bundle.getDoubleArray("Points");
            new AsyncTaskGeofox().execute();
        }
    }


    Signatur nutzer = new Signatur("mobi-hack", "H4m$urgH13okt");

    class AsyncTaskGeofox extends AsyncTask<POI, Integer, ArrayList<POI>> {


        @Override
        protected ArrayList<POI> doInBackground(POI... arrayLists) {


            SDName startDestination = new SDName(doubleArray[0], doubleArray[1], SDType.UNKNOWN);
            checkNameRequest checkNameRequestStart = new checkNameRequest(startDestination, "HHV_LISTED", Locale.getDefault(), 30);
            Anfrage anfrage = new Anfrage(nutzer, checkNameRequestStart.getBody(), checkNameRequestStart.getRequestUri());

            try {
                anfrage.senden();
                startDestination.completWithCheckNameResponse(anfrage.getResponseBody(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            POI pois = arrayLists[0];


            SDName endDestination = new SDName(doubleArray[2], doubleArray[3], SDType.UNKNOWN);
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
                pois.timereq = time;

                Paths pfad = new Paths();

                pfad.ValuesFromJSON(dieRoute.getPath());


//                pfad.getSegmentByIndex(5).getAttribute() //wegbeschaffenheit

                ArrayList<Path> pathArrayList = new ArrayList<>();
                for (int i = 0; i < pfad.size(); i++) {
                    PathSegment pathSegment = pfad.getSegmentByIndex(i);

                    for (int pathIndex = 0; pathIndex < pathSegment.size(); pathIndex++) {
                        Path path = new Path();
                        path.lat = pathSegment.getCoordinateByIndex(pathIndex).getCoordinateX();
                        path.lng = pathSegment.getCoordinateByIndex(pathIndex).getCoordinateY();
                        pathArrayList.add(path);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<POI> pois) {
        }
    }

}
