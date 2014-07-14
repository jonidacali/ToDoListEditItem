package com.codepath.example.todolist1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ToDoActivity extends Activity {
	final Context context = this;
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	public static final String ITEM_TO_EDIT= "item";
	public static final String ITEM_POSITION= "position";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do);
		
		lvItems = (ListView) findViewById(R.id.lvItemList);
		items = new ArrayList<String> ();
		readItems();
		itemsAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		items.add("First item");
		items.add("Second item");
		setupViewListener();
	}
	
	public void addTodoItem(View v){
		EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
		saveItems();
	}
	
	private void setupViewListener(){
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, 
					View view, int position, long rowId){
				items.remove(position);
				itemsAdapter. notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
		
		lvItems.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long rowId) {
				String item = ((String) parent.getItemAtPosition(position).toString());
				//TextView item = (TextView) parent.getItemAtPosition(position);
				
				Intent i = new Intent(context,ToDoEditActivity.class);
				i.putExtra(ITEM_TO_EDIT, item);
				i.putExtra(ITEM_POSITION, position);
				startActivityForResult(i, 2);
			}
		});
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data){  
		super.onActivityResult(requestCode, resultCode, data);                    
	    if(requestCode==2) {  
	    	String updatedItem=data.getStringExtra(ITEM_TO_EDIT);
	    	
	    	items.remove(data.getExtras().getInt(ITEM_POSITION));
	    	items.add(data.getExtras().getInt(ITEM_POSITION), updatedItem);
	    	itemsAdapter.notifyDataSetChanged();
			saveItems();
			Toast.makeText(context, R.string.success, Toast.LENGTH_SHORT).show();
	        }  
	}  
	
	private void readItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try{
			items = new ArrayList<String> (FileUtils.readLines(todoFile));
		} catch (IOException e){
			items = new ArrayList<String>();
			e.printStackTrace();
		}
		
	}
	
	private void saveItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try{
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}
}
