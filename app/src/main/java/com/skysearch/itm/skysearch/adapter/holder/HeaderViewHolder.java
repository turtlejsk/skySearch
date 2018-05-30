package com.skysearch.itm.skysearch.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    private TextView text;

    public HeaderViewHolder(View itemView) {
        super(itemView);
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }
}
