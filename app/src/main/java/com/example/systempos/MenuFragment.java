package com.example.systempos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.example.systempos.adapter.adapter_main;

import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment {

    GridView gridView;
    List<HomePage> datahomepage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        datahomepage = new ArrayList<>();

        datahomepage.add(new HomePage("Invoice", R.drawable.invoice2));
        datahomepage.add(new HomePage("Product", R.drawable.itemimg));
        datahomepage.add(new HomePage("Report", R.drawable.report));
        datahomepage.add(new HomePage("Catogory", R.drawable.person));
        datahomepage.add(new HomePage("User", R.drawable.userimg));
        datahomepage.add(new HomePage("About", R.drawable.about_us1));
        datahomepage.add(new HomePage("Customer", R.drawable.about_us1));
        datahomepage.add(new HomePage("Location", R.drawable.about_us1));
        datahomepage.add(new HomePage("Sale", R.drawable.about_us1));
        datahomepage.add(new HomePage("Card", R.drawable.about_us1));

        gridView = view.findViewById(R.id.gridview);
        gridView.setVerticalScrollBarEnabled(false);


        gridView.setAdapter(new adapter_main(datahomepage, requireContext()));

        return view;
    }
}