package com.sadicomputing.foodordering.adapter;

import android.support.v7.widget.RecyclerView;

import com.sadicomputing.foodordering.entity.CommandeArticle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by modykane on 21/12/2017.
 */

public abstract class ServeurCommandesdujourAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    private ArrayList<CommandeArticle> items = new ArrayList<CommandeArticle>();

    public ServeurCommandesdujourAdapter() {
        setHasStableIds(true);
    }

    public void add(CommandeArticle object) {
        items.add(object);
        notifyDataSetChanged();
    }

    public void add(int index, CommandeArticle object) {
        items.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends CommandeArticle> collection) {
        if (collection != null) {
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void addAll(CommandeArticle... items) {
        addAll(Arrays.asList(items));
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(String object) {
        items.remove(object);
        notifyDataSetChanged();
    }

    public CommandeArticle getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}