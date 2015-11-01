package com.cititmobilechallenge.citifit.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

    private ISuccessDialogFragmentListenr mSuccessDialogListener = null;

    public SuccessDialogFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void setUpWheelView() {
        //TODO - The percentage to be calculated based on data being processed and fetched dynamically
        // dummy data for kindle
        float totalPointsTarget = 7719; // Points target
        float currentPoints = 7000; // User has achieved current points
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

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.fragment_success_dialog);

        mOkBtn = (Button) dialog.findViewById(R.id.btnOk);
        mWheelIndicatorView = (WheelIndicatorView) dialog.findViewById(R.id.success_wheel_indicator_view);

        FontHelper.applyFont(getActivity(), dialog.findViewById(R.id.rl_success_container));

        setUpWheelView();

        mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSuccessDialogListener.onClick();
            }
        });


        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            if (activity instanceof ISuccessDialogFragmentListenr) {
                mSuccessDialogListener = (ISuccessDialogFragmentListenr) activity;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface ISuccessDialogFragmentListenr {
        void onClick();
    }
}
