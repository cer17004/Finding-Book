package com.example.findingbook.filterViews.addressesView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.findingbook.R;
import com.example.findingbook.dataLayer.OurAddress;

import java.util.List;

public class AddressesRVAdapter extends RecyclerView.Adapter<AddressesRVAdapter.ViewHolder> {

        private List<OurAddress> mData;
        private LayoutInflater mInflater;
        private AddressesRVAdapter.ItemClickListener mClickListener;

        // data is passed into the constructor
        AddressesRVAdapter(Context context, List<OurAddress> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // inflates the row layout from xml when needed
        @Override
        public AddressesRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.rvaddresses_row, parent, false);
            return new AddressesRVAdapter.ViewHolder(view);
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(AddressesRVAdapter.ViewHolder holder, int position) {
            String address1 = mData.get(position).getAddress1();
            String[] splitAddress1 = address1.split(" ");
            holder.myTextView.setText(splitAddress1[0]);
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
                myTextView = itemView.findViewById(R.id.singleAddress);
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
        void setClickListener(AddressesRVAdapter.ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

        // parent activity will implement this method to respond to click events
        public interface ItemClickListener {
            void onItemClick(View view, int position);
        }
}
