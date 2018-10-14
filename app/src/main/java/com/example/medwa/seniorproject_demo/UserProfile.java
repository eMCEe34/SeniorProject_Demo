package com.example.medwa.seniorproject_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }

    public void preferredRouteClick(View view) {
        Intent intent = new Intent(this, PreferredRoutes.class);
        startActivity(intent);
        finish();
    }
}
