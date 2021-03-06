package com.example.src.GeneralActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.src.Adapters.B_StoreListAdapter;
import com.example.src.BusinessObjects.B_DogSitter;
import com.example.src.BusinessObjects.B_DogTrainer;
import com.example.src.BusinessObjects.B_DogWalker;
import com.example.src.BusinessObjects.B_PetShop;
import com.example.src.BusinessObjects.B_Store;
import com.example.src.BusinessObjects.B_VetStore;
import com.example.src.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreView extends AppCompatActivity {

    private static final String TAG = "Store_dog_sitter";
    private ArrayList<B_Store> store_array = new ArrayList<>();
    private DatabaseReference myRef;
    private ListView mListView;
    private TextView headText;
    private int typeOfStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_view);
        Log.d(TAG, "onDataChange: start1");
        myRef = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG, "onDataChange: start");
        mListView = (ListView) findViewById(R.id.list_view_store);
        headText = (TextView)  findViewById(R.id.store_view_text) ;
        Bundle type = getIntent().getExtras();
        typeOfStore = (int)type.get("type_number");
        headText.setText(get_head_text(typeOfStore));
        Context this_con = this;
        add_all_stores_to_array(this_con);
    }

    private String get_head_text(int type) {
        switch (type) {
            case 0 : return "Dog Sitter Store`s";
            case 1 : return "Dog Trainer Store`s";
            case 2 : return "Dog Walker`s";
            case 3 : return "Pet Shops Store`s";
            case 4 : return "Veterinarian";
        }
        return "";
    }


    private String get_db_text(int type) {
        switch (type) {
            case 0 : return "Dog_Sitters";
            case 1 : return "Dog_Trainers";
            case 2 : return "Dog_Walker";
            case 3 : return "Pet_Store";
            case 4 : return "Vet_Store";
        }
        return "";
    }

    private void add_all_stores_to_array(Context this_con) {

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: Start take data from firebase");
                store_array.clear();

                for (DataSnapshot sortStore : snapshot.child("Store_Categories").child(get_db_text(typeOfStore)).getChildren()) {
                    DataSnapshot store = snapshot.child("Stores").child(sortStore.getValue().toString());
                            Log.d(TAG, "onDataChange: add store");
                            switch (typeOfStore) {
                                case 0 :
                                    store_array.add(store.getValue(B_DogSitter.class));
                                    break;

                                case 1 :
                                    store_array.add(store.getValue(B_DogTrainer.class));
                                    break;

                                case 2 :
                                    store_array.add(store.getValue(B_DogWalker.class));
                                    break;

                                case 3 :
                                    store_array.add(store.getValue(B_PetShop.class));
                                    break;

                                case 4 :
                                    store_array.add(store.getValue(B_VetStore.class));
                                    break;
                            }



                }
                B_StoreListAdapter adapter = new B_StoreListAdapter(this_con, R.layout.b_store_view, store_array);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}