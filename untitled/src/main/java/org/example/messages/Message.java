package org.example.messages;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Message {
    private String idFrom;
    private String idTo;
    private String text;
    private Timestamp sendTime;


    public Message(String idFrom, String idTo, String text, Timestamp sendTime) {
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.text = text;
        this.sendTime = sendTime;
    }

    public Message(String idFrom, String idTo, String text) {
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.text = text;
        this.sendTime = Timestamp.valueOf(LocalDateTime.now());
    }

    public String getIdFrom() {
        return idFrom;
    }

    public String getIdTo() {
        return idTo;
    }

    public String getText() {
        return text;
    }

    public String getSendTime() {
        return sendTime.toString().substring(0,16);
    }
}
