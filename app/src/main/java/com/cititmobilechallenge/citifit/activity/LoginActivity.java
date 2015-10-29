package com.cititmobilechallenge.citifit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cititmobilechallenge.citifit.R;
import com.cititmobilechallenge.citifit.common.FontHelper;

/**
 * Created by Ashwini Kumar
 */
public class LoginActivity extends Activity {

    Button btnLogin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin =(Button)findViewById(R.id.btnLogin);

        FontHelper.applyFont(this, findViewById(R.id.rl_login_container));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this,RewardActivity.class);
                startActivity(i);
            }
        });
    }

}
