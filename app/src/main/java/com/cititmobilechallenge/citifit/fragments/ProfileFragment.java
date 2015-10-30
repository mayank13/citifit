package com.cititmobilechallenge.citifit.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cititmobilechallenge.citifit.R;
import com.cititmobilechallenge.citifit.common.FontHelper;
import com.cititmobilechallenge.citifit.common.ImageHelper;


public class ProfileFragment extends Fragment {

    private ImageView mIvProfile = null;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment getInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        FontHelper.applyFont(getActivity(), view.findViewById(R.id.rl_profie_container));

        mIvProfile = (ImageView) view.findViewById(R.id.profileImage);

        Bitmap profileImage = BitmapFactory.decodeResource(getResources(), R.drawable.profile);
        Bitmap circularProfileImage = ImageHelper.getRoundedCornerBitmap(profileImage, 200);

        mIvProfile.setImageBitmap(circularProfileImage);

        return view;
    }

}
