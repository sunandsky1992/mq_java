package Queue;

import java.util.Date;

/**
 * Created by ss on 16-3-29.
 */
public class Message {
    private byte[] content;

    private Date date;

    int tag;

    public Message(byte[] content,int tag) {
        this.content = content;
        this.tag = tag;
        date = new Date();
    }

    public byte[] getContent(){
        return content;
    }

    public int getTag() {
        return tag;
    }
}
