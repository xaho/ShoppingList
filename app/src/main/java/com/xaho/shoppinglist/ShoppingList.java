package com.xaho.shoppinglist;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Xaho on 4/18/2015.
 */
public class ShoppingList implements Serializable{
    String Name = "Shopping list";
    ArrayList<String> items = new ArrayList<>();
}
