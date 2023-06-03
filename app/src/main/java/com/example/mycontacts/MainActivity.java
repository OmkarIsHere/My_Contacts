package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    SearchView searchView;
    ImageView addContact;
    RecyclerView recyclerView;
    ArrayList<Contact> arrContact;
    ArrayList<AllContact> arrList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addContact =findViewById(R.id.addBtn);
        searchView =findViewById(R.id.searchView);
        recyclerView =findViewById(R.id.contactsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG, "setLayout");

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(i);
                finish();
            }
        });
        setData();

    }
    public void setData(){
        DBhelper dbHelper = DBhelper.getDB(this);
        arrContact =(ArrayList<Contact>)dbHelper.contactDao().getAllContact();

            for(int i=0; i<arrContact.size(); i++){

                int cId = arrContact.get(i).getId();
                String cName = arrContact.get(i).getName();
                String cPhone = arrContact.get(i).getNumber();
                String nType = arrContact.get(i).getNumType();

                AllContact con = new AllContact(cId , cName , cPhone, nType);
                arrList.add(con);
            }

            ContactRecyclerAdapter contactRecyclerAdapter = new ContactRecyclerAdapter(getApplicationContext(), arrList);
            recyclerView.setAdapter(contactRecyclerAdapter);
        }


}