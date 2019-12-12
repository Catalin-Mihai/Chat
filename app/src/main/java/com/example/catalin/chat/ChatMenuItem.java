package com.example.catalin.chat;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class ChatMenuItem {

    private int layout_resource_id;
    //private Node<View> viewsTree;
    private ItemHandler itemHandler;

    ChatMenuItem(int layout_resource_id, ItemHandler itemHandler)
    {
        this.layout_resource_id = layout_resource_id;
        this.itemHandler = itemHandler;
        //viewHolder = null;
        //viewsTree = null;
        //viewHolder = new MenuItemViewHolder(views);
    }

    public int getLayout_resource_id() {
        return layout_resource_id;
    }

    public void setLayout_resource_id(int layout_resource_id) {
        this.layout_resource_id = layout_resource_id;
    }

    public ItemHandler getItemHandler() {
        return itemHandler;
    }

    public void setItemHandler(ItemHandler itemHandler) {
        this.itemHandler = itemHandler;
    }

    /*public void computeViewsTree(View v){

        viewsTree = getViewsTree(v);
    }

    private static Node<View> getViewsTree(View v) {

        if (!(v instanceof ViewGroup)) { //has no children
            Node<View> parent = new Node<View>(v);
            return parent;
        }

        Node<View> parent = new Node<View>(v);

        ViewGroup vg = (ViewGroup) v; //Safe cast. Treated in the beginning of the function
        for (int i = 0; i < vg.getChildCount(); i++) {

            View childView = vg.getChildAt(i);

           // ArrayList<View> viewArrayList = new ArrayList<View>();
           // viewArrayList.add(v);
           // viewArrayList.addAll(getAllChildren(child));

           Node<View> child = getViewsTree(childView); //the child is another parent with his children or just a parent with no children
           parent.addChild(child); //append the processed child to the parent
        }

        return parent;
    }

    public Node<View> getViewsTree() {
        return viewsTree;
    }

    public void setViewsTree(Node<View> viewsTree) {
        this.viewsTree = viewsTree;
    }*/
}

