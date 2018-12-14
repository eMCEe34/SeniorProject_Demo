package com.example.medwa.seniorproject_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfile extends AppCompatActivity {

    Button signout;
    FirebaseAuth mAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        signout = (Button)findViewById(R.id.signoutButton);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    mAuth.signOut();

                    Toast.makeText(UserProfile.this,"You have been signed out!",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UserProfile.this, MainActivity.class);
                    startActivity(intent);
                    finish();

            }
        });

    }

    public void preferredRouteClick(View view) {
        Intent intent = new Intent(this, PreferredRoutes.class);
        startActivity(intent);
    }
}
