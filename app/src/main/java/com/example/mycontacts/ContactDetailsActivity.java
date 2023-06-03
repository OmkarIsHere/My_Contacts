package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ContactDetailsActivity extends AppCompatActivity {

    private static final String TAG = "ContactDetailsActivity";

    String id, cName , cNumber, numType, cImg;
    ImageButton backBtn, editBtn, deleteBtn;
    TextView txtName, txtNumber, contactImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        contactImg=findViewById(R.id.contactImg);
        txtName = findViewById(R.id.txtviewName);
        txtNumber = findViewById(R.id.txtviewNumber);
        backBtn = findViewById(R.id.btnBack);
        editBtn = findViewById(R.id.btnEdit);
        deleteBtn = findViewById(R.id.btnDelete);

        if(getIntent().hasExtra("id")){
            id = getIntent().getStringExtra("id");
            cImg = getIntent().getStringExtra("img");
            cName = getIntent().getStringExtra("name");
            cNumber = getIntent().getStringExtra("number");
            numType = getIntent().getStringExtra("numType");
            Log.d(TAG, "onCreate: "+ cName + " " + cImg);
            txtName.setText(cName);
            txtNumber.setText(cNumber);
            contactImg.setText(cImg);
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}