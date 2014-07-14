package com.codepath.example.todolist1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ToDoEditActivity extends Activity {
	EditText itemToBeEdited; 
    Button updateButton;  
	public static final String ITEM_TO_EDIT= "item";
	public static final String ITEM_POSITION= "position";
	int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		setTitle(R.string.edit_label);
		String itemValue =  getIntent().getExtras().getString(ITEM_TO_EDIT);
		
		itemToBeEdited = (EditText) findViewById(R.id.todoItem);	
        updateButton=(Button)findViewById(R.id.btnUpdateItem);  		
        itemToBeEdited.setText(String.valueOf(itemValue));
        position = getIntent().getExtras().getInt(ITEM_POSITION);
	}
	
	public void editTodoItem(View v){
		String editedItem=itemToBeEdited.getText().toString();  
        Intent returnIntent=new Intent();  
        returnIntent.putExtra("item",editedItem);
        returnIntent.putExtra("position", position);
          
        setResult(2,returnIntent);  
          
        finish();//finishing activity  

	}
//	
	
}
