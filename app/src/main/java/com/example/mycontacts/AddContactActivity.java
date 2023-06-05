package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity {
    private static final String TAG = "AddContact";
    private EditText cName , cNo;
    ImageView closeBtn;
    Button saveBtn;
    private Spinner spinner;
    String contactName, contactNo, numberType;
    ArrayList<String> arrSpinner = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);


        closeBtn = findViewById(R.id.closeBtn);
        saveBtn = findViewById(R.id.saveBtn);
        cName = findViewById(R.id.editTextName);
        cNo = findViewById(R.id.editTextPhoneNo);
        spinner = findViewById(R.id.noTypeSpinner);

        DBhelper dbHelper = DBhelper.getDB(this);


        arrSpinner.add("Mobile");
        arrSpinner.add("Work");
        arrSpinner.add("Home");
        arrSpinner.add("Main");
        arrSpinner.add("No Label");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrSpinner);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                numberType = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent refresh = new Intent(AddContactActivity.this, MainActivity.class);
                startActivity(refresh);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactName = cName.getText().toString().trim();
                contactNo = cNo.getText().toString();
                Log.d(TAG, "onClick: "+ contactName);
                Log.d(TAG, "onClick: "+ contactNo);
                if(checkNumber(contactNo)){
                    if(contactName.isEmpty()){
                        Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                    } else{
                        dbHelper.contactDao().addContact(
                                new Contact(contactName, contactNo, numberType));
                        ArrayList<Contact> arrContact= (ArrayList<Contact>)dbHelper.contactDao().getAllContact();
                        for(int i=0; i<arrContact.size(); i++){
                            Log.d(TAG, "Name: "+ arrContact.get(i).getName()+ "No: "+ arrContact.get(i).getNumber() + " " + arrContact.get(i).getNumType());
                        }

                    }
                }
                finish();
                Intent refresh = new Intent(AddContactActivity.this, MainActivity.class);
                startActivity(refresh);
            }
        });

    }
    private boolean checkNumber(String no){
        Log.d(TAG, "checkNumber: "+ no);
        if(!no.isEmpty()){
            if(no.length() != 10){
                Toast.makeText(getApplicationContext(), "Invalid phone no", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return android.util.Patterns.PHONE.matcher(no).matches();
    }
}