package com.example.medwa.seniorproject_demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.loginTxt);
        password = (EditText) findViewById(R.id.passwordTxt2);

        mAuth = FirebaseAuth.getInstance();




    }


    public void registerClick(View view) {
        Intent intent = new Intent(this, RegisterScreen.class);
        startActivity(intent);
        finish();

    }

    public void loginClick(View view) {
       String e = email.getText().toString();
       String p = password.getText().toString();

       if(TextUtils.isEmpty(e) || TextUtils.isEmpty(p)){

           Toast.makeText(this,"Email or Password field is empty!",Toast.LENGTH_SHORT).show();
       }else {

           mAuth.signInWithEmailAndPassword(e, p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if (task.isSuccessful()) {
                       Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                       Intent intent = new Intent(MainActivity.this, UserProfile.class);
                       startActivity(intent);
                       finish();
                   } else {
                       Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                   }
               }
           });
       }







    }
}
