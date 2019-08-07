package com.lentimosystems.licio.blesbokandroid.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lentimosystems.licio.blesbokandroid.AmboseliActivity;
import com.lentimosystems.licio.blesbokandroid.MaraActivity;
import com.lentimosystems.licio.blesbokandroid.NakuruActivity;
import com.lentimosystems.licio.blesbokandroid.NanyukiActivity;
import com.lentimosystems.licio.blesbokandroid.R;
import com.lentimosystems.licio.blesbokandroid.SamburuActivity;
import com.lentimosystems.licio.blesbokandroid.TsavoActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DestinationsFragment extends Fragment implements View.OnClickListener{
    CardView mara_card,samburu_card,amboseli_card,nakuru_card,nanyuki_card,tsavo_card;


    public DestinationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_destinations, container, false);

        mara_card = (CardView)itemView.findViewById(R.id.mara_card);
        samburu_card = (CardView)itemView.findViewById(R.id.samburu_card);
        amboseli_card = (CardView)itemView.findViewById(R.id.amboseli_card);
        nanyuki_card = (CardView)itemView.findViewById(R.id.nanyuki_card);
        nakuru_card = (CardView)itemView.findViewById(R.id.nakuru_card);
        tsavo_card = (CardView)itemView.findViewById(R.id.tsavo_card);

        mara_card.setOnClickListener(this);
        samburu_card.setOnClickListener(this);
        amboseli_card.setOnClickListener(this);
        nakuru_card.setOnClickListener(this);
        nanyuki_card.setOnClickListener(this);
        tsavo_card.setOnClickListener(this);

        return itemView;
    }
    @Override
    public void onClick(View view) {
        //Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
        if (view == mara_card){
            Intent intent = new Intent(getActivity(), MaraActivity.class);
            startActivity(intent);
        } else if (view == samburu_card){
            Intent intent = new Intent(getActivity(), SamburuActivity.class);
            startActivity(intent);
        } else if (view == amboseli_card){
            Intent intent = new Intent(getActivity(), AmboseliActivity.class);
            startActivity(intent);
        } else if (view == nakuru_card){
            Intent intent = new Intent(getActivity(), NakuruActivity.class);
            startActivity(intent);
        } else if (view == nanyuki_card){
            Intent intent = new Intent(getActivity(), NanyukiActivity.class);
            startActivity(intent);
        } else if (view == tsavo_card){
            Intent intent = new Intent(getActivity(), TsavoActivity.class);
            startActivity(intent);
        }

    }

}
