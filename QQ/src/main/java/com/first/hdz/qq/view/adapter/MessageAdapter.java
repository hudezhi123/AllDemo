package com.first.hdz.qq.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.first.hdz.qq.R;
import com.first.hdz.qq.bean.MessageBean;
import com.first.hdz.qq.view.customview.CircleImageView;

import java.util.List;

/**
 * created by hdz
 * on 2018/9/18
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MessageBean> messageList;

    public MessageAdapter(Context context, List<MessageBean> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(this.context).inflate(R.layout.message_item, viewGroup, false);
        return new MessageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        MessageBean message = messageList.get(position);
        if (viewHolder instanceof MessageHolder) {
            ((MessageHolder) viewHolder).messageTime.setText(message.getTime());
            ((MessageHolder) viewHolder).messageTitle.setText(message.getTitle());
            ((MessageHolder) viewHolder).messageContent.setText(message.getContent());
            ((MessageHolder) viewHolder).messageSpeaker.setText(message.getSpeaker());
            ((MessageHolder) viewHolder).messageIcon.setImageResource(message.getImgResId());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    private class MessageHolder extends RecyclerView.ViewHolder {
        CircleImageView messageIcon;
        TextView messageTitle;
        TextView messageSpeaker;
        TextView messageContent;
        TextView messageTime;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            messageIcon = itemView.findViewById(R.id.img_message_icon);
            messageTitle = itemView.findViewById(R.id.text_message_title);
            messageSpeaker = itemView.findViewById(R.id.text_message_people);
            messageContent = itemView.findViewById(R.id.text_message_content);
            messageTime = itemView.findViewById(R.id.text_message_time);
        }
    }
}
