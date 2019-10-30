package com.example.inclass09httpconnection.utils;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class EmailPO implements Serializable {

    @JSONField(name = "id")
    String emailId;
    @JSONField(name = "sender_id")
    String senderId;
    @JSONField(name = "message")
    String message;
    @JSONField(name = "created_at")
    String createdAt;
    @JSONField(name = "subject")
    String subject;
    @JSONField(name = "sender_fname")
    String senderFName;
    @JSONField(name = "sender_lname")
    String senderLName;


    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public EmailPO() {

    }

    public EmailPO(String emailId, String senderId, String message, String createdAt, String subject) {
        this.emailId = emailId;
        this.senderId = senderId;
        this.message = message;
        this.createdAt = createdAt;
        this.subject = subject;
    }
}
