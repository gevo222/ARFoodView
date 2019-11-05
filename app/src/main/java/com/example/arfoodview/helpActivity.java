package com.example.arfoodview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class helpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String name, email, description;

    EditText textName;
    EditText textEmail;
    EditText textDescription;
    Button submit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        textName = (EditText) findViewById(R.id.textName);
        textEmail = (EditText) findViewById(R.id.textEmail);
        textDescription = (EditText) findViewById(R.id.textDescription);

        submit = (Button) findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name = textName.getText().toString();
                email = textEmail.getText().toString();
                description = textDescription.getText().toString();

                appendText(description);// this is for testing the entry.

            }
        });

        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.options,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        textDescription.setText(text);

       // Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();
    }// end of Onitemselected

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }// end of onNothing
    public void appendText(String text){
        Toast.makeText(helpActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}//end of helpactivity cladd
//<Spinner
//        android:id="@+id/spinner1"
//                android:layout_width="wrap_content"
//                android:layout_height="wrap_content"
//                android:layout_marginTop="100dp"
//                android:layout_marginEnd="125dp"
//                app:layout_constraintBottom_toTopOf="@+id/textInputLayout2"
//                app:layout_constraintEnd_toEndOf="parent"
//                app:layout_constraintStart_toStartOf="parent"
//                app:layout_constraintTop_toBottomOf="@+id/HelpSupportText" />