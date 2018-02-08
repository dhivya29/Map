package map.android.maps;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import map.android.maps.googlemap.MapViewFragment;

public class MainActivity extends AppCompatActivity {
   ArrayList<String> AddressArray=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addGoogleMap();
    }

    public void addGoogleMap() {
        try {
            AddressArray.add("Kodungaiyur,Chennai");
            AddressArray.add("Koyembedu,Chennai");
            AddressArray.add("Guindy,Chennai");
            AddressArray.add("Meenambakkam, Chennai");
            AddressArray.add("Pallavaram");
            AddressArray.add("Chrompet,Chennai");
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(BundleKeys.map_address,AddressArray);
            MapViewFragment mapViewFragment = new MapViewFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            mapViewFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.lnrMaps, mapViewFragment).commit();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

}
