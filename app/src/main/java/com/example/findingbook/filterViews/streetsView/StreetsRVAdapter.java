package com.example.findingbook.filterViews.streetsView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.findingbook.R;

import java.util.List;

public class StreetsRVAdapter extends RecyclerView.Adapter<StreetsRVAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private StreetsRVAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    StreetsRVAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rvstreets_row, parent, false);
        return new StreetsRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(StreetsRVAdapter.ViewHolder holder, int position) {
        String streetName = mData.get(position);
        holder.myTextView.setText(streetName);
    }

    @Override
    public int getItemCount() { return mData.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.singleStreet);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) { return mData.get(id); }

    // allows clicks events to be caught
    void setClickListener(StreetsRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
