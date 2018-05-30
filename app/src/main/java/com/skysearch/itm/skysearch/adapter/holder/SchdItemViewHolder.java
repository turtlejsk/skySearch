package com.skysearch.itm.skysearch.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.skysearch.itm.skysearch.DTO.DTO_SCHD;
import com.skysearch.itm.skysearch.listener.OnItemClickListener;

import butterknife.ButterKnife;

public class SchdItemViewHolder {
    public Context context;
    public TextView timeText;
    public TextView titleText;
    public Button reserveButton;
    private OnItemClickListener onItemClickListener;


    public SchdItemViewHolder(Context context, ViewGroup parent, OnItemClickListener onItemClickListener, int resource) {
        LayoutInflater.from(context).inflate(resource,parent,false);
        this.context= context;
        this.onItemClickListener= onItemClickListener;

    }
//    public void onBind(DTO_SCHD item, final int position){
//        itemView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                if (onItemClickListener != null){
//                    onItemClickListener.onItemClick(position);
//                }
//            }
//        });
//
//    }
}
