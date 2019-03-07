package com.example.jakobwilbrandt.chatt.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.jakobwilbrandt.chatt.DataClasses.IMessage;
import com.example.jakobwilbrandt.chatt.serverFactory.FirebaseServerFactory;
import com.example.jakobwilbrandt.chatt.serverFactory.IServerFactory;
import com.example.jakobwilbrandt.chatt.serverFactory.IUserHandling;
import com.example.jakobwilbrandt.chatt.serverFactory.ServerProducer;

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

    private String photoUrl;
    private boolean isOwnMessage = false;

    //Determines if sender or receiver
    private final int VIEW_TYPE_MESSAGE_SENT = 0;
    private final int VIEW_TYPE_MESSAGE_RECEIVED = 1;
    private final int VIEW_TYPE_LOADING  = 2;

    //getting the context and messages list with constructor
    public MessageAdapter(Context context, List<IMessage> messageList) {
        this.context = context;
        this.messageList = messageList;
        IServerFactory serverFactory = new FirebaseServerFactory();
        IUserHandling userHandling = serverFactory.CreateUserHandler();
        photoUrl = userHandling.getAvatarUrl();
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
            isOwnMessage = true;
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_item_sent, parent, false);
            return new SentMessageViewHolder(view);
        }
        else{
            isOwnMessage = false;
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_item_received, parent, false);
            return new ReceivedMessageViewHolder(view);
        }


    }





    // Determines the appropriate ViewType according to the sender of the message or loading view.
    @Override
    public int getItemViewType(int position) {
        IMessage message = messageList.get(position);




        IServerFactory serverFactory = ServerProducer.getFactory("firebase");
        IUserHandling userHandling = serverFactory.CreateUserHandler();
        String currentUserId = userHandling.getCurrentUserId();
        if(message.getSenderId().equals(currentUserId)) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        }
        else{    // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        IMessage message = messageList.get(holder.getAdapterPosition());

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageViewHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageViewHolder) holder).bind(message);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }


    private class SentMessageViewHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;
        ImageView avatarOwn;

        public SentMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.sent_text);
            timeText = itemView.findViewById(R.id.sent_sender_time);
            avatarOwn = itemView.findViewById(R.id.avatar_sent_message);



        }

        void bind(IMessage message) {
            messageText.setText(message.getMessageContent());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getTimeOfMessage());
            //TODO: set sender name as well
            Glide.with(context).load(photoUrl).into(avatarOwn);
        }
    }

    private class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        ImageView avatarOther;
        TextView timeText;

        public ReceivedMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.received_text);
            timeText = itemView.findViewById(R.id.received_sender_time);
            avatarOther = itemView.findViewById(R.id.avatar_received_message);

        }

        void bind(IMessage message) {
            messageText.setText(message.getMessageContent());
            IServerFactory serverFactory = ServerProducer.getFactory("firebase");
            IUserHandling userHandling = serverFactory.CreateUserHandler();
            String currentUserId = userHandling.getCurrentUserId();
            if (message.getSenderId().equals(currentUserId)){
            Glide.with(context).load(photoUrl).into(avatarOther);
            }
            timeText.setText(message.getTimeOfMessage());
            //TODO: set sender name as well
        }
    }


}
