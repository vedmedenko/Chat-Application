package com.vedmedenko.chat.core;

import android.support.annotation.NonNull;

public class Message {

    private String nickname;
    private String text;

    public Message() {
    }

    public Message(String nickname, String text) {
        this.nickname = nickname;
        this.text = text;
    }

    public void setNickname(@NonNull String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
