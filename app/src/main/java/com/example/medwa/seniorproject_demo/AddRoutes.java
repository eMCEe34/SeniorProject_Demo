package com.example.medwa.seniorproject_demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddRoutes extends AppCompatActivity {

    ArrayList<String> addRoutes = new ArrayList<>();
    ListView list;
    FirebaseDatabase mDatabase;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    DatabaseReference rootRef;
    FirebaseUser user;
    int count = 1;
    Button addRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routes);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Routes");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        rootRef = mDatabase.getReference();

        list = (ListView)findViewById(R.id.addRouteList);
        addRoute = (Button)findViewById(R.id.addButton);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    try {
                        addRoutes.add(String.valueOf(ds.getValue()));
                    }
                    catch(Exception e){
                        Log.d("AddRoutes", "Exception Thrown");
                    }
                    count++;
                }

                ArrayAdapter adapter = new ArrayAdapter(AddRoutes.this, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, addRoutes);
                list.setChoiceMode(list.CHOICE_MODE_MULTIPLE);
                list.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selected = "";
                int choices = list.getCount();
                SparseBooleanArray sparseBooleanArray = list.getCheckedItemPositions();

                for(int i=0; i<choices;i++){
                    if(sparseBooleanArray.get(i)){
                        selected = list.getItemAtPosition(i).toString().trim();
                        rootRef.child(user.getUid()).child("Preferred").child(String.valueOf(i)).setValue(selected);
                    }
                }

                Intent intent = new Intent(AddRoutes.this, PreferredRoutes.class);
                startActivity(intent);
            }
        });



    }
}
