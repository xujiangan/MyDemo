package pojo;



import java.io.Serializable;
import java.util.Date;

public class Spittle implements Serializable {
    private static final long serialVersionUID = -4023409644611464259L;

    //交换机
    private long id;

    private String message;

    private Date postTime;

    //路由
    private Spittle spittle;

    public Spittle(long id, Spittle spittle, String message, Date postTime) {
        this.id = id;
        this.spittle = spittle;
        this.message = message;
        this.postTime = postTime;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Spittle getSpittle() {
        return spittle;
    }

    public void setSpittle(Spittle spittle) {
        this.spittle = spittle;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Spittle{");
        sb.append("id=").append(id);
        sb.append(", message='").append(message).append('\'');
        sb.append(", postTime=").append(postTime);
        sb.append(", spittle=").append(spittle);
        sb.append('}');
        return sb.toString();
    }
}
