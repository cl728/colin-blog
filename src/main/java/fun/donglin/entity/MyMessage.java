package fun.donglin.entity;

public class MyMessage extends Message {

    private Long parentMessageId;

    public Long getParentMessageId() {
        return parentMessageId;
    }

    public void setParentMessageId(Long parentMessageId) {
        this.parentMessageId = parentMessageId;
    }

    @Override
    public String toString() {
        return super.toString() + "MyMessage{" +
                ", parentMessageId=" + parentMessageId +
                '}';
    }
}
