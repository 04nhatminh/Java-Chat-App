package dto;

public class Message {
    private String sender;
    private String receiver;
    private String content;
    private String createdAt;

    public Message(String sender, String receiver, String content, String createdAt) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.createdAt = createdAt;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}