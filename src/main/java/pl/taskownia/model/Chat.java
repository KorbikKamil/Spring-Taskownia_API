package pl.taskownia.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.taskownia.serializer.ChatSerializer;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chat")
@JsonSerialize(using = ChatSerializer.class)
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chat")
    private Long id;
    @Column(nullable = true)
    private String message;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    @Temporal(value= TemporalType.TIMESTAMP)
    private Date date;

    public Chat() {
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
