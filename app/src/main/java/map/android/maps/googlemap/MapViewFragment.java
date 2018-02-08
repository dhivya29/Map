package map.android.maps.googlemap;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import map.android.maps.BundleKeys;
import map.android.maps.MainActivity;
import map.android.maps.R;


public class MapViewFragment extends Fragment implements OnMapReadyCallback {

    MapView mMapView;
    private GoogleMap mMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_fragment, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(BundleKeys.map_address)) {
            ArrayList<String> myAddress = bundle.getStringArrayList(BundleKeys.map_address);
            if (mMap != null) {
                try {
                    for (int k = 0; k < myAddress.size(); k++) {
                        new LoadMap().execute(myAddress.get(k));
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    private List<Address> getLocationfromAddress(String Addres) {
        Geocoder geocoder = new Geocoder(getActivity());
        if (Addres != null) {
            try {
                return geocoder.getFromLocationName(Addres, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public class LoadMap extends AsyncTask<String, Void, List<Address>> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getContext(),"Processing...",
                    "Loading..Please wait");
        }


        @Override
        protected List<Address> doInBackground(String... strings) {
            return getLocationfromAddress(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Address> AddressList) {
            progressDialog.dismiss();
            if (AddressList != null && AddressList.size() > 0) {
                Address address = AddressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                mMap.addMarker(new MarkerOptions().position(latLng).title(String.valueOf(latLng)));
            }
        }
    }
}

