package com.example.lectorqr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Mapas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mapas extends Fragment implements OnMapReadyCallback {

    private static final  String FILE_NAME = "Location.txt";
    TextView gpsText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Mapas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Mapas.
     */
    // TODO: Rename and change types and number of parameters
    public static Mapas newInstance(String param1, String param2) {
        Mapas fragment = new Mapas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mapas, container, false);
        ;

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return v;
    }

    /*private void loaddata(GoogleMap googleMap) {
        FileInputStream fis = null;
        try {
            fis = getActivity().openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            Polyline polyline1 = googleMap.addPolyline(new PolylineOptions

            while ((text = br.readLine()) != null){

                sb.append(text).append("\n");
            }

            gpsText.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        FileInputStream fis = null;
        try {
            fis = getActivity().openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;



            ArrayList<LatLng> points=new ArrayList<LatLng>();

            while ((text = br.readLine()) != null){
                Log.d("STATUS", text);
                if (text.length() > 5){
                String[] point = text.split(" ");
                String[] pt1 = point[1].split("\t");
                Log.d("STATUS1", pt1[0]);
                Log.d("STATUS2", point[2]);

                double latitude = parseDouble(pt1[0]);
                double longitude = parseDouble(point[2]);

                points.add(new LatLng(latitude,longitude));

                }
            }
            Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true));

            polyline1.setPoints(points);

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), 13));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //loaddata();

        //Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
        //        .clickable(true)
        //        .add(
        //                new LatLng(-35.016, 143.321),
        //                new LatLng(-34.747, 145.592),
        //                new LatLng(-34.364, 147.891),
        //                new LatLng(-33.501, 150.217),
        //                new LatLng(-32.306, 149.248),
        //                new LatLng(-32.491, 147.309)));

        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));
    }


    public interface OnFragmentItereactionListerner {
    }
}