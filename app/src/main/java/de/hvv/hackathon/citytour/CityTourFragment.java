package de.hvv.hackathon.citytour;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityTourFragment extends Fragment implements AdapterView.OnItemSelectedListener, LocationListener {


    private View baseView;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private EventsAdapter eventsAdapter;
    private LocationManager locationManager;
    private Location currentLocation;

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

        eventsAdapter = new EventsAdapter();
        recyclerView.setAdapter(eventsAdapter);


        spinner.setOnItemSelectedListener(this);

        spinner.setSelection(1);


        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
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
                locationManager.requestLocationUpdates(provider, 2 * 60 * 1000, 10, this);
        }

        return baseView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String selectedItem = (String) adapterView.getSelectedItem();
        switch (selectedItem) {
            case "0 Kilometer":
                Toast.makeText(getContext(), "0", Toast.LENGTH_SHORT).show();
                reloadWithValues(0);
                break;
            case "5 Kilometer":
                Toast.makeText(getContext(), "5", Toast.LENGTH_SHORT).show();
                reloadWithValues(5);
                break;
            case "10 Kilometer":
                Toast.makeText(getContext(), "10", Toast.LENGTH_SHORT).show();
                reloadWithValues(10);

                break;
            case "15 Kilometer":
                Toast.makeText(getContext(), "15", Toast.LENGTH_SHORT).show();
                reloadWithValues(15);

                break;
            case "25 Kilometer":
                Toast.makeText(getContext(), "25", Toast.LENGTH_SHORT).show();
                reloadWithValues(25);

                break;
            case "50 Kilometer":
                Toast.makeText(getContext(), "50", Toast.LENGTH_SHORT).show();
                reloadWithValues(50);

                break;
            default:
                Toast.makeText(getContext(), "Soweit kann man doch nicht aus Hamburg raus wollen", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void reloadWithValues(int meter) {
        //TODO
    }


    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
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
}
