package com.example.medwa.seniorproject_demo;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PreferredRoutes extends AppCompatActivity {

    ListView listView;
    ArrayList<String> list = new ArrayList<>();
    FirebaseUser user;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference preferredRef;
    RouteInformation routeInfo;
    ImageButton mImageButton;
    TextView addText;
    ImageView addImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferred_routes);

        mImageButton = (ImageButton)findViewById(R.id.addRouteButton);
        addText = (TextView)findViewById(R.id.addText);
        addImage = (ImageView)findViewById(R.id.addImage);

        routeInfo = new RouteInformation();

        listView = (ListView)findViewById(R.id.routesListView);
        //String data[] = new String[99];

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        preferredRef = database.getReference(user.getUid()+"/Preferred");

        //myRef.child(user.getUid()).child("bus").setValue("N70 Bus");
        //routeInfo.setBus("N70 Bus");


        // Read from the database
        preferredRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String value = dataSnapshot.child(user.getUid()).child("bus").getValue(String.class);

                list.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    list.add(String.valueOf(ds.getValue()));
                }

                if(!list.isEmpty()){
                    addText.setVisibility(View.GONE);
                    addImage.setVisibility(View.GONE);
                }
                else{
                    addText.setVisibility(View.VISIBLE);
                    addImage.setVisibility(View.VISIBLE);
                }
                //list.add(value);

                ArrayAdapter adapter = new ArrayAdapter(PreferredRoutes.this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
                listView.setAdapter(adapter);

                //showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = listView.getItemAtPosition(position).toString();

                myRef.child(user.getUid()).child("bus").setValue(value);

                //Intent intent = new Intent(Intent.ACTION_VIEW);
                //intent.setData(Uri.parse("geo:40.7499880,-73.4219212?z=15&q="+value));
                Intent intent = new Intent(PreferredRoutes.this, MapsActivity.class);
                startActivity(intent);

            }
        });


        //for (int i=0;i <99; i++){
         //   data[i] = "Test" + i;
        //}

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreferredRoutes.this, AddRoutes.class);
                startActivity(intent);
            }
        });



    }

   /* private void showData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            RouteInformation ri = new RouteInformation();
            ri.setBus(ds.child("route").getValue(RouteInformation.class).getBus());

            ArrayList<String> array = new ArrayList<>();
            array.add(ri.getBus());

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
            ListView listView = (ListView)findViewById(R.id.routesListView);
            listView.setAdapter(adapter);
        }
    }*/
}
