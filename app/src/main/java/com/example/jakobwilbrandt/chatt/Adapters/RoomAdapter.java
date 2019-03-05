package com.example.jakobwilbrandt.chatt.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jakobwilbrandt.chatt.DataClasses.IRoom;
import com.example.jakobwilbrandt.chatt.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */


public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {


    //this context we will use to inflate the layout
    private Context context;

    //we are storing all the rooms in a list
    private ArrayList<IRoom> roomList;

    //Click listener for reacting to clicks in activities
    private View.OnClickListener clickListener;


    //getting the context and rooms list with constructor
    public RoomAdapter(Context context, ArrayList<IRoom> roomList, View.OnClickListener clickListener) {
        this.context = context;
        this.roomList = roomList;
        this.clickListener = clickListener;
    }

    public void refreshRooms(ArrayList<IRoom> Rooms) {
        this.roomList.clear();
        this.roomList.addAll(Rooms);
        notifyDataSetChanged();
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.room_item, null);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        //getting the room of the specified position
        IRoom room = roomList.get(position);

        //binding the data with the viewholder views
        holder.RoomNameTxt.setText(room.getName());

        holder.chevronIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_chevron));

        //TODO: set on click listener

    }


    @Override
    public int getItemCount() {
        return roomList.size();
    }


    class RoomViewHolder extends RecyclerView.ViewHolder {

        TextView RoomNameTxt;
        ImageView chevronIcon;

        public RoomViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(this);
            itemView.setOnClickListener(clickListener);
            RoomNameTxt = itemView.findViewById(R.id.room_item_name);
            chevronIcon = itemView.findViewById(R.id.chevron_icon);

            //TODO: set last message sent time

        }




    }



}
