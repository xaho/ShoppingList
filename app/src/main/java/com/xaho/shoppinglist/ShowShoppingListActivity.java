package com.xaho.shoppinglist;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Xaho on 4/18/2015.
 */
public class ShowShoppingListActivity extends Activity implements View.OnDragListener {

    private ShoppingList shoppingList = new ShoppingList();
    private ShoppingListItemAdapter shoppingListItemAdapter;
    private String tag = "ShowShoppingListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        shoppingList = (ShoppingList)intent.getSerializableExtra("shoppinglist");
        setContentView(R.layout.shoppinglist);
        ListView lv = (ListView)findViewById(R.id.shoppinglistListView);
        shoppingListItemAdapter = new ShoppingListItemAdapter(getApplicationContext(),shoppingList.items);
        lv.setAdapter(shoppingListItemAdapter);
        lv.setOnDragListener(this);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ClipData cp = ClipData.newPlainText("dragShoppingListItem","reorder");
                View.DragShadowBuilder sb = new View.DragShadowBuilder(view);
                view.startDrag(cp,sb,view,0);
                return true;
            }
        });

        View view = findViewById(R.id.emptyItem);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ClipData cp = ClipData.newPlainText("dragShoppingListItem", "new");
                View.DragShadowBuilder sb = new View.DragShadowBuilder(v);
                v.startDrag(cp,sb,v,0);
                return true;
            }
        });
        view.setOnDragListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent result = new Intent("com.xaho.shoppinglist", Uri.parse("content://result_uri"));
        result.putExtra("shoppinglist",shoppingList);
        setResult(Activity.RESULT_OK,result);
        finish();
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        ///Log.i(tag,"");
        if (event.getAction() == DragEvent.ACTION_DROP & v.getClass().getCanonicalName().equals(ListView.class.getCanonicalName())) {
            if (event.getClipData().toString().contains("new")) {
                shoppingList.items.add("Item " + shoppingList.items.size());
                shoppingListItemAdapter.notifyDataSetChanged();
            } else {
                //reorder
            }
            return true;
        }
        else if (event.getAction() == DragEvent.ACTION_DRAG_STARTED)
            return true;
        return false;
    }
}
