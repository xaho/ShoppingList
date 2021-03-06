package com.xaho.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Xaho on 4/18/2015.
 */
public class ShoppingListItemAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> shoppingList;
    private String tag = "ShoppingListItemAdapter";
    LayoutInflater li;

    public ShoppingListItemAdapter(Context context, ArrayList<String> shoppingList) {
        super(context, R.layout.shopping_list_item, shoppingList);
        this.shoppingList = shoppingList;
        this.context = context;
        li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder {
        public CheckBox checkBox;
        public EditText editText;
        public EditText editTextextra;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = null;
        if (convertView != null)
            rowView = convertView;
        else {
            rowView = li.inflate(R.layout.shopping_list_item, parent, false);
            ViewHolder holder = new ViewHolder();

            rowView.setTag(holder);
        }
        ViewHolder tag = (ViewHolder) rowView.getTag();

        return rowView;

        //return li.inflate(R.layout.shopping_list_item,parent,false);
        //EditText et = (EditText)rowView.findViewById(R.id.editText);
        //et.setText(shoppingList.get(position));
       /* et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Log.i(tag, v.getClass().getCanonicalName());
                    if (v.getClass().getCanonicalName().equals(EditText.class.getCanonicalName())) {
                        Log.i(tag, "Saving text");
                        shoppingList.set(position,((EditText)v).getText().toString());
                        notifyDataSetChanged();
                    }
                }
            }
        });*/
    }
}
