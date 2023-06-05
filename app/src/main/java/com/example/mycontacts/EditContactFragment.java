package com.example.mycontacts;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import kotlin.contracts.Returns;


public class EditContactFragment extends BottomSheetDialogFragment {
    private static final String TAG = "EditContactFragment";
    ArrayList<String> arrSpinner = new ArrayList<>();
    private EditText cName , cNo;
    private Button btnSave;
    private Spinner spinner;
    private String id, name, num, numType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_contact, container, false);

        cName = (EditText)view.findViewById(R.id.editTextName);
        cNo = (EditText)view.findViewById(R.id.editTextPhoneNo);
        btnSave = (Button)view.findViewById(R.id.saveBtn);
        spinner = (Spinner)view.findViewById(R.id.noTypeSpinner);

        arrSpinner.add("Mobile");
        arrSpinner.add("Work");
        arrSpinner.add("Home");
        arrSpinner.add("Main");
        arrSpinner.add("No Label");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arrSpinner);
        spinner.setAdapter(spinnerAdapter);

        SharedPreferences contactDetailsPref =  view.getContext().getSharedPreferences("contactDetails", MODE_PRIVATE);
        id = contactDetailsPref.getString("id", "0");
        int cId = Integer.parseInt(id);

        DBhelper dbHelper = DBhelper.getDB(getActivity());
        dbHelper.contactDao().getContactById(cId);
        ArrayList<Contact> arrContact= (ArrayList<Contact>)dbHelper.contactDao().getContactById(cId);

            name = arrContact.get(0).getName();
            num = arrContact.get(0).getNumber();
            numType = arrContact.get(0).getNumType();
//       Log.d(TAG, "Name: "+ arrContact.get(i).getName()+ "No: "+ arrContact.get(i).getNumber() + " " + arrContact.get(i).getNumType());
        cName.setText(name);
        cNo.setText(num);
        switch (numType) {
            case "Mobile":
                spinner.setSelection(0);
                break;
            case "Work":
                spinner.setSelection(1);
                break;
            case "Home":
                spinner.setSelection(2);
                break;
            case "Main":
                spinner.setSelection(3);
                break;
            case "No Label":
                spinner.setSelection(4);
                break;
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numType = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(v -> {
            name = cName.getText().toString().trim();
            num = cNo.getText().toString();
            dbHelper.contactDao().updateContactById(name,num, numType, cId);
            Log.d(TAG, "onClick btn save: ");
            dismiss();
            Toast.makeText(getActivity(), "Please refresh the page", Toast.LENGTH_SHORT).show();
        });



        return view;
    }
}