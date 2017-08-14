package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find the listitem
        lvItems = (ListView) findViewById(R.id.lvItems);
        //Attach adapter to ListView
        //items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();

    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?>adapter,View item, int pos, long id){
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }

        );
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent i = new Intent(MainActivity.this,EditItemActivity.class);
                        i.putExtra("itemText",  items.get(position).toString());
                        i.putExtra("pos", position);
                        startActivityForResult(i,REQUEST_CODE);
                    }


                }
        );





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     //   Toast.makeText(this,resultCode,Toast.LENGTH_SHORT).show();

        // REQUEST_CODE is defined above
        if (resultCode == 200 && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String editText = data.getExtras().getString("editText");
            int itemPos = data.getExtras().getInt("itemPos", 0);

            //change the text on the listview to the edited text
            items.set(itemPos,editText.toString());
          //  lvItem = itemsAdapter.getItem(itemPos);
            itemsAdapter.notifyDataSetChanged();
         //   Toast.makeText(this,editText,Toast.LENGTH_SHORT).show();
            writeItems();
        }
    }


    public void onAddItem(View v)
    {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add (itemText);
        etNewItem.setText("");
        writeItems();
    }

    private void readItems()
    {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch (IOException e){
            items = new ArrayList<String>();
        }
    }
    private void writeItems()
    {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(todoFile,items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
