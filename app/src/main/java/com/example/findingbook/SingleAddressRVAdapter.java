package com.example.findingbook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.findingbook.dataLayer.Person;

import java.util.List;

public class SingleAddressRVAdapter extends RecyclerView.Adapter<SingleAddressRVAdapter.ViewHolder> {

    private List<Person> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    SingleAddressRVAdapter(Context context, List<Person> mData) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    // inflates the row layout from xml when needed
    @Override
    public SingleAddressRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rvperson_card, parent, false);
        return new SingleAddressRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleAddressRVAdapter.ViewHolder holder, int position) {
        Person person = mData.get(position);
        if (person != null) {
            String fullName = person.getFirstName() + " " + person.getLastName();
            holder.myTextViewFullName.setText(fullName);
            holder.myTextViewPhoneNumber.setText(String.valueOf(person.getPhone_number()));
            holder.myTextViewEmail.setText(person.getEmail());
            holder.myTextViewFacebook.setText(person.getFacebook());
            holder.addedToAreabook.setChecked(person.getMoved_to_areabook());

            switch (person.getStatus()) {
                case 0:
                    holder.myTextViewStatus.setText("Member");
                    break;
                case 1:
                    holder.myTextViewStatus.setText("Person Being Taught");
                    break;
                case 2:
                    holder.myTextViewStatus.setText("Person Not Being Taught");
                    break;
                case 3:
                    holder.myTextViewStatus.setText("Person Has Interest");
                    break;
                case 4:
                    holder.myTextViewStatus.setText("Soft & Closed Heart");
                    break;
                case 5:
                    holder.myTextViewStatus.setText("Hard & Closed Heart");
                    break;
                case 6:
                    holder.myTextViewStatus.setText("Uncontacted");
                    break;
                default:
                    break;
            }


                //holder.myTextViewNotes.setText(person.getNotes());

            //setAvailability code here
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        else
            return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextViewStatus, myTextViewFullName, myTextViewPhoneNumber, myTextViewEmail, myTextViewFacebook,
                myTextViewNotes;
        CheckBox addedToAreabook;

        ViewHolder(View itemView) {
            super(itemView);

            myTextViewStatus = itemView.findViewById(R.id.textViewStatus);
            myTextViewFullName = itemView.findViewById(R.id.textViewFullName);
            myTextViewPhoneNumber = itemView.findViewById(R.id.textViewPhoneNumber);
            myTextViewEmail = itemView.findViewById(R.id.textViewEmail);
            myTextViewFacebook = itemView.findViewById(R.id.textViewFacebook);
//            myTextViewNotes = itemView.findViewById(R.id.textViewNotes);
            addedToAreabook = itemView.findViewById(R.id.checkBoxAddedToAreaBook);
        }
    }

    // convenience method for getting data at click position
    Person getItem(int id) {
        return mData.get(id);
    }

}
