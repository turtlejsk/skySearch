package com.skysearch.itm.skysearch.GridLayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.skysearch.itm.skysearch.ListLayout.Program;
import com.skysearch.itm.skysearch.R;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<Program> mItems;
    private int progress;

    private int lastPosition = -1; // Allows to remember the last item shown on screen


    public GridAdapter(ArrayList items, int progress, Context mContext) {
        mItems = items;
        this.progress = progress;
        context = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.image.setImageResource(mItems.get(position).getImage());
        holder.progressBar.setProgress(progress);
        holder.channelText.setText(mItems.get(position).getChannel());
        holder.titleText.setText(mItems.get(position).getTitle());

        setAnimation(holder.image, position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private void setAnimation(View viewToAnimate, int position) { // 새로 보여지는 뷰라면 애니메이션을 해줍니다
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ImageView image;
        public TextView channelText, titleText;

        public ViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.grid_image);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

            channelText = (TextView) view.findViewById(R.id.grid_channel_textview);
            titleText = (TextView) view.findViewById(R.id.grid_title_textview);
        }
}
}
