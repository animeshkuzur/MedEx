package com.hashinclude.medex.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hashinclude.medex.R;
import com.hashinclude.medex.activities.MainActivity;

/**
 * Created by pankaj on 11/2/17.
 */

public class Medicines extends Fragment {
    View rootView;
    Button next;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.medicine_fragment_content_one, container, false);
        ((MainActivity)getActivity()).title.setText("Medicines");
        init();

        return rootView;
    }

    private void init() {
        next = (Button) rootView.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new Medicine_02());
                view.setVisibility(View.VISIBLE);
                ft.commit();
            }
        });
    }
}
