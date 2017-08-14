package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private int itemPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //get the passed in values
        String itemText = getIntent().getStringExtra("itemText");
        itemPos = getIntent().getIntExtra("pos",0);

        //assign the value to the multiline  text box
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(itemText);
        etEditItem.setSelection(etEditItem.getText().length());

    }

    public void onSave(View v)
    {
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result

        data.putExtra("editText", etEditItem.getText().toString());
        data.putExtra("itemPos", itemPos);
        // Activity finished ok, return the data
        setResult(200, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
