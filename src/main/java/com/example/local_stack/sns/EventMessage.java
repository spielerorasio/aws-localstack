package com.example.local_stack.sns;

import java.util.Objects;

public class EventMessage {
    private String msgBody;

    public EventMessage() { }

    public EventMessage(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventMessage that = (EventMessage) o;
        return Objects.equals(msgBody, that.msgBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msgBody);
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "msgBody='" + msgBody + '\'' +
                '}';
    }
}
