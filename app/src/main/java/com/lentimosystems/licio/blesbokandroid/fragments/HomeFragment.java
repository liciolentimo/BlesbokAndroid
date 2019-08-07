package com.lentimosystems.licio.blesbokandroid.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lentimosystems.licio.blesbokandroid.MaraActivity;
import com.lentimosystems.licio.blesbokandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    CardView mara_card,samburu_card,amboseli_card;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView =  inflater.inflate(R.layout.fragment_home, container, false);

         mara_card = (CardView)itemView.findViewById(R.id.mara_card);
         mara_card.setOnClickListener(this);

        return itemView;
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), MaraActivity.class);
        startActivity(intent);
    }
}
