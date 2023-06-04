package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ContactDetailsActivity extends AppCompatActivity {

    private static final String TAG = "ContactDetailsActivity";

    long lastClick;
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

            txtName.setText(cName);
            txtNumber.setText(cNumber);
            contactImg.setText(cImg);

            SharedPreferences contactDetailsPref = getApplicationContext().getSharedPreferences("contactDetails", MODE_PRIVATE);
            SharedPreferences.Editor editPref = contactDetailsPref.edit();
            editPref.putString("id", id);
            editPref.apply();
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClick < 2000){  //For single Click
                    return;
                }
                lastClick = SystemClock.elapsedRealtime();
                EditContactFragment editContactFragment = new EditContactFragment();
                editContactFragment.show(getSupportFragmentManager(), editContactFragment.getTag());
            }
        });

    }
}
