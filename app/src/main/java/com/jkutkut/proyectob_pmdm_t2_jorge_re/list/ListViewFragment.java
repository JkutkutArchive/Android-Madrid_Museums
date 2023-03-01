package com.jkutkut.proyectob_pmdm_t2_jorge_re.list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkutkut.proyectob_pmdm_t2_jorge_re.R;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.result.MuseumResultAPI;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListViewFragment extends Fragment {

    private static final String ARG_DATA = "obj";

    private MuseumResultAPI data;

    public ListViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param data Object with the data.
     * @return A new instance of fragment ListViewFragment.
     */
    public static ListViewFragment newInstance(MuseumResultAPI data) {
        ListViewFragment fragment = new ListViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data = (MuseumResultAPI) getArguments().getSerializable(ARG_DATA);
            // TODO?
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        RecyclerView rvLstView = v.findViewById(R.id.rvLstView);
        rvLstView.setLayoutManager(new LinearLayoutManager(getContext()));
        MuseumAdapter adapter = new MuseumAdapter(data.getMuseums());
        rvLstView.setAdapter(adapter);
        return v;
    }
}