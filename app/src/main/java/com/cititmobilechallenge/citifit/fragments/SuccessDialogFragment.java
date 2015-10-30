package com.cititmobilechallenge.citifit.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.cititmobilechallenge.citifit.R;
import com.cititmobilechallenge.citifit.common.FontHelper;
import com.dlazaro66.wheelindicatorview.WheelIndicatorItem;
import com.dlazaro66.wheelindicatorview.WheelIndicatorView;

/**
 * Created by ashwiask on 10/30/2015.
 */
public class SuccessDialogFragment extends DialogFragment {

    private WheelIndicatorView mWheelIndicatorView = null;

    private Button mOkBtn = null;

    public SuccessDialogFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_success_dialog, container, false);

        mOkBtn = (Button) view.findViewById(R.id.btnOk);
        mWheelIndicatorView = (WheelIndicatorView) view.findViewById(R.id.success_wheel_indicator_view);

        FontHelper.applyFont(getActivity(), view.findViewById(R.id.rl_success_container));

        setUpWheelView();


        return view;

    }

    private void setUpWheelView() {
        //TODO - The percentage to be calculated based on data being processed and fetched dynamically
        // dummy data for kindle
        float totalPointsTarget = 7719; // Points target
        float currentPoints = 4110; // User has achieved current points
        float div = currentPoints / totalPointsTarget;
        int percentageOfExerciseDone = (int) (div * 100);

        mWheelIndicatorView.setFilledPercent(percentageOfExerciseDone);

        WheelIndicatorItem runningActivityIndicatorItem = new WheelIndicatorItem(1.0f, ContextCompat.getColor(getActivity(), R.color.purple));

        mWheelIndicatorView.addWheelIndicatorItem(runningActivityIndicatorItem);
        mWheelIndicatorView.startItemsAnimation(); // Animate!
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return dialog;
    }
}
