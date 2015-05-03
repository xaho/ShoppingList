package com.xaho.shoppinglist;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ListActivity {
    private ShoppingList template = new ShoppingList();
    private ArrayList<ShoppingList> shoppingList = new ArrayList<>();
    private String tag = "shoppinglist";
    private ShoppingListAdapter shoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        template.Name = "Template";

        shoppingListAdapter = new ShoppingListAdapter(MainActivity.this,shoppingList);

        ListView lv = getListView();
        setListAdapter(shoppingListAdapter);

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        lv,
                        new SwipeDismissListViewTouchListener.OnDismissCallback() {
                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    shoppingListAdapter.remove(shoppingListAdapter.getItem(position));
                                }
                                shoppingListAdapter.notifyDataSetChanged();
                            }
        });

        lv.setOnTouchListener(touchListener);
        lv.setOnScrollListener(touchListener.makeScrollListener());
        //lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(tag,"Pressed on position: " + position);
                Intent intent = new Intent(getApplicationContext(),ShowShoppingListActivity.class);
                intent.putExtra("shoppinglist",shoppingList.get(position));
                //startActivity(intent);
                startActivityForResult(intent,position);
            }
        });
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
