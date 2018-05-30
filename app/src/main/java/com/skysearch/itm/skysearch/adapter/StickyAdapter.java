package com.skysearch.itm.skysearch.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.skysearch.itm.skysearch.DTO.DTO_SCHD;
import com.skysearch.itm.skysearch.adapter.contract.StickyAdapterContract;
import com.skysearch.itm.skysearch.adapter.holder.SchdItemViewHolder;
import com.skysearch.itm.skysearch.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class StickyAdapter extends BaseAdapter implements StickyListHeadersAdapter, StickyAdapterContract.View, StickyAdapterContract.Model{

    private Context context;
    private OnItemClickListener onItemClickListener;
    private ArrayList<DTO_SCHD> schdItems;

    public StickyAdapter(Context context, ArrayList items){
        this.context = context;
        this.schdItems= items;
    }



    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public long getHeaderId(int position) {
        return 0;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public void addItems(ArrayList<DTO_SCHD> items) {
        this.schdItems = items;
    }

    @Override
    public void clearItem() {
        if(schdItems != null){
            schdItems.clear();
        }
    }

    @Override
    public DTO_SCHD getItem(int position) {
        return schdItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void setOnClickListener(OnItemClickListener clickListener) {

    }

    @Override
    public void notifyAdapter() {

    }
}
