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

public class RegisterScreen extends AppCompatActivity {

    EditText first, last, dob, email, pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        email = (EditText)findViewById(R.id.emailTxt);
        pass = (EditText)findViewById(R.id.registerPasswordTxt);

        mAuth = FirebaseAuth.getInstance();


    }

    public void registerScreenClick(View view) {

        String e = email.getText().toString();
        String p = pass.getText().toString();

        if(TextUtils.isEmpty(e) || TextUtils.isEmpty(p)){

            Toast.makeText(this,"A field is empty!",Toast.LENGTH_SHORT).show();
        }else {

            mAuth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterScreen.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterScreen.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }



    }
}
