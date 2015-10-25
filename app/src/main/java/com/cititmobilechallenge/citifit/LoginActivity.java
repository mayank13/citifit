package com.cititmobilechallenge.citifit;

import android.app.Activity;
import android.os.Bundle;

import com.cititmobilechallenge.citifit.common.FontHelper;

/**
 * Created by Ashwini Kumar
 */
public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FontHelper.applyFont(this, findViewById(R.id.rl_login_container));
    }

}
