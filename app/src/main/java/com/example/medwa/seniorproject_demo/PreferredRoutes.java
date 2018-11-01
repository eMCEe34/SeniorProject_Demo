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
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PreferredRoutes extends AppCompatActivity {

    ListView listView;
    TextView text;
    String busValue;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferred_routes);

        text = (TextView)findViewById(R.id.routesTextView);
        listView = (ListView)findViewById(R.id.routesListView);
        //String data[] = new String[99];


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("bus");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                busValue = value;
                text.setText(value);

                list.add(busValue);
                list.add("Test Item 1");
                list.add("Test Item 2");
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

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:40.7499880,-73.4219212?z=15&q="+value));
                startActivity(intent);

                text.setText(value);
            }
        });


        //for (int i=0;i <99; i++){
         //   data[i] = "Test" + i;
        //}





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
