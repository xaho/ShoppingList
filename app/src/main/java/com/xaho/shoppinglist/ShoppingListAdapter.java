package com.xaho.shoppinglist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Xaho on 4/18/2015.
 */
public class ShoppingListAdapter extends ArrayAdapter<ShoppingList> {
    private String tag = "ShoppingListAdapter";
    private final Context context;
    private final ArrayList<ShoppingList> shoppingList;

    public ShoppingListAdapter(Context context, ArrayList<ShoppingList> shoppingList) {
        super(context, R.layout.shopping_list_entry, shoppingList);
        this.shoppingList = shoppingList;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = li.inflate(R.layout.shopping_list_entry,parent,false);

        /*rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Pressed on position: " + position);
                Intent intent = new Intent(context,ShowShoppingListActivity.class);
                intent.putExtra("shoppinglist",shoppingList.get(position));
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ((MainActivity)context).startActivityForResult(intent,position);
            }
        });*/
        TextView tv = (TextView)rowView.findViewById(R.id.listEntryTextView);
        tv.setText(shoppingList.get(position).Name);
        return rowView;
    }

    public void removeSure(ShoppingList object) {
        super.remove(object);
    }

    @Override
    public void remove(final ShoppingList object) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        removeSure(object);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Delete shopping list?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
        //super.remove(object);
    }
}
