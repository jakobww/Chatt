package com.example.jakobwilbrandt.chatt.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jakobwilbrandt.chatt.DataClasses.IMessage;
import com.example.jakobwilbrandt.chatt.DataClasses.IRoom;
import com.example.jakobwilbrandt.chatt.DataClasses.IUser;
import com.example.jakobwilbrandt.chatt.R;
import com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory.IServerFactory;
import com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory.IUserHandling;
import com.example.jakobwilbrandt.chatt.ServerHandling.ServerFactory.ServerProducer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */


public class MessageAdapter extends RecyclerView.Adapter {


    //this context we will use to inflate the layout
    private Context context;

    //we are storing all the messages in a list
    private List<IMessage> messageList;

    //Determines if sender or receiver
    private final int VIEW_TYPE_MESSAGE_SENT = 0;
    private final int VIEW_TYPE_MESSAGE_RECEIVED = 1;

    //getting the context and messages list with constructor
    public MessageAdapter(Context context, List<IMessage> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    public void refreshMessages(ArrayList<IMessage> Messages) {
        this.messageList.clear();
        this.messageList.addAll(Messages);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder

        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_item_sent, parent, false);
            return new SentMessageViewHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_item_received, parent, false);
            return new ReceivedMessageViewHolder(view);
        }

        return null;
    }





    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        IMessage message = messageList.get(position);

        IServerFactory serverFactory = ServerProducer.getFactory("firebase");
        IUserHandling userHandling = serverFactory.CreateUserHandler();
        String currentUserId = userHandling.getCurrentUserId();
        if (message.getSenderId().equals(currentUserId)) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        IMessage message = messageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageViewHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageViewHolder) holder).bind(message);
        }
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }


    class SentMessageViewHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;
        ImageView avatar;

        public SentMessageViewHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.room_item_name);
            timeText = itemView.findViewById(R.id.sent_sender_time);
            avatar = itemView.findViewById(R.id.chevron_icon);



        }

        void bind(IMessage message) {
            messageText.setText(message.getMessageContent());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getTimeOfMessage());
            //TODO: set sender name as well
        }
    }

    class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        ImageView avatar;
        TextView timeText;

        public ReceivedMessageViewHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.received_text);
            timeText = itemView.findViewById(R.id.received_sender_time);
            avatar = itemView.findViewById(R.id.avatar_received_message);


        }

        void bind(IMessage message) {
            messageText.setText(message.getMessageContent());


            timeText.setText(message.getTimeOfMessage());
            //TODO: set sender name as well
        }
    }
}
