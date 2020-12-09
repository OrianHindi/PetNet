package com.example.petnet.BusinessActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.petnet.Adapters.B_store_list_adapter;
import com.example.petnet.Bobjects.B_new_store_dialog;
import com.example.petnet.Bobjects.B_pet_shop;
import com.example.petnet.Bobjects.B_store;
import com.example.petnet.Bobjects.B_veterinarian_store;
import com.example.petnet.Bobjects.B_dog_sitter;
import com.example.petnet.Bobjects.B_dog_trainer;
import com.example.petnet.Bobjects.B_dog_walker;
import com.example.petnet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class B_my_store extends AppCompatActivity {

    private static final String TAG = "Bmy_stores";
    private ImageView new_store_button;
    static Long store_count ;
    private ArrayList<B_store> store_array = new ArrayList<>();
    private DatabaseReference myRef;
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onDataChange: start1");
        setContentView(R.layout.b_my_store);


        Log.d(TAG, "onDataChange: start");
        mListView = (ListView) findViewById(R.id.list_view);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        myRef = FirebaseDatabase.getInstance().getReference().child("Stores").child(currentFirebaseUser.getUid());
        Log.d(TAG, "onDataChange: load page");
        Context this_con = this;
        new_store_button = (ImageView) findViewById(R.id.new_store_butt);

        refresh(this_con);

        change_event_update(this_con);

        new_store_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_my_store_bar();
            }
        });


    }

    private void change_event_update(Context this_con) {

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                refresh(this_con);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                refresh(this_con);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                refresh(this_con);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void refresh(Context this_con) {


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: Start take data from firebase");
                store_count = snapshot.getChildrenCount();
                store_array.clear();
                Log.d(TAG, "onDataChange: Store count=" + store_count);

                for (int i = 0; i < store_count; i++) {
                    Log.d(TAG, "onDataChange: read all stores");
                    int type = Integer.parseInt(snapshot.child("s" + i).child("_store_type").getValue().toString());
                    switch (type)
                    {
                        case 0:
                            store_array.add(snapshot.child("s" + i).getValue(B_dog_sitter.class));
                            break;

                        case 1:
                            store_array.add(snapshot.child("s" + i).getValue(B_dog_trainer.class));
                            break;

                        case 2:
                            store_array.add(snapshot.child("s" + i).getValue(B_dog_walker.class));
                            break;

                        case 3:
                            store_array.add(snapshot.child("s" + i).getValue(B_pet_shop.class));
                            break;

                        case 4:
                            store_array.add(snapshot.child("s" + i).getValue(B_veterinarian_store.class));
                            break;

                    }
                    Log.d(TAG, "onDataChange: got data from firebase " + store_array.get(i).get_store_name());
                    Log.d(TAG, "onDataChange: got data from firebase " + store_array.get(i));
                }
                B_store_list_adapter adapter = new B_store_list_adapter(this_con, R.layout.b_store_view, store_array);
                Log.d(TAG, "onDataChange: store_array" + store_array);
                mListView.setAdapter(adapter);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void go_to_my_store_bar() {
        B_new_store_dialog dialog = new B_new_store_dialog();
        dialog.showDialog(this);
    }





    public String return_type_as_string(int type)
    {
        switch (type)
        {
            case 0: return "Dog Sitter";
            case 1: return "Dog Trainer";
            case 2: return "Dog Walker";
            case 3: return "Pet Shop";
            case 4: return "Pet Vet";
        }

        return "";
    }
}