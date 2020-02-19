package com.example.findingbook.filterViews.apartmentsView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.findingbook.R;
import com.example.findingbook.dataLayer.OurAddress;

import java.util.List;

public class ApartmentsRVAdapter extends RecyclerView.Adapter<ApartmentsRVAdapter.ViewHolder> {

    private List<OurAddress> mData;
    private LayoutInflater mInflater;
    private ApartmentsRVAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    ApartmentsRVAdapter(Context context, List<OurAddress> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ApartmentsRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rvapartments_row, parent, false);
        return new ApartmentsRVAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ApartmentsRVAdapter.ViewHolder holder, int position) {
        String address2 = mData.get(position).getAddress2();
        holder.myTextView.setText(address2);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        else
            return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.singleApartment);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    OurAddress getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ApartmentsRVAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
