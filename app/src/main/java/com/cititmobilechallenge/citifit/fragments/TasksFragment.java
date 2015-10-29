package com.cititmobilechallenge.citifit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cititmobilechallenge.citifit.R;
import com.cititmobilechallenge.citifit.common.FontHelper;


public class TasksFragment extends Fragment {

    public TasksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_tasks, container, false);

        FontHelper.applyFont(getActivity(), getActivity().findViewById(R.id.task_fragment_container));

        return inflatedView;
    }

}
