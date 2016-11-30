package com.vedmedenko.chat.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.vedmedenko.chat.R;
import com.vedmedenko.chat.core.Message;
import com.vedmedenko.chat.utils.ConstantsManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageAdapter extends FirebaseRecyclerAdapter<Message, MessageAdapter.MessageHolder> {

    @Inject
    public MessageAdapter(@NonNull DatabaseReference databaseReference) {
        super(Message.class, R.layout.chat_item, MessageHolder.class, databaseReference.child(ConstantsManager.COLLECTION_NAME));
    }

    @Override
    protected void populateViewHolder(MessageHolder viewHolder, Message model, int position) {
        viewHolder.setMessage(model.getNickname(), model.getText());
    }

    public static class MessageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.message)
        TextView message;

        public MessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setMessage(String nickname, String text) {
            message.setText(nickname + ": " + text);
        }
    }
}
