package com.jkutkut.proyectob_pmdm_t2_jorge_re.map;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.R;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.result.MuseumResultAPI;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapViewFragment extends Fragment implements OnMapReadyCallback {

    private static final String ARG_OBJ = "obj";

    private MuseumResultAPI data;

    public MapViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param obj Object with the data.
     * @return A new instance of fragment MapViewFragment.
     */
    public static MapViewFragment newInstance(MuseumResultAPI obj) {
        MapViewFragment fragment = new MapViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_OBJ, obj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data = (MuseumResultAPI) getArguments().getSerializable(ARG_OBJ);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map_view, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        // Web: https://mapstyle.withgoogle.com/
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(
            requireContext(),
            R.raw.map_style_json
        ));

        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // TODO
    }
}