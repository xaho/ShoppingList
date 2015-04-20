package com.xaho.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    SimpleCursorAdapter mAdapter;
    private ShoppingList template = new ShoppingList();
    private ArrayList<ShoppingList> shoppingList = new ArrayList<>();
    private String tag = "shoppinglist";
    private ShoppingListAdapter shoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        template.Name = "Template";

        ListView lv = (ListView)findViewById(R.id.listView);
        shoppingListAdapter = new ShoppingListAdapter(MainActivity.this,shoppingList);

        lv.setAdapter(shoppingListAdapter);
        /*lv.setClickable(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(tag,"Pressed on position: " + position);
                Intent intent = new Intent(getApplicationContext(),ShowShoppingListActivity.class);
                intent.putExtra("shoppinglist",shoppingList.get(position));
                startActivity(intent);
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editButton:
                Log.i(tag,"Edit template");
                Intent intent = new Intent(getApplicationContext(),ShowShoppingListActivity.class);
                intent.putExtra("shoppinglist",template);
                //startActivity(intent);
                startActivityForResult(intent,1337);
                break;
            case R.id.cloneButton:
                Log.i(tag,"Clone template");
                shoppingList.add(template);
                shoppingListAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1337){
            //started activity for template
            Log.i(tag,"Received template from activity");
            template = (ShoppingList)data.getSerializableExtra("shoppinglist");
        } else if (resultCode == Activity.RESULT_OK) {
            //started activity for shoppingList.get(requestCode)
            Log.i(tag,"Received shoppinglist " + requestCode + " from activity");
            shoppingList.set(requestCode,(ShoppingList)data.getSerializableExtra("shoppinglist"));
        }

    }
}
